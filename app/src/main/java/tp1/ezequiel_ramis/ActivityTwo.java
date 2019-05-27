package tp1.ezequiel_ramis;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        Bundle Response = this.getIntent().getExtras();

        resList = new ArrayList<String>();
        myListView = findViewById(R.id.Lv);
        myAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, resList);

        AsyncTask myTask;
        if(Response.get("TipoDeBusqueda").equals("Nombre")) myTask= new findByName();
        else myTask = new findByGeo();

        myTask.execute();

    }

    private class findByName extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... Voids){
            try{
                /*URL myRoute=new URL("http://epok.buenosaires.gob.ar/getCategorias");
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
                }*/
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
                /*URL myRoute=new URL("http://epok.buenosaires.gob.ar/getCategorias");
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
                }*/
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
}
