package tp1.ezequiel_ramis;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ActivityTwo extends AppCompatActivity {

    ArrayList resList;
    ListView myListView;
    ArrayAdapter myAdapter;
    Bundle Response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        Response = this.getIntent().getExtras();

        resList = new ArrayList<String>();
        myListView = findViewById(R.id.Lv);
        myAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resList);

        if(Response.get("TipoDeBusqueda").equals("Nombre")){
            findByName myTask;
            myTask= new findByName();
            myTask.execute();
        }
        else {
            findByGeo myTask;
            myTask = new findByGeo();
            myTask.execute();
        }



    }

    private class findByName extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... Voids){
            try{
                String url = "http://epok.buenosaires.gob.ar/buscar/?texto="+Response.getString("Nombre")+"&categorias="+Response.getString("Categoria");
                URL myRoute=new URL(url);
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
            myListView.setAdapter(myAdapter);
        }
    }

    private class findByGeo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... Voids){
            try{
                String url = "http://epok.buenosaires.gob.ar/reverseGeocoderLugares/?categorias="+Response.getString("Categoria")+"&x="+Response.getString("X")+"&y="+Response.getString("Y")+"&radio="+Response.getString("R");
                Log.d("URL", url);
                URL myRoute=new URL(url);
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
            myListView.setAdapter(myAdapter);
        }
    }

    public void processJson(InputStreamReader readStream){
        JsonReader readJson=new JsonReader(readStream);
        try{
            readJson.beginObject();
            while (readJson.hasNext()){
                String actualItemName=readJson.nextName();
                Log.d("LecturaJson", "Nombre del actual item: "+actualItemName);
                if(actualItemName.equals("instancias")) {
                    readJson.beginArray();
                    while (readJson.hasNext()) {
                        readJson.beginObject();
                        int index = 0;
                        while (readJson.hasNext()) {
                            actualItemName = readJson.nextName();
                            if (actualItemName.equals("nombre")){
                                resList.add(readJson.nextString());
                                Log.d("Objetos", ""+resList.get(index));
                                index++;
                            }
                            else readJson.skipValue();
                        }
                        readJson.endObject();
                    }
                    readJson.endArray();

                }
                else readJson.skipValue();
            }
            readJson.endObject();
        } catch (Exception err){
            Log.d("LecturaJson", "Error: " + err.getMessage());
        }
    }
}
