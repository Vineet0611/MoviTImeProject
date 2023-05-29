package com.example.home;

import android.content.Intent;
import android.content.SharedPreferences;
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

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home.databinding.ActivityMovieDetailsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = "MovieDetailsActivity";
    private long lastClick = 0;
    ProgressBar progressBar;
    RelativeLayout rl;
    Button bookbtn;
    TextView moviename, moviegenre, movieabout, movielanguage, moviequality, movieduration, moviereleased;
    ImageView movietrailer, movieimg;

    ImageButton backButton;
    RecyclerView castRecycler;
    String movie_name, movie_genre, movie_img, movie_id, movie_about, movie_languages, movie_quality, movie_released, movie_trailer, movie_duration,movieVerticalImage;
    private static final String url = "https://inundated-lenders.000webhostapp.com/api/moviedetails.php";
    private static final String castUrl = "https://inundated-lenders.000webhostapp.com/api/cast.php";
//    private static final String crewUrl = "https://inundated-lenders.000webhostapp.com/api/crew.php";
    ArrayList<CastRecycler> arrCast =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        progressBar = findViewById(R.id.progress_bar);
        rl = findViewById(R.id.progress_bar_layout);
        backButton = findViewById(R.id.backButton);
        bookbtn = findViewById(R.id.bookButton);
        moviename = findViewById(R.id.moviename);
        moviegenre = findViewById(R.id.moviegenre);
        movieabout = findViewById(R.id.movieabout);
        movielanguage = findViewById(R.id.languages);
        moviequality = findViewById(R.id.screenType);
        moviereleased = findViewById(R.id.released);
        movieduration = findViewById(R.id.duration);
        movietrailer = findViewById(R.id.trailerlogo);
        movieimg = findViewById(R.id.movieimg);

        castRecycler=(RecyclerView)findViewById(R.id.castRecycler);
        castRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));


        if(getIntent().hasExtra("movie_name") ){
            movie_name = getIntent().getStringExtra("movie_name");
            moviename.setText(movie_name);
            getDetails();
        }
        else{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            finish();
        }

        bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClick < 2000){  //Single Click
                return;
                }
                lastClick = SystemClock.elapsedRealtime();
                TheatreListFragment theatreListFragment = new TheatreListFragment();
                theatreListFragment.show(getSupportFragmentManager(),theatreListFragment.getTag());
                }
        });

        movietrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailsActivity.this, WebViewActivity.class);
                intent.putExtra("movie_trailer", movie_trailer);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void getDetails(){
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
                            movieVerticalImage = list.getJSONObject(0).getString("movieVerticalImage");


                            SharedPreferences prefMovieId = getSharedPreferences("movieId", MODE_PRIVATE);
                            SharedPreferences.Editor prefEditor = prefMovieId.edit();
                            prefEditor.putString("movieId",movie_id);
                            prefEditor.apply();

                            SharedPreferences prefMdetails = getSharedPreferences("moviedetails", MODE_PRIVATE);
                            SharedPreferences.Editor peditor = prefMdetails.edit();
                            peditor.putString("moviename", movie_name);
                            peditor.putString("movieimg", movie_img);
                            peditor.putString("movieVerticalImage", movieVerticalImage);
                            peditor.putString("movieduration", movie_duration);
                            peditor.apply();

                            castDetails();

                            moviegenre.setText(movie_genre);
                            movieabout.setText(movie_about);
                            movielanguage.setText(movie_languages);
                            moviequality.setText(movie_quality);
                            movieduration.setText(movie_duration);
                            moviereleased.setText(movie_released);
                            if(!Objects.equals(movie_img, "Image path not found")) {
                                Glide.with(getApplicationContext())
                                        .load(movie_img)
                                        .placeholder(R.drawable.no_image)
                                        .into(movieimg);
                            }else {
                                movieimg.setBackgroundResource(R.drawable.no_image);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        progressBar.setVisibility(View.GONE);
                        rl.setVisibility(View.GONE);
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
        Log.d("movie", "queued success");

    }
    public void castDetails(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, castUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray castList = new JSONArray(response);
                            for(int i=0; i<castList.length(); i++){
                                JSONObject castObjects = castList.getJSONObject(i);
                                String cImg = castObjects.getString("cast_img");
                                String cName = castObjects.getString("cast_name");
                                String cRole = castObjects.getString("cast_role");

                                CastRecycler castRecycler = new CastRecycler(cImg, cName, cRole);
                                arrCast.add(castRecycler);
                            }
                            Log.d("movie","arrCastData"+ arrCast);

                            CastRecyclerAdapter fadapter = new CastRecyclerAdapter(MovieDetailsActivity.this , arrCast);
                            castRecycler.setAdapter(fadapter);
                            Log.d("movie", "Cast set adapter ");

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MovieDetailsActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("movie", "Cast LogE " + error.getMessage());

                    }

                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("movieid", movie_id);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(this).add(stringRequest);
        Log.d("movie", "Cast queued success: ");
    }






}

