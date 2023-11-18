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



// Propiedad detalles



const imagenes = document.getElementsByClassName("imagen-oculta");
const atras = document.getElementById("atras");
const adelante = document.getElementById("adelante");
let posicionActual = 0;
const btnMostrarServicios = document.getElementById("mostrar-servicios");
const divMostrarServicios = document.getElementById("mostrar-td-serv");
const btnSalir = document.getElementById("salir");
const btnComentario = document.getElementById("ver-comentarios");
const btnCerrarComentario = document.getElementById("cerrar-comentarios");
const comentarios = document.getElementById("modalc");
const estrellas = document.getElementsByClassName('starp');
const puntuacion = Number(document.getElementById("calificacion").value);




renderizarImagen();

function salir(){
divMostrarServicios.close();
}
function escalarServicios() {
 divMostrarServicios.showModal();
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
agregarPuntuacion();
function agregarPuntuacion() {

    for(let i=0; i<=puntuacion-1; i++){
      estrellas[i].style.color = "gold";
    }
}

btnComentario.addEventListener("click", () =>{
    comentarios.showModal();
})
btnCerrarComentario.addEventListener("click", () =>{
    comentarios.close();
})
atras.addEventListener("click", atrasimagen);
adelante.addEventListener("click", adelanteimagen);
btnMostrarServicios.addEventListener("click", escalarServicios);
btnSalir.addEventListener("click", salir);

