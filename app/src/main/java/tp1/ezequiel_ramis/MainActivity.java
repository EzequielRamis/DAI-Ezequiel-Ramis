package tp1.ezequiel_ramis;

import android.content.Intent;
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
        Fragment searchTypeFr = new SearchTypeFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.holder, searchTypeFr);
        fragmentTransaction.commit();
    }
}
