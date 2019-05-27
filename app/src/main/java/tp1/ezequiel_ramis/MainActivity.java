package tp1.ezequiel_ramis;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList categoryList;
    ArrayList categoryListNormalized;
    Spinner myCategorySpinner;
    ArrayAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.Et_X).setEnabled(false);
        findViewById(R.id.Et_Y).setEnabled(false);
        findViewById(R.id.Et_R).setEnabled(false);

        categoryList=new ArrayList<String>();
        categoryListNormalized=new ArrayList<String>();
        myCategorySpinner=findViewById(R.id.Sp_Categoria);

        Log.d("AccesoAPI", "Starting Task");

        myAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryList);
        myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        categoryList.add("Todas");
        categoryListNormalized.add("");

        getCategoriaTask myTask=new getCategoriaTask();
        myTask.execute();

        Log.d("AccesoAPI", "Task Finished");
    }

    public void rbConfig(View view){
        switch (view.getId()){
            case R.id.Rb_N:
                findViewById(R.id.Et_X).setEnabled(false);
                findViewById(R.id.Et_Y).setEnabled(false);
                findViewById(R.id.Et_R).setEnabled(false);

                findViewById(R.id.Et_N).setEnabled(true);
                break;
            case R.id.Rb_G:
                findViewById(R.id.Et_N).setEnabled(false);

                findViewById(R.id.Et_X).setEnabled(true);
                findViewById(R.id.Et_Y).setEnabled(true);
                findViewById(R.id.Et_R).setEnabled(true);
        }
    }

    public void buscar(View view){
        RadioButton rb  = findViewById(R.id.Rb_N);
        AsyncTask myTask;
        if (rb.isChecked()) myTask = new findByName();
        else myTask = new findByGeo();
        myTask.execute();
    }


    private class getCategoriaTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... Voids){
            try{
                URL myRoute=new URL("http://epok.buenosaires.gob.ar/getCategorias");
                HttpURLConnection myConnection = (HttpURLConnection) myRoute.openConnection();
                Log.d("AccesoAPI", "Connecting...");
                if(myConnection.getResponseCode()==200){
                    Log.d("AccesoAPI", "Connected");
                    InputStream res=myConnection.getInputStream();
                    InputStreamReader resLector=new InputStreamReader(res, "UTF-8");

                    processJson(resLector);
                }
                else{
                    Log.d("AccesoAPI", "Connection Error");
                }
            } catch (Exception err) {
                Log.d("AccesoAPI", "Error: " + err.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            myCategorySpinner.setAdapter(myAdapter);
        }
    }

    public void processJson(InputStreamReader readStream){
        JsonReader readJson=new JsonReader(readStream);
        try{
            readJson.beginObject();
            while (readJson.hasNext()){
                String actualItemName=readJson.nextName();
                Log.d("LecturaJson", "Nombre del actual item: "+actualItemName);

                if(actualItemName.equals("cantidad_de_categorias")){
                    Log.d("LecturaJson", "Cantidad de categorías: " + readJson.nextInt());
                }

                else {
                    readJson.beginArray();
                    while (readJson.hasNext()){
                        readJson.beginObject();
                        while (readJson.hasNext()){
                            actualItemName=readJson.nextName();
                            if(actualItemName.equals("nombre")) categoryList.add(readJson.nextString());
                            else if(actualItemName.equals("nombre_normalizado")) categoryListNormalized.add(readJson.nextString());
                            else readJson.skipValue();
                        }
                        readJson.endObject();
                    }
                    readJson.endArray();
                }

            }
            readJson.endObject();
        } catch (Exception err){
            Log.d("LecturaJson", "Error: " + err.getMessage());
        }
    }
}
