package com.example.sat4997;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Set;

public class DisplayBondedDevicesActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.sat4997.MESSAGE";

    TextView textview;
    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter btAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bonded_devices);

        textview = (TextView) findViewById(R.id.textView);

        // Getting the Bluetooth adapter
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        textview.append("\nAdapter: " + btAdapter);

        CheckBluetoothState();
    }

    /** Called when an activity completes. */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            CheckBluetoothState();
        }
    }

    private void CheckBluetoothState() {
        // Checks for the Bluetooth support and then makes sure it is turned on
        // If it isn't turned on, request to turn it on
        // List paired devices
        if (btAdapter == null) {
            textview.append("\nBluetooth NOT supported. Aborting.");
            return;
        } else {
            if (btAdapter.isEnabled()) {
                textview.append("\nBluetooth is enabled...");

                // Listing paired devices
                textview.append("\nPaired Devices are:");
                Set<BluetoothDevice> devices = btAdapter.getBondedDevices();
                for (BluetoothDevice device : devices) {
                    textview.append("\n  Device: " + device.getName() + ", " + device);
                }
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_display_bonded_devices, menu);
        return true;
    }
}