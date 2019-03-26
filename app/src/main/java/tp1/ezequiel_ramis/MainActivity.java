package tp1.ezequiel_ramis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void activityOne(View v){
        startActivity(new Intent(MainActivity.this, ActivityOne.class));
    }

    public void activityTwo(View v){
        startActivity(new Intent(MainActivity.this, ActivityTwo.class));
    }

    public void activityThree(View v){
        startActivity(new Intent(MainActivity.this, ActivityThree.class));
    }
}
