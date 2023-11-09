function seleccionar(event){
    let menu = document.getElementsByClassName('menu');
    for(let i=0; i<menu.length; i++){
        if(menu[i].classList.contains('active')){
            menu[i].classList.remove('active');
        }
    }
    event.classList.add('active');
}

function editarServicio(id, tipo, valor){
    document.getElementById('formServicio').setAttribute('action','/negocio/modificarServicio');
    document.getElementById('idServicio').value = id;
    document.getElementById('tipoServicio').value = tipo;
    document.getElementById('valorServicio').value = valor;  
}

function limpiarServicio(){
    document.getElementById('formServicio').setAttribute('action','/negocio/registroServicio');
    document.getElementById('idServicio').value = null;
    document.getElementById('tipoServicio').value = null;
    document.getElementById('valorServicio').value = null;  
    document.getElementById('eliminarServicio').setAttribute('disabled',true);
}

function eliminarServicio(){
    document.getElementById('formServicio').setAttribute('action','/negocio/eliminarServicio');
    document.getElementById('eliminarServicio').removeAttribute('disabled');    
}



