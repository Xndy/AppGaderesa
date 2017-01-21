package gaderesa.com.demoappgeogaderesav2.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import gaderesa.com.demoappgeogaderesav2.R;
import gaderesa.com.demoappgeogaderesav2.conecctions.CrdConnections;
import gaderesa.com.demoappgeogaderesav2.dao.Cdr1DAO;
import gaderesa.com.demoappgeogaderesav2.domains.Crd1;
import gaderesa.com.demoappgeogaderesav2.util.Crd1AdapterDBAndroid;

/**
 * A simple {@link Fragment} subclass.
 */
public class Sync extends Fragment {

    private View view;
    private ListView lvList;
    private ArrayList<Crd1> crd1List = new  ArrayList<Crd1>();
    private Crd1AdapterDBAndroid crd1Adapter;

    private Button btnSincronizar, btnEliminar;
    private ProgressDialog progressDialog;
    private Cdr1DAO cdr1DAO = new Cdr1DAO();

    private CrdConnections crd1Connections = new CrdConnections();
    private String street;

    private TextView txtManifiestoSync, txtGpsSync,txtSync;

    private Bundle bundle = new Bundle() ;

    public Sync() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sync, container, false);
        btnSincronizar = (Button) view.findViewById(R.id.btnSincronizar);
        //btnEliminar = (Button) view.findViewById(R.id.btnEliminar);
        lvList = (ListView) view.findViewById(R.id.lvListSync);

        txtSync = (TextView) view.findViewById(R.id.txtSync);


        crd1List = cdr1DAO.getAllCrd1UpdateOffline(Sync.this);
        crd1Adapter = new Crd1AdapterDBAndroid(getActivity().getApplicationContext(), crd1List);
        lvList.setAdapter(crd1Adapter);
        crd1Adapter.notifyDataSetChanged();

        txtManifiestoSync = (TextView) view.findViewById(R.id.lblNombreSucursalDBA);
        txtGpsSync  = (TextView) view.findViewById(R.id.lblGeolocalizacionDBA);

        //coordenadas manifiesto

        if(crd1Adapter.isEmpty()){
            txtSync.setVisibility(View.VISIBLE);
        }else{
            txtSync.setVisibility(View.INVISIBLE);
        }

        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMax(20);
                progressDialog.setMessage("Actualizando Registros....");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.show();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                if(crd1List.isEmpty()){
                                    Toast.makeText(getActivity(),"No Existen Clientes que Actualizar",Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }else{
                                    for(Crd1 c : crd1List){

                                        crd1Connections.saveDataClientOfflinetoOnline(progressDialog, Sync.this, c.getCardCode()
                                                , c.getGlblLocNum());

                                    }
                                }





                            }
                        }, 3000);
            }
        });



/*        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


        lvList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {

                Crd1 mLog = crd1Adapter.getItem(pos);
                showSettingsAlert(mLog.getCardCode());


                return true;
            }
        });

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Crd1 mLog = crd1Adapter.getItem(position);

                bundle.putString("control","sync");
                bundle.putString("docnum" , mLog.getCardCode());
                bundle.putString("gps",mLog.getGlblLocNum());
                bundle.putString("status", mLog.getCounty());
                bundle.putString("id",mLog.getAddress());


                ModoOffline modoOffline = new ModoOffline();
                modoOffline.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_activity_container, modoOffline);
                transaction.addToBackStack("notback");
                transaction.commit();









            }
        });



        return view;
    }

    public void showSettingsAlert(final String manifiestoid){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("¿Desea Eliminar este registro?");

        // Setting Dialog Message
        //alertDialog.setMessage("GPS no esta habilitado. ¿Desea ir a ajustes para habilitarlo?");

        // On pressing Settings button
        alertDialog.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                cdr1DAO.deleteCrd1UpdateOffline(Sync.this , manifiestoid);

            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }



}
