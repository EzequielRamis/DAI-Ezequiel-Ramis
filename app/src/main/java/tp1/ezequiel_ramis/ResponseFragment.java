package tp1.ezequiel_ramis;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ResponseFragment extends Fragment {

    ArrayList resList;
    ListView myListView;
    ArrayAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_response, container, false);
        Bundle bundle = this.getArguments();

        String[] ResArray = {
                bundle.getString("type"),
                bundle.getString("category"),
                bundle.getString("name"),
                bundle.getString("x"),
                bundle.getString("y"),
                bundle.getString("r"),
        };

        myListView=view.findViewById(R.id.Lv);
        resList = new ArrayList<String>();
        myAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, resList);

        if(ResArray[0].equals("nombre")) new findByName().execute(ResArray);
        else new findByGeo().execute(ResArray);

        return view;
    }

    private class findByName extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params){
            try{
                String url = "http://epok.buenosaires.gob.ar/buscar/?texto="+params[2];
                url += (!params[1].equals("")) ? "&categorias="+params[1] : "";
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
                String url = "http://epok.buenosaires.gob.ar/reverseGeocoderLugares/?x="+params[3]+"&y="+params[4]+"&radio="+params[5];
                url += (!params[1].equals("")) ? "&categorias="+params[1] : "";
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
