package com.example.home;

import static android.content.ContentValues.TAG;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class activity_payment extends AppCompatActivity implements PaymentResultListener {

    Button book_now;

    private CountDownTimer timer;
    ImageView movieImage, back_btn;
    TextView payment_txt, service_fees, total_seats, movie_name, movie_genre, movie_location, movie_dateTime, Timer;
    String getUsername, getEmail, getPhone, getUserId;
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
        payment_txt = findViewById(R.id.paymentamt);
        service_fees = findViewById(R.id.servicefees);
        book_now = findViewById(R.id.btnbooknow);
        Timer = findViewById(R.id.timer);
        back_btn = findViewById(R.id.backbtn);

        //These Code is for getting selected seats from seat Page
        seatList = (ArrayList<String>) getIntent().getSerializableExtra("seatNo");
        total_seats.setText(String.join(", ", seatList));
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
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class); //<-- Replace the Activity Name with Seat Booking activity name
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
        int amt = Integer.parseInt(payment_txt.getText().toString().trim());
        total = amt + 18;
        String serviceFees = "INR 18.00";
        String totalPayment = "Book Now | INR " + total + ".00";
        String PaymentTxt = "INR " + total + ".00";
        service_fees.setText(serviceFees);
        payment_txt.setText(PaymentTxt);
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
//        for(String seatNo : seatList) {
//
//            //pass the showId, seatNo, seatType
//            insertBookings(getUserId, "17", seatNo, "Gold");
//        }
//        insertPayment(total);
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
                                StyleableToast.makeText(getApplicationContext(), "Payment SuccessFull", Toast.LENGTH_LONG, R.style.error_toast).show();

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