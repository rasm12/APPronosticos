package richard.com.navigationdrawer_demo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import richard.com.navigationdrawer_demo.java.SaveWrapperRequest;
import richard.com.navigationdrawer_demo.java.UtilsConexion;

public class SinFechasFragment extends Fragment implements View.OnClickListener{
    View view ;
    ProgressDialog progressDialog;
    Button b;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sin_fechas_fragment, container, false);
        System.out.println("####################################################################");

        HttpAsyncTask httpAsyncTask = new HttpAsyncTask();
        progressDialog = UtilsConexion.getProgressDialog(getContext(),"Wait", "Loading");

        System.out.println("####################################################################");

        httpAsyncTask.execute();
        return view;
    }

    @Override
    public void onClick(View view) {

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog = UtilsConexion.getProgressDialog(getContext(),"Cargando","loading...");
        @Override
        protected String doInBackground(String... urls) {

            String ret = UtilsConexion.sendGet("getFixture.php?fecha=1");


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }
    }
}
