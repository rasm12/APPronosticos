package richard.com.navigationdrawer_demo.java;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Richard on 2/12/2017.
 */

public class AsyncEnviarFecha extends AsyncTask<Object, Void, EnviarResultadosRespuesta> {

    private ProgressDialog progressDialog;
    private Context contexto;

    public AsyncEnviarFecha(ProgressDialog progressDialog, Context contexto) {
        this.progressDialog = progressDialog;
        this.contexto = contexto;
    }

    @Override
    protected EnviarResultadosRespuesta doInBackground(Object... parametros) {
        EnviarResultadosRespuesta response = null;

        //progressDialog = (ProgressDialog) parametros[1];
        SaveWrapperRequest save = (SaveWrapperRequest) parametros[0];

        try {
            String json = UtilsConexion.objetoToJson(save);
            String respuesta = UtilsConexion.sendPost("/pronosticos/SavePDO.php", json);
            if (respuesta != null) {
                response = UtilsConexion.jsonAObjetoJava(EnviarResultadosRespuesta.class, respuesta);
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
    protected void onPostExecute(EnviarResultadosRespuesta respuesta) {
        progressDialog.dismiss();
        if (respuesta == null) {
            Toast.makeText(contexto, "Ha ocurrido un error", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(contexto, respuesta.getDescripcionRespuesta(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
