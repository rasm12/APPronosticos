package richard.com.navigationdrawer_demo;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
//import android.view.View;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import richard.com.navigationdrawer_demo.java.AsyncEnviarFecha;
import richard.com.navigationdrawer_demo.java.Fixture;
import richard.com.navigationdrawer_demo.java.Partidos;
import richard.com.navigationdrawer_demo.java.SaveWrapperRequest;
import richard.com.navigationdrawer_demo.java.UtilsConexion;

public class ModeloFragmentDos extends Fragment
        implements Spinner.OnItemSelectedListener,
        View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    private Button bEnviar;
    private ProgressDialog progressDialog;
    private View vista;
    private Spinner spinner;
    private Fixture fixture;
    private ToggleButton local;
    private ToggleButton visita;
    private ToggleButton empate;
    private ViewGroup viewGroup;
    private Dialog dialogo;
    private Button dCerrar;
    private Button dConfirmar;
    private List<Partidos> cPartidos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_modelo_dos, container, false);

        viewGroup = container;

        bEnviar = (Button) vista.findViewById(R.id.btnEnviar);
        bEnviar.setOnClickListener(this);


        HttpAsyncTask httpAsyncTask = new HttpAsyncTask();
        progressDialog = UtilsConexion.getProgressDialog(getContext(), "Descargando fechas", "Loading");

        // EJECUTANDO EL HILO
        httpAsyncTask.execute();

        return vista;
    }


    private void setearImagenes() {


        //String retorno = UtilsConexion.sendGet("getFixture.php?fecha=2");
        //Fixture fixture = UtilsConexion.jsonAObjetoJava(Fixture.class, retorno);

        if (Integer.valueOf(fixture.getCodigoRespuesta()) != 0) {
            return;
        }

        List<Integer> idDrawerImage = new ArrayList<>();
        idDrawerImage.add(R.drawable.cerro);
        idDrawerImage.add(R.drawable.olimpia);
        idDrawerImage.add(R.drawable.sol);
        idDrawerImage.add(R.drawable.capiata);
        idDrawerImage.add(R.drawable.nacional);
        idDrawerImage.add(R.drawable.libertad);
        idDrawerImage.add(R.drawable.rubio);
        idDrawerImage.add(R.drawable.luque);
        idDrawerImage.add(R.drawable.independiente);
        idDrawerImage.add(R.drawable.trinidense);
        idDrawerImage.add(R.drawable.guarani);
        idDrawerImage.add(R.drawable.general);


        byte nPartido = 1;

        for (Partidos partido : fixture.getPartidos()) {
            switch (nPartido) {
                case 1:
                    ((ImageView) vista.findViewById(R.id.eq1)).setImageResource(idDrawerImage.get(Integer.valueOf(partido.getLocal()) - 1));
                    ((ImageView) vista.findViewById(R.id.eq2)).setImageResource(idDrawerImage.get(Integer.valueOf(partido.getVisita()) - 1));
                    cargarSpinner((Spinner) vista.findViewById(R.id.sp1), partido);
                    break;
                case 2:
                    ((ImageView) vista.findViewById(R.id.eq3)).setImageResource(idDrawerImage.get(Integer.valueOf(partido.getLocal()) - 1));
                    ((ImageView) vista.findViewById(R.id.eq4)).setImageResource(idDrawerImage.get(Integer.valueOf(partido.getVisita()) - 1));
                    cargarSpinner((Spinner) vista.findViewById(R.id.sp2), partido);
                    break;
                case 3:
                    ((ImageView) vista.findViewById(R.id.eq5)).setImageResource(idDrawerImage.get(Integer.valueOf(partido.getLocal()) - 1));
                    ((ImageView) vista.findViewById(R.id.eq6)).setImageResource(idDrawerImage.get(Integer.valueOf(partido.getVisita()) - 1));
                    cargarSpinner((Spinner) vista.findViewById(R.id.sp3), partido);
                    break;
                case 4:
                    ((ImageView) vista.findViewById(R.id.eq7)).setImageResource(idDrawerImage.get(Integer.valueOf(partido.getLocal()) - 1));
                    ((ImageView) vista.findViewById(R.id.eq8)).setImageResource(idDrawerImage.get(Integer.valueOf(partido.getVisita()) - 1));
                    cargarSpinner((Spinner) vista.findViewById(R.id.sp4), partido);
                    break;
                case 5:
                    ((ImageView) vista.findViewById(R.id.eq9)).setImageResource(idDrawerImage.get(Integer.valueOf(partido.getLocal()) - 1));
                    ((ImageView) vista.findViewById(R.id.eq10)).setImageResource(idDrawerImage.get(Integer.valueOf(partido.getVisita()) - 1));
                    cargarSpinner((Spinner) vista.findViewById(R.id.sp5), partido);
                    break;
                case 6:
                    ((ImageView) vista.findViewById(R.id.eq11)).setImageResource(idDrawerImage.get(Integer.valueOf(partido.getLocal()) - 1));
                    ((ImageView) vista.findViewById(R.id.eq12)).setImageResource(idDrawerImage.get(Integer.valueOf(partido.getVisita()) - 1));
                    cargarSpinner((Spinner) vista.findViewById(R.id.sp6), partido);
                    break;
            }
            nPartido++;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int posicion, long l) {
        //Toast.makeText(getContext(),"Item " + posicion,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void cargarSpinner(Spinner combo, Partidos match) {
        List<String> lista = new ArrayList<String>();

        /**
         * la lista se llena por orden, empate = 0, local = 1, visita = 2
         */

        lista.add("Empate");
        lista.add(match.getLocalName());
        lista.add(match.getGuestName());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, lista);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        combo.setAdapter(dataAdapter);
        combo.setOnItemSelectedListener(this);


    }

    private void enviarResultados() {
        Toast.makeText(getContext(), "Enviando resultados", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {

        Integer id = view.getId();

        if (id == R.id.dialogo_confirmar) {
            // se obtiene el id del elemento seleccionado
            // getSelectedItemId() el id de la posicion
            // getSelectedItem el nombre que muestra
            String rs1 = String.valueOf(((Spinner) getActivity().findViewById(R.id.sp1)).getSelectedItem());
            String rs2 = String.valueOf(((Spinner) getActivity().findViewById(R.id.sp2)).getSelectedItem());
            String rs3 = String.valueOf(((Spinner) getActivity().findViewById(R.id.sp3)).getSelectedItem());
            String rs4 = String.valueOf(((Spinner) getActivity().findViewById(R.id.sp4)).getSelectedItem());
            String rs5 = String.valueOf(((Spinner) getActivity().findViewById(R.id.sp5)).getSelectedItem());
            String rs6 = String.valueOf(((Spinner) getActivity().findViewById(R.id.sp6)).getSelectedItem());


            //equipos
            Map<String, Integer> eMap = new HashMap<>();
            eMap.put("Cerro Porteno", 1);
            eMap.put("Olimpia", 2);
            eMap.put("Sol", 3);
            eMap.put("Capiata", 4);
            eMap.put("Nacional", 5);
            eMap.put("Libertad", 6);
            eMap.put("Rubio Nu", 7);
            eMap.put("Luqueno", 8);
            eMap.put("Independiente", 9);
            eMap.put("Trinidense", 10);
            eMap.put("Guarani", 11);
            eMap.put("Gral. Diaz", 12);
            eMap.put("Empate", 0);


            String credenciales[] = loadPreferencias();
            String user = credenciales[0];
            char status = 'U';

            SaveWrapperRequest enviar = new SaveWrapperRequest();
            enviar.setFecha("22");
            enviar.setUser(user);
            enviar.setStatus(status);

            enviar.setRs1(eMap.get(rs1).toString());
            enviar.setRs2(eMap.get(rs2).toString());
            enviar.setRs3(eMap.get(rs3).toString());
            enviar.setRs4(eMap.get(rs4).toString());
            enviar.setRs5(eMap.get(rs5).toString());
            enviar.setRs6(eMap.get(rs6).toString());

            System.out.println(UtilsConexion.objetoToJson(enviar));
            dialogo.cancel();


            progressDialog = UtilsConexion.getProgressDialog(getContext(), "Procesando peticion", "Enviando pronostico");
            AsyncEnviarFecha thread = new AsyncEnviarFecha(progressDialog, getContext());

            // PARAMETROS doInBackground()
            thread.execute(enviar, progressDialog);


        }else if(id == R.id.btnEnviar){
            generarDialogo();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


    }

    private String[] loadPreferencias() {
        SharedPreferences preferences = getActivity().getSharedPreferences("Credenciales", Context.MODE_PRIVATE);

        String usuario = preferences.getString("usuario", "NN");
        String password = preferences.getString("password", "NN");

        String[] credenciales = {usuario, password};

        return credenciales;
    }

    private void generarDialogo() {
        dialogo = new Dialog(getContext());
        dialogo.setContentView(R.layout.resumen_pronostico);
        dialogo.setTitle("Confirmar Resultados");

        dConfirmar = (Button) dialogo.findViewById(R.id.dialogo_confirmar);
        dCerrar = (Button) dialogo.findViewById(R.id.dialogo_cerrar);
        dCerrar.setEnabled(true);

        //listener
        dCerrar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.cancel();
            }
        });

        dConfirmar.setOnClickListener(this);


        TextView tvNroPartido1 = null, tvNroPartido2 = null, tvNroPartido3 = null, tvNroPartido4 = null, tvNroPartido5 = null, tvNroPartido6 = null;
        TextView tvPartido1 = null, tvPartido2 = null, tvPartido3 = null, tvPartido4 = null, tvPartido5 = null, tvPartido6 = null;
        TextView tvR1 = null, tvR2 = null, tvR3 = null, tvR4 = null, tvR5 = null, tvR6 = null;

        tvNroPartido1 = (TextView) dialogo.findViewById(R.id.cNro1);
        tvNroPartido2 = (TextView) dialogo.findViewById(R.id.cNro2);
        tvNroPartido3 = (TextView) dialogo.findViewById(R.id.cNro3);
        tvNroPartido4 = (TextView) dialogo.findViewById(R.id.cNro4);
        tvNroPartido5 = (TextView) dialogo.findViewById(R.id.cNro5);
        tvNroPartido6 = (TextView) dialogo.findViewById(R.id.cNro6);


        tvPartido1 = (TextView) dialogo.findViewById(R.id.cPartido1);
        tvPartido2 = (TextView) dialogo.findViewById(R.id.cPartido2);
        tvPartido3 = (TextView) dialogo.findViewById(R.id.cPartido3);
        tvPartido4 = (TextView) dialogo.findViewById(R.id.cPartido4);
        tvPartido5 = (TextView) dialogo.findViewById(R.id.cPartido5);
        tvPartido6 = (TextView) dialogo.findViewById(R.id.cPartido6);

        tvR1 = (TextView) dialogo.findViewById(R.id.cRs1);
        tvR2 = (TextView) dialogo.findViewById(R.id.cRs2);
        tvR3 = (TextView) dialogo.findViewById(R.id.cRs3);
        tvR4 = (TextView) dialogo.findViewById(R.id.cRs4);
        tvR5 = (TextView) dialogo.findViewById(R.id.cRs5);
        tvR6 = (TextView) dialogo.findViewById(R.id.cRs6);


        byte iter = 1;
        FragmentActivity activity = getActivity();
        for (Partidos partido : cPartidos) {
            switch (iter) {
                case 1:
                    tvNroPartido1.setText(iter + "");
                    tvPartido1.setText(partido.getLocalName() + " Vs " + partido.getGuestName());
                    tvR1.setText(((Spinner) activity.findViewById(R.id.sp1)).getSelectedItem().toString());
                    break;
                case 2:
                    tvNroPartido2.setText(iter + "");
                    tvPartido2.setText(partido.getLocalName() + " Vs " + partido.getGuestName());
                    tvR2.setText(((Spinner) activity.findViewById(R.id.sp2)).getSelectedItem().toString());
                    break;
                case 3:
                    tvNroPartido3.setText(iter + "");
                    tvPartido3.setText(partido.getLocalName() + " Vs " + partido.getGuestName());
                    tvR3.setText(((Spinner) activity.findViewById(R.id.sp3)).getSelectedItem().toString());
                    break;
                case 4:
                    tvNroPartido4.setText(iter + "");
                    tvPartido4.setText(partido.getLocalName() + " Vs " + partido.getGuestName());
                    tvR4.setText(((Spinner) activity.findViewById(R.id.sp4)).getSelectedItem().toString());
                    break;
                case 5:
                    tvNroPartido5.setText(iter + "");
                    tvPartido5.setText(partido.getLocalName() + " Vs " + partido.getGuestName());
                    tvR5.setText(((Spinner) activity.findViewById(R.id.sp5)).getSelectedItem().toString());
                    break;
                case 6:
                    tvNroPartido6.setText(iter + "");
                    tvPartido6.setText(partido.getLocalName() + " Vs " + partido.getGuestName());
                    tvR6.setText(((Spinner) activity.findViewById(R.id.sp6)).getSelectedItem().toString());
                    break;
            }
            iter++;
        }

        dialogo.show();
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog = UtilsConexion.getProgressDialog(getContext(), "Pronosticos", "Cargando fechas, por favor espere");

        @Override
        protected String doInBackground(String... urls) {
            String retorno = UtilsConexion.sendGet("/pronosticos/fixturePDO.php?fecha=22");
            fixture = UtilsConexion.jsonAObjetoJava(Fixture.class, retorno);
            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            // solo aqui se tiene acceso a la vista de la app
            setearImagenes();
            progressDialog.dismiss();
            System.out.println("####################### " + Integer.valueOf(fixture.getCodigoRespuesta()));
            if (Integer.valueOf(fixture.getCodigoRespuesta()) == 0) {
                vista.setVisibility(View.VISIBLE);

                // ALMANCENA PARA EL DIALOGO DE CONFIRMACION
                cPartidos = fixture.getPartidos();
            } else {
                vista = LayoutInflater.from(getContext()).inflate(R.layout.sin_fechas_fragment, viewGroup);
                vista.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), fixture.getDescripcionRespuesta(), Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPreExecute() {
            vista.setVisibility(View.INVISIBLE);
            progressDialog.show();
        }
    }
}

