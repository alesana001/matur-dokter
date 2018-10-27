package com.ngeartstudio.maturdokter.maturdokter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class PuskesmasAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    public PuskesmasAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_puskesmas, null);

        TextView id_puskesmas = (TextView) vi.findViewById(R.id.idPuskesmas);
        TextView nama_puskesmas = (TextView) vi.findViewById(R.id.namaPuskesmas);

        HashMap<String, String> daftar_puskesmas = new HashMap<String, String>();
        daftar_puskesmas = data.get(position);

        id_puskesmas.setText(daftar_puskesmas.get(ListPuskesmasActivity.TAG_ID));
        nama_puskesmas.setText(daftar_puskesmas.get(ListPuskesmasActivity.TAG_NAMA));

        return vi;
    }
}
