package com.example.home;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class activity_payment extends AppCompatActivity implements PaymentResultListener {

    Button book_now;

    private CountDownTimer timer;
    ImageView movieImage, back_btn;
    TextView paymentTxt, service_fees, total_seats, movie_name, movie_genre, movie_location, movie_dateTime,NoOfTicketsTV,Timer;
    String getUsername, getEmail, getPhone, getUserId, getShowId;
    int total;
    ArrayList<String> seatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        movieImage = findViewById(R.id.movieimage);
        movie_name = findViewById(R.id.moviename);
        movie_genre = findViewById(R.id.moviegenre);
        movie_location = findViewById(R.id.movielocation);
        movie_dateTime = findViewById(R.id.moviedateTime);
        total_seats = findViewById(R.id.seats);
        paymentTxt = findViewById(R.id.PaymentAmount);
        service_fees = findViewById(R.id.servicefees);
        book_now = findViewById(R.id.btnbooknow);
        Timer = findViewById(R.id.timer);
        NoOfTicketsTV = findViewById(R.id.ticket2);
        back_btn = findViewById(R.id.backbtn);

        //These Code is for getting selected seats from seat Page
        // seatList = (ArrayList<String>) getIntent().getSerializableExtra("seatNo");
        Intent intent = getIntent();
        String SeatNumbers = intent.getStringExtra("SeatNumbers");


//        total_seats.setText(SeatNumbers);
        seatList=new ArrayList<>();
        if(SeatNumbers.contains("/")){
            String arrySeatList[]=SeatNumbers.split("/");
            Collections.addAll(seatList,arrySeatList);
        }else{
            seatList.add(SeatNumbers);
        }
        total_seats.setText(String.join(", ", seatList));

        SharedPreferences getSharedData = this.getSharedPreferences("SeatInfo", Context.MODE_PRIVATE);
        String numberOfTickets= getSharedData.getString("noOfTickets", "Data Not Found");
        NoOfTicketsTV.setText(numberOfTickets +" Ticket");
        String myAmount= getSharedData.getString("Amount", "Data Not Found");
//        myAmount=myAmount.replace("₹","");
        paymentTxt.setText(myAmount);
        SharedPreferences getmovieData = this.getSharedPreferences("moviedetails", Context.MODE_PRIVATE);
        String MovieName=getmovieData.getString("moviename","Data Not Found");
        String MovieImage=getmovieData.getString("movieVerticalImage","Data Not Found");
        movie_name.setText(MovieName);
        String theaterName= getSharedData.getString("TheaterName", "Data Not Found");
        movie_location.setText(theaterName);
        String myDate= getSharedData.getString("date", "Data Not Found");
        String ShowTime= getSharedData.getString("ShowTime", "Data Not Found");
        movie_dateTime.setText(myDate+" "+ShowTime);
        getShowId = getSharedData.getString("ShowId", "Data Not Found");
        Picasso.get().load(MovieImage).into(movieImage);
        //------------------------------------------------------
        timer = new CountDownTimer(600000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 60000;
                long seconds = (millisUntilFinished % 60000) / 1000;

                Timer.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                //Write a Code for redirecting the user to Home Page
            }
        };

        timer.start();
        getData();
        calculatePayment();

        book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePayment();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelecteSeat.class); //<-- Replace the Activity Name with Seat Booking activity name
                startActivity(intent);
            }
        });
    }
    private void getData() {
        SharedPreferences getSharedData = getSharedPreferences("MovieTime", MODE_PRIVATE);

        getUserId = getSharedData.getString("UserId", "Data Not Found");
        getUsername = getSharedData.getString("Username", "Data Not Found");
        getEmail = getSharedData.getString("Email", "Data Not Found");
        getPhone = getSharedData.getString("Phone", "Data Not Found");
    }
    private void calculatePayment() {
        int amt = Integer.parseInt(paymentTxt.getText().toString().trim().replace("₹",""));
        total = amt + 18;
        String serviceFees = "₹18.00";
        String totalPayment = "Book Now | ₹" + total + ".00";
        service_fees.setText(serviceFees);
        book_now.setText(totalPayment);
    }
    private void makePayment() {

        Checkout.preload(getApplicationContext());
        Checkout checkout = new Checkout();
//
        try {
            checkout.setKeyID("rzp_test_Fn4uJmdo9qlGIG");
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("name", getUsername);
            orderRequest.put("description", "Payment for MovieTime website");
            orderRequest.put("amount", total * 100);
            orderRequest.put("currency", "INR");

            JSONObject prefill = new JSONObject();
            prefill.put("email", getEmail);
            prefill.put("contact", getPhone);
            orderRequest.put("prefill", prefill);


            checkout.open(activity_payment.this, orderRequest);
        }
        catch (Exception e) {
            StyleableToast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG, R.style.success_toast).show();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        //Below Code is for Getting paymentId from Razorpay
        String paymentId = s;

        //UnComment the below code for for inserting the data in database
        for(String seatNo : seatList) {

            if(seatNo.contains("Silver")) {
                insertBookings(getUserId, getShowId, seatNo.substring(0,2), "Silver");
            }

            else if(seatNo.contains("Gold")) {
                insertBookings(getUserId, getShowId, seatNo.substring(0,2), "Gold");
            }

            else if(seatNo.contains("Platinum")) {
                insertBookings(getUserId, getShowId, seatNo.substring(0,2), "Platinum");
            }
            //pass the showId, seatNo, seatType

        }
        insertPayment(total);
    }

    @Override
    public void onPaymentError(int i, String s) {
        StyleableToast.makeText(getApplicationContext(), "Error Something Went Wrong!", Toast.LENGTH_LONG, R.style.success_toast).show();
    }
    private void insertBookings(String userId, String showId, String seatNo, String seatType) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://inundated-lenders.000webhostapp.com/Android/payment.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "LogIn Response: " + response.toString());
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");


                            if (status.equals("success")) {
                                Log.d(TAG, "Booking Response: " + "Success!");
                            }

                            else if(status.equals("error")) {
                                String errorMsg = jsonObject.getString("error");
                                StyleableToast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG, R.style.error_toast).show();
                                Log.d(TAG, "Booking Response: " + "Error!");
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Booking Response: " + "Error!");
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();

                paramV.put("booking", "true");
                paramV.put("userId", userId);
                paramV.put("showId", showId);
                paramV.put("seatNo", seatNo);
                paramV.put("seatType", seatType);

                return paramV;
            }
        };
        queue.add(stringRequest);
    }

    private void insertPayment(int totalAmt) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://inundated-lenders.000webhostapp.com/Android/payment.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "LogIn Response: " + response.toString());
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");


                            if (status.equals("success")) {
                                Log.d(TAG, "Payment Response: " + "Success!");
                                StyleableToast.makeText(getApplicationContext(), "Payment Successfull", Toast.LENGTH_LONG, R.style.error_toast).show();

                                Intent intent = new Intent(activity_payment.this, PrintTicket.class); //<-- Replace activity_home Home with Print Ticket Activity
                                startActivity(intent);
                                finish();
                            }

                            else if(status.equals("error")) {
                                String errorMsg = jsonObject.getString("error");
                                StyleableToast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG, R.style.error_toast).show();
                                Log.d(TAG, "Payment Response: " + "Error!");
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Payment Response: " + "Error!");
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();

                paramV.put("payment", "true");
                paramV.put("total", String.valueOf(totalAmt));

                return paramV;
            }
        };
        queue.add(stringRequest);
    }
}