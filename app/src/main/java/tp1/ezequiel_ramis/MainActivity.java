package tp1.ezequiel_ramis;

import android.content.Intent;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction;
    String searchValue = "_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragmentSearch = new SearchFragment();
        Fragment fragmentResponse = new SearchResponseFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayoutSearch, fragmentSearch)
                           .replace(R.id.FrameLayoutResponse, fragmentResponse)
                           .commit();
    }

    public void goToFragment(String frg) {
        Fragment fragment;
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (frg) {
            case "movie":
                Log.d("fragment", frg);
                fragment = new MovieFragment();
                break;
            case "movieList":
                Log.d("fragment", frg);
                fragment = new MovieListFragment();
                break;
            case "response":
                Log.d("fragment", frg);
                fragment = new SearchResponseFragment();
                break;
            default:
                Log.d("fragment", frg);
                fragment = new SearchResponseFragment();
                break;
        }
        fragmentTransaction.replace(R.id.FrameLayoutResponse, fragment)
                           .commit();
    }
}
