package com.ngeartstudio.maturdokter.maturdokter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListPuskesmasActivity extends AppCompatActivity {
    private ProgressBar spinner;
    //private WebView webview;
    private SwipeRefreshLayout swipe;
    //private String currentURL = "https://beritaklaten.com/category/kesehatan/";
    JSONParser jsonParser = new JSONParser();

    private ProgressDialog progressDialog;

    ArrayList<HashMap<String,String>> ListPuskesmas = new ArrayList<HashMap<String, String>>();
    public static final String TAG_ID="id_kecamatan";
    public static final String TAG_NAMA="nama";

    String id;

    JSONArray jsonArray = null;
    ListView ls;
    PuskesmasAdapter adapter;
    AppConfig config = new AppConfig();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_puskesmas);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);

        Intent a = getIntent();
        id = a.getStringExtra(TAG_ID);

        ListPuskesmas = new ArrayList<HashMap<String,String>>();
        new TampilPuskesmas().execute();
        ls =(ListView)findViewById(R.id.listpuskesmas);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map = ListPuskesmas.get(position);
                Toast.makeText(getApplicationContext(), map.get(TAG_ID).toString(), Toast.LENGTH_LONG).show();
                Intent a = new Intent(getApplicationContext(), ListDokterActivity.class);
                a.putExtra("id_puskesmas", map.get(TAG_ID));
                a.putExtra(TAG_NAMA, map.get(TAG_NAMA));
                startActivity(a);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SetListPuskesmas(ListPuskesmas);
                ListPuskesmas = new ArrayList<HashMap<String,String>>();
                new TampilPuskesmas().execute();
            }
        });
    }

    class TampilPuskesmas extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ListPuskesmasActivity.this);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Info");
            progressDialog.setMessage("Mengambil Data..");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> parampa = new ArrayList<>();
            JSONObject jsonObject = jsonParser.makeHttpRequest(config.url_puskesmas+id, "GET", parampa);
            Log.i("JSON : ", "" + jsonObject);
            try {
                jsonArray = jsonObject.getJSONArray("puskesmas");
                for (int a = 0; a < jsonArray.length(); a++) {
                    JSONObject js = jsonArray.getJSONObject(a);
                    String idPuskesmas = js.getString("id_puskesmas");
                    String namaPuskesmas = js.getString("nama");
                    //Log.i("JSON : ", "" + idPuskesmas+namaPuskesmas);

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TAG_ID, idPuskesmas);
                    map.put(TAG_NAMA, namaPuskesmas);
                    ListPuskesmas.add(map);
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
                    SetListPuskesmas(ListPuskesmas);
                }
            });
        }
    }
    private void SetListPuskesmas(ArrayList<HashMap<String, String>> listPuskesmas) {
        adapter = new PuskesmasAdapter(this,ListPuskesmas);
        ls.setAdapter(adapter);
    }
}
