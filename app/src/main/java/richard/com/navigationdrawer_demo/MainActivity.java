package richard.com.navigationdrawer_demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import richard.com.navigationdrawer_demo.java.Fixture;
import richard.com.navigationdrawer_demo.java.Partidos;
import richard.com.navigationdrawer_demo.java.SaveWrapperRequest;
import richard.com.navigationdrawer_demo.java.UtilsConexion;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Bundle bundle = getIntent().getExtras();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragment = getSupportFragmentManager();
        fragment.beginTransaction().replace(R.id.contendor, new ModeloFragmentDos()).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Class framentClass = null;
        FragmentManager fragment = getSupportFragmentManager();

        if (id == R.id.posiciones) {
            framentClass = PosicionesFragment.class;
        } else if (id == R.id.pronostico) {
            framentClass = ModeloFragmentDos.class;
        } else if (id == R.id.posiciones) {
            framentClass = PosicionesFragment.class;
        } else if (id == R.id.resultados) {
            framentClass = ResultadosFragment.class;
        } else if (id == R.id.nav_estadistica) {
            framentClass = ModeloFragmentDos.class;
        } else if (id == R.id.nav_Calendario) {
            framentClass = PosicionesFragment.class;
        }


        try {
            FragmentTransaction transaction = fragment.beginTransaction();

            transaction.replace(R.id.contendor, (Fragment) framentClass.newInstance());
            transaction.commit();

        } catch (Exception e) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog = UtilsConexion.getProgressDialog(getApplicationContext(), "Cargando", "loading...");

        @Override
        protected String doInBackground(String... urls) {


            SaveWrapperRequest save = new SaveWrapperRequest();


            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(save);

            try {
                return UtilsConexion.sendPost("/pronosticos/Save.php", json);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
