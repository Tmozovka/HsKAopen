package com.example.hskaopen.helpers;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class SharedPreferencesHelper {
    private SharedPreferences sharedPreferences;
    private static String sentMACsKey = "sentMACs";
    private String macAddress;

    public SharedPreferencesHelper(SharedPreferences sharedPreferences, String macAdr){
        this.macAddress = macAdr;
        this.sharedPreferences = sharedPreferences;
    }

    public SharedPreferencesHelper(SharedPreferences sharedPreferences){
        this.macAddress = "";
        this.sharedPreferences = sharedPreferences;
    }

    public JSONArray deleteSentMacsFromJSONArrayAndFromSP(JSONArray response) {
        Set<String> allSentMacs = sharedPreferences.getStringSet(sentMACsKey, Collections.<String>emptySet());
        Log.d("deleteSentMacsFromJSONA","allSentMacs before" + allSentMacs);

        Set<String> newSentMacs = new HashSet<>(allSentMacs);

        Log.d("deleteSentMacsFromJSONA","response before" + response.toString());

        for (String mac : allSentMacs) {
            for (int i = 0; i < response.length(); i++) {
                try {
                    String strComp = response.getString(i);
                    if (strComp.equals(mac)) {
                        response.remove(i);
                        if (!this.macAddress.equals(mac)){
                            newSentMacs.remove(mac);
                        }

                    }
                } catch (JSONException e) {
                    Log.d("deleteSentMacsFromJSONA", "Error: " + e);
                }
            }
        }

        Log.d("deleteSentMacsFromJSONA","response after" + response.toString());
        updateSetStringInSP(newSentMacs);

        Set<String> newMacs = sharedPreferences.getStringSet(sentMACsKey, Collections.<String>emptySet());
        Log.d("deleteSentMacsFromJSONA","allSentMacs after" + newMacs);

        return response;
    }

    public void addMacToSharedPreferences(){
        Set<String> allSentMacs = sharedPreferences.getStringSet(sentMACsKey, new HashSet<String>());
        allSentMacs.add(this.macAddress);
        updateSetStringInSP(allSentMacs);
    }

    private void updateSetStringInSP(Set<String> newSet){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(sentMACsKey);
        edit.apply();
        edit.putStringSet(sentMACsKey, newSet);
        edit.apply();
    }
}
