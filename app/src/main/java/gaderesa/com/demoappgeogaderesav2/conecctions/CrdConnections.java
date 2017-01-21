package gaderesa.com.demoappgeogaderesav2.conecctions;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import gaderesa.com.demoappgeogaderesav2.domains.Crd1;
import gaderesa.com.demoappgeogaderesav2.fragments.ActualizarDatosSucursal;
import gaderesa.com.demoappgeogaderesav2.R;
import gaderesa.com.demoappgeogaderesav2.dao.Cdr1DAO;
import gaderesa.com.demoappgeogaderesav2.fragments.ConsultaClienteSucursal;
import gaderesa.com.demoappgeogaderesav2.fragments.Sync;
import gaderesa.com.demoappgeogaderesav2.util.SharedPreferencesBasicAuth;
import gaderesa.com.demoappgeogaderesav2.util.Url;

/**
 * Created by Xndy on 08/01/2017.
 */

public class CrdConnections {


    private Url url = new Url();
    private String  uri = url.getUrl()+"clientsucur?address=";
    private Cdr1DAO cdr1DAO = new Cdr1DAO();

    public void updateDataClient(final ProgressDialog progressDoalog, final ActualizarDatosSucursal actualizarDatosSucursal, String id, String card, String street, String gps){

        RequestQueue queue = Volley.newRequestQueue(actualizarDatosSucursal.getActivity().getApplicationContext());
        Map<String, String> jsonParams = new HashMap<String, String>();

        jsonParams.put("address", id);
        jsonParams.put("cardCode", card);
        jsonParams.put("street", street);
        jsonParams.put("glblLocNum", gps);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, uri+id.replace(" ", "%20"),

                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(actualizarDatosSucursal.getActivity(), R.string.toast_correcto, Toast.LENGTH_LONG).show();
                        progressDoalog.dismiss();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if(error.toString().equals("com.android.volley.ParseError: org.json.JSONException: End of input at character 0 of ")){
                            Toast.makeText(actualizarDatosSucursal.getActivity(), R.string.toast_correcto, Toast.LENGTH_LONG).show();
                            progressDoalog.dismiss();
                            ConsultaClienteSucursal consultaClienteSucursal = new ConsultaClienteSucursal();
                            FragmentTransaction transaction = actualizarDatosSucursal.getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_activity_container, consultaClienteSucursal);
                            transaction.addToBackStack("notback");
                            transaction.commit();

                        }
                        else{
                            Toast.makeText(actualizarDatosSucursal.getActivity(),R.string.toast_errordeconex,Toast.LENGTH_LONG).show();
                            progressDoalog.dismiss();
                        }

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                SharedPreferencesBasicAuth sharedPreferencesBasicAuth = SharedPreferencesBasicAuth.getInstance(actualizarDatosSucursal.getActivity());
                String userName = sharedPreferencesBasicAuth.getUser("userName");
                String password = sharedPreferencesBasicAuth.getPass("password");
                String creds = String.format("%s:%s",userName,password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        queue.add(postRequest);


    }

    public void saveDataClientOfflinetoOnline(final ProgressDialog progressDoalog, final Sync sync, final String id, String gps){

        RequestQueue queue = Volley.newRequestQueue(sync.getActivity().getApplicationContext());
        Map<String, String> jsonParams = new HashMap<String, String>();
//reemplace por cardcode el docnum xk se hubiese creado la necesidad de otra clase k solo sirve àra almacena una variable y motiva a la reestruccturacion de
        //todo el proyecto

        jsonParams.put("cardCode", id);
        jsonParams.put("glblLocNum", gps);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, "http://186.3.54.53:9090/clientes/clientsucur",

                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Gson gson = new Gson();
                        Crd1 crd1 = gson.fromJson(response.toString(), Crd1.class);
                        if(crd1.getCounty().equals("0")){
                            Toast.makeText(sync.getActivity(),"No existe el Manifiesto" + id,Toast.LENGTH_LONG).show();
                            progressDoalog.dismiss();
                        }
                        else
                            if(crd1.getCounty().equals("SI")){
                            Toast.makeText(sync.getActivity(), "El manifiesto: " + id + " Ya Habia sido Actualizado", Toast.LENGTH_LONG).show();
                            progressDoalog.dismiss();
                        }else
                            if(!crd1.getCounty().equals("0") || !crd1.getuGadUpgpsandroid().equals("SI")){
                            Toast.makeText(sync.getActivity(), "Ingreso Correcto del Manifiesto: " + id , Toast.LENGTH_LONG).show();
                            cdr1DAO.deleteCrd1UpdateOffline(sync, id);
                            progressDoalog.dismiss();
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("-el error es: " + error.toString());

                        if(error.toString().equals("com.android.volley.ParseError: org.json.JSONException: End of input at character 0 of ")){
                            Toast.makeText(sync.getActivity(), "Ingreso Correcto del Manifiesto: " + id, Toast.LENGTH_SHORT).show();
                            cdr1DAO.deleteCrd1UpdateOffline(sync, id);
                            progressDoalog.dismiss();
                        }
                        else
                            if(!error.toString().equals("com.android.volley.ParseError: org.json.JSONException: End of input at character 0 of ") ){
                            Toast.makeText(sync.getActivity(),R.string.toast_errordeconex,Toast.LENGTH_LONG).show();
                            progressDoalog.dismiss();
                        }

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                SharedPreferencesBasicAuth sharedPreferencesBasicAuth = SharedPreferencesBasicAuth.getInstance(sync.getActivity());
                String userName = sharedPreferencesBasicAuth.getUser("userName");
                String password = sharedPreferencesBasicAuth.getPass("password");
                String creds = String.format("%s:%s",userName,password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        queue.add(postRequest);


    }



}
