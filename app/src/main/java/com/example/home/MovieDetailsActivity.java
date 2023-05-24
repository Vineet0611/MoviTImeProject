package com.example.home;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.home.databinding.ActivityMovieDetailsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = "MovieDetailsActivity";
    Button btn;
    TextView moviename, moviegenre, movieabout, movielanguage, moviequality, movieduration, moviereleased;
    String movie_name= "NA";
    ImageView movietrailer, movieimg;
    String movie_genre, movie_img, movie_id, movie_about, movie_languages, movie_quality, movie_released, movie_trailer, movie_duration;
    String url = "https://inundated-lenders.000webhostapp.com/api/moviedetails.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        btn = findViewById(R.id.bookButton);
        moviename = findViewById(R.id.moviename);
        moviegenre = findViewById(R.id.moviegenre);
        movieabout = findViewById(R.id.movieabout);
        movielanguage = findViewById(R.id.languages);
        moviequality = findViewById(R.id.screenType);
        moviereleased = findViewById(R.id.released);
        movieduration = findViewById(R.id.duration);
        movietrailer = findViewById(R.id.trailerlogo);
        movieimg = findViewById(R.id.movieimg);


        if(getIntent().hasExtra("movie_name") ){
            movie_name = getIntent().getStringExtra("movie_name");
            moviename.setText(movie_name);
            getDetails();
        }


    }

    private void getDetails(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray list = new JSONArray(response);
                            movie_id = list.getJSONObject(0).getString("movie_id");
                            movie_genre = list.getJSONObject(0).getString("movie_genre");
                            movie_trailer = list.getJSONObject(0).getString("movie_ytlink");
                            movie_duration = list.getJSONObject(0).getString("movie_duration");
                            movie_about = list.getJSONObject(0).getString("movie_about");
                            movie_released = list.getJSONObject(0).getString("movie_release");
                            movie_languages = list.getJSONObject(0).getString("movie_languages");
                            movie_quality = list.getJSONObject(0).getString("movie_quality");
                            movie_img = list.getJSONObject(0).getString("movie_image");
//                            JSONObject myJsonObj = list.getJSONObject();
//                            myJsonArray.put(myJsonObj);
//                            Log.d("movie ", "myJsonObj created");
//                            Log.d("movie ", "movie_genre done");
//                            Toast.makeText(MovieDetailsActivity.this, response, Toast.LENGTH_LONG).show();
                            moviegenre.setText(movie_genre);
                            movieabout.setText(movie_about);
                            movielanguage.setText(movie_languages);
                            moviequality.setText(movie_quality);
                            movieduration.setText(movie_duration);
                            moviereleased.setText(movie_released);
                            Glide.with(MovieDetailsActivity.this)
                                    .load(movie_img)
                                    .placeholder(R.drawable.no_image)
                                    .into(movieimg);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("movie", "LogE " + error.getMessage());

                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("moviename", movie_name);
                return params;
            }
        };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(MovieDetailsActivity.this).add(stringRequest);
        Log.d("movie ", "queued success");
    }

}