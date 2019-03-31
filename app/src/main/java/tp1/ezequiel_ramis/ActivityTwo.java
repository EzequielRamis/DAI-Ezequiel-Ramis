package tp1.ezequiel_ramis;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
    }

    public void two_showResults(View v){
        /*Definición de variables*/
        int cantAs = 0;
        EditText editText=findViewById(R.id.two_editText);
        CheckBox checkBox=findViewById(R.id.two_checkBox);
        TextView textView=findViewById(R.id.two_res);

        /*Punto Opcional: Definir variable EditText de un char*/
        EditText editTextChar=findViewById(R.id.two_editTextChar);
        if(editTextChar.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "No ingresó caracter a buscar", Toast.LENGTH_LONG).show();
            return;
        }
        char charInput = editTextChar.getText().toString().toLowerCase().toCharArray()[0];

        /*Checkear la cant de caracteres*/
        if(checkBox.isChecked() && editText.getText().length() <= 10){
            Toast.makeText(getApplicationContext(), "Ingreso inválido: No tiene más de diez caracteres", Toast.LENGTH_LONG).show();
            return;
        }
        /*Si el if anterior es falso sigue el algoritmo*/
        /*Calcular cant de CARACTER INGRESADO en el texto*/
        for(char ch : String.valueOf(editText.getText()).toLowerCase().toCharArray()){
            /*Si el caracter es igual sumarle a cant 1, si no 0*/
            cantAs+= (ch == charInput) ? 1 : 0;
        }

        /*Mostrar cant de As*/
        textView.setText("Cantidad de '"+ charInput+"' en el texto: " + cantAs);
    }
}
