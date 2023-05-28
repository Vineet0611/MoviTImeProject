package com.example.home;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class activity_login extends AppCompatActivity {


    ProgressBar progressBar;
    RelativeLayout rl;
    Boolean isValid;
    TextView signup_txt, forgot_pswd;
    EditText username, password;
    Button btn_login, fb_login, google_login;
    ImageView password_img;

    String save_userId, save_username, save_email, save_phone, save_googleId;
    int RC_SIGN_IN = 100;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        rl = (RelativeLayout) findViewById(R.id.progress_bar_layout);

        signup_txt = findViewById(R.id.txt_signup);
        forgot_pswd = findViewById(R.id.txt_forgot_password);
        btn_login = findViewById(R.id.btn_login);
        username = findViewById(R.id.txt_username);
        password = findViewById(R.id.txt_password);
        password_img = findViewById(R.id.eye_password);
        google_login = findViewById(R.id.google_btn);
        fb_login = findViewById(R.id.facebook_btn);

        changeButtonBackground();
        setPasswordImg();

        signup_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_login.this, com.example.home.activity_signup.class);
                startActivity(intent);
            }
        });

        password_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    if(getMode() == 1) {
                        password_img.setImageResource(R.drawable.eye_close_dark);
                    }
                    else if(getMode() == 0) {
                        password_img.setImageResource(R.drawable.eye_close);
                    }
                }
                else {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    if(getMode() == 1) {
                        password_img.setImageResource(R.drawable.eye_password_dark);
                    }
                    else if(getMode() == 0) {
                        password_img.setImageResource(R.drawable.eye_open);
                    }
                }
            }
        });

        forgot_pswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_login.this, com.example.home.activity_forgotPassword.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);  //<-- Temp Code
                startActivity(intent);
                finish();
                if (validation()) {    //<-- Temporary commenting this code for testing...
                    progressBar.setVisibility(View.VISIBLE);
                    rl.setVisibility(View.VISIBLE);
                    checkLoginCredentials();
                }
            }
        });

        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                rl.setVisibility(View.VISIBLE);
                googleLogin();
            }
        });

        fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FacebookAuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        changeButtonBackground();
    }

    private void changeButtonBackground() {

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                google_login.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bg_dark)));
                fb_login.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bg_dark)));
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                google_login.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.rectangle_4_color)));
                fb_login.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.rectangle_4_color)));
                break;
        }
    }
    private void checkLoginCredentials() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://inundated-lenders.000webhostapp.com/Android/select.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "LogIn Response: " + response.toString());
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");

                            if (status.equals("success")) {
                                save_userId = jsonObject.getString("UserId");
                                save_username = jsonObject.getString("Name");
                                save_email= jsonObject.getString("Email");
                                save_phone = jsonObject.getString("Phone");


                                saveData(false);

                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class); //<-- Replace activity_home with Homepage activity
                                intent.putExtra("username", save_username);
                                intent.putExtra("email", save_email);
                                intent.putExtra("phone", save_phone);

                                progressBar.setVisibility(View.GONE);
                                rl.setVisibility(View.GONE);

                                StyleableToast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG, R.style.success_toast).show();
                                startActivity(intent);
                                finish();
                            }

                            else if(status.equals("error")) {
                                progressBar.setVisibility(View.GONE);
                                rl.setVisibility(View.GONE);
                                String errorMsg = jsonObject.getString("error");
                                StyleableToast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG, R.style.error_toast).show();
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();

                paramV.put("Login", "true");
                paramV.put("Email", username.getText().toString().trim());
                paramV.put("Password", password.getText().toString().trim());

                return paramV;
            }
        };
        queue.add(stringRequest);

    }
    private boolean validation() {

        if (username.getText().toString().trim().isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Enter Username", Toast.LENGTH_LONG, R.style.warning_toast).show();
            return false;
        } else if (password.getText().toString().trim().isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_LONG, R.style.warning_toast).show();
            return false;
        }

        return true;
    }
    private void saveData(boolean google_flag) {
        SharedPreferences shared = getSharedPreferences("MovieTime", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        editor.putString("UserId", save_userId);
        editor.putString("Username", save_username);
        editor.putString("Email", save_email);
        editor.putString("Phone", save_phone);

        if(google_flag) {
            editor.putString("Google_login", "true");
            editor.putString("FG_ID", save_googleId);
        }

        editor.apply();
    }
    public void googleLogin() {

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        gsc = GoogleSignIn.getClient(getApplicationContext(), gso);

        //Google Sign In OnClick
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //Getting Data From Google API
                GoogleSignInAccount account1 = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

                if(account1 != null) {
                    save_username = account1.getDisplayName();
                    save_email = account1.getEmail();
                    save_googleId = account1.getId();

                    saveData(true);
                    checkGoogleLogin();
                }
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkGoogleLogin() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://inundated-lenders.000webhostapp.com/Android/select.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "LogIn Response: " + response.toString());
                            JSONObject jsonObject = new JSONObject(response);

                            String status = jsonObject.getString("status");

                            if (status.equals("success")) {

                                SharedPreferences shared = getSharedPreferences("MovieTime",MODE_PRIVATE);
                                SharedPreferences.Editor editor = shared.edit();
                                String phone = jsonObject.getString("Phone");
                                String userId = jsonObject.getString("UserId");

                                editor.putString("UserId", userId);
                                editor.putString("Phone", phone);
                                editor.apply();

                                progressBar.setVisibility(View.GONE);
                                rl.setVisibility(View.GONE);

                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                StyleableToast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG, R.style.success_toast).show();
                                startActivity(intent);
                            }

                            else if(status.equals("google_signup")) {

                                progressBar.setVisibility(View.GONE);
                                rl.setVisibility(View.GONE);

                                Intent intent = new Intent(getApplicationContext(), activity_email_phone.class);
                                startActivity(intent);
                            }

                            else if(status.equals("error")) {
                                progressBar.setVisibility(View.GONE);
                                rl.setVisibility(View.GONE);

                                String errorMsg = jsonObject.getString("error");
                                StyleableToast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG, R.style.error_toast).show();
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                StyleableToast.makeText(getApplicationContext(), "Sorry something went wrong please try again!", Toast.LENGTH_LONG, R.style.error_toast).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();

                paramV.put("check_google", "true");
                paramV.put("FG_Id", save_googleId);

                return paramV;
            }
        };
        queue.add(stringRequest);

    }
    private void setPasswordImg() {
        if (getMode() == 1) {
            password_img.setImageResource(R.drawable.eye_close_dark);
        } else if (getMode() == 0) {
            password_img.setImageResource(R.drawable.eye_close);
        }
    }
    private int getMode() {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                return 1;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                return 0;
        }
        return 2;
    }
}