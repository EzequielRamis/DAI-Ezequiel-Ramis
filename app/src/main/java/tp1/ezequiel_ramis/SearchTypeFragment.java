package tp1.ezequiel_ramis;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SearchTypeFragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_type, container, false);

        Button byName = view.findViewById(R.id.byName);
        Button byGeo = view.findViewById(R.id.byGeo);

        byName.setOnClickListener(this);
        byGeo.setOnClickListener(this);

        return view;
    }

    public void onClick(View view){
        Log.d("Boton apretado", String.valueOf(view.getId()));
    }
}
