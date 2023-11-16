
function seleccionar(event) {
    let menu = document.getElementsByClassName('menu');
    for (let i = 0; i < menu.length; i++) {
        if (menu[i].classList.contains('active')) {
            menu[i].classList.remove('active');
        }
    }
    event.classList.add('active');
}

function editarServicio(id, tipo, valor) {
    console.log(id+" "+tipo+" "+valor);
    document.getElementById('formServicio').setAttribute('action', '/negocio/modificarServicio');
    document.getElementById('idServicio').value = id;
    document.getElementById('tipoServicio').value = tipo;
    document.getElementById('valorServicio').value = valor;
}

function limpiarServicio() {
    document.getElementById('formServicio').setAttribute('action', '/negocio/registroServicio');
    document.getElementById('idServicio').value = null;
    document.getElementById('tipoServicio').value = null;
    document.getElementById('valorServicio').value = null;
    document.getElementById('eliminarServicio').setAttribute('disabled', true);
}

function eliminarServicio() {
    document.getElementById('formServicio').setAttribute('action', '/negocio/eliminarServicio');
    document.getElementById('eliminarServicio').removeAttribute('disabled');
}

function editarTipoPropiedad(idTipoPropiedad, tipoPropiedadTipo, tipoPropiedadTitulo, 
    tipoPropiedadEmoji,tipoPropiedadDescripcion) {
    document.getElementById('formTipoPropiedad').setAttribute('action', '/negocio/modificarTipoPropiedad');
    document.getElementById('idTipoPropiedad').value = idTipoPropiedad;
    document.getElementById('tipoPropiedadTipo').value = tipoPropiedadTipo;
    document.getElementById('tipoPropiedadTitulo').value = tipoPropiedadTitulo;
    document.getElementById('tipoPropiedadEmoji').value = tipoPropiedadEmoji;
    document.getElementById('tipoPropiedadDescripcion').value = tipoPropiedadDescripcion;
}

function limpiarTipoPropiedad() {
    document.getElementById('formTipoPropiedad').setAttribute('action', '/negocio/registroTipoPropiedad');
    document.getElementById('idTipoPropiedad').value = null;
    document.getElementById('tipoPropiedadTipo').value = null;
    document.getElementById('tipoPropiedadTitulo').value = null;
    document.getElementById('tipoPropiedadEmoji').value = null;
    document.getElementById('tipoPropiedadDescripcion').value = null;
    document.getElementById('eliminarTipoPropiedad').setAttribute('disabled', true);
}

function eliminarTipoPropiedad() {
    document.getElementById('formTipoPropiedad').setAttribute('action', '/negocio/eliminarTipoPropiedad');
    document.getElementById('eliminarTipoPropiedad').removeAttribute('disabled');
}

function editarTipoContacto(idTipoContacto, tipoContactoTipo) {
    document.getElementById('formTipoContacto').setAttribute('action', '/negocio/modificarTipoContacto');
    document.getElementById('idTipoContacto').value = idTipoContacto;
    document.getElementById('tipoContactoTipo').value = tipoContactoTipo;
}

function limpiarTipoContacto() {
    document.getElementById('formTipoContacto').setAttribute('action', '/negocio/registroTipoContacto');
    document.getElementById('idTipoContacto').value = null;
    document.getElementById('tipoContactoTipo').value = null;
    document.getElementById('eliminarTipoContacto').setAttribute('disabled', true);
}

function eliminarTipoContacto() {
    document.getElementById('formTipoContacto').setAttribute('action', '/negocio/eliminarTipoContacto');
    document.getElementById('eliminarTipoContacto').removeAttribute('disabled');
}

