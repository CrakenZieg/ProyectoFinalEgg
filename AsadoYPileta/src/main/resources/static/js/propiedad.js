function enviarDatos() {
    let checkboxInicioFin = document.getElementById('checkboxInicioFin');
    var inicioValue = document.getElementById('inicio').value;
    var finValue = document.getElementById('fin').value;
    if(inicioValue!=0 && finValue!=0){
        var arregloEnteros = [inicioValue, finValue]; 
        for(let i=0; i<arregloEnteros.length; i++){
        checkboxInicioFin.insertAdjacentHTML("beforeend",input("porFechaReserva",arregloEnteros[i]));
        }
    }
}            

function input(nombre,valor){
    return `<input type="checkbox" id="${nombre}${valor}" name="${nombre}" value="${valor}" hidden="true" checked="true"/>`;    
}
let fechaActual = new Date();

function cargar(){
    generarCalendario(fechasDisponibles);
}
    var fechasDisponibles = document.getElementById('fechasDisponiblesInput').value;
    var fechasReservadas = document.getElementById('fechasReservadasInput').value;
    function generarCalendario(fechasDisponibles) {
    const calendarBody = document.getElementById('calendar-body');
    calendarBody.innerHTML = ''; 

    const primerDiaMes = new Date(fechaActual.getFullYear(), fechaActual.getMonth(), 1);
    const primerDiaSemana = primerDiaMes.getDay(); 
    const ultimoDiaMes = new Date(fechaActual.getFullYear(), fechaActual.getMonth() + 1, 0);

    let fecha = new Date(primerDiaMes);
    fecha.setDate(1); 

    
    while (fecha.getDay() !== 0) {
        fecha.setDate(fecha.getDate() - 1);
    }

    let diasEnFila = 0;

    while (fecha <= ultimoDiaMes) {
        const fila = document.createElement('tr');

        for (let i = 0; i < 7; i++) {
            const celda = document.createElement('td');
            celda.textContent = fecha.getDate();

            // Verificar si la fecha está disponible o no
            const fechaISO = fecha.toISOString().split('T')[0];
            if (fechasDisponibles.includes(fechaISO)) {
                celda.classList.add('disponible', 'custom-cell');
            }else {
                celda.classList.add('no-disponible', 'custom-cell');
            }
            if(fechasReservadas.includes(fechaISO)){
                celda.classList.add('reservado', 'custom-cell');
                }
            fila.appendChild(celda);

            fecha.setDate(fecha.getDate() + 1);
            diasEnFila++;

            if (diasEnFila === 7) {
                diasEnFila = 0;
                break;
            }
        }

        calendarBody.appendChild(fila);
    }

    document.getElementById('mes-actual').textContent = `${obtenerNombreMes(primerDiaMes.getMonth())} ${primerDiaMes.getFullYear()}`;
}

  function cambiarMes(delta) {
    fechaActual.setMonth(fechaActual.getMonth() + delta);
    generarCalendario(fechasDisponibles);
  }

  function obtenerNombreMes(numeroMes) {
    const meses = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
    return meses[numeroMes];
  }

  function obtenerProvincia(){
    let select = document.getElementById('selectProvincia');
    let proSelected = select.options[select.selectedIndex].value;
    let provinciaUrl = proSelected.replaceAll(' ','%20');
    window.location.href = '/propiedad/registrar/'+provinciaUrl+'&nombre&200&true';
}

// Propiedad detalles

const btnMostrarServicios = document.getElementById("mostrar-servicios");
const divMostrarServicios = document.getElementById("mostrar-td-serv");
const btnSalir = document.getElementById("salir");
const btnComentario = document.getElementById("ver-comentarios");
const btnCerrarComentario = document.getElementById("cerrar-comentarios");
const comentarios = document.getElementById("modalc");
const estrellas = document.getElementsByClassName('starp');
const puntuacion = Number(document.getElementById("calificacion").value);


function salir(){
divMostrarServicios.close();
}
function escalarServicios() {
 divMostrarServicios.showModal();
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

btnMostrarServicios.addEventListener("click", escalarServicios);
btnSalir.addEventListener("click", salir);
