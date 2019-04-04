
$(document).ready(function(){
	var user = sessionStorage.getItem('userName');
	document.getElementById("user").innerHTML = user;

	$('#btnDescargar').hide();
	
	$("#uploadFile").on("submit", function (event){
		event.preventDefault();
		enviar_archivo();    
	});
	
	$("#btnDescargar").click(function(){
		descargar_excel();
	});
	
	function enviar_archivo(){
		var files = document.getElementById('file-upload').files;
		
		if(files.length === 0){
			$('#file_warning').css('display', 'inline-block');
			
			setTimeout(function(){
				$('#file_warning').css('display', 'none');
			},1000);
			
			return;
		}
		var data = new FormData();
		data.append('file', $('input[type=file]')[0].files[0]);
		
		$.ajax({
			url: gPath + '/carga/carga-ofertas',
			type: 'POST',
			data: data,
			cache: false,
			dataType: 'json',
			contentType: false,
			processData: false,
			success: function(response){
				console.log("Response: ", response);
				if(response.estado == 1){
					$('#file_error').css('display', 'none');
					generarTabla(response.objetoRespuesta);
					$('#btnDescargar').show();
				}else{
					$('#file_error').css('display', 'inline-block');
				}
			},
			error: function(){
				alert('Servicio Caido')
			}
		});
	}
	
	function generarTabla(listaOfertas){
		if(!listaOfertas){
			return
		}
		
		var htmlTable = '<table id="ofertas-tabla" class="table table-hover table-bordered">';
		htmlTable += '<thead class="thead-dark"><tr class="success">'
			+ '<th>NRO</th>'
			+ '<th>TIP_DOI</th>'
			+ '<th>NUM_DOI</th>'
			+ '<th>ID</th>'		
			+ '<th>COD_CENTRAL</th>'          
			+ '<th>TIP_DOC_BASE</th>'
			+ '<th>CODDOC_BASE</th>'
			+ '<th>LINEA_VEHICULAR</th>'
			+ '<th>VEH_CUOTA</th>'
			+ '<th>REQUIERE_VERF_LABORAL</th>'
			+ '<th>REQUIERE_VERF_DOMICILIARIA</th>'
			+ '<th>SUELDO</th>'
			+ '<th>MARCA_PH</th>'
			+ '<th>TIENE_OTRA_OFERTA</th>'
			+ '<th>DOC_SINCEROS</th>'
			+ '<th>PROCESO_RIESGOS</th>'
			+ '<th>FACTORD_VEHICULAR</th>'
			+ '<th>AUTO_2DA</th>'
			+ '<th>PLAZO_VEH_48</th>'
			+ '<th>PLAZO_VEH_60</th>'
			+ '<th>TIPO_RIESGO</th>'
			+ '<th>TIPO_CLIENTE</th>'
			+ '<th>FLUJO_OPERATIVO</th>'
			+ '</tr></thead><tbody>';  
		
		for(var i = 0; i < listaOfertas.length; i++){
			htmlTable += '<tr>'
				+ '<td>' + listaOfertas[i].id_carga + '</td>'
				+ '<td>' + listaOfertas[i].tipoDocumento + '</td>'
				+ '<td>' + listaOfertas[i].doi + '</td>'          
				+ '<td>' + listaOfertas[i].id + '</td>'
				+ '<td>' + listaOfertas[i].tipoDocBase + '</td>'
				+ '<td>' + listaOfertas[i].codDocBase + '</td>'
				+ '<td>' + listaOfertas[i].codigoCentral + '</td>'
				+ '<td>' + listaOfertas[i].lineaVehicular + '</td>'
				+ '<td>' + listaOfertas[i].vehiculoCuota + '</td>'
				+ '<td>' + listaOfertas[i].verificacionLaboral + '</td>'
				+ '<td>' + listaOfertas[i].verificacionDomiciliaria + '</td>'
				+ '<td>' + listaOfertas[i].sueldo + '</td>'
				+ '<td>' + listaOfertas[i].marcaPH + '</td>'
				+ '<td>' + listaOfertas[i].tieneOtraOferta + '</td>'
				+ '<td>' + listaOfertas[i].docSinceros + '</td>'
				+ '<td>' + listaOfertas[i].procesoRiesgos + '</td>'
				+ '<td>' + listaOfertas[i].factordVehicular + '</td>'
				+ '<td>' + listaOfertas[i].auto2DA + '</td>'
				+ '<td>' + listaOfertas[i].plazoVeh48 + '</td>'
				+ '<td>' + listaOfertas[i].plazoVeh60 + '</td>'
				+ '<td>' + listaOfertas[i].tipoRiesgo + '</td>'
				+ '<td>' + listaOfertas[i].tipoCliente + '</td>'
				+ '<td>' + listaOfertas[i].flujoOperativo + '</td>'
				+ '</tr>';
		}
		
		htmlTable += '</tbody></table>';
		$('#resultado-ofertas').html(htmlTable);
	}
	
	function descargar_excel(){
		window.location = gPath + '/carga/exportar-ultima-Carga';
		$('#resultado-ofertas').empty();
		$('#btnDescargar').hide();
	}	
})

