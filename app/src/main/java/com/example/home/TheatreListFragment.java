package com.example.home;

import static android.content.Context.MODE_PRIVATE;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Movie;
import android.nfc.Tag;
import android.nfc.tech.TagTechnology;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class TheatreListFragment extends BottomSheetDialogFragment {

    private static final String TAG = "TheatreListFragment";
    ProgressBar progressBar;
    ArrayList<CalendarRecycler> calendarList =new ArrayList<>();
    ArrayList<TheatreRecycler> theatreList = new ArrayList<>();

    private static final String url = "https://inundated-lenders.000webhostapp.com/api/theatre.php";
    RecyclerView calendarRecycler, theatreRecycler;
    String mid, c, d;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theatre_list, container, false);

        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);

        calendarRecycler = (RecyclerView)view.findViewById(R.id.calendarRecycler);
        calendarRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
        theatreRecycler = (RecyclerView)view.findViewById(R.id.theatreRecycler);
        theatreRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        SharedPreferences prefMovieId = getActivity().getSharedPreferences("movieId", MODE_PRIVATE);
        mid = prefMovieId.getString("movieId", "0");
        SharedPreferences prefCity = getActivity().getSharedPreferences("location", MODE_PRIVATE);
        c = prefCity.getString("city", "Select City");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LocalDate localDate = LocalDate.now();
        d = localDate.toString();

        setCalendarRecycler();
        setTheatreList(d, c, mid);


    }

        public void setCalendarRecycler(){

            LocalDate localDate = LocalDate.now();
            LocalDate t =  localDate.minusDays(1);
            for(int i =1 ; i<=15; i++){
                LocalDate date =  t.plusDays(i);
                LocalDate dateparse = LocalDate.parse(""+ date);
                String d = date.toString();
                DayOfWeek dayOfWeek = dateparse.getDayOfWeek();
                String s =  dayOfWeek.toString().substring(0, 3);
                int dayOfMonth = dateparse.getDayOfMonth();
                String ss = Integer.toString(dayOfMonth);
                Log.d("calendar" ,s + ss);
                CalendarRecycler calendarRecycler = new CalendarRecycler(ss, s, d);
                calendarList.add(calendarRecycler);
                Log.d("calendar" ,"List Set");
            }

            CalendarRecyclerAdapter fadapter = new CalendarRecyclerAdapter(getActivity(), calendarList);
            calendarRecycler.setAdapter(fadapter);
            Log.d("calendar ", "set adapter ");

        }


        public void setTheatreList(String date, String city, String movie_id){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                if (Objects.equals(response, "no")){
                                    Toast.makeText(getApplicationContext(),"No record found", Toast.LENGTH_SHORT).show();
                                }else{
                                    JSONArray tList = new JSONArray(response);
                                    for(int i=0; i<tList.length(); i++){
                                        JSONObject theatreListObjects = tList.getJSONObject(i);
                                        String tId = theatreListObjects.getString("theatre_id");
                                        String tName = theatreListObjects.getString("theatre_name");
                                        String language = theatreListObjects.getString("show_language");
                                        String screen = theatreListObjects.getString("screen_type");
                                        String date = theatreListObjects.getString("show_date");
                                        String showid = theatreListObjects.getString("show_id");
                                        String time = theatreListObjects.getString("show_time");

                                        TheatreRecycler tRecycler = new TheatreRecycler(tName, language, screen, showid, time, tId, date);
                                        theatreList.add(tRecycler);
                                    }
                                    Log.d("theatre","arrtheatreData"+ theatreList);

                                    TheatreListRecyclerAdapter fadapter = new TheatreListRecyclerAdapter(getApplicationContext() , theatreList);
                                    theatreRecycler.setAdapter(fadapter);
                                    Log.d("theatre", "Theatre set adapter");
                                }

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("theatre", "theatre LogE " + error.getMessage());

                        }

                    }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("movieid", movie_id);
                    params.put("city", city);
                    params.put("date", date);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
            Log.d("movie", "theatre queued success: ");
        }
}