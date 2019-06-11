package tp1.ezequiel_ramis;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchByGeoFragment extends Fragment implements View.OnClickListener  {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_by_geo, container, false);

        Spinner spCategoria=view.findViewById(R.id.Sp_Categoria);
        EditText etN=view.findViewById(R.id.Et_N);
        Button btn = view.findViewById(R.id.searchByNameBtn);

        btn.setOnClickListener(this);

        return view;
    }
    
    public void onClick(View view){
        Log.d("Boton apretado", String.valueOf(view.getId()));
    }


}
