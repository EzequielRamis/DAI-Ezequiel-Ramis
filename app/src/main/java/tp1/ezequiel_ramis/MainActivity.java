package tp1.ezequiel_ramis;

import android.app.Activity;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.view.View;

public class MainActivity extends Activity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

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
}
