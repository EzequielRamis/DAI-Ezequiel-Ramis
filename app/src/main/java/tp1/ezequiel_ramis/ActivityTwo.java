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
        EditText editText;
        CheckBox checkBox;
        TextView textView;
        editText=findViewById(R.id.two_editText);
        checkBox=findViewById(R.id.two_checkBox);
        textView=findViewById(R.id.two_res);

        /*Checkear la cant de caracteres*/
        if(checkBox.isChecked() && editText.getText().length() <= 10){
            Toast.makeText(getApplicationContext(), "Ingreso inválido: No tiene más de diez caracteres", Toast.LENGTH_LONG).show();
            return;
        }

        /*Calcular cant de As*/
        for(char ch : String.valueOf(editText.getText()).toLowerCase().toCharArray()){
            cantAs+= (ch == 'a') ? 1 : 0;
        }

        /*Mostrar cant de As*/
        textView.setText("Cantidad de A en el texto: " + cantAs);
    }
}
