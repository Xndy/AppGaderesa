package gaderesa.com.demoappgeogaderesav2.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.test.mock.MockPackageManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import gaderesa.com.demoappgeogaderesav2.R;
import gaderesa.com.demoappgeogaderesav2.domains.Oprj;
import gaderesa.com.demoappgeogaderesav2.domains.Oslp;
import gaderesa.com.demoappgeogaderesav2.servicie.GPSTracker;
import gaderesa.com.demoappgeogaderesav2.util.RequestQueueSingleton;
import gaderesa.com.demoappgeogaderesav2.util.SharedPreferencesBasicAuth;
import gaderesa.com.demoappgeogaderesav2.util.Url;

import static gaderesa.com.demoappgeogaderesav2.R.id.txt_Nombre;


public class DatosGenerales extends Fragment {


    private String[] tipoGrupo = new String[]{
            "Hospitalario",
            "Hospitalario e Industrial",
            "Industrial",
            "Los 70 Incobrables",
            "Deuda Procredit",
            "Deudas Produbanco",
            "Empleados",
            "Proveedor Caja Chica",
            "Relacionados",
            "Servicios Basicos",
            "Estrategico",
            "Especiales",
            "No Estrategicos"

    };




    private String[] tipoDocumento = new String[]{
            "DOCUMENTO NACIONAL DE IDENTIDA",
            "OTROS TIPOS DE DOCUMENTOS",
            "PASAPORTE",
            "REGISTRO UNICO DE CONTRIBUYENT"
    };

    private String[] tipoCliente = new String[]{
            "Cliente",
            "Lead"
    };

    private Url url = new Url();
    private View view;
    private Button btnSiguienteCl, btnActualizarGeoMc;
    private EditText txtRazonSocial, txtNombre, txtCodigo, txtRuc, txtTelf1, txtTelf2, txtTelfM, txtEmail;
    private  static  String razonSocialaux = "gaderesa.com.demoappgeogaderesav2.fragments.razonSocial",
                                 nombreaux = "gaderesa.com.demoappgeogaderesav2.fragments.nombre",
                                 rucaux = "gaderesa.com.demoappgeogaderesav2.fragments.ruc",
                                 telefono1aux = "gaderesa.com.demoappgeogaderesav2.fragments.telefono1",
                                 telefono2aux = "gaderesa.com.demoappgeogaderesav2.fragments.telefono2",
                                 telefonoMaux = "gaderesa.com.demoappgeogaderesav2.fragments.telefonoM",
                                 emailaux = "gaderesa.com.demoappgeogaderesav2.fragments.email",
                                 projectaux  = "gaderesa.com.demoappgeogaderesav2.fragments.project",
                                 geolocalizacionaux = "gaderesa.com.demoappgeogaderesav2.fragments.geolocalizacion",
                                 comentsaux = "gaderesa.com.demoappgeogaderesav2.fragments.coments",
                                 grupoaux = "gaderesa.com.demoappgeogaderesav2.fragments.coments",
                                 codeTipoDocumento = "gaderesa.com.demoappgeogaderesav2.fragments.codeTipoDocumento"      ;

    private static Integer codeasesoraux;
    private char cardTypeaux = 'c';
    private String control;

    private Spinner spnTipo, spnGrupo, spnAdviser, spnProyecto, spntipoDocumento;
    private TextView lblGeolocalizacionCreate, txtComents;
    private ProgressDialog progressDoalog;;

    private ArrayAdapter<Oslp> oslpArrayAdapter;
    private ArrayAdapter<Oprj> oprjArrayAdapter;


    private Oslp oslpTemp;
    private Oprj oprjTemp;


    private SharedPreferencesBasicAuth sharedPreferencesBasicAuth = SharedPreferencesBasicAuth.getInstance(getContext());

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    private GPSTracker gps;



