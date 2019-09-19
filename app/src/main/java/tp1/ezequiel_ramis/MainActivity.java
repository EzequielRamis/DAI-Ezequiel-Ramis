package tp1.ezequiel_ramis;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    Button btnStats;
    int takePhotoCode = 0;
    int selectPhotoCode = 1;
    FaceServiceRestClient serviceRestClient;


    String[] genders;
    double[] smiles, beards, ages, happy, sad, neutral, bald;

    AlertDialog.Builder builder;
    String[] optionsS = {"Sonrisa", "Barba"};
    boolean[] optionsB = {true, true};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Analizar atributos");
        builder.setMultiChoiceItems(optionsS, optionsB, optionsL);
        builder.setPositiveButton("Aceptar", ADlistener);
        builder.create();

        dialog = new ProgressDialog(this);
        imageView = findViewById(R.id.Image);
        textView = findViewById(R.id.Results);
        buttonSelect = findViewById(R.id.SelectPhoto);
        buttonTake = findViewById(R.id.TakePhoto);
        btnStats = findViewById(R.id.btnStats);

        SharedPreferences preferences = getSharedPreferences("EzequielRamis", Context.MODE_PRIVATE);

        String endpoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0";
        //String key = "30a86d43dbfd4435b94e603fa28ee1ba";
        String key = "06b4c956a3b0439fb5356b239519f22a";

        try {
            serviceRestClient = new FaceServiceRestClient(endpoint, key);
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

    DialogInterface.OnClickListener ADlistener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == -1) {
                Log.d("Dialogo", "Positivo");
                Log.d("Dialogo", optionsB[0] + " " + optionsB[1]);
            }
            else if (which == -2) {
                Log.d("Dialogo", "Negativo");
            }
            else {
                Log.d("Dialogo", "No  . ");
            }
        }
    };

    DialogInterface.OnMultiChoiceClickListener optionsL = new DialogInterface.OnMultiChoiceClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
            Log.d("Dialogo", which + "" + isChecked);
        }
    };

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
        enableStats(false);

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

    public void Settings(View view) {
        builder.show();
    }

    public void processPhoto(final Bitmap photoToProcess) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        photoToProcess.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        class processPhoto extends AsyncTask<InputStream, String, Face[]> {

            @Override
            protected Face[] doInBackground(InputStream... photoToProcess) {
                publishProgress("Detectando caras...");

                Face[] result = null;
                try {
                    FaceServiceClient.FaceAttributeType[] attributeTypes = new FaceServiceClient.FaceAttributeType[] {
                            FaceServiceClient.FaceAttributeType.Age,
                            FaceServiceClient.FaceAttributeType.Glasses,
                            FaceServiceClient.FaceAttributeType.Smile,
                            FaceServiceClient.FaceAttributeType.FacialHair,
                            FaceServiceClient.FaceAttributeType.Gender,
                            FaceServiceClient.FaceAttributeType.Hair,
                            FaceServiceClient.FaceAttributeType.Emotion,
                            FaceServiceClient.FaceAttributeType.Makeup,
                    };
                    result = serviceRestClient.detect(photoToProcess[0], true, false, attributeTypes);
                } catch (Exception err) {
                    Log.d("Procesar imagen", err.getMessage());
                }
                return result;
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
            protected void onPostExecute(Face[] result) {
                super.onPostExecute(result);
                dialog.dismiss();
                if (result==null) {
                    Log.d("Procesar imagen", "AHHHHHHHHHHHHH");
                    textView.setText("Error en procesamiento");
                }
                else {
                    if (result.length > 0) {
                        Log.d("Procesar imagen", "Recuadrar imagen");
                        highlightFaces(photoToProcess, result);
                        getData(result);
                        enableStats(true);
                    }
                    else {
                        Log.d("Procesar imagen", "No hay caras");
                        textView.setText("No se detectó ninguna jeta");
                    }
                }
            }

        }

        new processPhoto().execute(inputStream);
    }

    void highlightFaces(Bitmap image, Face[] faces) {
        Bitmap imageNew = image.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(imageNew);
        Paint pencil = new Paint();
        pencil.setAntiAlias(true);
        pencil.setStyle(Paint.Style.STROKE);
        pencil.setColor(Color.RED);
        pencil.setStrokeWidth(30);

        for (Face face:faces) {
            FaceRectangle faceRectangle = face.faceRectangle;
            canvas.drawPoint(faceRectangle.left, faceRectangle.top, pencil);
            canvas.drawPoint(faceRectangle.left + faceRectangle.width, faceRectangle.top, pencil);
            canvas.drawPoint(faceRectangle.left, faceRectangle.top + faceRectangle.height, pencil);
            canvas.drawPoint(faceRectangle.left + faceRectangle.width, faceRectangle.top + faceRectangle.height, pencil);
        }

        imageView.setImageBitmap(imageNew);
    }

    void enableStats(boolean b) {
        textView.setText("");
        btnStats.setEnabled(b);
        if (b) btnStats.setVisibility(View.VISIBLE);
        else btnStats.setVisibility(View.INVISIBLE);
    }

    void getData(Face[] faces) {
        ages = new double[faces.length];
        genders = new String[faces.length];
        smiles = new double[faces.length];
        beards = new double[faces.length];
        happy = new double[faces.length];
        sad = new double[faces.length];
        neutral = new double[faces.length];
        bald = new double[faces.length];
        for (int i=0; i<faces.length; i++) {
            ages[i] = faces[i].faceAttributes.age;
            smiles[i] = faces[i].faceAttributes.smile;
            beards[i] = faces[i].faceAttributes.facialHair.beard;
            genders[i] = faces[i].faceAttributes.gender;
            happy[i] = faces[i].faceAttributes.emotion.happiness;
            sad[i] = faces[i].faceAttributes.emotion.sadness;
            neutral[i] = faces[i].faceAttributes.emotion.neutral;
            bald[i] = faces[i].faceAttributes.hair.bald;
        }
    }

    void seeResults(View view) {
        Bundle bundle = new Bundle();
        bundle.putDoubleArray("Ages", ages);
        bundle.putDoubleArray("Smiles", smiles);
        bundle.putDoubleArray("Beards", beards);
        bundle.putStringArray("Genders", genders);
        bundle.putDoubleArray("Happy", happy);
        bundle.putDoubleArray("Sad", sad);
        bundle.putDoubleArray("Neutral", neutral);
        bundle.putDoubleArray("Bald", bald);
        bundle.putBooleanArray("Options", optionsB);
        Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
