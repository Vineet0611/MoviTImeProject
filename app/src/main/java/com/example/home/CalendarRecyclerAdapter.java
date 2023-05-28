package com.example.home;

import static android.content.Context.MODE_PRIVATE;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<CalendarRecycler>calendarList;
    String movie_id, city;

    CalendarRecyclerAdapter(Context context, ArrayList<CalendarRecycler> calendarList){
        this.context= context;
        this.calendarList = calendarList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.date_layout, parent, false);

        SharedPreferences prefMovieId = getApplicationContext().getSharedPreferences("movieId", MODE_PRIVATE);
        movie_id = prefMovieId.getString("movieId", "0");
        SharedPreferences prefCity = getApplicationContext().getSharedPreferences("location", MODE_PRIVATE);
        city = prefCity.getString("city", "Select City");


        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.date.setText(calendarList.get(position).date);
        Log.d("calendar", "date ssuccess: ");
        holder.day.setText(calendarList.get(position).day);
        Log.d("calendar", "day ssuccess: ");
        holder.hdate.setText(calendarList.get(position).hiddenDate);
        Log.d("calendar", "day ssuccess: ");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = calendarList.get(holder.getLayoutPosition()).hiddenDate;
                Toast.makeText(view.getContext(), date, Toast.LENGTH_SHORT).show();
//                holder.itemView.setBackgroundColor(R.drawable.background_colored);

                TheatreListFragment t = new TheatreListFragment();
                t.setTheatreList(date, city, movie_id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return calendarList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView date, day, hdate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            date = itemView.findViewById(R.id.date);
            day = itemView.findViewById(R.id.day);
            hdate = itemView.findViewById(R.id.invisibledate);
        }
    }
//    public void setTheatreList(String date){
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            if (!Objects.equals(response, "no record found")){
//                                JSONArray tList = new JSONArray(response);
//                                for(int i=0; i<tList.length(); i++){
//                                    JSONObject theatreListObjects = tList.getJSONObject(i);
//                                    String tId = theatreListObjects.getString("theatre_id");
//                                    String tName = theatreListObjects.getString("theatre_name");
//                                    String language = theatreListObjects.getString("show_language");
//                                    String screen = theatreListObjects.getString("screen_type");
//                                    String date = theatreListObjects.getString("show_date");
//                                    String showid = theatreListObjects.getString("show_id");
//                                    String time = theatreListObjects.getString("show_time");
//
//                                    TheatreRecycler tRecycler = new TheatreRecycler(tName, language, screen, showid, time, tId, date);
//                                    theatreList.add(tRecycler);
//                                }
//                                Log.d("theatre","arrtheatreData"+ theatreList);
//
//                                TheatreListRecyclerAdapter fadapter = new TheatreListRecyclerAdapter(getActivity() , theatreList);
//                                theatreRecycler.setAdapter(fadapter);
//                                Log.d("theatre", "Theatre set adapter");
//                            }else{
//                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
//                            }
//
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_LONG).show();
//                        Log.e("theatre", "theatre LogE " + error.getMessage());
//
//                    }
//
//                }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                params.put("movieid", movie_id);
//                params.put("city", city);
//                params.put("date", date);
//                return params;
//            }
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                50000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
//        Log.d("movie", "theatre queued success: ");
//    }
}

