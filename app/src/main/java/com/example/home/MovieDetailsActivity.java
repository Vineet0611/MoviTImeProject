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
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = "MovieDetailsActivity";
    Button btn;
    TextView moviename;
    String movie_name= "NA";
    String url = "https://inundated-lenders.000webhostapp.com/api/moviedetails.php";
    static JSONArray myJsonArray = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        btn = findViewById(R.id.bookButton);
        moviename = findViewById(R.id.moviename);

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
//                        try {
//                            JSONObject myJsonObj = new JSONObject(response);
//                            myJsonArray.put(myJsonObj);
//                            Log.d("movie ", "myJsonObj created");
//                            movie_genre = myJsonArray.getJSONObject(0).getString("movie_genre");
//                            Log.d("movie ", "movie_genre done");
                            Toast.makeText(MovieDetailsActivity.this, response, Toast.LENGTH_LONG).show();

//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MovieDetailsActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("movie", "LogE " + error.getMessage());

                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("moviename", movie_name);
                return getParams();
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