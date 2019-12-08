# DAI-Ezequiel-Ramis: TP3

Fecha de entrega: ANTES del martes 28 de Mayo

ANTES del martes 4 de Junio

 

Trabajo Práctico 3

El objetivo de este TP es utilizar información pública referida a objetos geográfico de CABA, disponibles en http://epok.buenosaires.gob.ar/docs/index.html. 

Ahí está la documentación completa de uso de todos sus métodos, pero tiene un enorme problema: estudios recientes en la Universidad de South Rocherground indican que la documentación sólo sirve si es leída.  Por tal motivo, no tendremos más remedio que leerla, y al hacerlo, nos enteraremos que esa API tiene cuatro métodos:



Si entramos a cada una, veremos tres cosas fundamentales:

- Cómo invocarla?

- Qué parámetros requiere que le envíemos?

- Cómo es la estructura del JSON que nos devuelve?

Por ejemplo, si entramos a la primera, la que venimos usando en el tutorial, la "getCategorias", vemos:



Ahí podemos ver que la API se llama en http://epok.buenosaires.gob.ar/getCategorias, o sea, la URL indicada en el connection, y el método invocado en el request.

Por qué puse el "http://", si en la ruta no aparece?  Porque vemos que la conección es de tipo HTTP (HTTPConnection).   Y por qué NO pongo el .HTML final?  Porque NO estoy pidiendo algo en HTML (cómo sería una página WEB normal).  Tengamos en cuenta también que, por lo general, las rutas son case sensitive.

También vemos que este método en particular NO requiere parámetros.  Si los requiriera irían luego del getCategorias, como por ejemplo, /getCategorias/?Barrio=Paternal.   En los otros métodos de esta API podemos ver ejemplos en los que sí se requieren parámetros, algunos obligatorios y otros no.

Por último, vemos cómo es el JSON que devuelve, y entendemos su estructura.

Podemos usar la API sin haber leído cuidadosamente su documentación, entendido cómo funcionan sus método y que devuelven?  No way, man! Por lo tanto, para usar los otros tres métodos que tiene esa API, debemos obligatoriamente leer su documentación y entenderla.  

 

Dado que en el Labo no tenemos Postman, podemos usar un JSON Viewer online.  Existen muchos, uno de los cuáles es:

https://jsoneditoronline.org/

En esa web debemos copiar y pegar (sí, acá se puede, si) el JSON leído. Es decir, leemos el JSON crudo en un navegador, lo pegamos en esta WEB, y listo.

 

La aplicación debe iniciar con un menú de opciones, implementado con botones, para que el usuario elija lo que quiere hacer entre las siguientes opciones:

1) Buscar objetos por categoría: debe mostrar una lista de categorías, y al elegir una mostrar los nombres de los objetos de esa categoría.  De dónde obtenemos ese JSON?  Tal como indica en la documentación de esa API, la lista de categorías la obtenermos de de http://epok.buenosaires.gob.ar/getCategorias.  Una vez que el usuario eligió una categoria, para buscar los elementos de esa categoría necesito usar otro método distinto de la misma API, llamado "buscar".  Según vemos su documentación (que debemos leer, porque adivinar puede ser incómodo), ese método requiere un parámetro "texto", de forma que su URL sería algo así: http://epok.buenosaires.gob.ar/buscar/?texto=comisaria Obviamente el parámetro "comisaría" va a ser variable, de forma que la URL se va a armar en tiempo de ejecución en base a lo que el usuario haya elegido o ingresado.

2) Buscar objetos por nombre: debe pedir el ingreso del texto a buscar, y mostrar los nombres de objetos que tengan ese nombre.  De dónde obtenemos ese JSON?  Habrá que leer documentación en http://epok.buenosaires.gob.ar/docs/index.html

3) Buscar objetos por geolocalización: debe pedir el ingreso de dos coordenadas (x e y) y un radio, y mostrar los objetos encontrados en esa área.

 

Activamos el bol de gelatina, creamos un nuevo tablero en Trello, le ponemos como nombre DAI-TP3-5IX-Apellido-Apellido (Por ejemplo, DAI-TP3-5IB-Macri-Kirchner), y detallamos un bonito y super completo backlog.

 

Opcionales:

A) Que el listado resultante de la búsqueda pueda ser paginado, es decir, que la lista de resultados no se muestre completa, sino los primeros 10 resultados, y haya botón de "Página siguiente" y "Página anterior"

B) Que en el 3) el radio se seleccione por medio de una seekBar.

C) Que no sea necesario presionar un botón para procesar, sino que el usuario pueda tocar directamente un elemento de la lista.

D) Qué el usuario pueda elegir cuántos elementos obtener, en vez de que les llegue la lista entera.

E) Que en caso que la lista de resultados venga vacía, la lista NO se muestre, sino que aparezca un mensaje "Su búsqueda no arrojó resultados".

F) Que haya una lista de "Últimas búsquedas realizadas".  Obviamente, cómo aun no sabemos BD, esta lista se pierde al frenar la aplicación.
