package com.ngeartstudio.maturdokter.maturdokter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private static TextView signin;
    private static EditText fullName, emailId, mobileNumber, locationku, passwordku, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    private ProgressDialog pDialog;
    private SessionManager session;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    Intent intent;
    int success;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signin = (TextView) findViewById(R.id.already_user);
        fullName = (EditText) findViewById(R.id.fullName);
        emailId = (EditText) findViewById(R.id.userEmailId);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        locationku = (EditText) findViewById(R.id.location);
        passwordku = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        signUpButton = (Button) findViewById(R.id.signUpBtn);
        terms_conditions = (CheckBox) findViewById(R.id.terms_conditions);

        mContext = this;

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // UserActivity is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fullName.getText().toString().trim();
                String email = emailId.getText().toString().trim();
                String phone = mobileNumber.getText().toString().trim();
                String location = locationku.getText().toString().trim();
                String password = passwordku.getText().toString().trim();
                String confirmpassword = confirmPassword.getText().toString().trim();
                //checkValidation();
                //registerUser(name,email,password);
                //requestRegister();
                if (!name.isEmpty()) {
                    checkValidation();
                    checkRegister(name, email, phone, location, password, confirmpassword);
                    //requestRegister();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Ada data yang belum diisi!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });


    }

    // Check Validation Method
    private void checkValidation() {

        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getLocation = locationku.getText().toString();
        String getPassword = passwordku.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getLocation.equals("") || getLocation.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0)
            Toast.makeText(this, "Ada isian yang belum terisi.", Toast.LENGTH_SHORT).show();
            // Check if email id valid or not
        else if (!getConfirmPassword.equals(getPassword))
            Toast.makeText(this, "Keta sandi tidak sesuai.", Toast.LENGTH_SHORT).show();

            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked())
            Toast.makeText(this,  "Silahkan pilih/centang syarat dan ketentuan.", Toast.LENGTH_SHORT).show();

            // Else do signup or do your stuff
        //else
            //Toast.makeText(this, "Do SignUp.", Toast.LENGTH_SHORT).show();

    }

    private void checkRegister(final String username, final String email, final String phone, final String location, final String password, final String confirm_password) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Daftar akun ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        //Log.e("Berhasil mendaftarkan akun!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        fullName.getText().clear();
                        emailId.getText().clear();
                        mobileNumber.getText().clear();
                        locationku.getText().clear();
                        passwordku.getText().clear();
                        confirmPassword.getText().clear();

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("email", email);
                params.put("phone", phone);
                params.put("location", location);
                params.put("password", password);
                params.put("confirm_password", confirm_password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
//        intent = new Intent(RegisterActivity.this, MainActivity.class);
//        finish();
//        startActivity(intent);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(RegisterActivity.this, LoginActivity.class);
        finish();
        startActivity(intent);
    }

}
