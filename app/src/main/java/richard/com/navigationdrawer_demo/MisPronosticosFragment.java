package richard.com.navigationdrawer_demo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import richard.com.navigationdrawer_demo.java.Adaptador;
import richard.com.navigationdrawer_demo.java.Entidad;
import richard.com.navigationdrawer_demo.java.Fixture;
import richard.com.navigationdrawer_demo.java.Resultados;
import richard.com.navigationdrawer_demo.java.ResultadosRespuesta;
import richard.com.navigationdrawer_demo.java.UtilsConexion;

/**
 * Created by Richard on 11/12/2017.
 */

public class MisPronosticosFragment extends Fragment {

    private View vista;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.mis_pronosticos_fragment, container, false);


        return vista;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, ResultadosRespuesta> {

        ProgressDialog progressDialog = UtilsConexion.getProgressDialog(getContext(), "Pronosticos", "Cargando fechas, por favor espere");

        @Override
        protected ResultadosRespuesta doInBackground(String... args) {
            String usuario = args[0];
            String fecha = args[1];

            String retorno = UtilsConexion.sendGet("/pronosticos/MiPronostico.php?fecha=" + fecha.trim() + "$usuario="+usuario.trim());
            if (retorno == null) return null;
            ResultadosRespuesta r = UtilsConexion.jsonAObjetoJava(ResultadosRespuesta.class, retorno);
            return r;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ResultadosRespuesta result) {
            // solo aqui se tiene acceso a la vista de la app

            progressDialog.dismiss();
            System.out.println("####################### " + Integer.valueOf(result.getCodigoRespuesta()));
            if (Integer.valueOf(result.getCodigoRespuesta()) == 0) {


            } else {

                Toast.makeText(getContext(), result.getDescripcionRespuesta(), Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPreExecute() {
            vista.setVisibility(View.INVISIBLE);
            progressDialog.show();
        }
    }
}