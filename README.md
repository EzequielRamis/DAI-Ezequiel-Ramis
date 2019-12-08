# DAI-Ezequiel-Ramis: TP9

Fecha de entrega: En forma presencial, durante el horario de clases.

Como máximo el jueves 21 de Noviembre

 

La consigna de este TP es amplia y genérica, pero simple: hacer un juego en Android utilizando el framework Cocos2D, que tenga imágenes en movimiento que generen alguna clase de acción.  Puede ser de navecitas, de carreras de autos, de deportes, o de cualquier cosa que puedan inventar.  De hecho, cuanta más inventiva y originalidad, mejor será la calificación.

 

Este juego debe cumplir con todas las siguientes características:

 

Debe tener una jugabilidad atractiva. Esto quiere decir que tiene que tener alguna clase de desafío para el jugador, con alguna clase de dificultad que lo haga divertido e interesante.
 

Debe tener alguna clase de dificultad creciente.   Debe implementar, de alguna forma, que a medida que el juego avance, haya alguna clase de complicación para el jugador.  Esta complejidad creciente puede ser por niveles, por puntos, por tiempo, o como quieran.
 

Tiene que implementar alguna clase de puntaje, de forma que el jugador pueda comparar distintas partidas en cuanto su desempeño.
 

Debe tener colisiones.  Debe haber situaciones en las que dos o más sprites se toquen y esto genere alguna situación, ya sea buena (porque se trate de agarrar objetos) o mala (porque se trate de esquivarlos).
 

Tiene que tener una pantalla de inicio o bienvenida (la famosa “Splash Screen”), y luego del juego en sí, alguna clase de “Game Over” con un “Volver a jugar”
 

Tiene que tener sonidos: alguna música de fondo, y sonidos para los disparos, o movimientos, o impactos, o colisiones.
 

Opcionalmente, tiene que tener alguna animación para los movimientos: persona corriendo, o caminando, o saltando, o navecita a la que se le mueven las alas, o las hélices, o el motor, o auto al que se le mueven las ruedas, etc.
 

Pasos a seguir para este desarrollo:

1) Determinar el concepto del juego: modalidad, tipo de juego, tipo de comandos, objetivo, obstáculos, dificultad, etc.

2) Diseñar la pantalla (en papel, el Photoshop, o en lo que quieran, pero NO en Android), de forma que se pueda ver con claridad qué es lo que va a ver el jugador.  Tener en cuenta los tamaños de los objetos del juego en sí, espacios entre elementos, distancias, y tal.

3) Diseñar (también en papel, NO en Android), el esquema general del esqueleto de programación: cuáles capas va a tener, qué elementos van a ir en cada capa, cómo va a ser la arquitectura general del juego, schedules, rutinas, y tal.  La idea es que NO se pongan a pensar sobre la marcha, sino que tengan claro desde antes cómo va a estar diagramado.

4) Presentarme estos tres elementos, y requerir mi aprobación.  NO avanzar sin tenerla.

5) Recién entonces, comenzar a programar.

6) Condición fundamental para aprobarlo: el correcto uso de Logs.   No solo puestos de dónde deben ir, sino que sean lo suficientemente claros para que se entienda qué es lo que pasó, qué es lo que se está ejecutando, cuánto valen las variables que estamos evaluando, etc.

 

Empezar por la escena del juego.   Luego agregaremos la pantalla inicial, la de Game Over,  tal.  Programar con Baby Steps. 

Importantísimo: NO deducir lo que hace el código en base a lo que vemos en la ejecución.  Eso nos lleva a sacar conclusiones poco confiables.  Ejemplo: "Es OBVIO que no ejecuta está línea que pone un Sprite en la pantalla, porque el Sprite no aparece en la pantalla".  

Bueno, no, no es obvio, porque tal vez SÍ lo pone en la pantalla, pero una posición incorrecta, o detrás de otro Sprite, y por lo tanto, estamos horas asumiendo que no ejecuta una cierta instrucción que SÍ ejecuta.   

La única forma que sirve son los Logs.  Si no usan Logs, no voy a poder ayudarlos a resolver lo que les falle, simplemente porque no tengo forma de saber qué hace el código.  Y que los Logs sean claros y legibles.   Si ponen cualquier texto en los Log, solo para hacerlo porque yo lo pido, no sirve para nada, y entonces es como no haberlos puesto.  El Log debe tener un Tag claro, que indique el sector de código en el que está, y un texto claro que indique exactamente dónde está, y muestre información de variables en proceso.

 

Importante: es condición obligatoria para aprobar este TP que sea subido al Google Play.  Ver este tema con Dami Asman o con Eze Binker.
