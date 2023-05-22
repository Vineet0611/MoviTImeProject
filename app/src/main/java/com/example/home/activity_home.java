package com.example.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class activity_home extends AppCompatActivity {

    TextView username, email, phone, paymentid;
    String getUsername, getEmail, getPhone, getGoogleStatus, getFacebookStatus, getId, getUserId;
    Button logout_btn, payment_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        username = findViewById(R.id.txt_username);
//        email = findViewById(R.id.txt_email);
//        phone = findViewById(R.id.txt_phone);
       // paymentid = findViewById(R.id.txt_pid);

      //  logout_btn = findViewById(R.id.logout_btn);
      //  payment_btn = findViewById(R.id.payment_btn);

        getData();

//        logout_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logout();
//            }
//        });


//        payment_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ArrayList<String> list = new ArrayList<>();
//                list.add("H3");
//                list.add("H4");
//
//                Intent intent = new Intent(getApplicationContext(), activity_payment.class);
//                intent.putExtra("seatNo", list);
//                startActivity(intent);
//            }
//        });

    }

    //Below Code is Getting User Data from Shared SharedPreferences
    private void getData() {

        SharedPreferences getSharedData = getSharedPreferences("MovieTime", MODE_PRIVATE);

        getUserId= getSharedData.getString("UserId", "Data Not Found");
        getUsername = getSharedData.getString("Username", "Data Not Found");
        getEmail = getSharedData.getString("Email", "Data Not Found");
        getPhone = getSharedData.getString("Phone", "Data Not Found");
        getGoogleStatus = getSharedData.getString("Google_login", "false");
        getFacebookStatus = getSharedData.getString("Facebook_login", "false");

//        username.setText(getUsername);
//        email.setText(getEmail);
//        phone.setText(getPhone);
//        paymentid.setText(getUserId);
    }
    //--------------------------

    //Below Code is for logging out the User From Normal/Google/Facebook login
    private void logout() {

        if (getGoogleStatus.equals("true")) {

            GoogleSignInOptions gso;
            GoogleSignInClient gsc;

            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            gsc = GoogleSignIn.getClient(this, gso);

            gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    clearSharedData();
                }
            });
        }
        else if (getFacebookStatus.equals("true")) {

            LoginManager.getInstance().logOut();
            clearSharedData();
        }
        else {
            clearSharedData();
        }
    }
    private void clearSharedData() {
        SharedPreferences getSharedData = getSharedPreferences("MovieTime", MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedData.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), activity_login.class);
        startActivity(intent);
        finish();
    }
    //-------------------------------------------------------------------------
}