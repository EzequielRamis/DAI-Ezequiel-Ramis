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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchByGeoFragment extends Fragment implements View.OnClickListener  {

    ArrayList categoryList;
    ArrayList categoryListNormalized;
    Spinner myCategorySpinner;
    ArrayAdapter myAdapter;
    EditText etX;
    EditText etY;
    EditText etR;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_by_geo, container, false);

        myCategorySpinner = view.findViewById(R.id.Sp_Categoria);
        etX=view.findViewById(R.id.Et_X);
        etY=view.findViewById(R.id.Et_Y);
        etR=view.findViewById(R.id.Et_R);
        Button btn = view.findViewById(R.id.searchByGeoBtn);

        btn.setOnClickListener(this);

        categoryList=new ArrayList<String>();
        categoryListNormalized=new ArrayList<String>();

        myAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categoryList);
        myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        categoryList.add("Todas");
        categoryListNormalized.add("");

        new getCategoriaTask().execute();

        return view;
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

    public void onClick(View view){
        MainActivity mainActivity;
        mainActivity = (MainActivity) getActivity();
        mainActivity.startResponse("geo", categoryListNormalized.get(myCategorySpinner.getSelectedItemPosition()).toString(), "", etX.getText().toString(), etY.getText().toString(), etR.getText().toString());
    }


}
