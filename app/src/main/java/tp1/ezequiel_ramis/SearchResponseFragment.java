package tp1.ezequiel_ramis;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SearchResponseFragment extends Fragment {

    TextView textViewResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_response, container, false);
        textViewResponse = view.findViewById(R.id.TextViewResponse);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (!mainActivity.searchValue.equals("_")) setError(mainActivity.searchValue);
        return view;
    }

    public void setError(String error) {
        textViewResponse.setText("Ups, se ve que la increíble y fantástica API de IMDB no localizó la película " + error + ". Tratá de buscar algo que sí exista, especialmente si la protagoniza Keanu Reeves");
    }

}
