# DAI-Ezequiel-Ramis: TP5

Fecha de entrega: antes del martes 13 de Agosto

 

Trabajo Práctico 5

En este TP vamos a juntar nuestros conocimientos de Fragments, ListView, AsyncTask, JSon, APIs y Constraints Layout.  De paso, un poquito de Gson y descargas de imágenes de Internet.  

Vamos a hacer una app que permita obtener información sobre películas, utilizando una API pública llamada OMDb (Open Movie Database).   

Para poder usarla debemos registrarnos.  La versión gratuita nos permite 1000 request por día, así que servirá.  Nos registramos en http://www.omdbapi.com/apikey.aspx, y esperamos que nos manden la API Key a nuestro mail.  Esa API Key deberemos mandarla en cada request.

Para usar esa API deberemos hacer algo terrible: leer la documentación.   Al hacerlo, descubriremos que tiene dos formas de uso:

Por Id o Titulo, o por búsqueda.  

El acceso por "búsqueda" espera que le mandemos un string con el título a buscar, y nos devuelve todas las ocurrencias que encontró, con algunos datos de cada una, incluyendo la ID, el título y el poster.

Por ejemplo:

http://www.omdbapi.com/?apikey=1122334455&s=toy story

En este ejemplo deberemos reemplazar el "1122334455" por la API Key recibida.  Y nos devolverá todas las películas que tengan "Toy Story" en el título.  De cada una nos devolverá una Id, además de otros datos como el título completo, el año y la URL del poster.

El acceso por Id o Titulo espera que le mandemos una de las dos (Id o Titulo), y nos devuelve información sobre esa película, incluyendo el año de su estreno, el nombre del director, un resumen del argumento, y la URL del poster de la película.   Si le mandamos una Id o título que no existe, nos lo hará saber.

Entonces, haremos una bonita App, cuyo diseño queda completamente a vuestro criterio, cuyo objetivo será que el usuario pueda tipear un título (o parte de él) y le aparezca un listado con todas las películas que la API devuelva para esa búsqueda. 

Ese listado debe mostrar el título de la película, su año y su póster.   

Una vez que el usuario toca en una película del listado, deberán aparecerle los detalles: título, poster, año, director, resumen, etc.

Ni tengo que aclarar que el diseño debe ser completamente responsivo (aguanten los Constraints Layout), la app debe tener una sola Activity (aguanten los Fragments), las imágenes se descargan en tiempo de ejecución (aguanten los AsyncTask), la información debe leerse de una API (aguante Gson) y debe mostrarse en un ListView (aguanten los idem).
