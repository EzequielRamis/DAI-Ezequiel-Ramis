package tp1.ezequiel_ramis;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    ImageButton[] btnArray=new ImageButton[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        Bundle DatosRecibidos = this.getIntent().getExtras();
        String nombreRecibido = DatosRecibidos.getString("nombreIngresado");

        TextView nombre = findViewById(R.id.HolaNombre);
        nombre.setText("Hola, " + nombreRecibido + "!");

        btnArray[0] = findViewById(R.id.Btn0);
        btnArray[1] = findViewById(R.id.Btn1);
        btnArray[2] = findViewById(R.id.Btn2);
        btnArray[3] = findViewById(R.id.Btn3);
        btnArray[4] = findViewById(R.id.Btn4);
        btnArray[5] = findViewById(R.id.Btn5);
        btnArray[6] = findViewById(R.id.Btn6);
        btnArray[7] = findViewById(R.id.Btn7);
        btnArray[8] = findViewById(R.id.Btn8);

        for (ImageButton btn:btnArray) {
            btn.setClickable(false);
            btn.setEnabled(false);
        }
    }

    protected void randomArray(){
        for(ImageButton btn:btnArray){
            if (new Random().nextBoolean()) btn.setImageResource(R.drawable.ima01);
            else btn.setImageResource(R.drawable.ima02);
        }
    }

    protected void btnClicked(View view){
        ImageButton btnClicked = (ImageButton) view;
        int tag = Integer.parseInt(btnClicked.getTag().toString());
        updateArray(tag);
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
        switch (n){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
        }
    }
}
