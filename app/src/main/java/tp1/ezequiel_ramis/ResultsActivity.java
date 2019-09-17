package tp1.ezequiel_ramis;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

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
        int neutralKids = 0;
        int happyAndBeardedMen = 0;
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

            /*Cantidad de barbudos felices*/
            if (bundle.getDoubleArray("Beards")[i] > 0.5 && bundle.getDoubleArray("Happy")[i] > 0.5) happyAndBeardedMen++;

            /*Cantidad de infantes serios*/
            if (bundle.getDoubleArray("Neutral")[i] > 0.5 && bundle.getDoubleArray("Ages")[i] < 10) neutralKids++;

            /*Atributos*/
            attr.setText(attr.getText() + "\n-Edad: " + bundle.getDoubleArray("Ages")[i]);
            attr.setText(attr.getText() + "\n-Sexo: " + bundle.getStringArray("Genders")[i]);
            attr.setText(attr.getText() + "\n-Sonrisa: " + bundle.getDoubleArray("Smiles")[i]*100 + "%");
            attr.setText(attr.getText() + "\n-Barba: " + bundle.getDoubleArray("Beards")[i]*100 + "%");
            attr.setText(attr.getText() + "\n\n-----------------------------------------------\n");
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
        stats.setText(stats.getText() + "-Cantidad de barbudos felices: " + happyAndBeardedMen + "\n");
        stats.setText(stats.getText() + "-Cantidad de infantes serios: " + neutralKids + "\n");
    }
}
