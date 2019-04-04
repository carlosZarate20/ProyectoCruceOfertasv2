$("#loginValues").on("submit", function (event){
	event.preventDefault();
		
	login();    
});

function login(){
    if (!validateLogin()) {
        return;
    }
    var usuario = $('#username').val();
    var contrasena = $('#password').val();
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: gPath + '/login',
        data: JSON.stringify({
            username: usuario,
            password: contrasena
        }),
        success: function(response){
            if(response && response.successful){
                // window.location = 'http://localhost:8090/ofertasAprobadas/ofertasAprobadas';
                window.location = gPath + '/ofertasAprobadas/ofertasAprobadas';
                sessionStorage.setItem('userName', usuario);
                console.log("Inicio de session correcto con el usuario: ", usuario);
            }else if(response && response.message){
                mostrarAlerta(response.message);
		    	$('#password').val('');
            }else {
                mostrarAlerta('Login inv\u00E1lido.');
                $('#password').val('');
                // $('input[type="text"]').css({"border":"2px solid red","box-shadow":"0 0 3px red"});
                console.log("Inicio de session incorrecto con el usuario: ", usuario);
            }
        },error: function(e) {
            mostrarAlerta('Error!!!.');
            console.log(e);
        }
    });

}
function validateLogin(){
    var usuario = $('#username').val();
    var password = $('#password').val();

    if(usuario == 0){
        mostrarAlerta('Ingrese un usuario.');
		return false;
    }

    if (password == 0) {
        mostrarAlerta('Ingrese una contrase\u00F1a.');
		return false;
	}

    return true;
}

function cerrarAlerta() {
	$('#alertMessage').fadeOut();
}

function mostrarAlerta(mensaje) {
	$('#alertMessage').fadeOut();
	$('#alertMessage').text(mensaje);
	$('#alertMessage').fadeIn();
}