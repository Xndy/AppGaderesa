package gaderesa.com.demoappgeogaderesav2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import gaderesa.com.demoappgeogaderesav2.fragments.DatosGenerales;
import gaderesa.com.demoappgeogaderesav2.fragments.ConsultaClienteSucursal;
import gaderesa.com.demoappgeogaderesav2.fragments.Inicio;
import gaderesa.com.demoappgeogaderesav2.fragments.MisClientes;
import gaderesa.com.demoappgeogaderesav2.fragments.ModoOffline;
import gaderesa.com.demoappgeogaderesav2.fragments.Sync;
import gaderesa.com.demoappgeogaderesav2.util.SharedPreferencesBasicAuth;

public class Index extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferencesBasicAuth sharedPreferencesBasicAuth = SharedPreferencesBasicAuth.getInstance(Index.this);

    private Menu menu;
    private View header;
    private MenuItem nav_saveCliente, nav_buscar, nav_MisClientes, nav_Offline, nav_sync;

    private Bundle bundle =  new Bundle();
    private TextView txtUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Inicio inicio = new Inicio();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_activity_container, inicio);
        fragmentTransaction.commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        header=navigationView.getHeaderView(0);
        menu = navigationView.getMenu();
        nav_buscar = menu.findItem(R.id.nav_buscar);
        nav_Offline = menu.findItem(R.id.nav_Offline);
        nav_MisClientes = menu.findItem(R.id.nav_MisClientes);
        nav_saveCliente = menu.findItem(R.id.nav_saveCliente);
        nav_sync = menu.findItem(R.id.nav_Sync);

            if (sharedPreferencesBasicAuth.getUser("memo").equals("Operario")){

                nav_buscar.setVisible(true);
                nav_Offline.setVisible(true);
                nav_sync.setVisible(true);
                nav_MisClientes.setVisible(false);
                nav_saveCliente.setVisible(false);
                txtUserName = (TextView) header.findViewById(R.id.txtNameUser);
                txtUserName.setText(sharedPreferencesBasicAuth.getUser("slpname"));
            }
            if(sharedPreferencesBasicAuth.getUser("memo").equals("Vendedor") || sharedPreferencesBasicAuth.getUser("memo").equals("Comprador")){

                nav_MisClientes.setVisible(true);
                nav_saveCliente.setVisible(true);
                nav_buscar.setVisible(false);
                nav_Offline.setVisible(false);
                nav_sync.setVisible(false);
                txtUserName = (TextView) header.findViewById(R.id.txtNameUser);
                txtUserName.setText(sharedPreferencesBasicAuth.getUser("slpname"));
            }



        navigationView.setNavigationItemSelectedListener(this);


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
        getMenuInflater().inflate(R.menu.index, menu);
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

        if (id == R.id.nav_saveCliente) {

            DatosGenerales datosGenerales = new DatosGenerales();
            bundle.putString("control", "notNull");

            datosGenerales.setArguments(bundle);
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_activity_container, datosGenerales);
            fragmentTransaction.addToBackStack("notBack");
            fragmentTransaction.commit();

        }
            else if (id == R.id.nav_buscar) {

            ConsultaClienteSucursal consultaClienteSucursal = new ConsultaClienteSucursal();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_activity_container, consultaClienteSucursal);
            fragmentTransaction.addToBackStack("notBack");
            fragmentTransaction.commit();


            }
            else if (id == R.id.nav_logout) {
                SharedPreferencesBasicAuth sharedPreferencesBasicAuth = SharedPreferencesBasicAuth.getInstance(getApplicationContext());
                sharedPreferencesBasicAuth.saveUser("userName", "");
                sharedPreferencesBasicAuth.savePass("password", "");
                sharedPreferencesBasicAuth.saveMemo("memo", "");
                sharedPreferencesBasicAuth.saveSlpCode("slpcode", "");
                sharedPreferencesBasicAuth.saveSlpName("slpname", "");
                sharedPreferencesBasicAuth.saveUserId("userid", "");
                Intent intent = new Intent(Index.this, Login.class);
                startActivityForResult(intent, 0);
                Index.this.finish();

            }
            else if (id == R.id.nav_Inicio) {
                Inicio inicio = new Inicio();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_activity_container, inicio);

                fragmentTransaction.commit();

            }
            else if (id == R.id.nav_MisClientes) {
                MisClientes misClientes = new MisClientes();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("notBack");
                fragmentTransaction.replace(R.id.fragment_activity_container, misClientes);
                fragmentTransaction.commit();

            }
            else if (id == R.id.nav_Offline) {
                ModoOffline offline = new ModoOffline();
                bundle.putString("control", "index");
                offline.setArguments(bundle);
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("notBack");
                fragmentTransaction.replace(R.id.fragment_activity_container, offline);
                fragmentTransaction.commit();

            }
            else if (id == R.id.nav_Sync) {
                Sync sync = new Sync();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("notBack");
                fragmentTransaction.replace(R.id.fragment_activity_container, sync);
                fragmentTransaction.commit();
            }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public static Intent newinIntent(Context context){

        Intent intent = new Intent(context,Index.class);
        return intent;
    }





}
