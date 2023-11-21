
const stars = document.querySelectorAll('#stars');
const inptCalProp =document.getElementById("inp-cal-prop");

stars.forEach(function(star,index){
  star.addEventListener('click', () =>{
    for(let i=0; i<=index; i++){
      stars[i].style.color = "gold";
    }
    for(let i=index+1; i<stars.length ; i++){
        stars[i].style.color = "#bbb";
    }
    inptCalProp.value = index+1;
    console.log(inptCalProp.value)
  })
  star.addEventListener('mouseover', () =>{
    for(let i=0; i<=index; i++){
      stars[i].style.color = "gold";
    }
    for(let i=index+1; i<stars.length ; i++){
        stars[i].style.color = "#bbb";
    }
    inptCalProp.value = index+1;    
    console.log(inptCalProp.value)
  })
 
})

