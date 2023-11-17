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
const fechasDisponibles = ['2023-11-17', '2023-11-20', '2023-11-25'];
    const fechasNoDisponibles = ['2023-11-18', '2023-11-22', '2023-11-28'];

  function generarCalendario() {
    const calendarBody = document.getElementById('calendar-body');
    calendarBody.innerHTML = ''; // Limpiar el contenido actual

    const fechaActual = new Date();
    const primerDiaMes = new Date(fechaActual.getFullYear(), fechaActual.getMonth(), 1);
    const ultimoDiaMes = new Date(fechaActual.getFullYear(), fechaActual.getMonth() + 1, 0);

    let fecha = new Date(primerDiaMes);
    let diasEnFila = 0;

    while (fecha <= ultimoDiaMes) {
      const fila = document.createElement('tr');

      for (let i = 0; i < 7; i++) {
        const celda = document.createElement('td');
        celda.textContent = fecha.getDate();

        // Verificar si la fecha está disponible o no
        const fechaISO = fecha.toISOString().split('T')[0];
        if (fechasDisponibles.includes(fechaISO)) {
          celda.classList.add('available');
        } else if (fechasNoDisponibles.includes(fechaISO)) {
          celda.classList.add('not-available');
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
  }