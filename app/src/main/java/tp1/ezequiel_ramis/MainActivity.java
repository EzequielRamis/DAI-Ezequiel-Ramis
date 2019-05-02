package tp1.ezequiel_ramis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int captchaRes;
    int captchaN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Captcha*/
        captchaN= new Random().nextInt(10);
        captchaRes= new Random().nextInt(10);

        TextView suma = findViewById(R.id.SumaTV);
        suma.setText(captchaRes-captchaN + " + " + captchaN);
    }

    public void game(View v){
        /*Definir variables*/
        EditText nombre=findViewById(R.id.NombreET);
        EditText res=findViewById(R.id.ResET);
        Toast toast1= Toast.makeText(getApplicationContext(), "No se ingresó todos los campos", Toast.LENGTH_SHORT);
        Toast toast2= Toast.makeText(getApplicationContext(), "Resultado invalido", Toast.LENGTH_SHORT);

        /*Validación de los campos*/
        if(nombre.getText().toString().equals("") || res.getText().toString().equals("")) {toast1.show(); return;}
        if(!res.getText().toString().equals(""+captchaRes)) {toast2.show(); return;}

        /*Enviar datos a la GameActivity*/
        Bundle PaqueteDeDatos=new Bundle();
        PaqueteDeDatos.putString("nombreIngresado", nombre.getText().toString());

        Intent MainToGame=new Intent(MainActivity.this, GameActivity.class);
        MainToGame.putExtras(PaqueteDeDatos);
        startActivity(MainToGame);
    }
}
