package gaderesa.com.demoappgeogaderesav2.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import gaderesa.com.demoappgeogaderesav2.domains.Crd1;

/**
 * Created by Xndy on 27/11/2016.
 */

public class Crd1Adapter extends BaseAdapter {
    ArrayList<Crd1> crd1List = new ArrayList<Crd1>();
    Context context;

    public Crd1Adapter(Context context, ArrayList<Crd1> crd1List) {
        this.crd1List = crd1List;
        this.context = context;
    }

    @Override
    public int getCount() {
        return crd1List.size();
    }

    @Override
    public Crd1 getItem(int position) {
        return crd1List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Crd1ViewHolder view = (Crd1ViewHolder) convertView;
        if (view == null) {
            view = new Crd1ViewHolder(context);
        }
        Crd1 log = getItem(position);
        view.setLog(log);
        return view;
    }
}