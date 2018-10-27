package com.ngeartstudio.maturdokter.maturdokter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String id, username, email, notelp, alamat;
    SharedPreferences sharedpreferences;
    CardView konsulDokter, spgdtIntegrasi, beritaKesehatan, tipsKesehatan,forumKesehatan;
    ImageView telpDarurat;

    public final static String TAG_USERNAME = "username";
    public final static String TAG_ID = "id";
    public final static String TAG_EMAIL = "email";
    public final static String TAG_NOTELP = "notelp";
    public final static String TAG_ALAMAT = "alamat";
    public final static String TAG_KODE = "kode_puskesmas";
    public static final String my_shared_preferences = "my_shared_preferences";

    String kode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        kode = sharedpreferences.getString(TAG_KODE, null);

        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);
        email = getIntent().getStringExtra(TAG_EMAIL);
        notelp = getIntent().getStringExtra(TAG_NOTELP);
        alamat = getIntent().getStringExtra(TAG_ALAMAT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        konsulDokter = (CardView) findViewById(R.id.konsulDokter);
        spgdtIntegrasi = (CardView) findViewById(R.id.spgdtIntegrasi);
        beritaKesehatan = (CardView) findViewById(R.id.beritaKesehatan);
        tipsKesehatan = (CardView) findViewById(R.id.tipsKesehatan);
        forumKesehatan = (CardView) findViewById(R.id.forumKesehatan);
        telpDarurat = (ImageView) findViewById(R.id.telpDarurat);

        telpDarurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL); //use ACTION_CALL class
                callIntent.setData(Uri.parse("tel:0800000000"));    //this is the phone number calling
                //check permission
                //If the device is running Android 6.0 (API level 23) and the app's targetSdkVersion is 23 or higher,
                //the system asks the user to grant approval.
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //request permission from user if the app hasn't got the required permission
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                            10);
                    return;
                }else {     //have got permission
                    try{
                        startActivity(callIntent);  //call activity and make phone call
                    }
                    catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        konsulDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kode.equals("99")){
                    Intent intent = new Intent(MainActivity.this, KonsulActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    startActivity(intent);
                }
            }
        });

        spgdtIntegrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SPGDTActivity.class);
                startActivity(intent);
            }
        });

        beritaKesehatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InfoKesehatanActivity.class);
                startActivity(intent);
            }
        });

        tipsKesehatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TipsKesehatanActivity.class);
                startActivity(intent);
            }
        });
        forumKesehatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForumActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.tentang:
                Intent intentTentang = new Intent(MainActivity.this, TentangActivity.class);
                startActivity(intentTentang);
                return true;

            case R.id.profil:
                Intent intentProfil = new Intent(MainActivity.this, ProfilActivity.class);
                startActivity(intentProfil);
                return true;

            case R.id.keluar:
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dotsmenu, menu);
        return true;
    }
}