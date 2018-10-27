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

public class DokterAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    public DokterAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.list_dokter, null);

        TextView id_dokter = (TextView) vi.findViewById(R.id.idDokter);
        TextView nama_dokter = (TextView) vi.findViewById(R.id.namaDokter);

        HashMap<String, String> daftar_dokter = new HashMap<String, String>();
        daftar_dokter = data.get(position);

        id_dokter.setText(daftar_dokter.get(ListDokterActivity.TAG_ID));
        nama_dokter.setText(daftar_dokter.get(ListDokterActivity.TAG_NAMA));

        return vi;
    }
}
