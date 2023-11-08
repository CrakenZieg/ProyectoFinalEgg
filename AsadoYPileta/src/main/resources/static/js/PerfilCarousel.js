function seleccionar(event){
    let menu = document.getElementsByClassName('menu');
    for(let i=0; i<menu.length; i++){
        if(menu[i].classList.contains('active')){
            menu[i].classList.remove('active');
        }
    }
    event.classList.add('active');
}


