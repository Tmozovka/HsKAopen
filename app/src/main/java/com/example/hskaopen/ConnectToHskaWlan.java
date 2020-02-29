package com.example.hskaopen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;


public class ConnectToHskaWlan extends AppCompatActivity {


    private static boolean warningMACdeleted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_hska_wlan);
        if(warningMACdeleted){
            showWarning("You allow to register just 3 devices in HsKAopen. This devise will be registered and the first saved devise will be deleted.");
        }
    }

    public void showWarning(String message){
        warningMACdeleted = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void setWarningAlertMACdeleted(){
        Log.d("showWarningAlertMACde: ", "call showWarningAlertMACdeleted");
        warningMACdeleted = true;
    }


}