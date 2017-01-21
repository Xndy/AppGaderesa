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
import android.widget.AbsListView;
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
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gaderesa.com.demoappgeogaderesav2.R;
import gaderesa.com.demoappgeogaderesav2.domains.Ocrd;
import gaderesa.com.demoappgeogaderesav2.util.OcrdAdapter;
import gaderesa.com.demoappgeogaderesav2.util.RequestQueueSingleton;
import gaderesa.com.demoappgeogaderesav2.util.SharedPreferencesBasicAuth;
import gaderesa.com.demoappgeogaderesav2.util.Url;

/**
 * A simple {@link Fragment} subclass.
 */
public class MisClientes extends Fragment {

    private Url url = new Url();
    private SharedPreferencesBasicAuth sharedPreferencesBasicAuth = SharedPreferencesBasicAuth.getInstance(getContext());

    private View view;
    private ListView lvListmc;
    private ArrayList<Ocrd> ocrdList = new  ArrayList<Ocrd>();
    private OcrdAdapter ocrdAdapter;
    private Button btnClienteAsesor;

    private EditText txtClienteAsesor;

    private Bundle bundle = new Bundle() ;

    private int countList = 0;
    private int countSearch = 0;

    private boolean flagcountList= false;
    private boolean flagcountSearch= false;


