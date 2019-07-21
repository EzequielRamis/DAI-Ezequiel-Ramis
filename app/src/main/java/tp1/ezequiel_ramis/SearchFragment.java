package tp1.ezequiel_ramis;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SearchFragment extends Fragment implements View.OnClickListener {

    EditText editTextSearch;
    Button buttonSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_search, container, false);
        editTextSearch = view.findViewById(R.id.EditTextSearch);
        buttonSearch = view.findViewById(R.id.ButtonSearch);
        buttonSearch.setOnClickListener(this);
        return view;
    }

    public void onClick(View view) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (getSearchValue().equals("_")) mainActivity.goToFragment("response");
        else mainActivity.goToFragment("movieList");
    }

    public String getSearchValue() {
        MainActivity mainActivity = (MainActivity) getActivity();
        return mainActivity.searchValue = editTextSearch.getText().toString().length() == 0 ?
                                          "_" :
                                          editTextSearch.getText().toString();
    }
}
