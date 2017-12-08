package richard.com.navigationdrawer_demo.java;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeoutException;

/**
 * Created by Richard on 21/11/2017.
 */

public class UtilsConexion {

    private static final String TAG = "UtilsConexion";
    //private static final String BASE_URL = "http://10.0.2.2/PhpProject1/";
    private static final String BASE_URL = "https://test-student.000webhostapp.com/";

    // HTTP POST request
    public static String sendPost(String recurso, String json) throws Exception {

        StringBuffer response = null;
        try {
            //String url = "https://selfsolve.apple.com/wcResults.do";
            URL obj = new URL(BASE_URL + recurso);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            //con.setRequestProperty("User-Agent", USER_AGENT);
            //con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            //String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

            con.setReadTimeout(60000);
            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(json);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            Log.i(TAG, "\nSending 'POST' request to URL : " + obj);
            Log.i(TAG, "Post parameters : " + json);
            Log.i(TAG, "Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Log.i(TAG, "Response : " + response);
        } catch (Exception e) {
            response = null;
        }


        //print result
        return (response == null ? null : response.toString());

    }

    //
    public static String sendGet(String recurso) {
        String respuesta = null;
        String line = null;
        int response;
        try {
            // open a connection to the site
            //URL url = new URL("https://test-student.000webhostapp.com/json.php");
            URL url = new URL(BASE_URL + recurso);
            URLConnection con = url.openConnection();

            con.setReadTimeout(60000);

            // activate the output
            con.setDoOutput(true);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while ((respuesta = in.readLine()) != null) {
                line = respuesta;
            }

            Log.i("PHP PASS", line);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            line = null;
        } catch (IOException e) {
            e.printStackTrace();
            line = null;
        } catch (Exception exc) {
            line = null;
        }

        return line;
    }

    public static <T> T jsonAObjetoJava(Class<T> clase, String json) {
        try {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(json, clase);
        } catch (Exception e) {
            return null;
        }
    }

    public static String objetoToJson(Object clase) {
        try {
            Gson gson = new GsonBuilder().create();
            return gson.toJson(clase);
        } catch (Exception e) {
            return null;
        }
    }

    public static ProgressDialog getProgressDialog(Context context,
                                                   String title, String text) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setTitle(title);
        pd.setMessage(text);
        pd.setCancelable(false);
        return pd;
    }


}
