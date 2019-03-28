package tp1.ezequiel_ramis;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityOne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

            }
    public void showResults(View v){
        /*Definición de variables*/
        EditText editTextOne, editTextTwo;
        TextView resAOne, resATwo, resB,resC;
        editTextOne= findViewById(R.id.editTextOne);
        editTextTwo=findViewById(R.id.editTextTwo);
        resAOne=findViewById(R.id.ResAOne);
        resATwo=findViewById(R.id.ResATwo);
        resB=findViewById(R.id.ResB);
        resC=findViewById(R.id.ResC);

        /*Mostar resultados del punto A*/
        resAOne.setText("Largo del primer texto: " + String.valueOf(editTextOne.getText().length()));
        resATwo.setText("Largo del segundo texto: " + String.valueOf(editTextTwo.getText().length()));

        /*Mostrar resultado del punto B*/
        resB.setText("Cant. caract. excedentes: " + String.valueOf(Math.abs(editTextOne.getText().length()-editTextTwo.getText().length())));

        /*Mostrar resultado del punto C*/
        resC.setText("Combinación de los textos: " + String.valueOf(editTextOne.getText()).substring(0,3)+String.valueOf(editTextTwo.getText()).substring(0,3));
    }
}
