function enviarDatos() {
    let checkboxInicioFin = document.getElementById('checkboxInicioFin');
    var inicioValue = document.getElementById('inicio').value;
    var finValue = document.getElementById('fin').value;
    if(inicioValue!=null && finValue!=null){
        var arregloEnteros = [parseInt(inicioValue), parseInt(finValue)]; 
        for(let i=0; i<arregloEnteros.length; i++){
        checkboxInicioFin.insertAdjacentHTML("beforeend",input("porFechaReserva",arregloEnteros[i]));
        }
    }
}            

function input(nombre,valor){
    return `<input type="checkbox" id="${nombre}${valor}" name="${nombre}" value="${valor}" hidden="true" checked="true"/>`;    
}