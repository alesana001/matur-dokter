package com.ngeartstudio.maturdokter.maturdokter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KonsulActivity extends AppCompatActivity {
    private ProgressBar spinner;
    //private WebView webview;
    private SwipeRefreshLayout swipe;
    //private String currentURL = "https://beritaklaten.com/category/kesehatan/";
    JSONParser jsonParser = new JSONParser();

    private ProgressDialog progressDialog;

    ArrayList<HashMap<String,String>> ListKecamatan = new ArrayList<HashMap<String, String>>();
    public static final String TAG_ID="id_kecamatan";
    public static final String TAG_NAMA="nama";

    JSONArray jsonArray = null;
    ListView ls;
    KecamatanAdapter adapter;
    AppConfig config = new AppConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsul);

        //        webview =(WebView)findViewById(R.id.web_infokesehatan);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);

        ListKecamatan = new ArrayList<HashMap<String,String>>();
        new TampilKecamatan().execute();
        ls =(ListView)findViewById(R.id.listdatakecamatan);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String,String> map = ListKecamatan.get(position);
                    Intent a = new Intent(getApplicationContext(), ListPuskesmasActivity.class);
                    a.putExtra(TAG_ID, map.get(TAG_ID));
                    a.putExtra(TAG_NAMA, map.get(TAG_NAMA));
                    startActivity(a);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SetListKecamatan(ListKecamatan);
                ListKecamatan = new ArrayList<HashMap<String,String>>();
                new TampilKecamatan().execute();
            }
        });

    }

    class TampilKecamatan extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(KonsulActivity.this);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Info");
            progressDialog.setMessage("Mengambil Data..");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> parampa = new ArrayList<>();
            JSONObject jsonObject = jsonParser.makeHttpRequest(config.url_kecamatan, "GET", parampa);
            Log.i("JSON : ", "" + jsonObject);
            try {
                jsonArray = jsonObject.getJSONArray("kecamatan");
                for (int a = 0; a < jsonArray.length(); a++) {
                    JSONObject js = jsonArray.getJSONObject(a);
                    String idKecamatan = js.getString(TAG_ID);
                    String namaKecamatan = js.getString(TAG_NAMA);

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TAG_ID, idKecamatan);
                    map.put(TAG_NAMA, namaKecamatan);
                    ListKecamatan.add(map);
                }
            } catch (JSONException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            swipe.setRefreshing(false);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SetListKecamatan(ListKecamatan);
                }
            });
        }
    }
    private void SetListKecamatan(ArrayList<HashMap<String, String>> listKecamatan) {
        adapter = new KecamatanAdapter(this,ListKecamatan);
        ls.setAdapter(adapter);
    }

    @Override
    public void onBackPressed (){
            super.onBackPressed();

    }
}
