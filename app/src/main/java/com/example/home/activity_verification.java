package com.example.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.github.muddz.styleabletoast.StyleableToast;


public class activity_verification extends AppCompatActivity {

    ProgressBar progressBar;
    RelativeLayout rl;
    EditText et1, et2, et3, et4, et5, et6;
    Button submit_btn;
    TextView phone_no, resend_otp;
    String userOTP, getOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        rl = (RelativeLayout) findViewById(R.id.progress_bar_layout);

        et1 = findViewById(R.id.inputotp1);
        et2 = findViewById(R.id.inputotp2);
        et3 = findViewById(R.id.inputotp3);
        et4 = findViewById(R.id.inputotp4);
        et5 = findViewById(R.id.inputotp5);
        et6 = findViewById(R.id.inputotp6);

        phone_no = findViewById(R.id.phone_no);
        resend_otp = findViewById(R.id.resend);
        submit_btn = findViewById(R.id.btn_submit);

        getOTP = getIntent().getStringExtra("verify_otp");

        setPhoneNo();
        moveToNextTextBox();

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    progressBar.setVisibility(View.VISIBLE);
                    rl.setVisibility(View.VISIBLE);
                    setOtp();
                    checkOtp();
                }
            }
        });

        resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resendOtp();
            }
        });
    }

    private void resendOtp() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + getIntent().getStringExtra("phone_number"),
                60,
                TimeUnit.SECONDS,
                activity_verification.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(activity_verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String newOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        getOTP = newOtp;
                        Toast.makeText(activity_verification.this, "OTP Send Successfully", Toast.LENGTH_SHORT).show();
                    }
                }

        );
    }

    private void checkOtp() {
        if (getOTP != null) {
            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getOTP, userOTP);
            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                saveData();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                rl.setVisibility(View.GONE);
                                Toast.makeText(activity_verification.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(activity_verification.this, "An Unexpected Error Occur Please Resend The OTP", Toast.LENGTH_LONG).show();
        }
    }

    private void saveData() {

        SharedPreferences getSharedData = getSharedPreferences("MovieTime", MODE_PRIVATE);
        String facebook_status = getSharedData.getString("Facebook_login", "Data Not Found");
        String google_status = getSharedData.getString("Google_login", "Data Not Found");
        String getUsername = getSharedData.getString("Username", "Data Not Found");
        String getEmail = getSharedData.getString("Email", "Data Not Found");
        String getPhone = getSharedData.getString("Phone", "Data Not Found");
        String getId = getSharedData.getString("FG_ID", "Data Not Found");


        if (facebook_status.equals("true")) {
            insertData("Facebook", getUsername, getEmail, getPhone, getId);
        } else if (google_status.equals("true")) {
            insertData("Google", getUsername, getEmail, getPhone, getId);
        } else {
            progressBar.setVisibility(View.GONE);
            rl.setVisibility(View.GONE);
            Intent intent = new Intent(getApplicationContext(), activity_login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            StyleableToast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_LONG, R.style.success_toast).show();
            startActivity(intent);
        }
    }

    private void insertData(String loginType, String getUsername, String getEmail, String getPhone, String getId) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://inundated-lenders.000webhostapp.com/Android/select.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");

                            if (status.equals("success")) {

                                StyleableToast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG, R.style.success_toast).show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            } else if (status.equals("error")) {
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

                paramV.put(loginType, "true");
                paramV.put("FG_Id", getId);
                paramV.put("Username", getUsername);
                paramV.put("Email", getEmail);
                paramV.put("Phone", getPhone);

                return paramV;
            }
        };
        queue.add(stringRequest);

    }

    private void setOtp() {
        userOTP = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString() + et5.getText().toString() + et6.getText().toString();
    }

    private void setPhoneNo() {
        phone_no.setText(String.format(
                "+91-%S", getIntent().getStringExtra("phone_number")
        ));
    }

    private void moveToNextTextBox() {

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    et2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    et3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    et4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    et5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    et6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean validation() {
        if (et1.getText().toString().trim().isEmpty() || et2.getText().toString().trim().isEmpty() || et3.getText().toString().trim().isEmpty()
                || et4.getText().toString().trim().isEmpty() || et5.getText().toString().trim().isEmpty() || et6.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}