package gaderesa.com.demoappgeogaderesav2.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import gaderesa.com.demoappgeogaderesav2.R;
import gaderesa.com.demoappgeogaderesav2.domains.Crd1;

/**
 * Created by Xndy on 27/11/2016.
 */

public class Crd1ViewHolder  extends LinearLayout {
    Context mContext;
    Crd1 mLog;

    public Crd1ViewHolder(Context context) {
        super(context);
        mContext = context;
        setup();
    }

    public Crd1ViewHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setup();
    }

    private void setup() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_clients, this);
    }

    public void setLog(Crd1 log) {
        mLog = log;
        TextView tvTitle = (TextView) findViewById(R.id.lblNombreSucursal);
        tvTitle.setText(mLog.getCardCode() + "\n" + mLog.getAddress());
        TextView tvDescription = (TextView) findViewById(R.id.lblGeolocalizacion);
        tvDescription.setText(mLog.getGlblLocNum());
        ImageView imgFlagUpdate = imgFlagUpdate = (ImageView) findViewById(R.id.imgFlagUpdate1);
        if(mLog.getuGadUpgpsandroid().equals(""))
            imgFlagUpdate.setVisibility(INVISIBLE);
        else
            imgFlagUpdate.setVisibility(VISIBLE);
    }

}