package com.example.hskaopen.connectors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

public class WifiSettingsConnector {

    private static WifiManager wifiManager;
    private static ConnectivityManager connManager;

    public WifiSettingsConnector(WifiManager wifiManager, ConnectivityManager connManager){
        this.wifiManager = wifiManager;
        this.connManager = connManager;
    }


    public  String returnWifiName(){

        NetworkInfo networkInfo = this.connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            WifiInfo wifiInfo = this.wifiManager.getConnectionInfo();
            //wifiInfo.getBSSID();
            String name = networkInfo.getExtraInfo();
            String ssid = "\"" + wifiInfo.getSSID() + "\"";
            return ssid;
        }
        return "ERROR"; //TODO: What to do with errors. The Code taken from https://stackoverflow.com/questions/21391395/get-ssid-when-wifi-is-connected
    }

    public String getMacAddress(){
        String macAdr = "Not Inititalized";
        if ( android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M ){
            WifiInfo wInfo = this.wifiManager.getConnectionInfo();
            macAdr = wInfo.getMacAddress();
        }
        else{
            try{List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface nif : all) {
                    if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        macAdr = "";
                    }
                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        // res1.append(Integer.toHexString(b & 0xFF) + ":");
                        res1.append(String.format("%02X-",b));
                    }

                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    macAdr = res1.toString();
                }
            }
            catch(Exception ex) {
                macAdr = "EXCEPTION"; // TODO : what to do at this exception
            }

        }

        return macAdr;
    };
}
