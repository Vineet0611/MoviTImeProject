package com.example.home;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.github.muddz.styleabletoast.StyleableToast;

public class activity_signup extends AppCompatActivity {

    TextView login_txt;
    ProgressBar progressBar;
    RelativeLayout rl;
    EditText username, email, password, phone;
    Button signupBtn, fb_login, google_login;
    ImageView password_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Intent intent = new Intent(activity_signup.this, activity_verification.class);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        rl = (RelativeLayout) findViewById(R.id.progress_bar_layout);

        login_txt = findViewById(R.id.login);

        signupBtn = findViewById(R.id.signup_btn);
        google_login = findViewById(R.id.google_btn);
        fb_login = findViewById(R.id.facebook_btn);

        username = findViewById(R.id.txt_username);
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
        phone = findViewById(R.id.txt_phone);

        password_img = findViewById(R.id.eye_password);

        setPasswordImg();

        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_signup.this, activity_login.class);
                startActivity(intent);
                finish();
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    insertData();
                }
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    google_login.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bg_dark)));
                    fb_login.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bg_dark)));
                }
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    google_login.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.rectangle_4_color)));
                    fb_login.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.rectangle_4_color)));
                }
                break;
        }
    }
    private void insertData() {

        progressBar.setVisibility(View.VISIBLE);
        rl.setVisibility(View.VISIBLE);

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
                                sendOTP();
                            } else if (status.equals("error")) {
                                String errorMsg = jsonObject.getString("error");
                                StyleableToast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG, R.style.error_toast).show();
                            }

//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                                String error = jsonObject.getString("error");
//                                String Response = jsonObject.getString("response");
//
//                                Intent intent = new Intent(getApplicationContext(), activity_home.class);
//                                intent.putExtra("error", error);
//                                intent.putExtra("response", Response);
//                                startActivity(intent);
//                            }

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

                paramV.put("SignUp", "true");
                paramV.put("Username", username.getText().toString().trim());
                paramV.put("Email", email.getText().toString().trim());
                paramV.put("Phone", phone.getText().toString().trim());
                paramV.put("Password", password.getText().toString().trim());

                return paramV;
            }
        };
        queue.add(stringRequest);

    }
    private void sendOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phone.getText().toString(),
                60,
                TimeUnit.SECONDS,
                activity_signup.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        progressBar.setVisibility(View.GONE);
                        rl.setVisibility(View.GONE);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        StyleableToast.makeText(getApplicationContext(), "OTP Verification Failed", Toast.LENGTH_LONG, R.style.error_toast).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verify_otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                        Intent intent = new Intent(getApplicationContext(), activity_verification.class);
                        intent.putExtra("phone_number", phone.getText().toString());
                        intent.putExtra("verify_otp", verify_otp);
                        startActivity(intent);

                    }
                }
        );
    }
    private boolean validation() {

        if (username.getText().toString().trim().isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Enter Username", Toast.LENGTH_LONG, R.style.warning_toast).show();
            return false;
        } else if (email.getText().toString().trim().isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_LONG, R.style.warning_toast).show();
            return false;
        } else if (phone.getText().toString().trim().isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Enter Phone Number", Toast.LENGTH_LONG, R.style.warning_toast).show();
            return false;
        } else if (password.getText().toString().trim().isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_LONG, R.style.warning_toast).show();
            return false;
        } else if (!email.getText().toString().trim().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
            StyleableToast.makeText(getApplicationContext(), "Enter Valid Email Address", Toast.LENGTH_LONG, R.style.warning_toast).show();
            return false;
        } else if (password.getText().toString().trim().length() < 8) {
            StyleableToast.makeText(getApplicationContext(), "Password Should be of Atleast 8 Characters!", Toast.LENGTH_LONG, R.style.warning_toast).show();
            return false;
        } else if (phone.getText().toString().trim().length() != 10) {
            StyleableToast.makeText(getApplicationContext(), "Phone number should be of 10 digits", Toast.LENGTH_LONG, R.style.warning_toast).show();
            return false;
        }
        return true;
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