# ProyectoFinalEgg - ProyectoAlquileres de Quinchos para Fiestas

DESCRIPCIÓN DEL PROYECTO 
Nuestra idea es crear una plata forma para que los administradores de quinchos, casas de fin de semana, quintas, casas con pileta para hacer asados y encuentros casuales con amigos u otros eventos puedan cargar sus ofertas con un calendario que muestre los días disponibles para reservas y a su vez los clientes puedan reservar una fecha. También deseamos que toda la información esté allí para que el cliente pueda comparar y contactarse: ubicación, servicios, fotos, redessociales, teléfonos, mails. Una vez pasada la fecha de reserva, sino hay cancelaciones, los clientes pueden dejar sus opiniones con respecto al lugar y, de ser posible, subir alguna foto de su evento o video. 
A la hora de reservar el lugar, el cliente debe llenar un formulario donde, a través de una checklist, pueda seleccionar los servicios que va a contratar y además puedan dejar un mensaje o descripción con detalles para que el dueño pueda leerlo. Los servicios tendrán sus respectivos precios y, dependiendo de cuáles hayan sido seleccionados, mostrará un monto final para que el cliente visualice antes de confirmar la reserva. 

PROBLEMA 
No existe en el mercado una aplicación específica para alquileres de espacios para eventos y que permite comparar distintas ofertas en un mismo lugar. Además quisiéramos contar con un sitio confiable para evitar operaciones fraudulentas. 

SOLUCIÓN
La aplicación web ofrecerá alquileres temporarios (tanto de propiedades completas como parciales, tales como quinchos, patios, garages) donde se permitirá validar la identidad del propietario y del inquilino, luegode la experiencia podrán calificarla. 

AUDIENCIA 
● Propietarios de casas de fin de semana, quinchos, chalets, quintas, que deseen poner sus propiedades en alquiler para festejos. 
● Personas interesadas en alquilar temporalmente lugares para esparcimiento, festejos o eventos. 

CASOS DE USO 
● Los usuarios deben poder reservar el día deseado,en caso de estar disponible, y luego del evento dejar un testimonio en la publicación (ej: fotos, videos, comentarios) y calificar al propietario. 
● Nuestros propietarios deben crear las reservas, autorizarlas, modificar las y eliminarlas, además de poder elegir servicios disponibles en el lugar y, de no ser gratuitos, agregarles o modificarles el precio. 

# REQUERIMIENTOS TÉCNICOS A CUMPLIR 

Requerimientos obligatorios comunes a todos: 
● Registro y Login con Spring Security. 
● Crear al menos DOS roles distintos para los usuarios. 
● Incluir tabla html en alguna vista. 
● Carga y actualización de imagen. 
● Crear una Query de búsqueda personalizada.
● Crear un CRUD Que haya al menos un formulario. 
● Crear al menos 3 vistas distintas. 
● Diagrama UML de entidades. 

Requerimientos optativos:
● Motor de búsqueda.
● Tabla con opciones de agregar / modificar y eliminar registros.
● Utilizar atributos booleanos de alta y baja de usuarios (y poder modificarlos). 
● Listado en tabla por filtro (por nombre, fechas, altas o bajas, etc). 
● Generar por lo menos 5 vistas distintas que implementen th:fragments. 
● Generar un dashboard de administración de la app (el rol Administrador tendrá acceso a información que un rol User o Guest no tendría). 
● Aplicar principios de código limpio y buenas prácticas. 
● Añadir diagrama de casos de uso en UML. 

Requerimientos específicos de este proyecto: 
● La app debe contar inicialmente con la posibilidad de crear un perfil de USUARIO CLIENTE o PROPIETARIO. 
● Cada CLIENTE puede crear sus reservas y cancelarlas, hacer posteos de imágenes luego del evento. 
● Un USUARIO PROPIETARIO puede crear y ver sus tiemposde reservas disponibles, modificarlas y eliminarlas. También podrá cancelar las reservas de CLIENTES en su propio calendario. 
● Un ADMIN podrá eliminar posteos, publicaciones y USUARIOS, pero no crearlos ni modificarlos. 
● Tanto PROPIETARIO como CLIENTE generan un perfil propio, con foto, contacto y descripción de su rol en nuestra aplicación. La información de contacto solo debe estar disponible luego de la reserva para ambas partes. 
● Un GUEST (público) puede acceder a la web, ver las propiedades y sus servicios, ver las fechas disponibles para reserva y todo lo que tiene la página, pero no puede realizar reservas a menos que se registre como CLIENTE. 
● Cada PROPIETARIO deberá disponer de un calendario para reservas propio, al que todo CLIENTE pueda acceder y en el que pueda reservar. 
● Las reservas cuentan con un cliente asociado, los servicios contratados, los precios individuales y el total de los mismos. 
● Los servicios deben ser genéricos y los PROPIETARIOS pueden elegir si los brindan o no.
