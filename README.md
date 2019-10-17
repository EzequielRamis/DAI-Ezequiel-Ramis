# DAI-Ezequiel-Ramis

Entrega: antes del jueves 3-oct.

(Si, correcto, solo dos bloques)

El objetivo de este primer TP es solamente hacer un práctica liviana, inicial y superficial.  No pretende ninguna funcionalidad realmente útil, sino solamente didáctica.

 

Hacer una aplicación en Android con Cocos2D en la que, cada tres segundos, aparezca una imagen en un punto de la pantalla al azar de la pantalla, y automáticamente se desplace hacia el vértice más lejano.

 

De paso, recordemos algunos pequeños tips:

- Si queremos que la aplicación use la pantalla completa, o sea que no muestre los íconos de batería, intensidad del WiFi, y tal, en el onCreate de la Activity (NO AppCompatActivity) principal ponemos:

getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
 

- Si queremos que la aplicación NO muestre la barra de título (esa en la que aparece el nombre de la aplicación y su ícono), en el onCreate de la Activity principal ponemos:

requestWindowFeature(Window.FEATURE_NO_TITLE);
 

- Si queremos que la aplicación no permita la rotación de la pantalla (es decir, la rotación física no se puede impedir, pero podemos hacer que Android ignore esa acción del humano, y el celular se comporte cómo permaneciera en la misma posición), en el onCreate de la Activity principal ponemos:

setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
