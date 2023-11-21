function toggleInputs() {
    let inputs = document.getElementsByClassName("cambio");
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].disabled = !inputs[i].disabled;
    }
    let inputImagen = document.getElementById("imagenEdit");
    inputImagen.hidden = !inputImagen.hidden;
    let borrar = document.getElementsByClassName("borrarImg");
    for (var i = 0; i < borrar.length; i++) {
        borrar[i].hidden = !borrar[i].hidden;
    }
    let boton = document.getElementById("btnGuardar");
    boton.hidden = !boton.hidden;
    let botonEdit = document.getElementById("btnEdit");
    botonEdit.hidden = !botonEdit.hidden;
    
    let botonPass = document.getElementById("botn-camb");
    if(botonEdit.hidden){
        botonPass.hidden = false;
    }

}
function togglePasswordVisibility() {
    var passwordField = document.getElementById('input3');
    if (passwordField.type === 'password') {
      passwordField.type = 'text';
    } else {
      passwordField.type = 'password';
    }
  }
function toggleInputsGuardar() {
    let botonEdit = document.getElementById("btnEdit");
    botonEdit.hidden = !botonEdit.hidden;
    let boton = document.getElementById("btnGuardar");
    boton.hidden = !boton.hidden;
}
function confirmarEdicion() {
    var contraseña = prompt("Por favor, ingrese la contraseña:");
    var claveAlmacenada = document.getElementById("claveSecreta").value;
    if (contraseña === claveAlmacenada) {
        return true;
    } else {
        alert("Contraseña incorrecta.");
        return false;
    }
}


// Modal cambiar contrasena

const btonCambiarPass = document.getElementById("botn-camb");
const modal = document.getElementById("modale");
const cerrarModal = document.getElementById("cerrar-modal");

btonCambiarPass.addEventListener('click', ()  => {
    modal.showModal();
})

cerrarModal.addEventListener('click', ()  => {
    modal.close();
})

//bton modificar

const btonModificar =  document.querySelectorAll('.bton-modificar');;
const urlActual = window.location.pathname;
mostrarBotonModificar();

function mostrarBotonModificar(){

    if(urlActual == "/cliente/perfil"){
    btonModificar.forEach((b) =>{
    b.hidden = false;
})
}
}
