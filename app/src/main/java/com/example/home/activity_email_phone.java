package com.example.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import io.github.muddz.styleabletoast.StyleableToast;

public class activity_email_phone extends AppCompatActivity {

    EditText email, phone;
    Button submit;

    String getEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_phone);

        email = findViewById(R.id.txt_email);
        phone = findViewById(R.id.txt_phone);

        submit = findViewById(R.id.btn_submit);

        getEmail();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()) {
                    sendOTP();
                }
            }
        });
    }

    private void sendOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phone.getText().toString(),
                60,
                TimeUnit.SECONDS,
                activity_email_phone.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                    progressBar.setVisibility(View.GONE);
//                                    sendotp.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        StyleableToast.makeText(getApplicationContext(), "OTP Verification Failed", Toast.LENGTH_LONG, R.style.error_toast).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verify_otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                        savePhoneNo();
                        Intent intent = new Intent(getApplicationContext(), activity_verification.class);
                        intent.putExtra("phone_number", phone.getText().toString());
                        intent.putExtra("verify_otp", verify_otp);
                        startActivity(intent);

                    }
                }
        );
    }

    private void savePhoneNo() {
        SharedPreferences shared = getSharedPreferences("MovieTime",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        editor.putString("Phone", phone.getText().toString().trim());

        editor.apply();
    }
    private void getEmail() {

        SharedPreferences getSharedData = getSharedPreferences("MovieTime", MODE_PRIVATE);
        getEmail = getSharedData.getString("Email", "Data Not Found");

        if (!getEmail.equals("Data Not Found")) {
            email.setText(getEmail);
            email.setEnabled(false);
        }

    }
    private boolean validation() {

        if (phone.getText().toString().trim().isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Enter Phone Number", Toast.LENGTH_LONG, R.style.warning_toast).show();
            return false;
        }

        else if (phone.getText().toString().trim().length() != 10) {
            StyleableToast.makeText(getApplicationContext(), "Phone number should be of 10 digits", Toast.LENGTH_LONG, R.style.warning_toast).show();
            return false;
        }
        return true;
    }
}