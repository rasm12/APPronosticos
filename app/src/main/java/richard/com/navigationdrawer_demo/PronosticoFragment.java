package richard.com.navigationdrawer_demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

public class PronosticoFragment extends Fragment  {
    ToggleButton local;
    ToggleButton empate;
    ToggleButton visita;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_pronostico, container, false);
        local = (ToggleButton) view.findViewById(R.id.btn_local1);
        visita = (ToggleButton) view.findViewById(R.id.btn_visita1);
        empate = (ToggleButton) view.findViewById(R.id.btn_empate1);

        local.setOnCheckedChangeListener(changeChecker);
        visita.setOnCheckedChangeListener(changeChecker);
        empate.setOnCheckedChangeListener(changeChecker);

        return view;
    }

    CompoundButton.OnCheckedChangeListener changeChecker = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                if (buttonView == local) {
                    empate.setChecked(false);
                    visita.setChecked(false);
                }
                if (buttonView == empate) {
                    local.setChecked(false);
                    visita.setChecked(false);
                }
                if (buttonView == visita) {
                    local.setChecked(false);
                    empate.setChecked(false);
                }
            }
        }
    };


}
