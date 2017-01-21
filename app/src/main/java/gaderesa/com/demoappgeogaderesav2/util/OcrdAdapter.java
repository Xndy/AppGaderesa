package gaderesa.com.demoappgeogaderesav2.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import gaderesa.com.demoappgeogaderesav2.domains.Ocrd;

/**
 * Created by Xndy on 02/12/2016.
 */

public class OcrdAdapter extends BaseAdapter {
    ArrayList<Ocrd> ocrdList = new ArrayList<Ocrd>();
    Context context;

    public OcrdAdapter(Context context, ArrayList<Ocrd> ocrdList) {
        this.ocrdList = ocrdList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ocrdList.size();
    }

    @Override
    public Ocrd getItem(int position) {
        return ocrdList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OcrdViewHolder view = (OcrdViewHolder) convertView;
        if (view == null) {
            view = new OcrdViewHolder(context);
        }
        Ocrd log = getItem(position);
        view.setLog(log);
        return view;
    }
}