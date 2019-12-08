# DAI-Ezequiel-Ramis: TP6

Fecha de entrega (Actualizado):

- Antes del martes 24-Sep

 

Trabajo Práctico 6

En este TP usaremos todos los conocimientos adquiridos acerca del uso de los Microsoft Cognitive Services para reconocimiento facial, cuyo tutorial está aquí: 

https://campus.almagro.ort.edu.ar/informatica/articulo/1129324/reconocimiento-facial-en-android

Pero ojo, no vamos a usar solamente el médoto detect, explicado en el tutorial, sino otros disponibles en la misma API, cuya documentación completa está aquí:

https://westus.dev.cognitive.microsoft.com/docs/services/563879b61984550e40cbbe8d/operations/563879b61984550f30395236

 

Vamos a hacer una app que permita ir recolectando información facial a través de la cámara, sacándole fotos a distintas personas y generando un pequeño informe estadístico sobre los resultados.

Cómo venimos haciendo ultimamente, el diseño y arquitectura de la aplicación lo harán ustedes, pero el objetivo es que el usuario pueda elegir entre tomar una foto, elegir una foto de la galería, o ver los resultados estadísticos.   

El usuario debe poder elegir si quiere sacar fotos con la cámara o elegirlas de la galería, y debe poder elegir cuáles atributos de la foto quiere que sean analizados, eligiéndolos entre cuatro o cinco.  Es decir, que haya algunos obligatorios (Edad y Género, por ejemplo), y otros que el usuario puede o no querer analizar: Barba, Sonrisa, estados de ánimo, etc.

Qué información estadística mostrar?  Lo dejo a vuestro criterio, pero que sea algo copado.  No solo un "cantidad de hombres y cantidad de mujeres", eso sería muy básico, demasiado parecido al tutorial.

Prefiero algo más imaginativo, como "qué porcentaje de personas fotografiadas estaban sonriendo", o "cuántos de los hombres que tienen mucha barba tienen poco pelo", o "cuántas persons aparecen en más de una foto", o "qué porcentaje de las personas que aparecen en fotos con varias personas no sonríen", o tal.

Cuanto más original sea la estadística, mejor será la nota del TP.   Miren en la documentación cuáles son los parámetros de reconocimiento que el servicio detecta, elijan cuatro o cinco variables que les gusten, y armen la estadística al respecto.   

Cómo se debe ver esa estadística?  Cómo quieran, en una Activity separada, o con Fragments, o con un AlertDialog, o lo que les guste, pero que sea prolijo y profesional.

Algunos opcionales:

1) Que las caras detectadas no se enmarquen en forma completa, sino solamente las esquinas de dicho rectángulo.

2) Que en las fotos en que hay más de una cara, al mostrar la información detectada se indique a cuál cara pertenece.  Un ejemplo sería que cada marco sea de distinto color, y se indique cuál dato corresponder a cuál color

3) Que luego del procesamiento de una foto el usuario pueda elegir si incorporarla a la estadística, o desecharla.

4) Que el usuario tenga una opción de vaciar las estadísticas completas para arrancar desde cero.

5) Que el usuario puede elegir que cierto rasgos faciales sean marcados en la foto: ojos, nariz, etc.  Esos rasgos deben marcarse con colores sobre la foto original.
