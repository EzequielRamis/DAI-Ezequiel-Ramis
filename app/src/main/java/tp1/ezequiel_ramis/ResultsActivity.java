package tp1.ezequiel_ramis;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.microsoft.projectoxford.face.contract.Face;

public class ResultsActivity extends Activity {

    TextView attr, stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        attr = findViewById(R.id.Attr);
        stats = findViewById(R.id.Stats);

        Bundle bundle = this.getIntent().getExtras();
        statistics(bundle);
    }

    void statistics(Bundle bundle) {
        int sadAndBaldMen = 0;
        double happy = 0, sad = 0, neutral = 0, ageAvg = 0;
        String emotion;
        for (int i=0;i<bundle.getDoubleArray("Ages").length; i++){

            /*Edad promedio*/
            ageAvg += bundle.getDoubleArray("Ages")[i];

            /*Emocion general*/
            happy += bundle.getDoubleArray("Happy")[i];
            sad += bundle.getDoubleArray("Sad")[i];
            neutral += bundle.getDoubleArray("Neutral")[i];

            /*Cantidad de pelados tristes*/
            if (bundle.getDoubleArray("Bald")[i] > 0.5 && bundle.getDoubleArray("Sad")[i] > 0.5) sadAndBaldMen++;


            /*Atributos*/
            attr.setText(attr.getText() + "\n");
            attr.setText(attr.getText() + "-Edad: " + bundle.getDoubleArray("Ages")[i] + "\n");
            attr.setText(attr.getText() + "-Sexo: " + bundle.getStringArray("Genders")[i] + "\n");
            attr.setText(attr.getText() + "-Sonrisa: " + bundle.getDoubleArray("Smiles")[i]*100 + "%\n");
            attr.setText(attr.getText() + "-Barba: " + bundle.getDoubleArray("Beards")[i]*100 + "%\n");
            attr.setText(attr.getText() + "\n");
            attr.setText(attr.getText() + "-----------------------------------------------");
            attr.setText(attr.getText() + "\n");
        }
        ageAvg = Math.round(ageAvg/bundle.getDoubleArray("Ages").length);

        if (happy > neutral) {
            if (happy > sad) emotion = "felicidad";
            else emotion = "tristeza";
        }
        else if (neutral > sad) emotion = "seriedad";
        else emotion = "tristeza";


        stats.setText("-Edad promedio: " + ageAvg + "\n");
        stats.setText(stats.getText() + "-Emocion general: " + emotion + "\n");
        stats.setText(stats.getText() + "-Cantidad de pelados tristes: " + sadAndBaldMen + "\n");
    }
}
