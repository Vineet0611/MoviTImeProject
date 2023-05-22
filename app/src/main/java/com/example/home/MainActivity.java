package com.example.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Timer timer;
    String getUsername, getEmail, getPhone;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkData()) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, activity_login.class);
                    startActivity(intent);
                    finish();
                }
            }, 5000);
        }
    }
    private boolean checkData() {

        SharedPreferences getSharedData = getSharedPreferences("MovieTime", MODE_PRIVATE);

        getUsername = getSharedData.getString("Username", "Data Not Found");
        getEmail = getSharedData.getString("Email", "Data Not Found");
        getPhone = getSharedData.getString("Phone", "Data Not Found");

        return !getUsername.equals("Data Not Found") || !getEmail.equals("Data Not Found") || !getPhone.equals("Data Not Found");
    }
}