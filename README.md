# DAI-Ezequiel-Ramis

Fecha de entrega: antes del Jueves 11 de Abril.
 
 
El objetivo de este primer TP es familiarizarnos con el entorno de desarrollo de Android.
 
Vamos a programar una aplicación sencilla, utilizando todo lo recientemente aprendido en los tutoriales, tanto en lo relacionado al ingreso de datos por parte de un usuario, como en las distintas modalidades para mostrar resultados.
 
También vamos desplegar nuestros vastos conocimientos traídos de C# en lo referido a manejo de strings, teniendo en cuenta que en Java es muy parecido, aunque no idéntico.  Al pié de este artículo hay algunas ayuditas muy útiles.
 
Recordemos las directivas básicas:
Comentarios escritos antes de cada bloquecito de código
Nombres descriptivos y en castellano, sin acentos, enies ni espacios
Indentación
Baby Steps (escribir muy poquitas líneas de código y verificar su correcta ejecución antes de seguir codeando)
Celular en la mochila, no en la mano, no en la mesa, no en el bolsillo.
Iniciar sesión de Windows con usuario: devandroid - password: infoandroid
Por último, como siempre, nuestra herramienta mas importante, única e insustituible: el bol de gelatina.
 
 

Por dónde empezamos?
 
1) Leer la consigna hasta el final.
 
2) Abrir Trello, crear un tablero "DAI-TP1" y lo compartimos con LeoLob (aparecen varios "LeoLob", el mío es el único que tiene mi foto).
 
3) Creamos el BackLog, en base a la consigna. Es fundamental que hagamos esto a conciencia.  Si nuestro backlog solo dice "Hacer un TP", claramente no sirve para nada.  En cambio, si enumeran cada uno de los pasos que vamos a seguir para el desarrollo, y cómo vamos a probar cada punto, y cómo lo vamos a entregar, y tal, entonces SIRVE.  Tengamos presente que el correcto diseño del Trello influye en la nota.
4) Arrancamos por el primer paso del BackLog, lo mandamos a "Doing", una vez que esté a "Done", y así hasta que el backlog que vacío y el Done llenito.  
 
La aplicación deberá permitir que el usuario ingrese distintos tipos de información, y, para cada una de ellas, presente los resultados indicados.  
 
Ustedes determinarán como organizar la aplicación, los ingresos y la muestra de resultados, ya que, a diferencia de los forms de C#, notarán rápidamente lo pequeña que resulta la pantalla en una aplicación móvil.   Es imprescindible que todo aspecto visible de nuestra aplicación sea, como mínimo, prolijo y ordenado.
 
Tal vez quieran que en la actividad principal sólo haya botones que llaman a una actividad por tarea, tal vez quieran que hayan controles ocultos que aparezcan cuando sea necesario, o tal vez se les ocurran más opciones.  Todas sirven, siempre que cumplan con lo pedido.
 
 
El usuario debe poder realizar las siguientes tareas:
 
1) Ingresar dos textos (en dos casilleros distintos).  Al presionar un botón mostrar:
 
   a) El largo (cantidad de caracteres) de cada uno de ellos
 
   b) La cantidad de caracteres excedentes del más largo sobre el más corto (si, por ejemplo, el más largo tuviera 10 caracteres y el más corto tuviera 6, entonces el más largo tendría 4 caracteres excedentes sobre el más corto)
 
   c) Los tres primeros caracteres de cada uno de los textos, concatenados entre si. Ejemplo: Si se ingresara "Aguante" y "Android", debería mostrarse "AguAnd"
 
 
2) Ingresar un texto en un casillero, junto al cuál debe haber un botón y un CheckBox.  Al presionar el botón mostrar la cantidad de letras "A" que tiene el texto ingresado.  Si el CheckBox está tildado (con "tildado" no nos referimos a esa odiosa acción que afecta algunos softwares haciéndolos entrar en coma, sino a que el checkbox esté con el tilde colocado), emitir un mensaje de advertencia en caso que el texto ingresado NO tenga más de diez caracteres.  Si no está tildado, aceptar cualquier ingreso.
 
 
3) Ingresar un texto asumiendo que tendrá más de diez caracteres.  Al presionar un botón mostrarlo en forma invertida.  Por ejemplo, si ingresara "Que copado Android!" debería mostrar "!diordnA odapoc euQ".  Si ingresara "eel euq le otuP", debería mostrar…, este…, no, nada, nada.
 
 
 
 
Opcionales:
 
A) En el punto 1) c), que no sean exactamente tres los caracteres a concatenar de cada uno de los dos textos, sino que esa cantidad sea ingresada por el usuario.  Validar la situación de que ambos textos tengan una cantidad de caracteres suficiente para el pedido. 
 
B) En el punto 1) c), que la cantidad de carateres a concatenar se ingrese con una seekBar.
 
C) En el punto 2), que sea el usuario el que ingrese cuál es la letra de la cuál quiere saber la cantidad de apariciones.
 
D) En el punto 3) validar que el texto ingresado tenga la cantidad mínima de caracteres indicada.  Si no lo es, mostrar un mensaje de error.
 
E) En el punto 3) informar si el texto ingresado es capicúa, o sea, si se lee igual de adelante hacia atrás o desde atrás hacia adelante. 
 
F) En el punto 2 poder ingresar las letras en mayúsculas o minúsculas, indistintamente, y que las encuentre igual. Es decir, si se ingresa "AlabAMA", debería decir que hay 4 "A" o 4 "a", indistintamente.
