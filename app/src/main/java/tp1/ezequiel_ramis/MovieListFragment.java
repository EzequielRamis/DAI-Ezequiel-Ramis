package tp1.ezequiel_ramis;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class MovieListFragment extends Fragment {

    ArrayList<Movie> arrayListMovie = new ArrayList<Movie>();
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        // llenar array
        listView = view.findViewById(R.id.ListViewMovieList);
        listView.setAdapter(new MovieAdapter(arrayListMovie, getActivity()));
        return view;
    }
}
