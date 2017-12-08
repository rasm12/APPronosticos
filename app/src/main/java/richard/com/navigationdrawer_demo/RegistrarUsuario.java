package richard.com.navigationdrawer_demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import richard.com.navigationdrawer_demo.java.LoginResponse;
import richard.com.navigationdrawer_demo.java.RegistroUsuario;
import richard.com.navigationdrawer_demo.java.RegistroUsuarioResponse;
import richard.com.navigationdrawer_demo.java.UtilsConexion;

/**
 * Created by Richard on 3/12/2017.
 */

public class RegistrarUsuario extends AppCompatActivity implements View.OnClickListener {
    private Button btnRegistro;
    private EditText nombre;
    private EditText password;
    private EditText login;
    private EditText email;
    private EditText password2;
    private ProgressDialog progressDialog = null;

    private final StringBuilder RECURSO = new StringBuilder("/pronosticos/registrar_usuario_PDO.php");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        btnRegistro = (Button) findViewById(R.id.btn_signup);
        btnRegistro.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (validar()) {
            progressDialog = UtilsConexion.getProgressDialog(RegistrarUsuario.this, "Registro", "Procesando solicitud, por favor espere");
            HttpAsyncTask httpAsyncTask = new HttpAsyncTask(getApplicationContext(),progressDialog);

            String login = ((EditText) findViewById(R.id.input_login)).getText().toString();
            String nombre = ((EditText) findViewById(R.id.input_name)).getText().toString();
            String pass1 = ((EditText) findViewById(R.id.input_password)).getText().toString();
            String pass2 = ((EditText) findViewById(R.id.input_reEnterPassword)).getText().toString();
            String email = ((EditText) findViewById(R.id.input_email)).getText().toString();

            RegistroUsuario registroUsuario = new RegistroUsuario();
            registroUsuario.setNombre(nombre);
            registroUsuario.setLogin(login);
            registroUsuario.setPassword(pass1);

            httpAsyncTask.execute(registroUsuario);
        }
    }

    private boolean validar() {
        String login = ((EditText) findViewById(R.id.input_login)).getText().toString();
        String nombre = ((EditText) findViewById(R.id.input_name)).getText().toString();
        String pass1 = ((EditText) findViewById(R.id.input_password)).getText().toString();
        String pass2 = ((EditText) findViewById(R.id.input_reEnterPassword)).getText().toString();
        String email = ((EditText) findViewById(R.id.input_email)).getText().toString();


        boolean datosInvalidos = false;
        String msg = "";

        if (login.isEmpty()) {
            datosInvalidos = true;
            msg = "LOGIN NO PUEDE SER NULO NI VACIO";
        } else if (nombre.isEmpty()) {
            datosInvalidos = true;
            msg = "NOMBRE NO PUEDE SER NULO NI VACIO";
        } else if (pass1.isEmpty()) {
            datosInvalidos = true;
            msg = "PASSWORD NO PUEDE SER NULO NI VACIO";
        } else if (pass2.isEmpty()) {
            datosInvalidos = true;
            msg = "DEBE REPETIR EL PASSWORD";
        } else if (!pass1.equals(pass2)) {
            datosInvalidos = true;
            msg = "LAS CONTRASEÑAS NO COINCIDEN";
        }
        if (datosInvalidos) {
            Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
        }
        return !datosInvalidos;
    }

    private class HttpAsyncTask extends AsyncTask<RegistroUsuario, Void, RegistroUsuarioResponse> {

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
        protected RegistroUsuarioResponse doInBackground(RegistroUsuario... parametros) {

            RegistroUsuario enviar = parametros[0];
            RegistroUsuarioResponse response = null;
            try {
                String json = UtilsConexion.objetoToJson(enviar);

                String retorno = UtilsConexion.sendPost(RECURSO.toString(), json);

                if (retorno != null) {
                    response = UtilsConexion.jsonAObjetoJava(RegistroUsuarioResponse.class, retorno);
                }
            } catch (Exception e) {
                return null;
            }

            return response;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(RegistroUsuarioResponse respuesta) {
            // solo aqui se tiene acceso a la vista de la app
            /**
             * Luedo de ejecutar doInBackground
             * recibie el parametro retornado por doInBackground
             */
            System.out.println("############ RESPUESTA ############## " + respuesta);
            progressDialog.dismiss();

            if (respuesta != null) {
                if (Integer.valueOf(respuesta.getCodigoRespuesta()) == 0) {
                    Intent i = new Intent(getBaseContext(), LoginTemplate.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getBaseContext(), respuesta.getDescripcionRespuesta(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getBaseContext(), "Ha Ocurrio un errro al procesar su solicitud, vuelva a intentarlo", Toast.LENGTH_LONG).show();
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
