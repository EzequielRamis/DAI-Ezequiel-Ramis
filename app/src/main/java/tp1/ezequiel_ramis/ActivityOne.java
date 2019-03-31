package tp1.ezequiel_ramis;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityOne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

            }
    public void one_showResults(View v){
        /*Definición de variables*/
        EditText editTextOne, editTextTwo;
        TextView resAOne, resATwo, resB,resC;
        SeekBar seekBar = findViewById(R.id.one_seekBar);
        editTextOne= findViewById(R.id.one_editTextOne);
        editTextTwo=findViewById(R.id.one_editTextTwo);
        resAOne=findViewById(R.id.one_resAOne);
        resATwo=findViewById(R.id.one_resATwo);
        resB=findViewById(R.id.one_resB);
        resC=findViewById(R.id.one_resC);

        /*Punto Opcional: Definir variable para el punto C*/
        int maxLength=seekBar.getProgress();

        /*Checkear la cant de caracteres*/
        if(editTextOne.getText().length() < maxLength || editTextTwo.getText().length() < maxLength){
            Toast.makeText(getApplicationContext(), "Ingreso inválido: Algun texto ingreado no tiene más de "+maxLength+" caracteres", Toast.LENGTH_LONG).show();
            return;
        }

        /*Mostar resultados del punto A:           Convertir a string - Obtener el largo del texto*/
        resAOne.setText("Largo del primer texto: " + String.valueOf(editTextOne.getText().length()));
        resATwo.setText("Largo del segundo texto: " + String.valueOf(editTextTwo.getText().length()));

        /*Mostrar resultado del punto B            Convertir a String - Convertir a valor absoluto la diferencia entre los n° obtenidos*/
        resB.setText("Cant. caract. excedentes: " + String.valueOf(Math.abs(editTextOne.getText().length()-editTextTwo.getText().length())));

        /*Mostrar resultado del punto C            Convertir a String - Concatenar los Substrings de ambos textos*/
        resC.setText("Combinación de los textos: " + String.valueOf(editTextOne.getText()).substring(0,maxLength)+String.valueOf(editTextTwo.getText()).substring(0,maxLength));
    }
}
