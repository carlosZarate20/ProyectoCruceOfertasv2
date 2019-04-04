package com.bbva.cruce.CruceOfertas.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.bbva.cruce.CruceOfertas.dao.OfertasAprobadasDao;
import com.bbva.cruce.CruceOfertas.model.AplicacionOfertasBean;
import com.bbva.cruce.CruceOfertas.model.CargaCabecera;
import com.bbva.cruce.CruceOfertas.model.OfertasAprobadasPlantilla;

@Repository
public class OfertasAprobadasDaoImpl implements OfertasAprobadasDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Integer cargaCabeceraOferta(CargaCabecera cargaCabecera) throws Exception {
		Integer result;
		try {
			
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
			simpleJdbcCall.withProcedureName("spCargaCabeceraAppCliente")
						  .declareParameters( new SqlParameter("USUARIO", Types.NVARCHAR))
						  .declareParameters( new SqlParameter("NOMBRE_ARCHIVO", Types.NVARCHAR))
						  .declareParameters( new SqlParameter("FECHA_CARGA", Types.NVARCHAR))
						  .declareParameters( new SqlOutParameter("IDENTIFICADOR", Types.INTEGER));
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("USUARIO", cargaCabecera.getUsuario() ,Types.NVARCHAR)
					.addValue("NOMBRE_ARCHIVO", cargaCabecera.getNombreArchivo() ,Types.NVARCHAR)
					.addValue("FECHA_CARGA", cargaCabecera.getFechaCarga(), Types.NVARCHAR);
			
			Map<?, ?> map = simpleJdbcCall.execute(in);
			result = (Integer) map.get("IDENTIFICADOR");
		} catch(Exception e) {
			throw new Exception("No se puede cargar la tabla cabecera de ofertas aprobadas");
		}
		return result;

	}

	@Override
	public void cargaOfertas(List<AplicacionOfertasBean> listOfertasBean) {
		try {
			String sql ="INSERT INTO tCargaOferta (tip_Doi, doi, identificador) " + 
					"VALUES (?, ?, ?)";
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					AplicacionOfertasBean aplicacionOfertas = listOfertasBean.get(i);
					ps.setString(1, aplicacionOfertas.getTipDoi());
					ps.setString(2, aplicacionOfertas.getDoi());
					ps.setString(3, aplicacionOfertas.getIdentificador());
				}

				@Override
				public int getBatchSize() {
					return listOfertasBean.size();
				}
				
			});
		} catch(Exception e) {
			
		}
	}

	@Override
	public List<OfertasAprobadasPlantilla> cruceOferta(String nameTable, String identificador) throws Exception {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        MapSqlParameterSource mapSource = new MapSqlParameterSource();
        String query = "";
        query +="exec spCruceOfertasAprobadas :ID_CARGA_OFERTA, :NAME_TABLE";
        
        mapSource.addValue("ID_CARGA_OFERTA", identificador)
        		 .addValue("NAME_TABLE",nameTable );
        
        return template.query(query, mapSource, new CategoriaMapper());
	}
	
	public class CategoriaMapper implements RowMapper<OfertasAprobadasPlantilla> {

	    @Override
	    public OfertasAprobadasPlantilla mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	OfertasAprobadasPlantilla obj = new OfertasAprobadasPlantilla();
	    	obj.setId_carga(rs.getInt(1));
	    	obj.setTipoDocumento(rs.getString(2));
	    	obj.setDoi(rs.getString(3));
	    	obj.setId(rs.getInt(4));
	    	obj.setCodigoCentral(rs.getString(5));
	    	obj.setTipoDocBase(rs.getString(6));
	    	obj.setCodDocBase(rs.getString(7));
	    	obj.setLineaVehicular(rs.getDouble(8));
	    	obj.setVehiculoCuota(rs.getString(9));
	    	obj.setVerificacionLaboral(rs.getString(10));
	    	obj.setVerificacionDomiciliaria(rs.getString(11));
	    	obj.setSueldo(rs.getDouble(12));
	    	obj.setMarcaPH(rs.getString(13));
	    	obj.setTieneOtraOferta(rs.getString(14));
	    	obj.setDocSinceros(rs.getInt(15));
	    	obj.setProcesoRiesgos(rs.getString(16));
	    	obj.setFactordVehicular(rs.getDouble(17));
	    	obj.setAuto2DA(rs.getDouble(18));
	    	obj.setPlazoVeh48(rs.getString(19));
	    	obj.setPlazoVeh60(rs.getString(20));
	    	obj.setTipoRiesgo(rs.getString(21));
	    	obj.setTipoCliente(rs.getString(22));
	    	obj.setFlujoOperativo(rs.getString(23));
	        return obj;
	    }
	}

	@Override
	public CargaCabecera getCargaCabecera(int identify) {
		String sql = "SELECT usuario, nombre_Archivo, fecha_Carga FROM tCargaCabecera WHERE id_Carga_Cabecera = ?";
		CargaCabecera cargaCabecera = jdbcTemplate.queryForObject(sql, 
				new Object[]{identify},
	              (rs, rowNum) -> new CargaCabecera(
	                                  rs.getString("usuario"),
	                                  rs.getString("nombre_Archivo"),
	                                  rs.getString("fecha_Carga")
	                              )
	              );
		return cargaCabecera;
	}

	@Override
	public void deleteOfertas(String identiOfertas) throws Exception {
		try{
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
			simpleJdbcCall.withProcedureName("spEliminarDatosOferta")
			  			  .declareParameters( new SqlParameter("IDENTIFICADOR", Types.NVARCHAR));
			
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("IDENTIFICADOR", identiOfertas,Types.NVARCHAR);
			Map<?, ?> map = simpleJdbcCall.execute(in);
	
		}catch(Exception e){
			throw new Exception("No se puede realizar el la eliminacion de los datos de ofertas");
		}
	}

}
