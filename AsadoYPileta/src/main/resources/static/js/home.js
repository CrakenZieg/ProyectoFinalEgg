//Carrusel
const tracks = Array.from(document.getElementsByClassName("carusel__track"));
const botonSiguiente = Array.from(document.getElementsByClassName("carusel__boton--right"));
const botonPrevio = Array.from(document.getElementsByClassName("carusel__boton--left"));
const indicadorNav = Array.from(document.getElementsByClassName("carusel__nav"));

const modalImgComents = Array.from(
  document.getElementsByClassName("modal-img-coment")
);
const btnmodalImgComents = Array.from(
  document.getElementsByClassName("abrir-modal-img-coment")
);

const btnCerrarModalImgComents = Array.from(
  document.getElementsByClassName("cerrar-comentarios-imagenes")
);

const setCajas = () =>{
tracks.forEach(function (track, ind) {
  const cajas = Array.from(track.children);
  const cajaWidth = cajas[0].getBoundingClientRect().width;
  

  cajas.forEach(function (caja, index) {
    caja.style.left = cajaWidth * index + "px";
    if(index != 0 && indicadorNav[ind].children.length < cajas.length){
      let indicador = document.createElement("button");
      indicador.classList.add("carusel__indicador")  
      indicadorNav[ind].appendChild(indicador);
    }
  });
  
  
});
}
setCajas();

const moverCaja = (track, actualCaja, objetivoCaja) => {
  track.style.transform = "translateX(-" + objetivoCaja.style.left + ")";
  actualCaja.classList.remove("actual-indicador");
  objetivoCaja.classList.add("actual-indicador");
  
};

const actualizarIndicador = (actualIndicador, objetivoIndicador) => {
  actualIndicador.classList.remove("actual-indicador");
  objetivoIndicador.classList.add("actual-indicador");
};

const hiddeArrow = (index, cajas, btS, btP) => {
  if (index === 0) {
    btP.classList.add("is-hidden");
    btS.classList.remove("is-hidden");
  } else if (index === cajas.length - 1) {
    btP.classList.remove("is-hidden");
    btS.classList.add("is-hidden");
  } else {
    btP.classList.remove("is-hidden");
    btS.classList.remove("is-hidden");
  }
};
//Click derecho ,mover caja a la derecha
botonSiguiente.forEach(function(btnSiguiente){
    btnSiguiente.addEventListener('click',()=>{
        const trackTe = btnSiguiente.previousElementSibling.children[0];
        const indicador = trackTe.parentElement.nextElementSibling.nextElementSibling;
        const actualCaja = trackTe.querySelector(".actual-indicador");
console.log(actualCaja.nextElementSibling);
        const siguienteCaja = actualCaja.nextElementSibling;
        const actualIndicador = indicador.querySelector(".actual-indicador");
        const siguienteIndicador = actualIndicador.nextElementSibling;
        const siguienteIndex = Array.from(
          btnSiguiente.previousElementSibling.children[0].children
        ).findIndex((caja) => caja === siguienteCaja);
        console.log(actualCaja)
        moverCaja(trackTe, actualCaja, siguienteCaja);
        actualizarIndicador(actualIndicador, siguienteIndicador);
        hiddeArrow(siguienteIndex, Array.from(trackTe.children),btnSiguiente, btnSiguiente.previousElementSibling.previousElementSibling);
    } )
    
})

//Cliok izquierdo, mover caja a la izquierda
botonPrevio.forEach(function (btnPrev) {
  btnPrev.addEventListener('click', () => {
    const trackTe = btnPrev.nextElementSibling.children[0];
    
    const indicador = trackTe.parentElement.nextElementSibling.nextElementSibling;
    const actualCaja = trackTe.querySelector(".actual-indicador");
    const previaCaja = actualCaja.previousElementSibling;
    const actualIndicador = indicador.querySelector(".actual-indicador");
    const previoIndicador = actualIndicador.previousElementSibling;
    const previoIndex =
      Array.from(btnPrev.nextElementSibling.children[0].children).findIndex(
        (caja) => previaCaja === caja
      );

    moverCaja(trackTe, actualCaja, previaCaja);
    actualizarIndicador(actualIndicador, previoIndicador);
    hiddeArrow(previoIndex,Array.from(trackTe.children), btnPrev.nextElementSibling.nextElementSibling , btnPrev);
  });
});



//Click mover caja atravez de indicadores
indicadorNav.forEach(function(indicador){
    indicador.addEventListener('click', (e) => {
        const objetivoIndicador = e.target.closest("button");
        
        if (!objetivoIndicador) return;
        const trackTe = indicador.previousElementSibling.previousElementSibling.children[0];
        const actualCaja = trackTe.querySelector(".actual-indicador");
        const actualIndicador = indicador.querySelector(".actual-indicador");
        const objetivoIndex = Array.from(indicador.children).findIndex(
          (indicador) => indicador === objetivoIndicador
        );
        const objetivoCaja = Array.from(trackTe.children)[objetivoIndex];
      
        moverCaja(trackTe, actualCaja, objetivoCaja);
      
        actualizarIndicador(actualIndicador, objetivoIndicador);
      
        hiddeArrow(objetivoIndex,Array.from(trackTe.children), indicador.previousElementSibling, indicador.previousElementSibling.previousElementSibling.previousElementSibling);
      });
})


btnmodalImgComents.forEach(function (btn, index) {
  
  btn.addEventListener("click", () => {
    modalImgComents[index].showModal();
    document.getElementsByClassName("carusel")[1].style.height = "400px";
    setCajas();
  });
});

btnCerrarModalImgComents.forEach(function (btn, index) {
  
  btn.addEventListener("click", () => {
    modalImgComents[index].close();
    
  });
});