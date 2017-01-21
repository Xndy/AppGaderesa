package gaderesa.com.demoappgeogaderesav2.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import gaderesa.com.demoappgeogaderesav2.R;
import gaderesa.com.demoappgeogaderesav2.conecctions.CrdConnections;
import gaderesa.com.demoappgeogaderesav2.servicie.GPSTracker;

public class ActualizarDatosSucursal extends Fragment {

    private ProgressDialog progressDoalog;
    private TextView lblGeoActualActualizar, lblAddress, lblgeoloalizacion, lblCardCodeact, lblstreet, direccionesgeodecoder;
    private Button btnActualizar;
    private Button btnActualizarDatosCrd1;

    private CrdConnections crd1Connections = new CrdConnections();

    // GPSTracker class
    private GPSTracker gps;


    private  static final String addressaux="adr", cardcodeaux="cardcode",  streetaux= "street", glblLocNumaux = "gps" ;
    private String address, cardcode, street, glblLocNum, gadupdateAndroid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_actualizar_datos_sucursal, container, false);



        address = getArguments().getString("address");
        cardcode  = getArguments().getString("cardcode");
        street= getArguments().getString("street");
        glblLocNum = getArguments().getString("gps");

        lblAddress = (TextView) view.findViewById(R.id.lblAddress);
        lblAddress.setText(address);
        lblgeoloalizacion = (TextView) view.findViewById(R.id.lblGeolocalizacionAddress);
        lblgeoloalizacion.setText(glblLocNum);

        lblCardCodeact = (TextView) view.findViewById(R.id.lblCardcodeact);
        lblCardCodeact.setText(cardcode);
        lblstreet = (TextView) view.findViewById(R.id.lblstreet);
        lblstreet.setText(street);


        lblGeoActualActualizar = (TextView) view.findViewById(R.id.lblGeoActualActualizar);

        btnActualizar = (Button) view.findViewById(R.id.btnActualizar);
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
                                    lblGeoActualActualizar.setText(latitude.toString()+ " " + longitude.toString() );
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

        btnActualizarDatosCrd1 = (Button) view.findViewById(R.id.btnActualizarDatosCrd1);
        btnActualizarDatosCrd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(lblGeoActualActualizar.getText() == null || lblGeoActualActualizar.getText().equals("")
                        || lblGeoActualActualizar.getText().equals("Ubicacion Desconocida") ){
                    Toast.makeText(getActivity(), R.string.toast_gpsinvalido, Toast.LENGTH_SHORT).show();

                }else{

                    progressDoalog = new ProgressDialog(getActivity());
                    progressDoalog.setMax(20);
                    progressDoalog.setMessage("Actualizando Registros....");
                    progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDoalog.setIndeterminate(true);
                    progressDoalog.setCancelable(false);
                    progressDoalog.show();

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    crd1Connections.updateDataClient(progressDoalog, ActualizarDatosSucursal.this, lblAddress.getText().toString(), lblCardCodeact.getText().toString(), lblstreet.getText().toString(), lblGeoActualActualizar.getText().toString() );

                                }
                            }, 3000);

                }
            }
                }
        );


        return  view;

    }








    public static Intent newIntent(Context context, String address, String cardCode, String street, String glblLocNum){
        Intent intent = new Intent(context,ActualizarDatosSucursal.class);
        intent.putExtra(addressaux, address);
        intent.putExtra(cardcodeaux, cardCode);
        intent.putExtra(streetaux, street);
        intent.putExtra(glblLocNumaux, glblLocNum);

        return intent;
    }





}