    public MisClientes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_mis_clientes, container, false);
        txtClienteAsesor  = (EditText) view.findViewById(R.id.txtClienteAsesor);
        lvListmc = (ListView) view.findViewById(R.id.lvListmc);
        ocrdAdapter = new OcrdAdapter(getActivity(), ocrdList);
        lvListmc.setAdapter(ocrdAdapter);


        lvListmc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Ocrd mLog = ocrdAdapter.getItem(position);
                bundle.putString("control", mLog.getCardCode());
                bundle.putString("cardname" , mLog.getCardName());
                bundle.putChar("cardType", mLog.getCardType());
                bundle.putString("cardFName",mLog.getCardFName());
                bundle.putString("groupCode",mLog.getGroupCode().toString());
                bundle.putString("licTradNum",mLog.getLicTradNum());
                bundle.putString("phone1",mLog.getPhone1());
                bundle.putString("phone2",mLog.getPhone2());
                bundle.putString("cellular",mLog.getCellular());
                if(mLog.geteMail().equals("Correo Electronico no determinado"))
                    bundle.putString("eMail","");
                else
                    bundle.putString("eMail",mLog.geteMail());
                bundle.putInt("slpCode",mLog.getSlpCode());
                bundle.putString("notes",mLog.getNotes());

                bundle.putString("glblLocNum",mLog.getGlblLocNum());
                bundle.putString("projectCod",mLog.getProjectCod());
                bundle.putString("codeTipoDocumento",mLog.getU_bpp_bptd());


                DatosGenerales datosGenerales = new DatosGenerales();
                datosGenerales.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_activity_container, datosGenerales);
                transaction.addToBackStack("notback");
                transaction.commit();

                countList = 0;
                countSearch = 0;

            }
        });


        lvListmc.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if(ocrdList.isEmpty() && ocrdAdapter.isEmpty()){
                    if(txtClienteAsesor.getText().toString().equals("") && countList==0 ){

                        ocrdList.clear();
                        ocrdAdapter.notifyDataSetChanged();
                        CargarClientes(countList);
                    }

                }if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
                {
                    if(flagcountList == false)
                    {
                        countList = countList + 1;
                        flagcountList = true;
                        if(txtClienteAsesor.getText().toString().equals(""))
                        CargarClientes(countList);
                    }
                }

                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
                {
                    if(flagcountSearch == false){
                        countSearch = countSearch + 1;
                        flagcountSearch = true;
                        if(!txtClienteAsesor.getText().toString().equals(""))
                            getClientBySearched(txtClienteAsesor.getText().toString(), countSearch);
                    }
                }

            }
        });


        btnClienteAsesor = (Button) view.findViewById(R.id.btnClienteAsesor);
        btnClienteAsesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtClienteAsesor.getText().toString().equals(""))
                    Toast.makeText(getActivity().getApplicationContext(), R.string.toast_invalido, Toast.LENGTH_SHORT).show();
                else{
                    countSearch = 0;
                    if(!ocrdList.isEmpty()){
                        ocrdList.clear();
                        ocrdAdapter.notifyDataSetChanged();
                    }

                    getClientBySearched(txtClienteAsesor.getText().toString(), countSearch);
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(txtClienteAsesor.getWindowToken(), 0);
                }
            }
        });

        return view;
    }

    public void CargarClientes(final int count){

        if(count == 0){
            ocrdList.clear();
            ocrdAdapter.notifyDataSetChanged();

        }

        //load list name clientes
        StringRequest getAdviser=new StringRequest(Request.Method.GET, url.getUrl()+"client?slp_code="+sharedPreferencesBasicAuth.getUser("slpcode")+"&page="+count+"&size=13",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("la url es: " + url.getUrl()+"client?slp_code="+sharedPreferencesBasicAuth.getUser("slpcode")+"&page="+count+"&size=13");

                        if(count == 0){
                            ocrdList.clear();
                            ocrdAdapter.notifyDataSetChanged();

                        }


                        Gson gson = new Gson();
                        ArrayList<Ocrd> ocrd1s = gson.fromJson(response,
                               new TypeToken<ArrayList<Ocrd>>(){}.getType());
                        if(ocrd1s.isEmpty()){
                            Toast.makeText(getActivity().getApplicationContext(), R.string.toast_withoutClients, Toast.LENGTH_SHORT).show();
                        }else{
                       for(Ocrd o : ocrd1s){
                            Ocrd aux = new Ocrd();
                            aux.setCardCode(o.getCardCode());
                            aux.setCardName(o.getCardName());
                            aux.setCardType(o.getCardType());
                            aux.setCardFName(o.getCardFName());
                            aux.setGroupCode(o.getGroupCode());
                            aux.setLicTradNum(o.getLicTradNum());
                            aux.setPhone1(o.getPhone1());
                            aux.setPhone2(o.getPhone2());
                            aux.setCellular(o.getCellular());
                            aux.setSlpCode(o.getSlpCode());
                            aux.setNotes(o.getNotes());
                            aux.setGlblLocNum(o.getGlblLocNum());
                            aux.setProjectCod(o.getProjectCod());
                           if(o.geteMail()==null)
                                aux.seteMail("Correo Electronico no determinado");
                           else
                               aux.seteMail(o.geteMail());
                            ocrdList.add(aux);
                        }

                        flagcountList = false;
                        ocrdAdapter.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),R.string.toast_errordeconex,Toast.LENGTH_LONG).show();
                       // ocrdList.clear();
                       // ocrdAdapter.notifyDataSetChanged();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                SharedPreferencesBasicAuth sharedPreferencesBasicAuth = SharedPreferencesBasicAuth.getInstance(getContext());
                String userName = sharedPreferencesBasicAuth.getUser("userName");
                String password = sharedPreferencesBasicAuth.getPass("password");
                String creds = String.format("%s:%s",userName,password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }

        };
        RequestQueueSingleton.getInstance(getActivity()).addToRequestQueue(getAdviser);

    }

    public void getClientBySearched(String cardName, int count){
        //ocrdList.clear();
         //load list name asesors
        StringRequest getAdviser=new StringRequest(Request.Method.GET, url.getUrl()+"clientcn?card_name="+cardName+"&page="+count+"&size=13",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        ArrayList<Ocrd> ocrd1s = gson.fromJson(response,
                                new TypeToken<ArrayList<Ocrd>>(){}.getType());
                        if(ocrd1s.isEmpty()){
                            Toast.makeText(getActivity().getApplicationContext(), R.string.toast_withoutClients, Toast.LENGTH_SHORT).show();
                        }else{

                            for(Ocrd o : ocrd1s) {
                                Ocrd aux = new Ocrd();
                                aux.setCardCode(o.getCardCode());
                                aux.setCardName(o.getCardName());
                                aux.setCardType(o.getCardType());
                                aux.setCardFName(o.getCardFName());
                                aux.setGroupCode(o.getGroupCode());
                                aux.setLicTradNum(o.getLicTradNum());
                                aux.setPhone1(o.getPhone1());
                                aux.setPhone2(o.getPhone2());
                                aux.setCellular(o.getCellular());
                                aux.setSlpCode(o.getSlpCode());
                                aux.setNotes(o.getNotes());
                                aux.setGlblLocNum(o.getGlblLocNum());
                                aux.setProjectCod(o.getProjectCod());
                                if(o.geteMail()==null)
                                    aux.seteMail("Correo Electronico no determinado");
                                else
                                    aux.seteMail(o.geteMail());
                                ocrdList.add(aux);
                            }
                            flagcountSearch = false;
                            ocrdAdapter.notifyDataSetChanged();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        boolean exits = false;
                        if(exits == Boolean.valueOf(error.toString()))
                            Toast.makeText(getActivity(),R.string.toast_clienteexist,Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(),R.string.toast_errordeconex,Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                SharedPreferencesBasicAuth sharedPreferencesBasicAuth = SharedPreferencesBasicAuth.getInstance(getContext());
                String userName = sharedPreferencesBasicAuth.getUser("userName");
                String password = sharedPreferencesBasicAuth.getPass("password");
                String creds = String.format("%s:%s",userName,password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }

        };
        RequestQueueSingleton.getInstance(getActivity()).addToRequestQueue(getAdviser);
    }



}
