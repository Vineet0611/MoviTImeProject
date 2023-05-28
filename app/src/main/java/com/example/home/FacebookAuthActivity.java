package com.example.home;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class FacebookAuthActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    String getUsername, getEmail, getId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_facebook_auth);
        performFacebookLogin();
    }

    private void performFacebookLogin() {

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(FacebookAuthActivity.this, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        getUserData();
                    }

                    @Override
                    public void onCancel() {
                        Intent intent = new Intent(getApplicationContext(), activity_login.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        StyleableToast.makeText(getApplicationContext(), "Oops Something Went Wrong Please Try Again!", Toast.LENGTH_LONG, R.style.error_toast).show();
                        Intent intent = new Intent(getApplicationContext(), activity_login.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }
    private void getUserData() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        try {
                            getId = object.getString("id");
                            getUsername = object.getString("name");
                            getEmail = object.getString("email");

                            saveData();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        // Application code
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }
    private void saveData() {
        SharedPreferences shared = getSharedPreferences("MovieTime",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        editor.putString("FG_ID", getId);
        editor.putString("Username", getUsername);
        editor.putString("Email", getEmail);
        editor.putString("Facebook_login", "true");

        editor.apply();
        checkFacebookLogin();
    }

    private void checkFacebookLogin() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://inundated-lenders.000webhostapp.com/Android/select.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "LogIn Response: " + response.toString());
                            JSONObject jsonObject = new JSONObject(response);

                            String status = jsonObject.getString("status");

                            switch (status) {
                                case "success": {

                                    SharedPreferences shared = getSharedPreferences("MovieTime",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = shared.edit();
                                    String phone = jsonObject.getString("Phone");
                                    String userId = jsonObject.getString("UserId");

                                    editor.putString("UserId", userId);
                                    editor.putString("Phone", phone);
                                    editor.apply();

                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    StyleableToast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG, R.style.success_toast).show();
                                    startActivity(intent);
                                    finish();
                                    break;
                                }
                                case "facebook_signup": {
                                    Intent intent = new Intent(getApplicationContext(), activity_email_phone.class);
                                    startActivity(intent);
                                    finish();
                                    break;
                                }
                                case "error": {
                                    String errorMsg = jsonObject.getString("error");
                                    StyleableToast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG, R.style.error_toast).show();
                                    break;
                                }
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();

                paramV.put("check_facebook", "true");
                paramV.put("FG_Id", getId);

                return paramV;
            }
        };
        queue.add(stringRequest);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}