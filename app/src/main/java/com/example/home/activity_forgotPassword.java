package com.example.home;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class activity_forgotPassword extends AppCompatActivity {

    TextView txt_email, password, confirm_pass;
    Button submit_btn, fb_login, google_login;
    ImageView password_img_1, password_img_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password);

        txt_email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
        confirm_pass = findViewById(R.id.txt_con_password);

        submit_btn = findViewById(R.id.btn_forgot_passoword);
        fb_login = findViewById(R.id.facebook_btn);
        google_login = findViewById(R.id.google_btn);

        password_img_1 = findViewById(R.id.eye_password_1);
        password_img_2 = findViewById(R.id.eye_password_2);

        setPasswordImg();

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    sendEmailOtp();
                }
            }
        });

        password_img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    if (getMode() == 1) {
                        password_img_1.setImageResource(R.drawable.eye_close_dark);
                    } else if (getMode() == 0) {
                        password_img_1.setImageResource(R.drawable.eye_close);
                    }
                } else {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    if (getMode() == 1) {
                        password_img_1.setImageResource(R.drawable.eye_password_dark);
                    } else if (getMode() == 0) {
                        password_img_1.setImageResource(R.drawable.eye_open);
                    }
                }
            }
        });

        password_img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    if (getMode() == 1) {
                        password_img_2.setImageResource(R.drawable.eye_close_dark);
                    } else if (getMode() == 0) {
                        password_img_2.setImageResource(R.drawable.eye_close);
                    }
                } else {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    if (getMode() == 1) {
                        password_img_2.setImageResource(R.drawable.eye_password_dark);
                    } else if (getMode() == 0) {
                        password_img_2.setImageResource(R.drawable.eye_open);
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
    private void sendEmailOtp() {
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
                                String verify_otp = jsonObject.getString("verify_code");

                                Intent intent = new Intent(getApplicationContext(), activity_email_otp.class);
                                intent.putExtra("email", txt_email.getText().toString());
                                intent.putExtra("password", password.getText().toString());
                                intent.putExtra("otp", verify_otp);
                                startActivity(intent);
                                finish();
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

                paramV.put("Send_Email", "true");
                paramV.put("Email", txt_email.getText().toString().trim());

                return paramV;
            }
        };
        queue.add(stringRequest);

    }
    private boolean validation() {
        if (txt_email.getText().toString().trim().isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Enter Email!", Toast.LENGTH_LONG, R.style.warning_toast).show();
            return false;
        } else if (!txt_email.getText().toString().trim().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(txt_email.getText().toString().trim()).matches()) {
            StyleableToast.makeText(getApplicationContext(), "Enter Valid Email Address!", Toast.LENGTH_LONG, R.style.warning_toast).show();
            return false;
        } else if (password.getText().toString().trim().isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Enter Password!", Toast.LENGTH_LONG, R.style.warning_toast).show();
            return false;
        } else if (confirm_pass.getText().toString().trim().isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Enter Confirm Password!", Toast.LENGTH_LONG, R.style.warning_toast).show();
            return false;
        } else if (password.getText().toString().trim().length() < 8) {
            StyleableToast.makeText(getApplicationContext(), "Password Should be of Atleast 8 Characters!", Toast.LENGTH_LONG, R.style.warning_toast).show();
            return false;
        } else if (!password.getText().toString().trim().equals(confirm_pass.getText().toString().trim())) {
            StyleableToast.makeText(getApplicationContext(), "Confirm Password Not Match!", Toast.LENGTH_LONG, R.style.warning_toast).show();
            return false;
        }
        return true;
    }
    private void setPasswordImg() {
        if (getMode() == 1) {
            password_img_1.setImageResource(R.drawable.eye_close_dark);
        } else if (getMode() == 0) {
            password_img_1.setImageResource(R.drawable.eye_close);
        }

        if (getMode() == 1) {
            password_img_2.setImageResource(R.drawable.eye_close_dark);
        } else if (getMode() == 0) {
            password_img_2.setImageResource(R.drawable.eye_close);
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