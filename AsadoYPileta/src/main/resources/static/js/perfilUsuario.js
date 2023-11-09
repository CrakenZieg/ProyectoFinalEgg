function toggleInputs() {
    var inputs = document.getElementsByTagName("input");

    for (var i = 0; i < inputs.length; i++) {
      inputs[i].disabled = !inputs[i].disabled;
    }
    var textarea = document.getElementById("input4");
    textarea.disabled = !textarea.disabled;
    var inputImagen = document.getElementById("imagenEdit");
    inputImagen.hidden = !inputImagen.hidden;
    var boton = document.getElementById("btnGuardar");
    if (boton.style.display === "none") {
    boton.style.display = "inline-block"; // o "inline" o "inline-block" según tus necesidades
    } else {
    boton.style.display = "none";
    }
    var botonEdit = document.getElementById("btnEdit");
    if (botonEdit.style.display === "inline-block") {
        botonEdit.style.display = "none"; // o "inline" o "inline-block" según tus necesidades
    } else {
        botonEdit.style.display = "inline-block";
    }
  }