package gaderesa.com.demoappgeogaderesav2.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gaderesa.com.demoappgeogaderesav2.R;
import gaderesa.com.demoappgeogaderesav2.domains.Crd1;
import gaderesa.com.demoappgeogaderesav2.util.Crd1Adapter;
import gaderesa.com.demoappgeogaderesav2.util.RequestQueueSingleton;
import gaderesa.com.demoappgeogaderesav2.util.SharedPreferencesBasicAuth;
import gaderesa.com.demoappgeogaderesav2.util.Url;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultaClienteSucursal extends Fragment {

    private Bundle bundle = new Bundle() ;



    private Button btnBuscar;
    private ListView lvList;
    private ArrayList<Crd1> crd1List = new  ArrayList<Crd1>();
    private Crd1Adapter crd1Adapter;
    private EditText txtNombeSucursal;
    private  View footer;
    private Url url = new Url();

    private boolean flagClientbyAddress = false;
    private int countCrd1 = 0;


    public ConsultaClienteSucursal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.consulta_cliente_sucursal, container, false);

        lvList = (ListView) view.findViewById(R.id.lvList);
        crd1Adapter = new Crd1Adapter(getActivity(), crd1List);
        lvList.setAdapter(crd1Adapter);
        txtNombeSucursal = (EditText) view.findViewById(R.id.txtMatriz1);


        btnBuscar = (Button) view.findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNombeSucursal.getText().toString().equals(""))
                    Toast.makeText(getActivity().getApplicationContext(), R.string.toast_invalido, Toast.LENGTH_SHORT).show();
                else
                {

                    countCrd1 = 0;
                    crd1List.clear();
                    crd1Adapter.notifyDataSetChanged();
                    loadCrd1(txtNombeSucursal.getText().toString());
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(txtNombeSucursal.getWindowToken(), 0);
                }

            }
        });

        footer = ((LayoutInflater)getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.progress_dialog, null, false);


        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String geolocalizacion, street, gadupdateAndroid;

                Crd1 mLog = crd1Adapter.getItem(position);
                if(mLog.getGlblLocNum() == null)
                    geolocalizacion = "Coordenadas No determinadas";
                else{
                    geolocalizacion=mLog.getGlblLocNum();
                }
                if(mLog.getStreet() == null)
                    street = "";
                else{
                    street=mLog.getStreet();
                }

                if(mLog.getuGadUpgpsandroid().equals("SI")){
                    lvList.setEnabled(true);
                    txtNombeSucursal.setText("");
                    Toast.makeText(getActivity(),R.string.toast_yactualizado,Toast.LENGTH_LONG).show();

                }else {
                    bundle.putString("gps",geolocalizacion);
                    bundle.putString("street",street);
                    bundle.putString("address",mLog.getAddress());
                    bundle.putString("cardcode",mLog.getCardCode());

                    ActualizarDatosSucursal sucursal = new ActualizarDatosSucursal();
                    sucursal.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_activity_container, sucursal);
                    transaction.addToBackStack("notback");
                    transaction.commit();
                }




            }
        });






        return view;
    }

    public void loadCrd1(String nombreSucursal){

        //load list name sucursales
        StringRequest getSucursal=new StringRequest(Request.Method.GET, url.getUrl()+"clientes/sucursal?docnum="+Integer.parseInt(nombreSucursal),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.toString().equals("0")){
                            Toast.makeText(getActivity(),"No existen Manifiestos",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Gson gson= new Gson();
                            Crd1 crd1s = gson.fromJson(response, Crd1.class );

                            if(crd1s == null){
                                Toast.makeText(getActivity(),"Existen datos que no coinciden. Por favor, Ingrese Otro N° de Manifiesto",Toast.LENGTH_LONG).show();
                            }else {
                                Crd1 aux = new Crd1();
                                aux.setAddress(crd1s.getAddress());
                                aux.setCardCode(crd1s.getCardCode());
                                aux.setStreet(crd1s.getStreet());
                                if(crd1s.getGlblLocNum()==null)
                                    aux.setGlblLocNum("Coordenadas No Determinadas");
                                else
                                    aux.setGlblLocNum(crd1s.getGlblLocNum());
                                if(crd1s.getuGadUpgpsandroid()==null)
                                    aux.setuGadUpgpsandroid("");
                                else
                                    aux.setuGadUpgpsandroid(crd1s.getuGadUpgpsandroid());


                                crd1List.add(aux);


                                flagClientbyAddress = false;


//                            lvList.addFooterView(footer);

                                crd1Adapter.notifyDataSetChanged();
                            }


                        }

                        }



                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getActivity(),R.string.toast_errordeconex,Toast.LENGTH_LONG).show();

                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                SharedPreferencesBasicAuth sharedPreferencesBasicAuth = SharedPreferencesBasicAuth.getInstance(getActivity().getApplicationContext());
                String userName = sharedPreferencesBasicAuth.getUser("userName");
                String password = sharedPreferencesBasicAuth.getPass("password");
                String creds = String.format("%s:%s",userName,password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }

        };
        RequestQueueSingleton.getInstance(getActivity()).addToRequestQueue(getSucursal);
    }


}


