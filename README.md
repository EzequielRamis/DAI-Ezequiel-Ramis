# DAI-Ezequiel-Ramis: TP4

Breaking news!!!

Debido a ciertas actualizaciones de 💩, los Google 🗺 no están funcionando.  De hecho, creo un proyecto nuevo, le agrego una Google Map Activity, y la App 💥. 

Por ese motivo, el TP4 quedó completamente desvirtuado, y pasó a ser 🤮.

Así las cosas, vamos a terminarlo como se pueda, lo mejor posible, pero sin mapas.  No tiene sentido agregar más clases, prefiero que los resultados se muestren en horribles listas, y dejar que el TP ⚰️.

El martes 2 de Julio arrancaremos con TP5, que será 🎉, ya que incorporará los famosos Constraints Layout, y ListView en serio, tutoriales mediante.

 

Fecha de entrega: ANTES del martes 2 de Julio

 

Trabajo Práctico 4

El objetivo de este TP es aplicar todo lo aprendido sobre Fragments.  Por ese motivo, vamos a mejorar y completar el TP3, haciendo que ya no tenga distintas Activities para cada cosa, sino una única anfritriona que vaya mostrando los distintos elementos a medida que el usuario los necesite.

La App debe tener las mismas funcionalidad y característas que el TP3, pero con un agregado: la opción de buscar elementos por geolocalización ya no debe pedir el ingreso de las coordenadas por medio de horribles EditText, sino mostrar un mapa real para que el usuario elija en forma visual, tocando con el dedo (en realidad, que toque con lo que quiera) en el mapa, y eligiendo el radio por medio de un seekBar.

El tutorial ya está disponible.  Si bien aún no está cómo hacerlo con Fragments, se puede hacer en una Activity a la vieja usanza.  Así que para el punto en que el usuario debe elegir un punto haremos que lo señale en el mapa.  Y una vez que obtenemos un conjunto de resultados (en cualquiera de los tres puntos de búsqueda), los resultados deben mostrarse como marquitas en el mapa.  

Pero, atención: resulta que la API de EPOK, al devolvernos la info de cada punto, nos proporciona la ubicación geográfica en un formato rato, que no es compatible con un LatLng de Google Maps, o con coordenadas de latitud y longitud como se usa en casi todos lados.  Pero nos devuelve el domicilio postal del lugar.

Por lo tanto, tenemos que hacer un paso más: dado el domicilio, obtener su geolocalización.   Cómo?  Con otra API, totalmente distinta: http://servicios.usig.buenosaires.gob.ar/normalizar

Según informa su documentación, esa API tiene algunos métodos para normalizar calles, o geocodificarlas.  Por ejemplo, la siguiente URL me permite obtener las coordenadas dada una dirección: 

http://servicios.usig.buenosaires.gob.ar/normalizar/?direccion=yatay 240, caba&geocodificar=true

Al igual que siempre, habrá que leer cuidosamente la documentación para ver cómo usarla y qué devuelve.

Entonces, como final del TP4, ya sea que el usuario busque elementos eligiéndolos de una lista de categorías, ya sea que escriba lo que quiere buscar en un campo de texto, o ya sea que los elija tocando la pantalla (preferentemente con el dedo), el resultado final es que en un mapa aparezca una chicheta marquita en cada lugar, indicando el nombre de ese elemento.

Opcionales

Ya que estamos embalados con mapas y APIs, aprovechemos para usar otra API bonita:

https://datosgobar.github.io/georef-ar-api/

Esta API contiene información normalizada de datos geográficos de Argentina, como provincias y municipios, todo con sus geolocalizaciones.

Entonces, vamos a agregar una nueva opción: aparece un listado con los nombres de las Provincias (obtenidas de esta API, obviamente), y cuando el usuario elige una se muestra en un mapa todos los municipios de esa Provincia, debidamente señalados.
