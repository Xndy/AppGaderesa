package gaderesa.com.demoappgeogaderesav2.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import gaderesa.com.demoappgeogaderesav2.R;
import gaderesa.com.demoappgeogaderesav2.domains.Ocrd;

/**
 * Created by Xndy on 02/12/2016.
 */

public class OcrdViewHolder extends LinearLayout {
    Context mContext;
    Ocrd mLog;

    public OcrdViewHolder(Context context) {
        super(context);
        mContext = context;
        setup();
    }

    public OcrdViewHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setup();
    }

    private void setup() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_my_clients, this);
    }

    public void setLog(Ocrd log) {
        mLog = log;
        TextView tvTitle = (TextView) findViewById(R.id.lblCardCodemc);
        tvTitle.setText(mLog.getCardCode() + "");
        TextView tvDescription = (TextView) findViewById(R.id.lblNombreClientemc);
        tvDescription.setText(mLog.getCardName() + "");
        TextView tvEmail = (TextView) findViewById(R.id.lblEmailmc);
        tvEmail.setText(mLog.geteMail() + "");
    }

}