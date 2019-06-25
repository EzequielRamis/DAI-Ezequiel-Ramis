package tp1.ezequiel_ramis;

import android.app.Activity;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, MapsActivity.class));
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

    public void startResponse(String Type, String Category, String Name, String X, String Y, String R){
        Fragment responseFr = new ResponseFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", Type);
        bundle.putString("category", Category);
        bundle.putString("name", Name);
        bundle.putString("x", X);
        bundle.putString("y", Y);
        bundle.putString("r", R);
        responseFr.setArguments(bundle);
        start(responseFr);
    }

    protected void start(Fragment fr){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.holder, fr);
        fragmentTransaction.commit();
    }

}
