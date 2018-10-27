package com.ngeartstudio.maturdokter.maturdokter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfilActivity extends AppCompatActivity {
    TextView username,email,notelp,alamat;
    String usernameku,emailku,notelpku,alamatku;
    SharedPreferences sharedpreferences;

    public final static String TAG_USERNAME = "username";
    public final static String TAG_ID = "id";
    public final static String TAG_EMAIL = "email";
    public final static String TAG_NOTELP = "notelp";
    public final static String TAG_ALAMAT = "alamat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        username = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.email);
        notelp = (TextView) findViewById(R.id.notelp);
        alamat = (TextView) findViewById(R.id.alamat);

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        usernameku = sharedpreferences.getString(TAG_USERNAME, null);
        emailku = sharedpreferences.getString(TAG_EMAIL, null);
        notelpku = sharedpreferences.getString(TAG_NOTELP, null);
        alamatku = sharedpreferences.getString(TAG_ALAMAT, null);

        username.setText(usernameku);
        email.setText(emailku);
        notelp.setText(notelpku);
        alamat.setText(alamatku);
    }
}
