package richard.com.navigationdrawer_demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import richard.com.navigationdrawer_demo.java.LoginResponse;
import richard.com.navigationdrawer_demo.java.ResumenPorUsuario;
import richard.com.navigationdrawer_demo.java.Usuarios;
import richard.com.navigationdrawer_demo.java.UtilsConexion;

public class PosicionesFragment extends Fragment {
    private ListView lvItems;
    private Adaptador adaptador;
    private ProgressDialog progressDialog;
    private List<Entidad> resumenPorUsuario;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_posiciones, container, false);

        lvItems = view.findViewById(R.id.lista);

        String resource = "/pronosticos/procesarResultadosPDO.php?fecha=-1&usuario=-1";

        progressDialog = UtilsConexion.getProgressDialog(getContext(), "Posiciones", "Procesando solicitud, por favor espere");

        HttpAsyncTask thread = new HttpAsyncTask(getContext(), progressDialog);
        thread.execute(resource);


        return view;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, ResumenPorUsuario> {

        /**
         * Primer parametro Void, la entrada de doInBacktround
         * Segundo parametro Integer pasado a onProgressUpdate
         * Tercer parametro Void, pasado a onPostExecute
         */

        private Context contexto;
        private ProgressDialog progressDialog;

        public HttpAsyncTask(Context contexto, ProgressDialog progressDialog) {
            this.contexto = contexto;
            this.progressDialog = progressDialog;
        }

        @Override
        protected ResumenPorUsuario doInBackground(String... recurso) {


            String retorno = UtilsConexion.sendGet(recurso[0]);

            ResumenPorUsuario response = null;
            if (retorno != null) {
                response = UtilsConexion.jsonAObjetoJava(ResumenPorUsuario.class, retorno);
            }

            return response;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ResumenPorUsuario respuesta) {
            // solo aqui se tiene acceso a la vista de la app
            /**
             * Luedo de ejecutar doInBackground
             * recibie el parametro retornado por doInBackground
             */
            progressDialog.dismiss();

            if (respuesta != null) {
                if (Integer.valueOf(respuesta.getCodigoRespuesta()) == 0) {
                    resumenPorUsuario = respuesta.getUsuariosList();
                    if(!resumenPorUsuario.isEmpty()) {
                        adaptador = new Adaptador(resumenPorUsuario, contexto);

                        View header = getLayoutInflater().inflate(R.layout.list_header_row, null);

                        lvItems.addHeaderView(header);

                        lvItems.setAdapter(adaptador);
                    }else {
                        Toast.makeText(contexto,"Sin resultados que mostrar",Toast.LENGTH_LONG).show();
                        view.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Toast.makeText(contexto, respuesta.getDescripcionRespuesta(), Toast.LENGTH_LONG).show();
                    view.setVisibility(View.INVISIBLE);
                }
            } else {
                Toast.makeText(contexto, "Ha Ocurrio un errro al procesar su solicitud, vuelva a intentarlo", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            /**
             * Disparado cuando el usuario cancela la operacion, boton de atras
             */
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {
            /**
             * Antes de ejecutar doInBackground
             */
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            /**
             * Para pasar datos a la barra de progreso
             */
            super.onProgressUpdate(values);
        }
    }
}
