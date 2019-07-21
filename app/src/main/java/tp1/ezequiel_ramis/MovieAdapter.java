package tp1.ezequiel_ramis;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {
    private ArrayList<Movie> _movieArrayList;
    private Context _context;
    private ImageView imageViewPoster;

    public MovieAdapter(ArrayList<Movie> movieArrayList, Context context) {
        _movieArrayList = movieArrayList;
        _context = context;
    }

    public int getCount() {
        return _movieArrayList.size();
    }

    public Movie getItem(int i) {
        return _movieArrayList.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewToReturn = null;
        LayoutInflater layoutInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewToReturn = layoutInflater.inflate(R.layout.element_movie_list, viewGroup, false);
        imageViewPoster = viewToReturn.findViewById(R.id.ImageViewMovieList);
        TextView textViewTitle = viewToReturn.findViewById(R.id.TextViewTitleList);
        TextView textViewYear = viewToReturn.findViewById(R.id.TextViewYearList);
        new downloadImage().execute(getItem(i).get_poster());
        textViewTitle.setText(getItem(i).get_title());
        textViewYear.setText(getItem(i).get_year());
        return viewToReturn;
    }

    private class downloadImage extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... params) {
            Bitmap imageConverted = null;
            try {
                URL route = new URL(params[0]);
                HttpURLConnection urlConnection;
                urlConnection = (HttpURLConnection) route.openConnection();
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
