'use strict'

$(document).ready(function(){
    console.log("Se ejecuto el codigo");

    $.get("http://localhost:8080/recursos/string/menu/menu", function(htmlexterno){
        $("#cargaMenu").html(htmlexterno);
    });


});