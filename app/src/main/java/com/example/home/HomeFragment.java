package com.example.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment {

    ProgressBar progressBar;
    RelativeLayout rl;
    private static final String url1 = "https://inundated-lenders.000webhostapp.com/api/recycler1.php";
    private static final String url2 = "https://inundated-lenders.000webhostapp.com/api/recycler2.php";
    private static final String url3 = "https://inundated-lenders.000webhostapp.com/api/recycler3.php";
    private static final String url4 = "https://inundated-lenders.000webhostapp.com/api/recycler4.php";

    ImageSlider imageSlider;
    RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    ArrayList<FirstRecycler> arrFirst =new ArrayList<>();
    ArrayList<SecondRecycler> arrSecond =new ArrayList<>();
    ArrayList<ThirdRecycler> arrThird =new ArrayList<>();
    ArrayList<FourthRecycler> arrFourth =new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider = (ImageSlider)view.findViewById(R.id.slider);
        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
        rl = (RelativeLayout)view.findViewById(R.id.progress_bar_layout);


        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.image1 , "", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.image2, "", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.image3, "",  ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_CROP);


        recyclerView1 = (RecyclerView)view.findViewById(R.id.first_recycler);
        recyclerView1.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView2 = (RecyclerView)view.findViewById(R.id.second_recycler);
        recyclerView2.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView3 = (RecyclerView)view.findViewById(R.id.third_recycler);
        recyclerView3.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView4 = (RecyclerView)view.findViewById(R.id.fourth_recycler);
        recyclerView4.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));


        return view;


//        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//            arrFirst.add(new FirstRecycler(R.drawable.temp_poster, "Avatar: The Way of Water", "Action, Drama, Sci-fi"));
//            arrFirst.add(new FirstRecycler(R.drawable.temp_poster2, "Avatar: The Way of Water", "Action, Drama, Sci-fi"));
//            arrFirst.add(new FirstRecycler(R.drawable.temp_poster3, "Avatar: The Way of Water", "Action, Drama, Sci-fi"));
//            arrFirst.add(new FirstRecycler(R.drawable.temp_poster4, "Avatar: The Way of Water", "Action, Drama, Sci-fi"));
        if(isNetworkConnected()){
            loadFirstRec();
            loadSecondRec();
            loadThirdRec();
            loadFourthRec();

        }else{
            Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_LONG).show();
        }


    }
    private void loadFirstRec(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray list1 = new JSONArray(response);
                            for(int i=0; i<list1.length(); i++){
                                JSONObject list1Objects = list1.getJSONObject(i);
                                String mName = list1Objects.getString("movie_name");
                                String mGenre = list1Objects.getString("movie_genre");
                                String mImg = list1Objects.getString("image");

                                FirstRecycler firstRecycler = new FirstRecycler(mImg, mName, mGenre);
                                arrFirst.add(firstRecycler);
                            }
                                Log.d("volley","arrfirstData"+ arrFirst.toString());

                                FirstRecyclerAdapter fadapter = new FirstRecyclerAdapter(getActivity(), arrFirst);
                                recyclerView1.setAdapter(fadapter);
                                Log.d("volley ", "set adapter ");

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("volley", "LogE " + error.getMessage());

                    }

                });

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                Volley.newRequestQueue(requireActivity()).add(stringRequest);
                Log.d("volley ", "first queued success: ");

    }
    private void  loadSecondRec(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray list2 = new JSONArray(response);
                            for(int i=0; i< list2.length(); i++){
                                JSONObject list2Objects = list2.getJSONObject(i);
                                String mName = list2Objects.getString("movie_name");
                                String mGenre = list2Objects.getString("movie_genre");
                                String mImg = list2Objects.getString("image");

                                SecondRecycler secondRecycler = new SecondRecycler(mImg, mName, mGenre);
                                arrSecond.add(secondRecycler);
                            }
                            Log.d("volley","arrSecondData"+ arrSecond.toString());

                            SecondRecyclerAdapter fadapter = new SecondRecyclerAdapter(getActivity(), arrSecond);
                            recyclerView2.setAdapter(fadapter);
                            Log.d("volley", "second set adapter");

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("volley", "second LogE " + error.getMessage());

                    }

                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(requireActivity()).add(stringRequest);
        Log.d("volley ", " Second queued success: ");

    }
    private void loadThirdRec(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray list3 = new JSONArray(response);
                            for(int i=0; i<list3.length(); i++){
                                JSONObject list3Objects = list3.getJSONObject(i);
                                String mName = list3Objects.getString("movie_name");
                                String mGenre = list3Objects.getString("movie_genre");
                                String mImg = list3Objects.getString("image");

                                ThirdRecycler thirdRecycler = new ThirdRecycler(mImg, mName, mGenre);
                                arrThird.add(thirdRecycler);
                            }
                            Log.d("volley","arrThirdData"+ arrThird.toString());

                            ThirdRecyclerAdapter fadapter = new ThirdRecyclerAdapter(getActivity(), arrThird);
                            recyclerView3.setAdapter(fadapter);
                            Log.d("volley ", "third set adapter ");

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("volley", "Third LogE " + error.getMessage());

                    }

                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(requireActivity()).add(stringRequest);
        Log.d("volley ", "Third queued success: ");
    }
    private void loadFourthRec(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url4,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray list4 = new JSONArray(response);
                            for(int i=0; i<list4.length(); i++){
                                JSONObject list4Objects = list4.getJSONObject(i);
                                String mName = list4Objects.getString("movie_name");
                                String mGenre = list4Objects.getString("movie_genre");
                                String mImg = list4Objects.getString("image");

                                FourthRecycler fourthRecycler = new FourthRecycler(mImg, mName, mGenre);
                                arrFourth.add(fourthRecycler);
                            }
                            Log.d("volley","arrFourthData"+ arrFourth.toString());

                            FourthRecyclerAdapter fadapter = new FourthRecyclerAdapter(getActivity(), arrFourth);
                            recyclerView4.setAdapter(fadapter);
                            Log.d("volley ", "Fourth set adapter ");
                            progressBar.setVisibility(View.GONE);
                            rl.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("volley", "Fourth LogE " + error.getMessage());

                    }

                });

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(requireActivity()).add(stringRequest);
        Log.d("volley ", "fourth queued success: ");
    }


  @SuppressLint("MissingPermission")
  private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}

