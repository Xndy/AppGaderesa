package gaderesa.com.demoappgeogaderesav2.dao;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gaderesa.com.demoappgeogaderesav2.DBHelper.Contract.GadereContract;
import gaderesa.com.demoappgeogaderesav2.DBHelper.GadereDBHelper;
import gaderesa.com.demoappgeogaderesav2.R;
import gaderesa.com.demoappgeogaderesav2.domains.Crd1;
import gaderesa.com.demoappgeogaderesav2.fragments.DatosGenerales;
import gaderesa.com.demoappgeogaderesav2.fragments.ModoOffline;
import gaderesa.com.demoappgeogaderesav2.fragments.Sync;

/**
 * Created by Xndy on 08/01/2017.
 */

public class Cdr1DAO {

    private GadereDBHelper mDbHelper;


    public boolean saveUpdate(ModoOffline modoOffline, String docnum, String geolocation){

        boolean flagInset;
        mDbHelper = new  GadereDBHelper(modoOffline.getContext());

        try {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(GadereContract.GadereCrd1.COLUMN_NAME_MANIFIEST, docnum);
            values.put(GadereContract.GadereCrd1.COLUMN_NAME_GEOLOCATION, geolocation);
            values.put(GadereContract.GadereCrd1.COLUMN_NAME_ESTADO, "P");

            db.insert(GadereContract.GadereCrd1.TABLE_NAME, null, values);
            Toast.makeText(modoOffline.getActivity(), R.string.toast_correcto, Toast.LENGTH_LONG).show();
            flagInset = true;
        }catch (SQLException e){
            Toast.makeText(modoOffline.getActivity(), "Error de Ingreso " + e.toString(), Toast.LENGTH_LONG).show();
            flagInset = false;
        };

        return flagInset;

    }

    public ArrayList<Crd1> getAllCrd1UpdateOffline(Sync sync){
        mDbHelper = new  GadereDBHelper(sync.getContext());
        ArrayList<Crd1> crd1List = new ArrayList<Crd1>();

        String sql = "SELECT * FROM " + GadereContract.GadereCrd1.TABLE_NAME;
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);


        if (cursor.moveToFirst()) {
            do {
                Crd1 crd1 = new Crd1();
                crd1.setAddress(String.valueOf(cursor.getInt(0)));
                crd1.setCardCode(cursor.getString(1));
                crd1.setGlblLocNum(cursor.getString(2));
                crd1.setCounty(cursor.getString(3));
                crd1List.add(crd1);
            } while (cursor.moveToNext());
        }

        // return contact list
        return crd1List;

    }

    public void deleteCrd1UpdateOffline(Sync sync, String manifiestoid) {



        try {
            mDbHelper = new  GadereDBHelper(sync.getContext());
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.delete(GadereContract.GadereCrd1.TABLE_NAME, GadereContract.GadereCrd1.COLUMN_NAME_MANIFIEST + " = " + manifiestoid,
                    null);
            db.close();
            Toast.makeText(sync.getActivity(),"Manifiesto: " + manifiestoid + " Eliminado", Toast.LENGTH_LONG).show();

        }catch (SQLException e){
            Toast.makeText(sync.getActivity(), "Error de Eliminacion " + manifiestoid + e.toString(), Toast.LENGTH_LONG).show();

        };



    }


    public void updateSaveOffline(ModoOffline modoOffline, String id, String manifiesto, String gps) {


        try {
            mDbHelper = new  GadereDBHelper(modoOffline.getContext());
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(GadereContract.GadereCrd1.COLUMN_NAME_MANIFIEST, manifiesto);
            values.put(GadereContract.GadereCrd1.COLUMN_NAME_GEOLOCATION, gps);

            // updating row
            db.update(GadereContract.GadereCrd1.TABLE_NAME, values, GadereContract.GadereCrd1.COLUMN_NAME_ID + " = "+ id,
                    null);
            db.close();
            Toast.makeText(modoOffline.getActivity(),"Manifiesto: " + manifiesto + " Actualizado", Toast.LENGTH_SHORT).show();

        }catch (SQLException e){
            Toast.makeText(modoOffline.getActivity(), "Error de Actualizacion " + manifiesto + e.toString(), Toast.LENGTH_SHORT).show();

        };





    }



}
