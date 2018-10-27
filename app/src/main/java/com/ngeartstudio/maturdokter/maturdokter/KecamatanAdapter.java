package com.ngeartstudio.maturdokter.maturdokter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class KecamatanAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    public KecamatanAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader = new ImageLoader(activity.getApplicationContext());
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
            vi = inflater.inflate(R.layout.list_kecamatan, null);

        TextView id_kecamatan = (TextView) vi.findViewById(R.id.idKecamatan);
        TextView nama_kecamatan = (TextView) vi.findViewById(R.id.namaKecamatan);

        HashMap<String, String> daftar_kecamatan = new HashMap<String, String>();
        daftar_kecamatan = data.get(position);

        id_kecamatan.setText(daftar_kecamatan.get(KonsulActivity.TAG_ID));
        nama_kecamatan.setText(daftar_kecamatan.get(KonsulActivity.TAG_NAMA));

        return vi;
    }
}
