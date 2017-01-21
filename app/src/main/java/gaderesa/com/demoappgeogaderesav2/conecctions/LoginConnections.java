package gaderesa.com.demoappgeogaderesav2.conecctions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Base64;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import gaderesa.com.demoappgeogaderesav2.Index;
import gaderesa.com.demoappgeogaderesav2.Login;
import gaderesa.com.demoappgeogaderesav2.R;
import gaderesa.com.demoappgeogaderesav2.domains.Oslp;
import gaderesa.com.demoappgeogaderesav2.util.RequestQueueSingleton;
import gaderesa.com.demoappgeogaderesav2.util.SharedPreferencesBasicAuth;
import gaderesa.com.demoappgeogaderesav2.util.Url;

/**
 * Created by Administrator on 27/12/2016.
 */

public class LoginConnections {

    private Url u = new Url();

    public void login(final ProgressDialog progressDialog, final Login login, final String userName, final String password) {

        final StringRequest getUser=new StringRequest(Request.Method.GET, u.getUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getRolname(progressDialog, login, userName,password);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                       if(error.toString().equals("com.android.volley.TimeoutError")){
                            Toast.makeText(login, R.string.toast_errordeconex,Toast.LENGTH_LONG).show();
                           progressDialog.dismiss();
                        }else
                        if(error.toString().equals("com.android.volley.AuthFailureError")) {
                            Toast.makeText(login, R.string.toast_loginIncorrecto, Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(login,R.string.toast_errordeconex,Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String creds = String.format("%s:%s",userName,password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }

        };
        RequestQueueSingleton.getInstance(login).addToRequestQueue(getUser);
    }

    private void getRolname(final ProgressDialog progressDialog, final Login login, final String userName, final String password) {

        final SharedPreferencesBasicAuth sharedPreferencesBasicAuth = SharedPreferencesBasicAuth.getInstance(login.getApplicationContext());
        sharedPreferencesBasicAuth.saveUser("userName", userName);
        sharedPreferencesBasicAuth.savePass("password", password);

        final StringRequest getUser=new StringRequest(Request.Method.GET, u.getUrl()+"asesor/"+userName.replace(" ", "%20")+"/"+password.replace(" ", "%20"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson= new Gson();
                        Oslp oslp = gson.fromJson(response, Oslp.class);

                        if(oslp.getMemo().equals("Vendedor") || oslp.getMemo().equals("Comprador") || oslp.getMemo().equals("Operario")){
                            sharedPreferencesBasicAuth.saveMemo("memo", oslp.getMemo());
                            sharedPreferencesBasicAuth.saveSlpCode("slpcode", oslp.getSlpCode().toString());
                            sharedPreferencesBasicAuth.saveSlpName("slpname", oslp.getSlpName());
                            sharedPreferencesBasicAuth.saveUserId("userid", oslp.getUserId().toString());
                            Intent intent = Index.newinIntent(login);
                            login.startActivity(intent);
                            login.finish();
                            progressDialog.dismiss();
                        }
                        else
                            Toast.makeText(login, R.string.toast_auth, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String creds = String.format("%s:%s",userName,password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }

        };
        RequestQueueSingleton.getInstance(login).addToRequestQueue(getUser);
    }
}
