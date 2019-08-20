package tp1.ezequiel_ramis;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.microsoft.projectoxford.face.*;
import com.microsoft.projectoxford.face.contract.*;


public class MainActivity extends AppCompatActivity {

    ProgressDialog dialog;
    ImageView imageView;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        imageView = findViewById(R.id.Image);
        textView = findViewById(R.id.Results);
        button = findViewById(R.id.SelectImage);

        SharedPreferences preferences = getSharedPreferences("EzequielRamis", Context.MODE_PRIVATE);

        String endpoint = "https://westcentralus.api.cognitive.microsoft.com/face/v1.0";
        String key = "30a86d43dbfd4435b94e603fa28ee1ba";

        try {
            FaceServiceRestClient serviceRestClient = new FaceServiceRestClient(endpoint, key);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                button.setEnabled(false);
                ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.CAMERA
                }, 1);
            }
            else {
                Log.d("Inicio", "Tiene permiso, habilito el boton de tomar fotos");
                button.setEnabled(true);
            }
        } catch (Exception err) {
            Log.d("Inicio", err.getMessage());
        }

    }

    @Override
    public void onRequestPermissionsResult(int responseCode, @NonNull String[] permissionNames, @NonNull int[] permissionResults) {
        if (responseCode == 1) {
            for (int i = 0; i < permissionNames.length; i++) {
                
            }
        }
    }
}
