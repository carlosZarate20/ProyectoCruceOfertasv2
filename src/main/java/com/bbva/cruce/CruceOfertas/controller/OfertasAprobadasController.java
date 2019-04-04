package com.bbva.cruce.CruceOfertas.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bbva.cruce.CruceOfertas.model.AplicacionOfertasBean;
import com.bbva.cruce.CruceOfertas.model.CargaCabecera;
import com.bbva.cruce.CruceOfertas.model.CargaOfertas;
import com.bbva.cruce.CruceOfertas.model.OfertasAprobadasPlantilla;
import com.bbva.cruce.CruceOfertas.model.RespuestaWS;
import com.bbva.cruce.CruceOfertas.service.OfertasAprobadasService;
import com.bbva.cruce.CruceOfertas.util.Constantes;

@RestController
public class OfertasAprobadasController {

	private static final Logger LOGGER = LogManager.getLogger(OfertasAprobadasController.class);
	@Autowired
	private OfertasAprobadasService ofertasAprobadasService;
	
	@RequestMapping(value="/carga/carga-ofertas", method = RequestMethod.POST, consumes = "multipart/form-data")
	public RespuestaWS<List<OfertasAprobadasPlantilla>> cargarArchivo(@RequestParam("file") MultipartFile file, HttpServletRequest  request){
		RespuestaWS<List<OfertasAprobadasPlantilla>> respuesta = new RespuestaWS<>();
		String userName = "";
		
		try {
			
			String contentType = file.getContentType();
			String fileName = file.getOriginalFilename();
			
			boolean is2003 = true;
			if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equalsIgnoreCase(contentType)) {
				is2003 = false;
			} else if ("application/vnd.ms-excel".equalsIgnoreCase(contentType)) {
				is2003 = true;
			} else if ("application/octet-stream".equalsIgnoreCase(contentType)) {
				is2003 = file.getOriginalFilename().endsWith(".xls");
			} else {
				throw new Exception("Tipo de archivo no admitido.");
			}
			
			byte[] bytes = file.getBytes();
			
			try(ByteArrayInputStream is = new ByteArrayInputStream(bytes);
					Workbook workbook = is2003 ? new HSSFWorkbook(is) : new XSSFWorkbook(is);){
				Sheet firstSheet = workbook.getSheetAt(0);

				int i = 0;
				List<CargaOfertas> listOfertas = new ArrayList<>();
				CargaOfertas item;
				boolean hasHeader = true;
				boolean allItemsOk = true;
				
				Iterator<Row> iterator = firstSheet.iterator();
				while(iterator.hasNext()) {
					Row row = iterator.next();
					++i;
					if(hasHeader &&  i == 1) {
						continue;
					}
					
					item = getItemFromRow(row);
					
					if(item == null) {
						continue;
					}
					
					item = validateItemOfertas(item);
					if(!item.isOk()) {
						allItemsOk = false;
					}
					listOfertas.add(item);
				}
				if(!allItemsOk) {
					respuesta.setEstado(Constantes.CODE_WRONG);
					respuesta.setMensajeFuncional("Algunos datos del excel no estan escritos correctamente.");
					return respuesta;
				}
				String identificador;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
				Date date = new Date();
				identificador = simpleDateFormat.format(date);
				
				List<AplicacionOfertasBean> ofertasAprobadasList = new ArrayList<>();
				for(CargaOfertas cargaBean: listOfertas) {
					AplicacionOfertasBean ofertasItem = new AplicacionOfertasBean();
					ofertasItem.setTipDoi(cargaBean.getTipDoi());
					ofertasItem.setDoi(cargaBean.getNumDoi());
					ofertasItem.setIdentificador(identificador);
					ofertasAprobadasList.add(ofertasItem);
				}
				
				userName = (String) request.getSession().getAttribute("username");
				if(userName == null) {
					userName = "Usuario_Test";
				}
				
				List<OfertasAprobadasPlantilla> listOfertaAprobada = new ArrayList<>();
				listOfertaAprobada = ofertasAprobadasService.guardarArchivo(ofertasAprobadasList, identificador, fileName, request, userName);
				if(listOfertaAprobada == null) {
					respuesta.setEstado(Constantes.CODE_WRONG);
					respuesta.setMensajeFuncional("Hubo un error al cargar el Archivo");
					respuesta.setMensajeTecnico("No se realizo el merge con exito");
					respuesta.setObjetoRespuesta(listOfertaAprobada);

				}else {
					respuesta.setEstado(Constantes.CODE_SUCCESSFUL);
					respuesta.setMensajeFuncional("Se Cargo el Archivo Correctamente");
					respuesta.setMensajeTecnico("Se realizo el merge con exito");
					respuesta.setObjetoRespuesta(listOfertaAprobada);
				}
			}
			
		} catch(Exception e) {
			respuesta.setEstado(Constantes.CODE_WRONG);
			respuesta.setMensajeFuncional("No se cargo el archivo");
			respuesta.setMensajeTecnico(e.getMessage());
			return respuesta;
		}
		return respuesta;
	}
	
	private CargaOfertas getItemFromRow(Row row) {
		CargaOfertas carga = new CargaOfertas();
		DataFormatter df = new DataFormatter();
		
		String cellTipoDoi = df.formatCellValue(row.getCell(0));
		String cellDoiValue = df.formatCellValue(row.getCell(1));
		if (cellDoiValue.isEmpty() && cellTipoDoi.isEmpty()) {
			return null;
		}
		
		carga.setTipDoi(cellTipoDoi.toUpperCase());
		carga.setNumDoi(cellDoiValue);
		carga.setIndex(row.getRowNum());
		return carga;
	}
	
	private CargaOfertas validateItemOfertas(CargaOfertas bean) {
		if(bean.getTipDoi() != null && bean.getTipDoi().contains("DNI")) {
			if(bean.getNumDoi() == null) {
				bean.setOk(false);
				bean.setDetalleError("Campo DNI no puede estar vacio");
				return bean;
			}else if(bean.getNumDoi().length() != 8) {
				String dni = bean.getNumDoi();
				int largo = 8; 
				int cantidad = largo - bean.getNumDoi().length();
				if(cantidad >= 1) {
					for(int i = 0; i<cantidad; i++) {
						dni = "0" + bean.getNumDoi();
						bean.setNumDoi(dni);
					}
				}	
			}
			
		}else if(bean.getTipDoi() != null && bean.getTipDoi().contains("RUC")) {
			if(bean.getNumDoi() == null || bean.getNumDoi().length() != 11) {
				bean.setOk(false);
				bean.setDetalleError("RUC del cliente incorrecto");
				return bean;
			}
		} else if(bean.getTipDoi() != null && bean.getTipDoi().contains("CE")) {
			if(bean.getNumDoi() == null) {
				bean.setOk(false);
				bean.setDetalleError("CE del cliente incorrecto");
				return bean;
			}else if(bean.getNumDoi().length() != 9) {
				String ce = bean.getNumDoi();
				int largo = 9; 
				int cantidad = largo - bean.getNumDoi().length();
				if(cantidad >= 1) {
					for(int i = 0; i<cantidad; i++) {
						ce = "0" + bean.getNumDoi();
						bean.setNumDoi(ce);
					}
				}	
			}
		} else {
			bean.setOk(false);
			bean.setDetalleError("Tipo de documento vacio o incorrecto");
			return bean;
		}
		bean.setOk(true);
		bean.setDetalleError("Ok");
		return bean;
	}
	
	@RequestMapping(value="/carga/exportar-ultima-Carga", method = RequestMethod.GET)	
	public void exportarArchivo(HttpServletResponse response, HttpServletRequest request) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		CargaCabecera cargaCabecera = new CargaCabecera();
		cargaCabecera = ofertasAprobadasService.getCargaUltima(request);
		String nombreArhivo = cargaCabecera.getNombreArchivo().substring(0, cargaCabecera.getNombreArchivo().length() - 5);
		
		String filename = cargaCabecera.getUsuario() + "_" + cargaCabecera.getFechaCarga() + "_" + nombreArhivo;
		String sheetName = cargaCabecera.getNombreArchivo();
		
		RespuestaWS<List<OfertasAprobadasPlantilla>> responseOfertas = ofertasAprobadasService.listaOfertasAprobadas(request);
		List<OfertasAprobadasPlantilla> listOfertas = responseOfertas.getObjetoRespuesta();
		
		try(SXSSFWorkbook wb = new SXSSFWorkbook(workbook)){
			wb.setCompressTempFiles(true);
			SXSSFSheet sh = wb.createSheet(sheetName);
			sh.trackAllColumnsForAutoSizing();
	        sh.setRandomAccessWindowSize(100);// keep 100 rows in memory, exceeding rows will be flushed to disk
	        Cell cell = null;
	        
	        List<String> headers = Arrays.asList("TIP_DOC", "DOI", "ID", "CODCENTRAL", "TIP_DOC_BASE", "CODDOC_BASE", "LINEA_VEHICULAR", "VEH_CUOTA",
					"REQUIERE_VERF_LABORAL", "REQUIERE_VERF_DOMICILIARIA", "SUELDO", "MARCA_PH", "TIENE_OTRA_OFERTA", "DOC_SINCEROS", 
					"PROCESO_RIESGOS", "FACTORD_VEHICULAR", "AUTO_2DA", "PLAZO_VEH_48", "PLAZO_VEH_60", "TIPO_RIESGO", "TIPO_CIENTE", "FLUJO_OPERATIVO");
	        
	        int rowNum = 0;
	        
	        //Header
	        SXSSFRow rowHead = sh.createRow(rowNum++);
	        for(int i=0; i< headers.size(); i++) {
	        	cell = rowHead.createCell(i);
	        	cell.setCellValue(headers.get(i));
	        }
	        
	        //Body
	        for(OfertasAprobadasPlantilla ofertasAprobadas: listOfertas) {
	        	SXSSFRow row = sh.createRow(rowNum++);
	        	cell = row.createCell(0);
	        	cell.setCellValue(ofertasAprobadas.getTipoDocumento());
	        	cell = row.createCell(1);
	        	cell.setCellValue(ofertasAprobadas.getDoi());
	        	cell = row.createCell(2);
	        	cell.setCellValue(ofertasAprobadas.getId());
	        	cell = row.createCell(3);
	        	cell.setCellValue(ofertasAprobadas.getCodigoCentral());
	        	cell = row.createCell(4);
	        	cell.setCellValue(ofertasAprobadas.getTipoDocBase());
	        	cell = row.createCell(5);
	        	cell.setCellValue(ofertasAprobadas.getCodDocBase());
	        	cell = row.createCell(6);
	        	cell.setCellValue(ofertasAprobadas.getLineaVehicular());
	        	cell = row.createCell(7);
	        	cell.setCellValue(ofertasAprobadas.getVehiculoCuota());
	        	cell = row.createCell(8);
	        	cell.setCellValue(ofertasAprobadas.getVerificacionLaboral());
	        	cell = row.createCell(9);
	        	cell.setCellValue(ofertasAprobadas.getVerificacionDomiciliaria());
	        	cell = row.createCell(10);
	        	cell.setCellValue(ofertasAprobadas.getSueldo());
	        	cell = row.createCell(11);
	        	cell.setCellValue(ofertasAprobadas.getMarcaPH());
	        	cell = row.createCell(12);
	        	cell.setCellValue(ofertasAprobadas.getTieneOtraOferta());
	        	cell = row.createCell(13);
	        	cell.setCellValue(ofertasAprobadas.getDocSinceros());
	        	cell = row.createCell(14);
	        	cell.setCellValue(ofertasAprobadas.getProcesoRiesgos());
	        	cell = row.createCell(15);
	        	cell.setCellValue(ofertasAprobadas.getFactordVehicular());
	        	cell = row.createCell(16);
	        	cell.setCellValue(ofertasAprobadas.getAuto2DA());
	        	cell = row.createCell(17);
	        	cell.setCellValue(ofertasAprobadas.getPlazoVeh48());
	        	cell = row.createCell(18);
	        	cell.setCellValue(ofertasAprobadas.getPlazoVeh60());
	        	cell = row.createCell(19);
	        	cell.setCellValue(ofertasAprobadas.getTipoRiesgo());
	        	cell = row.createCell(20);
	        	cell.setCellValue(ofertasAprobadas.getTipoCliente());
	        	cell = row.createCell(21);
	        	cell.setCellValue(ofertasAprobadas.getFlujoOperativo());
	        }
	        
	        for (int i = 0; i < headers.size(); i++) {
				sh.autoSizeColumn(i);
			}
			response.setHeader("Content-disposition", "attachment; filename=" + filename + ".xlsx");
			response.setContentType("application/vnd.ms-excel");
			ServletOutputStream fileDownload = response.getOutputStream();
			wb.write(fileDownload);
			fileDownload.close();
			//Borrando los datos de la tabla tCargaOferta
			ofertasAprobadasService.deleteDataOfertas(cargaCabecera.getFechaCarga());
			LOGGER.info("Excel: {}", "Se realizo la descarga con éxito");
		}
	}
}
