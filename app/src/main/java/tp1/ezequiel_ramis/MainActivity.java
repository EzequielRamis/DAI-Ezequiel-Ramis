package tp1.ezequiel_ramis;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    ProgressDialog dialog;
    ImageView imageView;
    TextView textView;
    Button buttonSelect;
    Button buttonTake;
    int takePhotoCode = 0;
    int selectPhotoCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        imageView = findViewById(R.id.Image);
        textView = findViewById(R.id.Results);
        buttonSelect = findViewById(R.id.SelectPhoto);
        buttonTake = findViewById(R.id.TakePhoto);

        SharedPreferences preferences = getSharedPreferences("EzequielRamis", Context.MODE_PRIVATE);

        String endpoint = "https://westcentralus.api.cognitive.microsoft.com/face/v1.0";
        String key = "30a86d43dbfd4435b94e603fa28ee1ba";

        try {
            FaceServiceRestClient serviceRestClient = new FaceServiceRestClient(endpoint, key);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                buttonSelect.setEnabled(false);
                buttonTake.setEnabled(false);
                ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.CAMERA
                }, 1);
            }
            else {
                Log.d("Inicio", "Tiene permiso, habilito el boton de tomar fotos");
                buttonSelect.setEnabled(true);
                buttonTake.setEnabled(true);
            }
        } catch (Exception err) {
            Log.d("Inicio", err.getMessage());
        }

    }

    @Override
    public void onRequestPermissionsResult(int responseCode, @NonNull String[] permissionNames, @NonNull int[] permissionResults) {
        if (responseCode == 1) {
            Boolean getAllPermissions = true;
            for (int permission : permissionResults) {
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    getAllPermissions = false;
                }
            }
            if (getAllPermissions) {
                buttonSelect.setEnabled(true);
                buttonTake.setEnabled(true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        textView.setText("Procesando...");

        if (requestCode==takePhotoCode && resultCode==RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);

            processPhoto(photo);
        }

        if (requestCode==selectPhotoCode && resultCode==RESULT_OK && data != null) {
            Uri uri = data.getData();
            Bitmap photo = null;
            try {
                photo = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (Exception err) {
                Log.d("FotoObtenida", err.getMessage());
            }
            if (photo != null) {
                imageView.setImageBitmap(photo);
                processPhoto(photo);
            }
        }
    }

    public void TakePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, takePhotoCode);
    }

    public void SelectPhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Seleccione foto"), selectPhotoCode);
    }

    public void processPhoto(final Bitmap photoToProcess) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        photoToProcess.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        class processPhoto extends AsyncTask<InputStream, String, Face[]> {

            @Override
            protected Face[] doInBackground(InputStream... photoToProcess) {

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.show();
            }

            @Override
            protected void onProgressUpdate(String... processMessage) {
                super.onProgressUpdate(processMessage);
                dialog.setMessage(processMessage[0]);
            }

            @Override
            protected void onPostExecute(Face[] result) {}

        }

        new processPhoto().execute(inputStream);
    }
}
