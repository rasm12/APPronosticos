package richard.com.navigationdrawer_demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import richard.com.navigationdrawer_demo.java.Fixture;
import richard.com.navigationdrawer_demo.java.LoginResponse;
import richard.com.navigationdrawer_demo.java.UtilsConexion;

/**
 * Created by Richard on 2/12/2017.
 */

public class LoginTemplate extends AppCompatActivity implements View.OnClickListener {
    private final StringBuilder RECURSO = new StringBuilder("/pronosticos/login.php?");


    private EditText usuario;
    private EditText password;
    private AppCompatButton btnIngresar;
    private TextView signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_template);

        btnIngresar = (AppCompatButton) findViewById(R.id.btn_login);
        usuario = (EditText) findViewById(R.id.input_usuario);
        password = (EditText) findViewById(R.id.input_password);

        btnIngresar.setOnClickListener(this);

        signUp = (TextView)findViewById(R.id.link_signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(),RegistrarUsuario.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onClick(View view) {
        if(!validar()){
            HttpAsyncTask httpAsyncTask = new HttpAsyncTask();
            httpAsyncTask.execute(usuario.getText().toString(), password.getText().toString());
        }else{
            Toast.makeText(this,"Debe completar todos los campos",Toast.LENGTH_LONG).show();
        }

    }

    private void loadPreferencias() {
        SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);

        String usuario = preferences.getString("usuario", "NN");
        String password = preferences.getString("password", "NN");
    }

    private void savePreferences() {
        SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String user = usuario.getText().toString();
        String pass = password.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario", user);
        editor.putString("password", pass);
        editor.commit();
    }

    private boolean validar(){
        String user = this.usuario.getText().toString();
        String pass = this.password.getText().toString();
        return (user.isEmpty() || pass.isEmpty());
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, LoginResponse> {

        /**
         * Primer parametro Void, la entrada de doInBacktround
         * Segundo parametro Integer pasado a onProgressUpdate
         * Tercer parametro Void, pasado a onPostExecute
         */

        ProgressDialog progressDialog = UtilsConexion.getProgressDialog(LoginTemplate.this, "Acceso", "Procesando solicitud, por favor espere");

        @Override
        protected LoginResponse doInBackground(String... parametros) {
            String usuario = parametros[0];
            String password = parametros[1];

            StringBuilder full = new StringBuilder();
            full.append("user=").append(usuario).append("&password=").append(password);

            System.out.println("############ USUARIO ############## " + usuario);
            System.out.println("############ PASSWORD ############## " + password);
            System.out.println("############ RECURSO ############## " + RECURSO.toString() + full.toString());

            String retorno = UtilsConexion.sendGet(RECURSO.toString() + full.toString());

            LoginResponse loginResponse = null;
            if (retorno != null) {
                loginResponse = UtilsConexion.jsonAObjetoJava(LoginResponse.class, retorno);
            }

            return loginResponse;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(LoginResponse respuesta) {
            // solo aqui se tiene acceso a la vista de la app
            /**
             * Luedo de ejecutar doInBackground
             * recibie el parametro retornado por doInBackground
             */
            System.out.println("############ RESPUESTA ############## " + respuesta);
            progressDialog.dismiss();

            if (respuesta != null) {
                if (Integer.valueOf(respuesta.getCodRespuesta()) == 0) {
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    savePreferences();
                    startActivity(i);
                } else {
                    Toast.makeText(getBaseContext(), respuesta.getDesRetorno(), Toast.LENGTH_LONG).show();
                }
            }else{
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
