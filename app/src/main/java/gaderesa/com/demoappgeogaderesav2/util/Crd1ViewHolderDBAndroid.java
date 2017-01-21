package gaderesa.com.demoappgeogaderesav2.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import gaderesa.com.demoappgeogaderesav2.R;
import gaderesa.com.demoappgeogaderesav2.domains.Crd1;

/**
 * Created by Xndy on 27/11/2016.
 */

public class Crd1ViewHolderDBAndroid extends LinearLayout {
    Context mContext;
    Crd1 mLog;

    public Crd1ViewHolderDBAndroid(Context context) {
        super(context);
        mContext = context;
        setup();
    }

    public Crd1ViewHolderDBAndroid(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setup();
    }

    private void setup() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.card_view_dbandroidsucursales, this);
    }

    public void setLog(Crd1 log) {
        mLog = log;
        TextView tvTitle = (TextView) findViewById(R.id.lblNombreSucursalDBA);
        tvTitle.setText("Manifiesto: " + mLog.getCardCode());
        TextView tvDescription = (TextView) findViewById(R.id.lblGeolocalizacionDBA);
        tvDescription.setText("Coordenadas " + mLog.getGlblLocNum() + " ");
        TextView txtDireccionSync = (TextView) findViewById(R.id.txtDireccionSync);
        if (txtDireccionSync.getText().toString().equals("") || txtDireccionSync.getText() == null)
            txtDireccionSync.setEnabled(false);

    }

}