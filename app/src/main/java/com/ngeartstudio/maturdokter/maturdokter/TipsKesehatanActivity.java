package com.ngeartstudio.maturdokter.maturdokter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

public class TipsKesehatanActivity extends AppCompatActivity {
    private ProgressBar spinner;
    //private WebView webview;
    private SwipeRefreshLayout swipe;
    //private String currentURL = "https://beritaklaten.com/category/kesehatan/";
    JSONParser jsonParser = new JSONParser();

    private ProgressDialog progressDialog;

    ArrayList<HashMap<String,String>> ListTipsKesehatan = new ArrayList<HashMap<String, String>>();
    public static final String TAG_ID="id";
    public static final String TAG_JUDUL="judul";
    public static final String TAG_TANGGAL="tanggal";
    public static final String TAG_GAMBAR="gambar";
    JSONArray jsonArray = null;
    ListView ls;
    TipsKesehatanAdapter adapter;
    AppConfig config = new AppConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_kesehatan);

//        webview =(WebView)findViewById(R.id.web_infokesehatan);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);

        ListTipsKesehatan = new ArrayList<HashMap<String,String>>();
        new TampilTipsKesehatan().execute();
        ls =(ListView)findViewById(R.id.listtipskesehatan);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map = ListTipsKesehatan.get(position);
                Intent a = new Intent(getApplicationContext(), DetailTipsKesehatanActivity.class);
                a.putExtra(TAG_ID, map.get(TAG_ID));
                a.putExtra(TAG_JUDUL, map.get(TAG_JUDUL));
                a.putExtra(TAG_TANGGAL, map.get(TAG_TANGGAL));
                a.putExtra(TAG_GAMBAR, map.get(TAG_GAMBAR));
                startActivity(a);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SetListTipsKesehatan(ListTipsKesehatan);
                ListTipsKesehatan = new ArrayList<HashMap<String,String>>();
                new TampilTipsKesehatan().execute();
            }
        });

    }
    class TampilTipsKesehatan extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(TipsKesehatanActivity.this);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Info");
            progressDialog.setMessage("Mengambil Data..");
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> parampa = new ArrayList<>();
            JSONObject jsonObject = jsonParser.makeHttpRequest(config.url_tipskesehatan,"GET", parampa);
            Log.i("JSON : ", ""+jsonObject);
            try{
                jsonArray = jsonObject.getJSONArray("tipskesehatan");
                for(int a=0; a <jsonArray.length(); a++){
                    JSONObject js = jsonArray.getJSONObject(a);
                    String idBerita = js.getString(TAG_ID);
                    String jdlberita = js.getString(TAG_JUDUL);
                    String tglberita = js.getString(TAG_TANGGAL);
                    //String gbrBerita = config.gambar_berita+js.getString(TAG_GAMBAR);
                    String gbrBerita = js.getString(TAG_GAMBAR);

                    HashMap<String,String> map = new HashMap<String ,String>();
                    map.put(TAG_ID, idBerita);
                    map.put(TAG_JUDUL, jdlberita);
                    map.put(TAG_TANGGAL, tglberita);
                    map.put(TAG_GAMBAR, gbrBerita);
                    ListTipsKesehatan.add(map);
                }
            }catch (JSONException e){
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
                    SetListTipsKesehatan(ListTipsKesehatan);
                }
            });
        }

//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.getSettings().setDomStorageEnabled(true);
//        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
//        webview.loadUrl(currentURL);
//        webview.setWebViewClient(new MyWebViewClient());

//        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                //webview.loadUrl(currentURL);
//            }
//        });
    }

    //    public class MyWebViewClient extends WebViewClient{
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            swipe.setRefreshing(false);
//            currentURL = url;
//            super.onPageFinished(view, url);
//            spinner.setVisibility(View.GONE);
//        }
//    }
    private void SetListTipsKesehatan(ArrayList<HashMap<String, String>> listTipsKesehatan) {
        adapter = new TipsKesehatanAdapter(this,listTipsKesehatan);
        ls.setAdapter(adapter);
    }

    @Override
    public void onBackPressed (){

//        if (webview.isFocused() && webview.canGoBack()) {
//            webview.goBack();

        //}else{
        super.onBackPressed();
        //}

    }
}
