package com.example.hskaopen.warning_requirements;

import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hskaopen.R;
import com.example.hskaopen.warning_requirements.Group;
import com.example.hskaopen.warning_requirements.MyExpandableListAdapter;


public class WarningRequirements extends AppCompatActivity {

    // more efficient than HashMap for mapping integers to objects
    SparseArray<Group> groups = new SparseArray<Group>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning_requirements);
        createData();
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
                groups);
        listView.setAdapter(adapter);
    }

    public void createData() {
        int i = 0;

        Group group = new Group("Establish WIfi Connection");
        group.children.add("\nYou need Wifi connection to connect to HsKAopen.\n");

        groups.append(i++, group);

        if ( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O )
        {   group = new Group("Disable MAC Randomization");
            group.children.add(
                    "\nHow to disable MAC randomization?\n\n" +
                            "1.\tOpen the Settings app.\n" +
                            "2.\tTap Network & Internet.\n" +
                            "3.\tTap Wi-Fi.\n" +
                            "4.\tTap the gear icon associated with the wireless connection to be configured.\n" +
                            "5.\tTap Advanced.\n" +
                            "6.\tTap Privacy.\n" +
                            "7.\tTap Use Device MAC.\n\n");
            group.children.add(
                            "\nWhat is it MAC address?\n\n" +
                            "A media access control address (MAC address) is a unique identifier of your device assigned to a network interface controller (NIC). The NIC is essentially a computer circuit card that makes it possible for your phone to connect to a network.\n" +
                            "The MAC address can be used to track a device on Wi-Fi networks. If someone discovers the MAC address associated with your mobile device, he or she can easily spy on you. Say, you move around from the wireless network to wireless network. Each time your MAC address is saved in the network, it can be tracked, thereby giving away your movement throughout a city. That is not just a privacy issue but a data security problem.\n\n"
                            );
            group.children.add(
                    "\nWhat is it MAC randomization?\n\n" +
                            "MAC randomization prevents hackers from using MAC addresses to build a history of device activity, thus increasing user privacy.\n" +
                            "Starting in Android 8.0, Android devices use randomized MAC addresses when probing for new networks while not currently associated with a network. In Android 9, you can enable a developer option (it's disabled by default) to cause the device to use a randomized MAC address when connecting to a Wi-Fi network.\n" +
                            "In Android 10, MAC randomization is enabled by default for client mode.\n\n" );
            group.children.add(
                            "\nWhy HSKA-Open doesn’t work without MAC randomization?\n\n" +
                            "When you connect to HsKA-8021x, you are allowed just to use online services of the Karlsruhe University of Applied Sciences. Your identity must be confirmed to allow you to use other resources of Word Wide Web (for example Google Search, WhatsApp, etc.). MAC address of your device must be saved on the University Server for this propose. \n" +
                            "When your MAC address is saved on the server and you are correctly logged in to HsKA-8021x, you are consequently connected to HSKA-Open and can visit all Websites. But if MAC randomization is enabled on your device, your identity can not be confirmed. (Your MAC address is changed every time, when you connect to HsKA-8021x and MAC Randomisation is enabled.) \n"
            );
            groups.append(i++, group);


        }

        group = new Group("Enter correct login and password and click on \"CONNECT\"");
        group.children.add("\nEnter you university registration data. Login example: abcd1234.\n");

        groups.append(i++, group);

        group = new Group("Change your login information in HsKA-8021x Wi-fi");
        group.children.add("\nPlease register in HsKA-8021x with username: " +
                "\nUsername@<faculty>-wlan.hs-karlsruhe.de\nand yor university password \n\n" +
                "Username examples:\nabcd1234@iwi-wlan.hs-karlsruhe.de\nabcd1234@eit-wlan.hs-karlsruhe.de\n\n " +
                "Possible faculty shorthands:\n iwi, ab, eit, imm, mmt, w\n\n"  );
        groups.append(i++, group);

        group = new Group("Fix error issues with HsKA-8021x connection ");
        group.children.add("\nIf you can not connect to HsKA-8021x Wi-Fi, it could be because of security certificate missing.\n "  +
                "For this you absolutely need the \"T-Telesec Global Root Class 2\" certificate: \n" +
                "1) Download the security certificate via:\n" +
                "https://www.pki.dfn.de/fileadmin/PKI/zertifikate/DFN-Verein_Certification_Authority_2.crt\n"  +
                "2) Open downloaded file \n" +
                "3) In the window for the certificate setup, select \"Wi-Fi\"(WLAN) under \"Credential use\"(Verwendung der Anmeldedaten)\n" +
                "4)At the HsKA-8021x Settings for connection give following information:\n" +
                "   EAP method: PEAP\n" +
                "   Phase-2 authentication: MS-CHAP v2\n" +
                "   CA certificate: Hska(name under which you installed the certificate)\n" +
                "   Domain: hs-karlsruhe.de\n" +
                "   Identity: abcd1234@hs-karlsruhe.de(your university E-mail)\n" +
                "   Anonymous identity: anonymous@hs-karlsruhe.de\n" +
                "   Password: ********* (your university password)\n\n");
        groups.append(i++, group);

    }
}