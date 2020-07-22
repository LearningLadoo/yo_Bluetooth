package com.example.yo_bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.example.yo_bluetooth.MainActivity;

import static com.example.yo_bluetooth.MainActivity.btArray;
import static com.example.yo_bluetooth.MainActivity.deviceNameList;
import static com.example.yo_bluetooth.MainActivity.listArrayAdapter;

public class BlueBroadReceiver  extends BroadcastReceiver {
    int t = 0 ;



    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "inside On Receive", Toast.LENGTH_SHORT).show();
        if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            deviceNameList.add(device.getName());


//            listArrayAdapter.notifyDataSetChanged();
//            btArray[t] = device;
//            t++;

        }
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Toast.makeText(context, "Boot completed", Toast.LENGTH_SHORT).show();
        }
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            Toast.makeText(context, "Connectivity changed", Toast.LENGTH_SHORT).show();
        }
    }
}


