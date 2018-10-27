package com.ngeartstudio.maturdokter.maturdokter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailTipsKesehatanActivity extends AppCompatActivity {
    JSONParser jsonParser = new JSONParser();
    ProgressDialog progressDialog;
    InfoKesehatanAdapter adapter;
    JSONArray jsonArray = null;
    //    public ImageLoader imageLoader;{
//        imageLoader = new ImageLoader(null);
//    }
    String id;
    private static final String TAG_ID ="id";
    private static final String TAG_JUDUL ="judul";
    private static final String TAG_GAMBAR="gambar";
    private static final String TAG_ISI="isi";
    AppConfig config = new AppConfig();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tips_kesehatan);
        Intent a = getIntent();
        id = a.getStringExtra(TAG_ID);
        new TampilDetailTipsKesehatan().execute();
    }

    class TampilDetailTipsKesehatan extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(DetailTipsKesehatanActivity.this);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.setTitle("Info");
            progressDialog.setMessage("Mengambil Data..");
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                List<NameValuePair> parampa = new ArrayList<NameValuePair>();
                parampa.add(new BasicNameValuePair("id", id));
                JSONObject jsonObject = jsonParser.makeHttpRequest(config.url_detail_tipskesehatan,"GET",parampa);
                jsonArray = jsonObject.getJSONArray("tipskesehatan");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageView img = (ImageView)findViewById(R.id.imageView);
                        TextView jdl = (TextView)findViewById(R.id.judul);
                        TextView isi = (TextView)findViewById(R.id.isi);
                        try {
                            JSONObject jo = jsonArray.getJSONObject(0);
                            String jdlBerita = jo.getString("judul");
                            String isiBerita = jo.getString("isi");
                            String url_gambar_detail = jo.getString("gambar");
                            //String url_detail_image = jo.getString(TAG_GAMBAR);
                            jdl.setText(jdlBerita);
                            isi.setText(isiBerita);

                            Picasso.get().
                                    load(url_gambar_detail).
                                    error(R.drawable.nopoto).into(img);
                            //imageLoader.DisplayImage(config.gambar_berita+(TAG_GAMBAR), img);

                        }catch (JSONException e)
                        {
                        }
                    }
                });

            }catch (JSONException e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
        }
    }
}
