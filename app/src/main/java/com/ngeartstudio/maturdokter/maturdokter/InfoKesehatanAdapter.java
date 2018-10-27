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

public class InfoKesehatanAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader;

    public InfoKesehatanAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.list_berita, null);

        TextView id_berita = (TextView) vi.findViewById(R.id.id);
        TextView judul = (TextView) vi.findViewById(R.id.judul);
        TextView tanggal = (TextView) vi.findViewById(R.id.tanggal);
        ImageView thumb_image = (ImageView) vi.findViewById(R.id.gambar);

        HashMap<String, String> daftar_berita = new HashMap<String, String>();
        daftar_berita = data.get(position);

        id_berita.setText(daftar_berita.get(InfoKesehatanActivity.TAG_ID));
        judul.setText(daftar_berita.get(InfoKesehatanActivity.TAG_JUDUL));
        tanggal.setText(daftar_berita.get(InfoKesehatanActivity.TAG_TANGGAL));
        //imageLoader.DisplayImage(daftar_berita.get(InfoKesehatanActivity.TAG_GAMBAR),thumb_image);
        Picasso.get().load(daftar_berita.get(InfoKesehatanActivity.TAG_GAMBAR))
                .error(R.drawable.nopoto).into(thumb_image);

        return vi;
    }
}
