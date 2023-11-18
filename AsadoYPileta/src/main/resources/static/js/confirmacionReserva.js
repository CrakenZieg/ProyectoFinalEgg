function reserva(){

  document.getElementById('formularioReserva').style.display = 'none';
  document.getElementById('formularioCarga').style.display = 'flex';

  setTimeout(function() {
    document.getElementById('formularioCarga').style.display = 'none';
    document.getElementById('exitoReserva').style.display = 'block';
  }, 3000);

}

function cambiarMonto(valor,elem){
    let monto = document.getElementById("monto_total");
    if(elem.checked){        
        let base = Number.parseFloat(monto.innerHTML);
        base += base*Number.parseFloat(valor);
        monto.innerHTML = Math.floor(base);
    } else {
        let base = Number.parseFloat(monto.innerHTML);
        base -= base*Number.parseFloat(valor);
        monto.innerHTML = Math.floor(base);        
    }    
}

function hacerSubmit(){
    document.getElementById("submitForm").click();
}