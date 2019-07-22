package tp1.ezequiel_ramis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MovieFragment extends Fragment {

    TextView textViewTitle;
    TextView textViewRated;
    TextView textViewReleased;
    TextView textViewRuntime;
    TextView textViewGenre;
    TextView textViewDirector;
    TextView textViewPlot;
    ImageView imageViewPoster;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        textViewTitle = view.findViewById(R.id.TextViewTitle);
        textViewRated = view.findViewById(R.id.TextViewRated);
        textViewReleased = view.findViewById(R.id.TextViewReleased);
        textViewRuntime = view.findViewById(R.id.TextViewRuntime);
        textViewGenre = view.findViewById(R.id.TextViewGenre);
        textViewDirector = view.findViewById(R.id.TextViewDirector);
        textViewPlot = view.findViewById(R.id.TextViewPlot);
        imageViewPoster = view.findViewById(R.id.ImageViewMovie);
        MainActivity mainActivity = (MainActivity) getActivity();
        new getMovie().execute(mainActivity.selectedMovie);
        return view;
    }

    private class getMovie extends AsyncTask<String, Void, Movie> {
        protected Movie doInBackground(String... params) {
            Movie movie = new Movie();
            try {
                URL route = new URL("http://www.omdbapi.com/?t="+params[0]+"&apikey=630be3b5&plot=full&type=movie");
                HttpURLConnection urlConnection = (HttpURLConnection) route.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader streamReader = new InputStreamReader(inputStream, "UTF-8");
                    movie = processJson(streamReader);
                }
            }
            catch (Exception error) {
                Log.d("movielist", error.getMessage());
            }
            return movie;
        }

        protected void onPostExecute(Movie movie) {
            textViewTitle.setText(movie.get_title());
            textViewRated.setText(movie.get_rated());
            textViewReleased.setText(movie.get_released());
            textViewRuntime.setText(movie.get_runtime());
            textViewGenre.setText(movie.get_genre());
            textViewDirector.setText(movie.get_director());
            textViewPlot.setText(movie.get_plot());
            new downloadImage().execute(movie.get_poster());
        }
    }


    public Movie processJson(InputStreamReader reader) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(reader).getAsJsonObject();
        Movie movie = new Movie();
        if (jsonObject.get("Response").getAsString().equals("True")) {
            movie.set_title(jsonObject.get("Title").getAsString());
            movie.set_released("Released: " + jsonObject.get("Released").getAsString());
            movie.set_poster(jsonObject.get("Poster").getAsString());
            movie.set_rated("Rated: " + jsonObject.get("Rated").getAsString());
            movie.set_runtime("Runtime: " + jsonObject.get("Runtime").getAsString());
            movie.set_genre("Genre: " + jsonObject.get("Genre").getAsString());
            movie.set_director("Director: " + jsonObject.get("Director").getAsString());
            movie.set_plot(jsonObject.get("Plot").getAsString());
        }
        else {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.goToFragment("response");
        }
        return movie;
    }

    private class downloadImage extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... params) {
            Bitmap imageConverted = null;
            try {
                URL route = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) route.openConnection();
                if (urlConnection.getResponseCode() == 200){
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                    imageConverted = BitmapFactory.decodeStream(bufferedInputStream);
                    urlConnection.disconnect();
                }
            }
            catch (Exception error) {
                Log.d("descarga", error.getMessage());
            }
            return imageConverted;
        }

        protected void onPostExecute(Bitmap imageResult) {
            if (imageResult != null) imageViewPoster.setImageBitmap(imageResult);
            else imageViewPoster.setImageResource(android.R.drawable.ic_dialog_alert);
        }
    }
}
