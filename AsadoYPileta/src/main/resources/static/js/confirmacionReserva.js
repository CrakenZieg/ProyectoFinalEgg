function reserva(){

  document.getElementById('formularioReserva').style.display = 'none';
  document.getElementById('formularioCarga').style.display = 'flex';

  setTimeout(function() {
    document.getElementById('formularioCarga').style.display = 'none';
    document.getElementById('exitoReserva').style.display = 'block';
  }, 3000);

}
var inicial = 0;
function cambiarMonto(valor,elem){
    let monto = document.getElementById("monto_total");
    if(inicial == 0){
        inicial = Number.parseFloat(monto.dataset.valor);
    }
    let base = Number.parseFloat(monto.dataset.valor);
    if(elem.checked){        
        base += inicial*Number.parseFloat(valor);
    } else {
        base -= inicial*Number.parseFloat(valor);             
    }     
    monto.value= Math.floor(base);
    monto.dataset.valor= base;
}


function hacerSubmit(){
    document.getElementById("submitForm").click();
}