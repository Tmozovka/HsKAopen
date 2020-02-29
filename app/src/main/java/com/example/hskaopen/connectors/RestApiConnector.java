package com.example.hskaopen.connectors;
import com.example.hskaopen.helpers.SharedPreferencesHelper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hskaopen.MainActivity;
import com.example.hskaopen.ConnectToHskaWlan;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


public class RestApiConnector {

    private static Context appContext;
    private static final String url = "https://www.iwi.hs-karlsruhe.de/iwii/REST/8021x/registrations";
    private static String login;
    private static String password;
    private static TextView textViewInvisible;
    private static SharedPreferencesHelper spHelper;
    private static String macAddress;

    public RestApiConnector(Context appContext, String login, String password, TextView textViewInvisible, String macAddress, SharedPreferences sharedPreferences){
        this.appContext = appContext;
        this.login = login;
        this.password = password;
        this.textViewInvisible = textViewInvisible;
        this.spHelper = new SharedPreferencesHelper(sharedPreferences, macAddress);
        this.macAddress = macAddress;

    }


    public void sendMacAddressOnServerAndChangeBackgroundView(){

        final String macAddress = this.macAddress;
        spHelper.addMacToSharedPreferences();
        getMacAddressesFromServerAndWriteItToTextView();

        textViewInvisible.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) {
                try{
                    String invisible_text = textViewInvisible.getText().toString();
                    JSONArray jsonArray = new JSONArray(invisible_text);
                    if ( jsonArray.toString().contains(macAddress)){
                        sendTextFromTextViewOnServer();
                    }
                    else{
                        jsonArray.put(macAddress);
                        textViewInvisible.setText(jsonArray.toString());
                    }
                }
                catch(Exception ex)
                {
                    Log.d("Exception: ", ex.toString());
                }
            }
        });
    }

    public void getMacAddressesFromServerAndWriteItToTextView(){

        JSONArray currentAddresses;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response: ", response.toString());
                        response = spHelper.deleteSentMacsFromJSONArrayAndFromSP(response);
                        response = checkResponseSize(response);
                        MainActivity.allowToConnect();
                        textViewInvisible.setText(response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response: ", error.toString());
                        Toast.makeText(appContext,
                                "Please check Wifi connections, login data or other app requirements.",
                                Toast.LENGTH_SHORT).show();
                        Log.d("Exception: ", error.toString());
                        textViewInvisible.setText("ERROR");
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String credentials = login + ":" + password;
                String encoded = "Basic "+ Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", encoded);
                return headers;
            }
        };

        addRequestsToQueue(jsonArrayRequest);
    }

    private void addRequestsToQueue(Request... requests){
        RequestQueue requestQueue = Volley.newRequestQueue(appContext);
        for (Request request: requests){
            requestQueue.add(request);
        }

    }

    private void sendTextFromTextViewOnServer(){

        try{
            String text_to_send = textViewInvisible.getText().toString();
            final JSONArray postParams = new JSONArray(text_to_send);
            Log.d("sendTextFromTextView ", "postParams " + postParams);
            JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.POST,
                    url, postParams,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("sendTextFromTextView", "Success"); //TODO what to do with error
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("sendTextFromTextView","ERROR" + error.toString());
                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    String credentials = login + ":" + password;
                    String encoded = "Basic "+ Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Authorization", encoded);
                    return headers;
                }

            };

            addRequestsToQueue(jsonObjReq);
        }
        catch(Exception ex){
            Log.d("JSONPost", "Error: " + ex);
            Log.d("JSONPost ", "Value in textView " + textViewInvisible.getText().toString());
        }


    }

    private JSONArray checkResponseSize(JSONArray response){

        Log.d("checkResponseSize", "Response: " + response.toString());
        Log.d("checkResponseSize", "Size: " + response.length());
        if (response.length()>=3){
            JSONArray ret = new JSONArray();
            try {
                ret.put(response.getString(1));
                ret.put(response.getString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("checkResponseSize", "ret: " + ret.toString());

            ConnectToHskaWlan.setWarningAlertMACdeleted();

            return ret;

        }
        return response;
    }


}





