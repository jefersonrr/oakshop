const imgs = document.querySelectorAll('.img-select a');
const imgBtns = [...imgs];
let imgId = 1;
validateAll();
imgBtns.forEach((imgItem) => {
    imgItem.addEventListener('click', (event) => {
        event.preventDefault();
        imgId = imgItem.dataset.id;
        slideImage();
    });
});

function slideImage(){
    const displayWidth = document.querySelector('.img-showcase img:first-child').clientWidth;

    document.querySelector('.img-showcase').style.transform = `translateX(${- (imgId - 1) * displayWidth}px)`;
}

window.addEventListener('resize', slideImage);


/*
 * FUNCIONES DETALLES 
 *
*/

function validateAll(){
    const data = Object.values(datos.valores);
    const talla_selected = document.getElementById("tallas").value;
    const color_selected = document.getElementById("color").value;
    let talla_ref = data.find(function (element) {
        return talla_selected===element.talla_id && color_selected === element.color_id;
    })
    if(talla_ref===undefined){
        talla_ref = data.find(function (element) {
            return talla_selected ===element.talla_id;
        })
    }
    const talla = document.querySelector('#tallas');
    talla.value = talla_ref.talla_id;
    const color = document.querySelector('#color');
    color.value = talla_ref.color_id;
    
    document.getElementById("nombre_producto").innerHTML = talla_ref.referencia_producto;
    document.getElementById("productoId").value = talla_ref.producto_id;
    document.getElementById("idProducto").href = "./MostrarMetodoPago.do?idProducto="+  talla_ref.producto_id;
    document.getElementById("precio_habitual").innerHTML = talla_ref.precio_habitual;
    document.getElementById("disponibles").innerHTML = talla_ref.cantidad;
    document.getElementById("descuento").innerHTML = talla_ref.precio_descuento+" ("+talla_ref.descuento+"%)";
    if(talla_ref.cantidad == 0){
        document.getElementById("cantidad_dato").disabled = true;
        document.getElementById("idProducto").disabled = true;
        document.getElementById("carrito").disabled = true;
        
    }else{
        document.getElementById("cantidad_dato").disabled = false;
        document.getElementById("idProducto").disabled = false;
        document.getElementById("carrito").disabled = false;
    }
    return talla_ref;
}   

function selectActive(){
    validateAll();
}

function agregarCantidadItem(){
    const cantidad = document.getElementById("cantidad_dato").value;
    const talla_ref = validateAll();
    if(parseInt(cantidad, 10) >= parseInt(talla_ref.cantidad,10)){
        document.getElementById("cantidad_dato").max = talla_ref.cantidad;
    }
}