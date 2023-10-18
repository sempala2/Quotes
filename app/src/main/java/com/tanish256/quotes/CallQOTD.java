package com.tanish256.quotes;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class CallQOTD  {
    String Url;
    //String url = "https://favqs.com/api/qotd";

    RequestQueue Queue;
    String Response;


    public  CallQOTD(String url , RequestQueue queue,String response) {
        this.Response = response;
        this.Queue=queue;
        this.Url = url;


    }




    // Request a string response from the provided URL.
    public StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Response=response;
                    // Display the first 500 characters of the response string.
                    //Toast.makeText(Context, response, Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //Toast.makeText(Queue, error.getMessage(), Toast.LENGTH_LONG).show();
        }
    });
    public String getResponse(){
        return Response;
    }


// Add the request to the RequestQueue.




}
