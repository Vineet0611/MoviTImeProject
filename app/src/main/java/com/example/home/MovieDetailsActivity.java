package com.example.home;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.home.databinding.ActivityMovieDetailsBinding;

public class MovieDetailsActivity extends AppCompatActivity {

    Button btn;
    TextView moviename;
    String movie_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        btn = findViewById(R.id.bookButton);
        moviename = findViewById(R.id.moviename);

        if(getIntent().hasExtra("movie_name") ){
            movie_name = getIntent().getStringExtra("movie_name");
            moviename.setText(movie_name);
        }



    }

    private void getDetails(){

    }

}