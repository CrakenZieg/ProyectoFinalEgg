function actualizarInput(event){
    let elem = document.getElementById(`${event.id}Input`);
    elem.disabled = !elem.disabled;
}

function prepararArray(){
    let checkboxContactos = document.getElementById('checkboxContactos');
    let listaContactos = document.getElementsByName('tipoContacto');
    for(let i=0; i<listaContactos.length; i++){        
        if(listaContactos[i].checked){
            checkboxContactos.insertAdjacentHTML("beforeend",input("tipoContactoInput",listaContactos[i].id));
            let valor = document.getElementById(`${listaContactos[i].id}Input`).value;      
            checkboxContactos.insertAdjacentHTML("beforeend",input("contactosInput",valor));
        }
    }
}

function input(nombre,valor){
    return `<input type="checkbox" id="${nombre}${valor}" name="${nombre}" value="${valor}" hidden="true" checked="true"/>`;    
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


// Propiedad detalles



const imagenes = document.getElementsByClassName("imagen-oculta");
const atras = document.getElementById("atras");
const adelante = document.getElementById("adelante");
let posicionActual = 0;
const btnMostrarServicios = document.getElementById("mostrar-servicios");
const divMostrarServicios = document.getElementById("mostrar-td-serv");
const btnSalir = document.getElementById("salir");



renderizarImagen();

function salir(){
if (divMostrarServicios.style.display === "none") {
    divMostrarServicios.style.display = "flex";
  } else {
    divMostrarServicios.style.display = "none";
  }
}
function escalarServicios() {
  if (divMostrarServicios.style.display === "none") {
    divMostrarServicios.style.display = "flex";
  } else {
    divMostrarServicios.style.display = "none";
  }
}
function atrasimagen() {
  if (posicionActual === 0) {
    posicionActual = imagenes.length - 1;
  } else {
    posicionActual--;
  }
  renderizarImagen();
}
function adelanteimagen() {
  if (posicionActual >= imagenes.length - 1) {
    posicionActual = 0;
  } else {
    posicionActual++;
  }
  renderizarImagen();
}

function renderizarImagen() {
  if (imagenes[posicionActual].hidden) {
    imagenes[posicionActual].hidden = false;
    

    if (posicionActual != imagenes.length -1  ) {
      
      imagenes[posicionActual + 1].hidden = true;
      
    }else{
      imagenes[0].hidden = true;
    }
    if(posicionActual != 0){
      imagenes[posicionActual - 1].hidden = true;
    }
    if(posicionActual == 0){
      imagenes[imagenes.length-1].hidden = true;
      imagenes[posicionActual].hidden = false;
    }
    
  }
}

atras.addEventListener("click", atrasimagen);
adelante.addEventListener("click", adelanteimagen);
btnMostrarServicios.addEventListener("click", escalarServicios);
btnSalir.addEventListener("click", salir);

