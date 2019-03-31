package tp1.ezequiel_ramis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityThree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
    }

    public void three_showResults(View v){
        /*Definición de variables*/
        EditText editText = findViewById(R.id.three_editText);
        TextView textView = findViewById(R.id.three_res);

        /*Punto Opcional: Validar la cant minima de caracteres*/
        int minCarac=3;
        if(editText.getText().length() <= minCarac){
            Toast.makeText(getApplicationContext(), "Ingreso inválido: No se ingresó texto de más de "+ minCarac +" caracteres", Toast.LENGTH_LONG).show();
            return;
        }

        /*Definir variable con la clase StringBuilder para obtener el texto dado vuelta*/
        String invText = new StringBuilder(editText.getText()).reverse().toString();

        /*Mostrar resultado*/
        textView.setText("Texto invertido: " + invText);

        /*Punto Opcional: Validar si es capicua*/
        if(editText.getText().toString().toLowerCase().equals(invText.toLowerCase())){
            Toast.makeText(getApplicationContext(), "¡El texto es capicúa!", Toast.LENGTH_LONG).show();
        }
    }
}
