package gaderesa.com.demoappgeogaderesav2.fragments;


import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import gaderesa.com.demoappgeogaderesav2.R;
import gaderesa.com.demoappgeogaderesav2.dao.Cdr1DAO;
import gaderesa.com.demoappgeogaderesav2.servicie.GPSTracker;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModoOffline extends Fragment {

    private EditText txtManifiesto;
    private TextView txtGeolocalizacionActualizarO;
    private Button btnActualizar, btnActualizarDatosCrd1;

    private  static  String docnumaux = "gaderesa.com.demoappgeogaderesav2.fragments.docnumaux",
            gpsaux = "gaderesa.com.demoappgeogaderesav2.fragments.gpsaux",
            statusaux = "gaderesa.com.demoappgeogaderesav2.fragments.statusaux",
            id = "gaderesa.com.demoappgeogaderesav2.fragments.statusaux",
            control = "gaderesa.com.demoappgeogaderesav2.fragments.control";


    private View view;

    private Cdr1DAO cdr1DAO = new Cdr1DAO();

    private ProgressDialog progressDoalog;
    // GPSTracker class
    private GPSTracker gps;

    public ModoOffline() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_modo_offline, container, false);

        btnActualizar = (Button) view.findViewById(R.id.btnActualizarO);

        btnActualizarDatosCrd1 = (Button) view.findViewById(R.id.btnActualizarDatosCrd1O);
        txtManifiesto = (EditText) view.findViewById(R.id.txtManifiesto);
        txtGeolocalizacionActualizarO = (TextView) view.findViewById(R.id.txtGeolocalizacionActualizarO);

        control = getArguments().getString("control");

        if(control.equals("sync")){
            btnActualizarDatosCrd1.setText("Actualizar Datos");
            docnumaux = getArguments().getString("docnum");
            gpsaux = getArguments().getString("gps");
            statusaux = getArguments().getString("status");
            id= getArguments().getString("id");
            txtManifiesto.setText(docnumaux);
            txtGeolocalizacionActualizarO.setText(gpsaux);
        }
        if(control.equals("index"))
        btnActualizarDatosCrd1.setText("Grabar Nuevos Registros");



        btnActualizar.setOnClickListener(new View.OnClickListener() {
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
                                    txtGeolocalizacionActualizarO.setText(latitude.toString()+ " " + longitude.toString() );
                                    progressDoalog.dismiss();
                                    nwLocation.reset();

                                } else {
                                    gps.showSettingsAlert();
                                    progressDoalog.dismiss();
                                }


                            }
                        }, 3000);
            };
        });


        btnActualizarDatosCrd1.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {

                       if(txtManifiesto.getText().toString().equals("") || txtGeolocalizacionActualizarO.getText().toString().equals("")){

                           Toast.makeText(getActivity(), R.string.toast_invalido, Toast.LENGTH_SHORT).show();

                       }
                       else
                            {

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

                                         if(control.equals("sync")){
                                             cdr1DAO.updateSaveOffline(ModoOffline.this, id, txtManifiesto.getText().toString(),
                                                     txtGeolocalizacionActualizarO.getText().toString());

                                         }
                                         else
                                         {
                                             boolean flagInsert =  cdr1DAO.saveUpdate(ModoOffline.this, txtManifiesto.getText().toString(),
                                                     txtGeolocalizacionActualizarO.getText().toString());
                                             if(flagInsert == true)
                                                 cleanText();
                                         }



                                         progressDoalog.dismiss();
                                     }}, 3000);
                       }}
                      });

        return view;

    }

    public void cleanText(){
        txtManifiesto.setText("");
        txtGeolocalizacionActualizarO.setText("");
    }


}
