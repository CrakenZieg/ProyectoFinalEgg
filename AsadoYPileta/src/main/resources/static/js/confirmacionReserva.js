// var valcode = document.querySelector("#discount")
// var namecode = document.querySelector("#code")
// namecode.textContent = valcode.value

// var closeBtn = document.querySelector(".close")
// var couponCode = document.querySelector(".couponCode")
// closeBtn.addEventListener("click", function () {
// close()
// })
// valcode.addEventListener("change", function () {
// checkDiscountCoupon()
// })

// function checkDiscountCoupon() {
// if (valcode.value.length === 0) {
// // namecode.style.display = "none"
// close()
// }
// else {
// couponCode.classList.remove("d-none")
// namecode.style.display = "inline"
// namecode.textContent = valcode.value
// }
// }

// function close() {
// couponCode.classList.add("d-none")
// valcode.value = ""
// }


function reserva(){

  document.getElementById('formularioReserva').style.display = 'none';
  document.getElementById('formularioCarga').style.display = 'flex';

  setTimeout(function() {
    document.getElementById('formularioCarga').style.display = 'none';
    document.getElementById('exitoReserva').style.display = 'block';
  }, 3000);

}
