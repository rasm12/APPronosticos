package richard.com.navigationdrawer_demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import richard.com.navigationdrawer_demo.java.Resultados;
import richard.com.navigationdrawer_demo.java.ResultadosRespuesta;
import richard.com.navigationdrawer_demo.java.UtilsConexion;

/**
 * Created by Richard on 3/12/2017.
 */

public class ResultadosFragment extends Fragment {

    private ProgressDialog progressDialog = null;
    private View vista;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        vista = inflater.inflate(R.layout.resultados_fragment, container, false);
        vista.setVisibility(View.INVISIBLE);

        progressDialog = UtilsConexion.getProgressDialog(getContext(),"Resultados","Procesando solicitud");
        AsyncResultados asyncResultados = new AsyncResultados(progressDialog,getContext());
        asyncResultados.execute("1");

        return vista;
    }

    public class AsyncResultados extends AsyncTask<String, Void, ResultadosRespuesta> {

        private ProgressDialog progressDialog;
        private Context contexto;

        public AsyncResultados(ProgressDialog progressDialog, Context contexto) {
            this.progressDialog = progressDialog;
            this.contexto = contexto;
        }

        @Override
        protected ResultadosRespuesta doInBackground(String... parametros) {
            ResultadosRespuesta response = null;

            String fecha = parametros[0];

            try {
                String respuesta = UtilsConexion.sendGet("/pronosticos/resultadosPDO.php?fecha=0");
                if (respuesta != null) {
                    response = UtilsConexion.jsonAObjetoJava(ResultadosRespuesta.class, respuesta);
                }
            } catch (Exception e) {
                return null;
            }
            return response;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ResultadosRespuesta respuesta) {
            progressDialog.dismiss();
            if (respuesta == null) {
                Toast.makeText(contexto, "Ha ocurrido un error", Toast.LENGTH_LONG).show();
            } else {
                if(respuesta.getCodigoRespuesta() != 0){
                    Toast.makeText(contexto, respuesta.getDescripcionRespuesta(), Toast.LENGTH_LONG).show();
                }else{
                    vista.setVisibility(View.VISIBLE);
                    byte it = 0;
                    FragmentActivity fragmentActivity = getActivity();
                    ((TextView)fragmentActivity.findViewById(R.id.req1)).setText("Fecha Nro: " + respuesta.getLstResultados().get(0).getFecha().toString());
                    for (Resultados resultados : respuesta.getLstResultados()){
                        it++;
                        switch (it){
                            case 1:
                                // NOMBRES DE LOS EQUIPOS
                                ((TextView)fragmentActivity.findViewById(R.id.req1)).setText(resultados.getEquipoLocal());
                                ((TextView)fragmentActivity.findViewById(R.id.req2)).setText(resultados.getEquipoVisita());

                                // MARCADORES
                                ((TextView)fragmentActivity.findViewById(R.id.gol_local1)).setText((resultados.getGolLocal() != null) ? resultados.getGolLocal().toString() : "S/N");
                                ((TextView)fragmentActivity.findViewById(R.id.gol_visita1)).setText((resultados.getGolVisita() != null) ? resultados.getGolVisita().toString() : "S/N");

                                // GANADOR DEL JUEGO
                                ((TextView)fragmentActivity.findViewById(R.id.ganador_1)).setText((resultados.getGanador() != null) ? resultados.getGanador().toString() : "EMPATE");

                                break;
                            case 2:
                                // NOMBRES DE LOS EQUIPOS
                                ((TextView)fragmentActivity.findViewById(R.id.req3)).setText(resultados.getEquipoLocal());
                                ((TextView)fragmentActivity.findViewById(R.id.req4)).setText(resultados.getEquipoVisita());

                                // MARCADORES
                                ((TextView)fragmentActivity.findViewById(R.id.gol_local2)).setText((resultados.getGolLocal() != null) ? resultados.getGolLocal().toString() : "S/N");
                                ((TextView)fragmentActivity.findViewById(R.id.gol_visita2)).setText((resultados.getGolVisita() != null) ? resultados.getGolVisita().toString() : "S/N");

                                // GANADOR DEL JUEGO
                                ((TextView)fragmentActivity.findViewById(R.id.ganador_2)).setText((resultados.getGanador() != null) ? resultados.getGanador().toString() : "EMPATE");
                                break;
                            case 3:
                                // NOMBRES DE LOS EQUIPOS
                                ((TextView)fragmentActivity.findViewById(R.id.req5)).setText(resultados.getEquipoLocal());
                                ((TextView)fragmentActivity.findViewById(R.id.req6)).setText(resultados.getEquipoVisita());

                                // MARCADORES
                                ((TextView)fragmentActivity.findViewById(R.id.gol_local3)).setText((resultados.getGolLocal() != null) ? resultados.getGolLocal().toString() : "S/N");
                                ((TextView)fragmentActivity.findViewById(R.id.gol_visita3)).setText((resultados.getGolVisita() != null) ? resultados.getGolVisita().toString() : "S/N");

                                // GANADOR DEL JUEGO
                                ((TextView)fragmentActivity.findViewById(R.id.ganador_3)).setText((resultados.getGanador() != null) ? resultados.getGanador().toString() : "EMPATE");
                                break;
                            case 4:
                                // NOMBRES DE LOS EQUIPOS
                                ((TextView)fragmentActivity.findViewById(R.id.req7)).setText(resultados.getEquipoLocal());
                                ((TextView)fragmentActivity.findViewById(R.id.req8)).setText(resultados.getEquipoVisita());

                                // MARCADORES
                                ((TextView)fragmentActivity.findViewById(R.id.gol_local4)).setText((resultados.getGolLocal() != null) ? resultados.getGolLocal().toString() : "S/N");
                                ((TextView)fragmentActivity.findViewById(R.id.gol_visita4)).setText((resultados.getGolVisita() != null) ? resultados.getGolVisita().toString() : "S/N");

                                // GANADOR DEL JUEGO
                                ((TextView)fragmentActivity.findViewById(R.id.ganador_4)).setText((resultados.getGanador() != null) ? resultados.getGanador().toString() : "EMPATE");
                                break;
                            case 5:
                                // NOMBRES DE LOS EQUIPOS
                                ((TextView)fragmentActivity.findViewById(R.id.req9)).setText(resultados.getEquipoLocal());
                                ((TextView)fragmentActivity.findViewById(R.id.req10)).setText(resultados.getEquipoVisita());

                                // MARCADORES
                                ((TextView)fragmentActivity.findViewById(R.id.gol_local5)).setText((resultados.getGolLocal() != null) ? resultados.getGolLocal().toString() : "S/N");
                                ((TextView)fragmentActivity.findViewById(R.id.gol_visita5)).setText((resultados.getGolVisita() != null) ? resultados.getGolVisita().toString() : "S/N");

                                // GANADOR DEL JUEGO
                                ((TextView)fragmentActivity.findViewById(R.id.ganador_5)).setText((resultados.getGanador() != null) ? resultados.getGanador().toString() : "EMPATE");
                                break;
                            case 6:
                                // NOMBRES DE LOS EQUIPOS
                                ((TextView)fragmentActivity.findViewById(R.id.req11)).setText(resultados.getEquipoLocal());
                                ((TextView)fragmentActivity.findViewById(R.id.req12)).setText(resultados.getEquipoVisita());

                                // MARCADORES
                                ((TextView)fragmentActivity.findViewById(R.id.gol_local6)).setText((resultados.getGolLocal() != null) ? resultados.getGolLocal().toString() : "S/N");
                                ((TextView)fragmentActivity.findViewById(R.id.gol_visita6)).setText((resultados.getGolVisita() != null) ? resultados.getGolVisita().toString() : "S/N");

                                // GANADOR DEL JUEGO
                                ((TextView)fragmentActivity.findViewById(R.id.ganador_6)).setText((resultados.getGanador() != null) ? resultados.getGanador().toString() : "EMPATE");
                                break;
                        }
                    }
                }

            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
