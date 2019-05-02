package tp1.ezequiel_ramis;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class GameActivity extends AppCompatActivity {

    /*Declaración de variables globales*/
    ImageButton[] gameBtnArray=new ImageButton[9];
    Button[] btnArray=new Button[3];
    int playsN;
    boolean hasWon;
    TextView win;
    int iGMP;
    List<Integer> btnArrayGMP= new ArrayList<>();
    int size;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        /*Mostrar nombre de usuario*/
        Bundle DatosRecibidos = this.getIntent().getExtras();
        String nombreRecibido = DatosRecibidos.getString("nombreIngresado");

        TextView nombre = findViewById(R.id.HolaNombre);
        nombre.setText("Hola, " + nombreRecibido + "!");

        /*Definir variables*/
        gameBtnArray[0] = findViewById(R.id.Btn0);
        gameBtnArray[1] = findViewById(R.id.Btn1);
        gameBtnArray[2] = findViewById(R.id.Btn2);
        gameBtnArray[3] = findViewById(R.id.Btn3);
        gameBtnArray[4] = findViewById(R.id.Btn4);
        gameBtnArray[5] = findViewById(R.id.Btn5);
        gameBtnArray[6] = findViewById(R.id.Btn6);
        gameBtnArray[7] = findViewById(R.id.Btn7);
        gameBtnArray[8] = findViewById(R.id.Btn8);

        btnArray[0]=findViewById(R.id.BtnNormal);
        btnArray[1]=findViewById(R.id.BtnAzar);
        btnArray[2]=findViewById(R.id.BtnGPM);

        playsN=-1;

        enableGame(false);

        win=findViewById(R.id.HasWon);
    }

    protected void enableGame(boolean enable){          //Habilitar o deshabilitar botones del juego
        for (ImageButton btn:gameBtnArray) {
            btn.setClickable(enable);
            btn.setEnabled(enable);
        }
    }

    protected void enableBtn(boolean enable){           //Habilitar o deshabilitar botones de empezar partida
        for (Button btn:btnArray) {
            btn.setClickable(enable);
            btn.setEnabled(enable);
        }
    }

    protected void randomArray(){                       //Desordenar array de botones para reiniciar el juego
        for(ImageButton btn:gameBtnArray){
            if (new Random().nextBoolean()) btn.setImageResource(R.drawable.ima01);
            else btn.setImageResource(R.drawable.ima02);
        }
    }

    public void partidaNormal(View view){
        win.setText("");
        randomArray();
        enableGame(true);       //Se resetea el juego
        enableBtn(false);
        playsN=-1;
        updatePlays();
    }

    public void partidaAlAzar(View view){
        win.setText("");
        randomArray();                      //Se resetea el juego
        enableBtn(false);
        playsN=-1;
        updatePlays();
        final Timer MiReloj=new Timer();
        TimerTask RandomGame=new TimerTask() {
            @Override
            public void run(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateArray(new Random().nextInt(9));       //Se elige un boton al azar y se verifica la grilla
                        updatePlays();
                        if(verifyGrid()){
                            MiReloj.cancel();
                            winGame();
                        }
                    }
                });
            }
        };
        MiReloj.schedule(RandomGame, 500, 250);
    }

    public void partidaGMP(View view){
        win.setText("");
        randomArray();
        enableBtn(false);               //Se resetea el juego
        playsN=-1;
        updatePlays();
        btnArrayGMP=getBtnArrayGMP();
        size=btnArrayGMP.size();                //Se pide una lista con referencias a los botones con la imagen "menos comun", y se pide su tamaño
        iGMP=0;
        final Timer MiReloj=new Timer();
        TimerTask RandomGame=new TimerTask() {
            @Override
            public void run(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(iGMP<size){
                            updateArray(btnArrayGMP.get(iGMP));         //Se actualizan los botones obtenidos dependiendo del tamaño de la lista
                            updatePlays();
                            if(verifyGrid()){
                                MiReloj.cancel();
                                iGMP=0;                                 //Se verifica la grilla
                                winGame();
                            }
                            iGMP++;
                        }
                        else{
                            iGMP=0;                                     //Si se recorrió toda la lista, se resetean los valores de la lista, su tamaño y la variable index
                            btnArrayGMP.clear();
                            btnArrayGMP=getBtnArrayGMP();
                            size=btnArrayGMP.size();
                        }
                    }
                });
            }
        };
        MiReloj.schedule(RandomGame, 500, 500);
    }

    protected List<Integer> getBtnArrayGMP(){
        List<Integer> btnArray01=new ArrayList<>();
        List<Integer> btnArray02=new ArrayList<>();

        Drawable.ConstantState codeIma01 = getDrawable(R.drawable.ima01).getConstantState();

        for(ImageButton btn:gameBtnArray){                                                      //Se rellenan las listas con el tipo de boton
            Drawable.ConstantState codeBtn = btn.getDrawable().getConstantState();
            if(codeBtn==codeIma01) btnArray01.add(Integer.parseInt(btn.getTag().toString()));
            else btnArray02.add(Integer.parseInt(btn.getTag().toString()));
        }

        if(btnArray01.size()<btnArray02.size()) return btnArray01;                              //Se compara el tamaño de cada uno de las listas y se retorna el más chico
        else return btnArray02;
    }

    public void btnClicked(View view){
        ImageButton btnClicked = (ImageButton) view;
        int tag = Integer.parseInt(btnClicked.getTag().toString());
        updateArray(tag);
        updatePlays();
        hasWon=verifyGrid();
        if(hasWon) winGame();
    }

    protected void winGame(){
        win.setText("¡Ganaste!");
        enableGame(false);
        enableBtn(true);
    }

    protected void updatePlays(){
        TextView playsNTV = findViewById(R.id.PlaysN);
        playsN++;
        playsNTV.setText("Cantidad de jugadas: " + playsN);
    }

    protected void invertBtn(ImageButton[] array){
        Drawable.ConstantState codeIma01 = getDrawable(R.drawable.ima01).getConstantState();
        for(ImageButton btn:array){
            Drawable.ConstantState codeBtn = btn.getDrawable().getConstantState();
            if (codeIma01==codeBtn) btn.setImageResource(R.drawable.ima02);
            else btn.setImageResource(R.drawable.ima01);
        }
    }

    protected void updateArray(int n){
        ImageButton[] arrayToUpdate;
        if(n==4) arrayToUpdate = new ImageButton[5];
        else arrayToUpdate = new ImageButton[4];
        switch (n){                                             //En cada caso se agregan los botones a modificar en un array aparte, y luego se invierten los valores de cada uno
            case 0:
                arrayToUpdate[0]=findViewById(R.id.Btn0);
                arrayToUpdate[1]=findViewById(R.id.Btn1);
                arrayToUpdate[2]=findViewById(R.id.Btn3);
                arrayToUpdate[3]=findViewById(R.id.Btn4);
                break;
            case 1:
                arrayToUpdate[0]=findViewById(R.id.Btn0);
                arrayToUpdate[1]=findViewById(R.id.Btn1);
                arrayToUpdate[2]=findViewById(R.id.Btn2);
                arrayToUpdate[3]=findViewById(R.id.Btn4);
                break;
            case 2:
                arrayToUpdate[0]=findViewById(R.id.Btn1);
                arrayToUpdate[1]=findViewById(R.id.Btn2);
                arrayToUpdate[2]=findViewById(R.id.Btn4);
                arrayToUpdate[3]=findViewById(R.id.Btn5);
                break;
            case 3:
                arrayToUpdate[0]=findViewById(R.id.Btn0);
                arrayToUpdate[1]=findViewById(R.id.Btn3);
                arrayToUpdate[2]=findViewById(R.id.Btn4);
                arrayToUpdate[3]=findViewById(R.id.Btn6);
                break;
            case 4:
                arrayToUpdate[0]=findViewById(R.id.Btn1);
                arrayToUpdate[1]=findViewById(R.id.Btn3);
                arrayToUpdate[2]=findViewById(R.id.Btn4);
                arrayToUpdate[3]=findViewById(R.id.Btn5);
                arrayToUpdate[4]=findViewById(R.id.Btn7);
                break;
            case 5:
                arrayToUpdate[0]=findViewById(R.id.Btn2);
                arrayToUpdate[1]=findViewById(R.id.Btn4);
                arrayToUpdate[2]=findViewById(R.id.Btn5);
                arrayToUpdate[3]=findViewById(R.id.Btn8);
                break;
            case 6:
                arrayToUpdate[0]=findViewById(R.id.Btn3);
                arrayToUpdate[1]=findViewById(R.id.Btn4);
                arrayToUpdate[2]=findViewById(R.id.Btn6);
                arrayToUpdate[3]=findViewById(R.id.Btn7);
                break;
            case 7:
                arrayToUpdate[0]=findViewById(R.id.Btn4);
                arrayToUpdate[1]=findViewById(R.id.Btn6);
                arrayToUpdate[2]=findViewById(R.id.Btn7);
                arrayToUpdate[3]=findViewById(R.id.Btn8);
                break;
            case 8:
                arrayToUpdate[0]=findViewById(R.id.Btn4);
                arrayToUpdate[1]=findViewById(R.id.Btn5);
                arrayToUpdate[2]=findViewById(R.id.Btn7);
                arrayToUpdate[3]=findViewById(R.id.Btn8);
                break;
        }
        invertBtn(arrayToUpdate);
    }

    protected boolean verifyGrid(){                                                                 //Se usa el primer boton como referencia
        Drawable.ConstantState codeComparator = gameBtnArray[0].getDrawable().getConstantState();
        for(ImageButton btn:gameBtnArray){
            if(btn==gameBtnArray[0]) continue;
            if(codeComparator != btn.getDrawable().getConstantState()) return false;                //Si algun boton es diferente al primer boton directamente se retorna falso, ya que no todos los botones son iguales
        }
        return true;
    }
}