    public DatosGenerales() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_datos_generales, container, false);

        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        oslpArrayAdapter = new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item);
        oprjArrayAdapter = new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item);

        control = getArguments().getString("control");


        txtCodigo = (EditText) view.findViewById(R.id.txtCodigo);

        txtNombre = (EditText) view.findViewById(txt_Nombre);
        txtRuc  = (EditText) view.findViewById(R.id.txtRUC);
        txtRazonSocial = (EditText) view.findViewById(R.id.txtRazonSocial);
        txtTelf1 = (EditText) view.findViewById(R.id.txtTelf1);
        txtTelf2 = (EditText) view.findViewById(R.id.txtTelf2);
        txtTelfM = (EditText) view.findViewById(R.id.txtTelfM);
        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        txtComents = (TextView) view.findViewById(R.id.txtComents);
        lblGeolocalizacionCreate = (TextView) view.findViewById(R.id.lblGeolocalizacionCreate);


        btnSiguienteCl = (Button) view.findViewById(R.id.btnSiguienteCl);
        btnActualizarGeoMc = (Button) view.findViewById(R.id.btnActualizarGeoMc);
        if(control.equals("notNull")){
            grupoaux = " ";
            cardTypeaux = ' ';
            codeasesoraux= 0;
            codeTipoDocumento = " ";
            projectaux=null;
            loadSpinners(grupoaux, cardTypeaux, codeTipoDocumento, codeasesoraux, projectaux );

            btnSiguienteCl.setText("Crear Cliente");
            btnActualizarGeoMc.setVisibility(View.VISIBLE);
        }

        if(!control.equals("notNull")) {
            btnSiguienteCl.setText("Actualizar Cliente");
            btnActualizarGeoMc.setVisibility(View.VISIBLE);
            nombreaux = getArguments().getString("cardname");
            rucaux = getArguments().getString("licTradNum");
            razonSocialaux = getArguments().getString("cardFName");
            telefono1aux = getArguments().getString("phone1");
            telefono2aux = getArguments().getString("phone2");
            telefonoMaux = getArguments().getString("cellular");
            emailaux = getArguments().getString("eMail");
            cardTypeaux = getArguments().getChar("cardType");
            codeasesoraux = getArguments().getInt("slpCode");
            projectaux = getArguments().getString("projectCod");
            geolocalizacionaux = getArguments().getString("glblLocNum");
            comentsaux = getArguments().getString("notes");
            grupoaux = getArguments().getString("groupCode");
            codeTipoDocumento = getArguments().getString("codeTipoDocumento");
            getClientsToUpdate(control, nombreaux, cardTypeaux, rucaux, razonSocialaux, telefono1aux, telefono2aux,
                    telefonoMaux, emailaux, codeasesoraux, projectaux, geolocalizacionaux, comentsaux, grupoaux, codeTipoDocumento);
        }



        btnSiguienteCl.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onClick(View v) {
            if(control.equals("notNull")){
                if (txtCodigo.getText().toString().equals("") || txtNombre.getText().toString().equals("") ||
                        txtRazonSocial.getText().toString().equals("") || txtRuc.getText().toString().equals("") ||
                        txtTelf1.getText().toString().equals("")||  txtTelfM.getText().toString().equals("") || txtEmail.getText().toString().equals("")) {

                    Toast.makeText(getActivity().getApplicationContext(), R.string.toast_invalido, Toast.LENGTH_SHORT).show();

                }else{
                    progressDoalog = new ProgressDialog(getActivity());
                    progressDoalog.setMax(20);
                    progressDoalog.setMessage("Grabando Registros....");
                    progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDoalog.setIndeterminate(true);
                    progressDoalog.setCancelable(false);
                    progressDoalog.show();

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    saveClient();

                                }
                            }, 3000);
                }
            }else
                if(!control.equals("notNull")){
                    progressDoalog = new ProgressDialog(getActivity());
                    progressDoalog.setMax(20);
                    progressDoalog.setMessage("Grabando Registros....");
                    progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDoalog.setIndeterminate(true);
                    progressDoalog.setCancelable(false);
                    progressDoalog.show();

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    updateClientafterChanged();
                                    progressDoalog.dismiss();
                                }
                            }, 3000);
            }

            }
        });



        btnActualizarGeoMc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gps = new GPSTracker(getActivity());
                progressDoalog = new ProgressDialog(getActivity());
                progressDoalog.setMax(20);
                progressDoalog.setMessage("Buscando....");
                progressDoalog.setTitle("Determinando Ubicación");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDoalog.setIndeterminate(true);
                progressDoalog.setCancelable(false);
                progressDoalog.show();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                Location nwLocation = gps
                                        .getLocation(LocationManager.NETWORK_PROVIDER);

                                if (nwLocation != null) {
                                    Double latitude = nwLocation.getLatitude();
                                    Double longitude = nwLocation.getLongitude();
                                    lblGeolocalizacionCreate.setText(latitude.toString()+ " " + longitude.toString() );
                                    progressDoalog.dismiss();
                                    nwLocation.reset();

                                } else {
                                    gps.showSettingsAlert();
                                    progressDoalog.dismiss();
                                }

                            }
                        }, 3000);
            }
        });

        return view;
    }








    private void loadSpinners(String codeGrupo, char codeTipo, String codeTipoDocumento, final Integer codeAsesor, final String codeProject){

        spnGrupo = (Spinner) view.findViewById(R.id.spnGrupo);
        ArrayAdapter<String> adapterGroup = new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.spinners_items,tipoGrupo);
        adapterGroup.setDropDownViewResource(R.layout.spinners_items);

        spnGrupo.setAdapter(adapterGroup);

        if(!codeGrupo.equals(" ")){
            if(codeGrupo.equals("100"))
                spnGrupo.setSelection(0);
            else
            if(codeGrupo.equals("104"))
                spnGrupo.setSelection(1);
            else
            if(codeGrupo.equals("102"))
                spnGrupo.setSelection(2);
            else
            if(codeGrupo.equals("108"))
                spnGrupo.setSelection(3);
            else
                //Procredit
                if(codeGrupo.equals("112"))
                    spnGrupo.setSelection(4);
                else
                    //Produbanco
                    if(codeGrupo.equals("111"))
                        spnGrupo.setSelection(5);
                    else
                        //Empleados
                        if(codeGrupo.equals("110"))
                            spnGrupo.setSelection(6);
                        else
                            //Proveedor de caja chica
                            if(codeGrupo.equals("109"))
                                spnGrupo.setSelection(7);
                            else
                                //Relacionados
                                if(codeGrupo.equals("107"))
                                    spnGrupo.setSelection(8);
                                else
                                    //Servicios Basicos
                                    if(codeGrupo.equals("106"))
                                        spnGrupo.setSelection(9);
                                    else
                                        //Estrategico
                                        if(codeGrupo.equals("105"))
                                            spnGrupo.setSelection(10);
                                        else
                                            //Especiales
                                            if(codeGrupo.equals("103"))
                                                spnGrupo.setSelection(11);
                                            else
                                                //No Estrategicos
                                                if(codeGrupo.equals("101"))
                                                    spnGrupo.setSelection(11);
        }

        spnTipo = (Spinner) view.findViewById(R.id.spnTipo);
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinners_items,tipoCliente);
        adapterType.setDropDownViewResource(R.layout.spinner_tipo_cliente);

        spnTipo.setAdapter(adapterType);

        if(codeTipo != ' '){
            if(codeTipo == 'C')
                spnTipo.setSelection(0);
            else
            if(codeTipo == 'L')
                spnTipo.setSelection(1);
        }


        spntipoDocumento = (Spinner) view.findViewById(R.id.spnDocumento);
        ArrayAdapter<String> adapterDocumento = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinners_items,tipoDocumento);
        adapterType.setDropDownViewResource(R.layout.spinners_tipo_documento);

        spntipoDocumento.setAdapter(adapterDocumento);

        if(codeTipoDocumento != " "){
            if(codeTipoDocumento == "C")
                spntipoDocumento.setSelection(0);
            else
            if(codeTipoDocumento == "O")
                spntipoDocumento.setSelection(1);
            else
            if(codeTipoDocumento == "P")
                spntipoDocumento.setSelection(2);
            else
            if(codeTipoDocumento == "R")
                spntipoDocumento.setSelection(3);


        }

        //load list name asesors
        StringRequest getAdviser=new StringRequest(Request.Method.GET, url.getUrl()+"asesor",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int countAd = 0;
                        int positionAD =0;
                        Gson gson=new Gson();
                        List<Oslp> adviserList = gson.fromJson(response,
                                new TypeToken<List<Oslp>>(){}.getType());

                        oslpArrayAdapter.clear();
                        oslpArrayAdapter.addAll(adviserList);

                        spnAdviser = (Spinner)view.findViewById(R.id.spnAsesor);
                        spnAdviser.setAdapter(oslpArrayAdapter);

                        if(codeAsesor != null){
                            for (Oslp otemp : adviserList){
                                if (codeAsesor == otemp.getSlpCode()){
                                    positionAD = countAd;
                                }
                                countAd++;
                            }
                            spnAdviser.setSelection(positionAD);
                        }

                        spnAdviser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                oslpTemp = oslpArrayAdapter.getItem(position);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
        ){
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

        //Proyectos:

        StringRequest getProyectos=new StringRequest(Request.Method.GET, url.getUrl()+"proyecto",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int countProject = 0;
                        int positionProject =0;

                        Gson gson=new Gson();
                        List<Oprj> projectList = gson.fromJson(response,
                                new TypeToken<List<Oprj>>(){}.getType());

                        oprjArrayAdapter.clear();
                        oprjArrayAdapter.addAll(projectList);

                        spnProyecto = (Spinner)view.findViewById(R.id.spnProyecto);
                        spnProyecto.setAdapter(oprjArrayAdapter);

                        if(codeProject !=null){

                            for (Oprj otemp : projectList){
                                if (codeProject.equals(otemp.getPrjCode())){
                                    positionProject = countProject;
                                }
                                countProject++;
                            }
                            spnProyecto.setSelection(positionProject);
                        }


                        spnProyecto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                oprjTemp = oprjArrayAdapter.getItem(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                SharedPreferencesBasicAuth sharedPreferencesBasicAuth = SharedPreferencesBasicAuth.getInstance(getContext());
                String userName = sharedPreferencesBasicAuth.getUser("userName");
                String password = sharedPreferencesBasicAuth.getPass("password");
                String creds = String.format("%s:%s",userName,password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }

        };
        RequestQueueSingleton.getInstance(getActivity()).addToRequestQueue(getProyectos);



    }

    private void saveClient(){

        String cardType = spnTipo.getSelectedItem().toString();

        if(cardType.equals("Cliente"))
            cardType = "C";
        else
            if(cardType.equals("Proveedor"))
                cardType = "S";
        else
            if(cardType.equals("Lead"))
                cardType ="L";



        String documento = spntipoDocumento.getSelectedItem().toString();
        if(documento.equals("DOCUMENTO NACIONAL DE IDENTIDA"))
            documento = "C";
        else
            if(documento.equals("DOCUMENTO NACIONAL DE IDENTIDA"))
                documento = "O";
        else
            if(documento.equals("PASAPORTE"))
                documento = "P";
         else
            if(documento.equals("REGISTRO UNICO DE CONTRIBUYENT"))
                documento = "R";

        String groupCode = codegroup();

       RequestQueue queue = Volley.newRequestQueue(getActivity());
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("cardCode", "C"+txtCodigo.getText().toString());
        jsonParams.put("cardType", cardType);
        jsonParams.put("cardName", txtNombre.getText().toString());
        jsonParams.put("cardFName", txtRazonSocial.getText().toString());
        jsonParams.put("groupCode", groupCode);
        jsonParams.put("cmpPrivate", "C");
        jsonParams.put("licTradNum", txtRuc.getText().toString());
        jsonParams.put("phone1", txtTelf1.getText().toString());
        jsonParams.put("phone2", txtTelf2.getText().toString());
        jsonParams.put("cellular", txtTelfM.getText().toString());
        jsonParams.put("eMail", txtEmail.getText().toString());
        jsonParams.put("createDate", getDatePhone());
        jsonParams.put("slpCode", oslpTemp.getSlpCode().toString());
        jsonParams.put("notes", txtComents.getText().toString());
        jsonParams.put("glblLocNum", lblGeolocalizacionCreate.getText().toString());
        jsonParams.put("projectCod", oprjTemp.getPrjCode().toString());
        jsonParams.put("country", "EC");
        jsonParams.put("currency", "##");
        jsonParams.put("u_bpp_bptd", documento);
        jsonParams.put("userSign", sharedPreferencesBasicAuth.getUserId("userid"));

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url.getUrl()+"clientes",

                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(),R.string.toast_correcto,Toast.LENGTH_SHORT).show();
                        cleanComponents();
                        progressDoalog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    public static final String LOG_TAG = "com";

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("el erroor el ocrd es: " + error.toString());

                        if(error.toString().equals("com.android.volley.ParseError: org.json.JSONException: Value true of type java.lang.Boolean cannot be converted to JSONObject")){
                            Toast.makeText(getActivity(),R.string.toast_cliente,Toast.LENGTH_SHORT).show();
                            progressDoalog.dismiss();
                        }

                        if(error.toString().equals("com.android.volley.ParseError: org.json.JSONException: End of input at character 0 of ")){
                            Toast.makeText(getActivity(), R.string.toast_correcto, Toast.LENGTH_SHORT).show();
                            cleanComponents();
                            progressDoalog.dismiss();
                        }
                        else{
                            Toast.makeText(getActivity(),R.string.toast_errordeconex,Toast.LENGTH_SHORT).show();
                            progressDoalog.dismiss();
                        }

                    }


                })

        {
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
        queue.add(postRequest);



    }

    private String getDatePhone() {

        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formatteDate = df.format(date);
        return formatteDate;

    }



    private void getClientsToUpdate(String code, String cardcode, char cardtype, String rucmc, String razonsocial,
                                    String tlf1, String tlf2, String tlfM, String correo, Integer codeasesor, String project, String geolocalizacion
                                    ,String coments, String grupo, String codeTipoDocumento){

        txtCodigo.setText(code);
        txtCodigo.setEnabled(false);
        txtNombre.setText(cardcode);
        txtRuc.setText(rucmc);
        txtRazonSocial.setText(razonsocial);
        txtTelf1.setText(tlf1);
        txtTelf2.setText(tlf2);
        txtTelfM.setText(tlfM);
        txtEmail.setText(correo);
        txtComents.setText(coments);
        lblGeolocalizacionCreate.setText(geolocalizacion);
        loadSpinners(grupo, cardtype, codeTipoDocumento, codeasesor, project);



    }

    public void updateClientafterChanged(){

        String cardType = spnTipo.getSelectedItem().toString();

        if(cardType.equals("Cliente"))
            cardType = "C";
        else
        if(cardType.equals("Proveedor"))
            cardType = "S";
        else
        if(cardType.equals("Lead"))
            cardType ="L";

        String groupCode = codegroup();

        String documento = spntipoDocumento.getSelectedItem().toString();
        if(documento.equals("DOCUMENTO NACIONAL DE IDENTIDA"))
            documento = "C";
        else
        if(documento.equals("DOCUMENTO NACIONAL DE IDENTIDA"))
            documento = "O";
        else
        if(documento.equals("PASAPORTE"))
            documento = "P";
        else
        if(documento.equals("REGISTRO UNICO DE CONTRIBUYENT"))
            documento = "R";


        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("cardCode", txtCodigo.getText().toString());
        jsonParams.put("cardType", cardType);
        jsonParams.put("cardName", txtNombre.getText().toString());
        jsonParams.put("cardFName", txtRazonSocial.getText().toString());
        jsonParams.put("groupCode", groupCode);
        jsonParams.put("cmpPrivate", "C");
        jsonParams.put("licTradNum", txtRuc.getText().toString());
        jsonParams.put("phone1", txtTelf1.getText().toString());
        jsonParams.put("phone2", txtTelf2.getText().toString());
        jsonParams.put("cellular", txtTelfM.getText().toString());
        jsonParams.put("eMail", txtEmail.getText().toString());
        jsonParams.put("updateDate", getDatePhone());
        jsonParams.put("slpCode", oslpTemp.getSlpCode().toString());
        jsonParams.put("notes", txtComents.getText().toString());
        jsonParams.put("glblLocNum", lblGeolocalizacionCreate.getText().toString());
        jsonParams.put("projectCod", oprjTemp.getPrjCode().toString());
        jsonParams.put("country", "EC");
        jsonParams.put("u_bpp_bptd", documento);
        jsonParams.put("userSign2", sharedPreferencesBasicAuth.getUserId("userid"));

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url.getUrl()+"clienteupdate",

                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(),R.string.toast_actualizacioncorrecta,Toast.LENGTH_SHORT).show();
                        progressDoalog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    public static final String LOG_TAG = "com";

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("el erroor el ocrd es: " + error.toString());

                        if(error.toString().equals("com.android.volley.ParseError: org.json.JSONException: Value true of type java.lang.Boolean cannot be converted to JSONObject")){
                            Toast.makeText(getActivity(),R.string.toast_cliente,Toast.LENGTH_SHORT).show();
                            progressDoalog.dismiss();
                        }

                        if(error.toString().equals("com.android.volley.ParseError: org.json.JSONException: End of input at character 0 of ")){
                            Toast.makeText(getActivity(), R.string.toast_actualizacioncorrecta, Toast.LENGTH_SHORT).show();
                            progressDoalog.dismiss();
                        }
                        else
                        if(!error.toString().equals("com.android.volley.ParseError: org.json.JSONException: Value true of type java.lang.Boolean cannot be converted to JSONObject")
                                && !error.toString().equals("com.android.volley.ParseError: org.json.JSONException: End of input at character 0 of ") ){
                            Toast.makeText(getActivity(),R.string.toast_errordeconex,Toast.LENGTH_LONG).show();
                            progressDoalog.dismiss();
                        }


                    }


                })

        {
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
        queue.add(postRequest);
    }

    public void cleanComponents(){
        txtCodigo.setText("");
        txtNombre.setText("");
        txtRuc.setText("");
        txtRazonSocial.setText("");
        txtTelf1.setText("");
        txtTelf2.setText("");
        txtTelfM.setText("");
        txtEmail.setText("");
        txtComents.setText("");
    }


    public String codegroup(){
        String groupCode = spnGrupo.getSelectedItem().toString();
        if(groupCode.equals("Hospitalario"))
            groupCode = "100";
        else
        if(groupCode.equals("Hospitalario e Industrial"))
            groupCode = "104";
        else
        if(groupCode.equals("Industrial"))
            groupCode = "102";
        else
        if(groupCode.equals("Los 70 Incobrables"))
            groupCode = "108";
        else
        if(groupCode.equals("Deuda Procredit"))
            groupCode = "112";
        else
        if(groupCode.equals("Deudas Produbanco"))
            groupCode = "111";
        else
        if(groupCode.equals("Empleados"))
            groupCode = "110";
        else
        if(groupCode.equals("Proveedor Caja Chica"))
            groupCode = "109";
        else
        if(groupCode.equals("Relacionados"))
            groupCode = "107";
        else
        if(groupCode.equals("Servicios Basicos"))
            groupCode = "106";
        else
        if(groupCode.equals("Estrategico"))
            groupCode = "105";
        else
        if(groupCode.equals("Especiales"))
            groupCode = "103";
        else
        if(groupCode.equals("No Estrategicos"))
            groupCode = "101";

        return groupCode;
    }

}

