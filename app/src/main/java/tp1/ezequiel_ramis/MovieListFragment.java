package tp1.ezequiel_ramis;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MovieListFragment extends Fragment {

    ArrayList<Movie> arrayListMovie = new ArrayList<Movie>();
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        listView = view.findViewById(R.id.ListViewMovieList);
        MainActivity mainActivity = (MainActivity) getActivity();
        new getMovies().execute(mainActivity.searchValue);
        return view;
    }

    private class getMovies extends AsyncTask<String, Void, ArrayList<Movie>> {
        protected ArrayList<Movie> doInBackground(String... params) {
            ArrayList<Movie> list = new ArrayList<>();
            try {
                URL route = new URL("http://www.omdbapi.com/?s="+params[0]+"&apikey=630be3b5");
                HttpURLConnection urlConnection = (HttpURLConnection) route.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader streamReader = new InputStreamReader(inputStream, "UTF-8");
                    list = processJson(streamReader);
                }
            }
            catch (Exception error) {
                Log.d("movielist", error.getMessage());
            }
            return list;
        }

        protected void onPostExecute(ArrayList<Movie> movieArrayList) {
            arrayListMovie = movieArrayList;
            listView.setAdapter(new MovieListAdapter(arrayListMovie, getActivity()));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                    TextView textView = view.findViewById(R.id.TextViewTitleList);
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.selectedMovie = textView.getText().toString();
                    mainActivity.goToFragment("movie");
                }
            });
        }
    }

    public ArrayList<Movie> processJson(InputStreamReader reader) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(reader).getAsJsonObject();
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        if (jsonObject.get("Response").getAsString().equals("True")) {
            JsonArray jsonArray = jsonObject.get("Search").getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject movieObject = jsonArray.get(i).getAsJsonObject();
                movieArrayList.add(new Movie(
                        movieObject.get("imdbID").getAsString(),
                        movieObject.get("Title").getAsString(),
                        movieObject.get("Year").getAsString(),
                        movieObject.get("Poster").getAsString(),
                        "none",
                        "none",
                        "none",
                        "none",
                        "none",
                        "none"
                    )
                );
            }
        }
        else {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.goToFragment("response");
        }
        return movieArrayList;
    }
}
