package tp1.ezequiel_ramis;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    ArrayList resList;
    ListView myListView;
    ArrayAdapter myAdapter;

    String type;
    String category;
    String name;
    String x;
    String y;
    String r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager=getFragmentManager();

    }

    public void startSearchForm(View view){
        if(view.getTag().toString().equals("nombre")) {
            Fragment searchByNameFr = new SearchByNameFragment();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.holder, searchByNameFr);
            fragmentTransaction.commit();}
        else{
            Fragment searchByGeoFr = new SearchByGeoFragment();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.holder, searchByGeoFr);
            fragmentTransaction.commit();}
    }

    public void startResponse(){
        Fragment responseFr = new ResponseFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.holder, responseFr);
        fragmentTransaction.commit();
    }

    public void search(String Type, String Category, String Name, String X, String Y, String R){
        type=Type;
        category=Category;
        name=Name;
        x=X;
        y=Y;
        r=R;
        startResponse();
        /*
        if(type.equals("nombre")){
            findByName myTask;
            myTask= new findByName();
            myTask.execute(category,name);
        }
        else{
            findByGeo myTask;
            myTask = new findByGeo();
            myTask.execute(category, x, y, r);
        }*/
    }

    private class findByName extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params){
            try{
                String url = "http://epok.buenosaires.gob.ar/buscar/?texto="+params[1];
                url += (!params[0].equals("")) ? "&categorias="+params[0] : "";
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

    private class findByGeo extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params){
            try{
                String url = "http://epok.buenosaires.gob.ar/reverseGeocoderLugares/?x="+params[1]+"&y="+params[2]+"&radio="+params[3];
                url += (!params[0].equals("")) ? "&categorias="+params[0] : "";
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
