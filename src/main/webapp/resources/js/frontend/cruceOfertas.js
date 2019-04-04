

function cambiar(){
    var pdrs = document.getElementById('file-upload').files[0].name;
    document.getElementById('info').innerHTML = pdrs;
}

function validateFile() {
    var file_upload = document.getElementById('file-upload').files;

    if(file_upload.length === 0){
        $('#fileCastigos').notify('Debe seleccionar un archivo Excel.', 'warn');

    }
}