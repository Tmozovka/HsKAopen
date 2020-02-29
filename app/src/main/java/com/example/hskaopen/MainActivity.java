package com.example.hskaopen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hskaopen.connectors.RestApiConnector;
import com.example.hskaopen.connectors.WifiSettingsConnector;
import com.example.hskaopen.warning_requirements.WarningRequirements;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private WifiManager wifiManager;
    private ConnectivityManager connManager;
    private WifiSettingsConnector wifiSettingsConnector;
    private RestApiConnector restApiConnector;
    private static boolean readyToConnect = false;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        connManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiSettingsConnector = new WifiSettingsConnector(wifiManager, connManager );
        sharedPreferences = getSharedPreferences("HsKAopenMACs", MODE_PRIVATE);

        setOnClickListenersForRequirements();

        final Button btn = (Button) findViewById(R.id.buttonConnect);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setButtonEnable(btn, 2500);

                String macAddress = wifiSettingsConnector.getMacAddress();
                //String macAddress = "8bm";
                String login = ((EditText)findViewById(R.id.login)).getText().toString();
                String password = ((EditText)findViewById(R.id.password)).getText().toString();

                if (login != "" && password != "" && loginCorrect(login)){
                    final TextView backgroundView = (TextView)findViewById(R.id.test);
                    restApiConnector = new RestApiConnector(getApplicationContext(), login, password, backgroundView, macAddress, sharedPreferences);
                    restApiConnector.sendMacAddressOnServerAndChangeBackgroundView();
                    backgroundView.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                        @Override
                        public void afterTextChanged(Editable s) {
                            if (readyToConnect){
                                openSecondActivity(ConnectToHskaWlan.class);
                                readyToConnect = false;
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Please give your correct university login(Example: abcd1234) and your password.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });



    }



    private void setOnClickListenersForRequirements(){
        //set requirements warning
        View.OnClickListener click_listener_warning = new View.OnClickListener(){
            public void onClick(View v)
            { openSecondActivity(WarningRequirements.class); };
        };
        //Make warning button visible and workable
        ImageButton version_warning = (ImageButton)findViewById(R.id.buttonWarningRequirements);
        version_warning.setOnClickListener(click_listener_warning);
        //Make warning text visible
        TextView warning_text = (TextView)findViewById(R.id.warningRequirements);
        warning_text.setOnClickListener(click_listener_warning);
    }

    public void openSecondActivity(Class<?> second_activity_class){
        Intent intent = new Intent(this, second_activity_class);
        startActivity(intent);
    }

    private void setButtonEnable(final Button btn, int millis){

        btn.setEnabled(false);
        btn.postDelayed(new Runnable() {
            @Override
            public void run() {
                btn.setEnabled(true);
            }
        }, millis);
    }

    public boolean loginCorrect(String login){
        return Pattern.matches("[a-z]{4}[0-9]{4}", login.toLowerCase());
    }

    public static void allowToConnect(){
        Log.d("allowToConnect: ", "call allowToConnect");
        readyToConnect = true;
    }



}



