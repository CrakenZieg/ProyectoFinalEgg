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