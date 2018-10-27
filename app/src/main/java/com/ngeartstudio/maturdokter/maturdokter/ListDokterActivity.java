package com.ngeartstudio.maturdokter.maturdokter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListDokterActivity extends AppCompatActivity {
    private ProgressBar spinner;
    //private WebView webview;
    private SwipeRefreshLayout swipe;
    //private String currentURL = "https://beritaklaten.com/category/kesehatan/";
    JSONParser jsonParser = new JSONParser();

    private ProgressDialog progressDialog;

    ArrayList<HashMap<String,String>> ListDokter = new ArrayList<HashMap<String, String>>();
    public static final String TAG_ID="id_puskesmas";
    public static final String TAG_NAMA="nama";

    String id;

    JSONArray jsonArray = null;
    ListView ls;
    DokterAdapter adapter;
    AppConfig config = new AppConfig();
    public final static String TAG_KODE = "kode_puskesmas";
    public final static String TAG_EMAIL = "email";
    public static final String my_shared_preferences = "my_shared_preferences";
    SharedPreferences sharedpreferences;
    String kode,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dokter);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        kode = sharedpreferences.getString(TAG_KODE, null);
        email = sharedpreferences.getString(TAG_EMAIL, null);
        Intent a = getIntent();
        id = a.getStringExtra(TAG_ID);

        ListDokter = new ArrayList<HashMap<String,String>>();
        new TampilDokter().execute();
        ls =(ListView)findViewById(R.id.listdokter);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map = ListDokter.get(position);
                String mail_awal = EncodeString(email);
                String mail_tuju = EncodeString(map.get(TAG_EMAIL));
                UserDetails.username = mail_awal;
                UserDetails.chatWith = mail_tuju;
                Intent a = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(a);
                //Toast.makeText(getApplicationContext(),"Baru dikembangkan", Toast.LENGTH_LONG).show();
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SetListDokter(ListDokter);
                ListDokter = new ArrayList<HashMap<String,String>>();
                new TampilDokter().execute();
            }
        });
    }

    class TampilDokter extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ListDokterActivity.this);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Info");
            progressDialog.setMessage("Mengambil Data..");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> parampa = new ArrayList<>();
            JSONObject jsonObject = jsonParser.makeHttpRequest(config.url_dokter+id, "GET", parampa);
            Log.i("JSON : ", "" + jsonObject);
            try {
                jsonArray = jsonObject.getJSONArray("dokter");
                for (int a = 0; a < jsonArray.length(); a++) {
                    JSONObject js = jsonArray.getJSONObject(a);
                    String idDokter = js.getString("id");
                    String namaDokter = js.getString("nama");
                    String email = js.getString("email");
                    //Log.i("JSON : ", "" + idPuskesmas+namaPuskesmas);

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TAG_ID, idDokter);
                    map.put(TAG_NAMA, namaDokter);
                    map.put(TAG_EMAIL, email);
                    ListDokter.add(map);
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
                    SetListDokter(ListDokter);
                }
            });
        }
    }
    private void SetListDokter(ArrayList<HashMap<String, String>> listDokter) {
        adapter = new DokterAdapter(this,ListDokter);
        ls.setAdapter(adapter);
    }
    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }
}
