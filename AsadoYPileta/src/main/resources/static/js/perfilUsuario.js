function toggleInputs() {
    let inputs = document.getElementsByTagName("input");
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].disabled = !inputs[i].disabled;
    }
    let textarea = document.getElementById("input5");
    textarea.disabled = !textarea.disabled;
    let checkTyC = document.getElementById("prepararArrayInput");
    checkTyC.disabled = !checkTyC.disabled;
    let inputImagen = document.getElementById("imagenEdit");
    inputImagen.hidden = !inputImagen.hidden;
    let pass = document.getElementById("pass");
    pass.hidden = !pass.hidden;
    let pass2 = document.getElementById("pass2");
    pass2.hidden = !pass2.hidden;
    let boton = document.getElementById("btnGuardar");
    boton.hidden = !boton.hidden;
    let botonEdit = document.getElementById("btnEdit");
    botonEdit.hidden = !botonEdit.hidden;
    let checkTerminos = document.getElementById("prepararArrayInput");
    checkTerminos.checked = false;
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