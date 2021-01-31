package com.example.bobilerandroidclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String REQUEST_HOST = "request.host";
    private static final String REQUEST_PORT = "request.port";

    private ImageView imageView;
    private String post_img;
    private String request_host;
    private String request_port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue queue = Volley.newRequestQueue(this);
        imageView = findViewById(R.id.imageView);

        try {
            request_host = Util.getProperty(REQUEST_HOST, this);
            request_port = Util.getProperty(REQUEST_PORT, this);

            System.out.println("Retrieved config parameters! Host: " + request_host + " and port: " + request_port);
        } catch (IOException e) {
            System.out.println("Error occurred while retrieving the properties!");
            e.printStackTrace();
        }

        String url ="http://" + request_host + ":" + request_port + "/1";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                postResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error occurred while sending request!");
                System.out.println(error);
            }
        });

        queue.add(stringRequest);

        Glide.with(this).load(post_img).into(imageView);
    }

    private void postResponse(String response) {
        try {
            JSONArray respArray = new JSONArray(response);
            Random rand = new Random();
            int randomImageIndex = rand.nextInt(10);

            System.out.println("image index: " + randomImageIndex);

            JSONObject jsonObject = (JSONObject) respArray.get(randomImageIndex);

            System.out.println(jsonObject);

            post_img = (String) jsonObject.get("post_img");

            System.out.println("post_img1: " + post_img);
        } catch (JSONException e) {
            System.out.println("Error happened while parsing JSON!!!");
        }

        Glide.with(this).load(post_img).into(imageView);
    }
}