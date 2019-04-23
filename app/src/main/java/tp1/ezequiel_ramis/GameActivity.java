package tp1.ezequiel_ramis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

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

        btnArray[0] = findViewById(R.id.Btn1);
        btnArray[1] = findViewById(R.id.Btn2);
        btnArray[2] = findViewById(R.id.Btn3);
        btnArray[3] = findViewById(R.id.Btn4);
        btnArray[4] = findViewById(R.id.Btn5);
        btnArray[5] = findViewById(R.id.Btn6);
        btnArray[6] = findViewById(R.id.Btn7);
        btnArray[7] = findViewById(R.id.Btn8);
        btnArray[8] = findViewById(R.id.Btn9);

        for (ImageButton btn:btnArray) {
            btn.setClickable(false);
            btn.setEnabled(false);
        }

    }
}
