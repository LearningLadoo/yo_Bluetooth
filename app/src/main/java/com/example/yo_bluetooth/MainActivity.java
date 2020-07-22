package com.example.yo_bluetooth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.JobIntentService;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Button masterButton, listen, yoo;
    ListView blueDevicesListView;
    BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    static ArrayList<String> deviceNameList = new ArrayList<>();
    static ArrayAdapter<String> listArrayAdapter;
    static BluetoothDevice[] btArray;
    SendRecieve sendRecieve;
    BluetoothSocket mainSocket;
    TextView messageBox;
    BluetoothDevice theMainDevice;


    private final int LOCATION_PERMISSION_REQUEST = 101;
    int MESSAGE_RECEIVED = 10;
//    String MESSAGE_TO_SEND = "hey samsung brooo";
//
    String RECEIVER_IDENTITY = "@";
    String MESSAGE_TO_SEND = "hey vivoda";

    private static final String APP_NAME = "yo_Bluetooth";
    private static final UUID MY_UUID = UUID.fromString("d89e892c-c0c1-11ea-b3de-0242ac130004");


//    Intent btEnableIntent;
//    int requestCodeForEnabling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        masterButton = findViewById(R.id.MasterButton);
        blueDevicesListView = findViewById(R.id.BlueDevices);
        listen = findViewById(R.id.listen);
        yoo = findViewById(R.id.send);
        messageBox = findViewById(R.id.messageView);


        Toast.makeText(getApplicationContext(), "ready boss yesss ", Toast.LENGTH_LONG).show();
        Log.d("nnnnnn", "onCreate: the device name is "+ btAdapter.getName());
        btAdapter.setName(btAdapter.getName()+"@");
        Log.d("nnnnnn", "onCreate: the changed device name is "+ btAdapter.getName());


//        btEnableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

//        requestCodeForEnabling = 1;
        yoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
DiscoverDevices();
            }
        });
        listen.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          btAdapter.disable();
                                          RemoveIfAlreadyPaired removeIfAlreadyPaired = new RemoveIfAlreadyPaired();
                                          removeIfAlreadyPaired.start();
                                          DiscoverDevices();
                                          BlueDiscover();
                                      }
                                  }
        );
        masterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public synchronized void onClick(View view) {
//                ShowPairedDevices();


                ServerClass serverClass = new ServerClass();

                serverClass.start();

                handler.obtainMessage(0).sendToTarget();

                ClientClass clientClass = new ClientClass(theMainDevice);
                clientClass.start();

/*
                for(BluetoothDevice mainDevice : btArray){
                    if(mainDevice.getName().equals(RECEIVER_NAME)){

                        handler.obtainMessage(0 ).sendToTarget();
                        ClientClass clientClass = new ClientClass(mainDevice);
                        clientClass.start();
                        break;
                    }
                }
*/


            }
        });
        blueDevicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });
    }

/*

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == requestCodeForEnabling) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "yo bluetooth is on !!", Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "sry bt is canceled !!", Toast.LENGTH_LONG).show();
            }
        }
    }
*/

    // by default if the bluetooth is off it will automatically turn on with discoverable
/*
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {



                new AlertDialog.Builder(getApplicationContext())
                        .setCancelable(false)
                        .setMessage("Location permission is required.\n Please grant")
                        .setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                checkPermissions();
                            }
                        })
                        .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.this.finish();
                            }
                        }).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
*/


  void ChangeName(){


  }
    // TODO disable the dialog please
    void BlueDiscover() {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 60);

        startActivity(intent);

    }

    synchronized void Master() {
        BlueDiscover();
        DiscoverDevices();
        while (flag == 0) {
            Log.d("nnnnnnnn", "i'm stuck in this bro !!");
            if (flag == 1) {
                break;

            }
        }
        ServerClass serverClass = new ServerClass();

        serverClass.start();

        handler.obtainMessage(0).sendToTarget();

        ClientClass clientClass = new ClientClass(theMainDevice);
        clientClass.start();
        handler.obtainMessage(2).sendToTarget();


    }

    class CheckTheDevice extends Thread {

        @Override
        public void run() {
            while (theMainDevice == null) {

                Log.d("nnnnnnnnnnnnnnnnnn", "run: yahooo  " + theMainDevice + "   " + flag);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (flag == 1) {
                    break;
                }
            }
        }
    }

    void BlueOnOff() {
        if (btAdapter == null) {
            Toast.makeText(getApplicationContext(), "Some problem with bluetooth", Toast.LENGTH_LONG).show();

        } else {
            if (!btAdapter.isEnabled()) {
                btAdapter.enable();
                //    startActivityForResult(btEnableIntent, requestCodeForEnabling);
            } else {
                btAdapter.disable();

            }
        }

    }

    void ShowPairedDevices() {
        Set<BluetoothDevice> bd = btAdapter.getBondedDevices();
        String[] blueArray = new String[bd.size()];
        btArray = new BluetoothDevice[bd.size()];
        int count = 0;
        if (bd.size() > 0) {
            for (BluetoothDevice i : bd) {
                btArray[count] = i;
                blueArray[count] = i.getName();
                count++;
            }
            ArrayAdapter<String> blueArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, blueArray);
            blueDevicesListView.setAdapter(blueArrayAdapter);
        }

    }

    int flag = 0;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e("nnnnnnnn", "inside onReceive");
            if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

//   Parcelable[] uuidExtra = intent.getParcelableArrayExtra(device.EXTRA_UUID);
//
//  StringBuilder sb = new StringBuilder();



                if (device.getBondState() != BluetoothDevice.BOND_BONDED && device.getName() != null) {
                    deviceNameList.add(device.getName());
                    listArrayAdapter.notifyDataSetChanged();

                    Log.e("nnnnnnnn", "found a device !! " + device.getName());
//                    btArray[t] = device;
//                    t++;
                    int index = device.getName().length() - 1;
                    if (device.getName().substring(index).equals(RECEIVER_IDENTITY)) {
                        theMainDevice = device;
                        flag = 1;
                        Log.d("nnnnnnnn", "found the main device with @ !! " );
//                        if(uuidExtra!=null){
//                            if(uuidExtra.length>0) {
//                                List<String> uuids = new ArrayList<String>(uuidExtra.length);
//
//                                for (Parcelable parcelable : uuidExtra) {
//                                    sb.append(parcelable.toString()).append(',');
//                                    uuids.add(parcelable.toString());
//                                }
//                                Log.e("nnnnnn", "ACTION_UUID received for " + device + " uuids: " + sb.toString());
//                            }}else{
//                            Log.e("nnnnnn", "null hai bhai uuids of " + device );
//
//                        }

                    }
                }
            }

        }
    };


    void DiscoverDevices() {
        btAdapter.startDiscovery();
        // remove bond if already paired with that device


        Log.d("nnnnnnnn", "DiscDevice 1 " + btAdapter.isEnabled());
        handler.obtainMessage(1).sendToTarget();

        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        Log.d("nnnnnnnn", "DiscDevice 2 " + btAdapter.isDiscovering());


//        BlueBroadReceiver broadcastReceiver =  new BlueBroadReceiver();

        registerReceiver(broadcastReceiver, intentFilter);
        Log.d("nnnnnnnn", "DiscDevice 3 " + btAdapter.isDiscovering());

        listArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNameList);
        blueDevicesListView.setAdapter(listArrayAdapter);

    } // Solved: "this didnt work" => the problem is with permission

    private class ServerClass extends Thread {

        private BluetoothServerSocket serverSocket;

        public ServerClass() {

            try {
                serverSocket = btAdapter.listenUsingInsecureRfcommWithServiceRecord(APP_NAME, MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void run() {

            BluetoothSocket socket = null;
            while (socket == null) {

                try {

                    socket = serverSocket.accept();
                    mainSocket = socket;

                } catch (IOException e) {

                    e.printStackTrace();
                }
                if (socket != null) {
                    mainSocket = socket;
                    sendRecieve = new SendRecieve(socket);
                    new Thread(sendRecieve).start();

                    break;

                }
            }


        }
    }

    private class ClientClass extends Thread {
        private BluetoothSocket socket;
        private BluetoothDevice device;

        //  public ClientClass(BluetoothDevice device1) {
        public ClientClass(BluetoothDevice bd) {
            Log.d("nnnnnnnn", "called the client and the flag is " + flag);

            this.device = bd;

            try {
                socket = device.createRfcommSocketToServiceRecord(MY_UUID);

                Log.d("nnnnnnnn", "socket made in the client");

            } catch (IOException e) {
                e.printStackTrace();

                Log.d("nnnnnnnn", "nahi bana socket the client");

            }

        }

        public void run() {
            btAdapter.cancelDiscovery();//TODO check this shit
            try {
                if (!socket.isConnected()) {
                    socket.connect();
                }
                sendRecieve = new SendRecieve(socket);
                new Thread(sendRecieve).start();
                //TODO its added to yoo a pre message
                sendRecieve.write(MESSAGE_TO_SEND.getBytes());
                Log.d("nnnnnnnn", "the message is sent to the bro !!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class SendRecieve implements Runnable {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        private SendRecieve(BluetoothSocket socket) {
            this.bluetoothSocket = socket;
            InputStream iStream = null;
            OutputStream oStream = null;

            try {
                iStream = bluetoothSocket.getInputStream();
                oStream = bluetoothSocket.getOutputStream();

            } catch (IOException e) {
                e.printStackTrace();

            }

            inputStream = iStream;
            outputStream = oStream;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;


            while (true) {
                try {
                    bytes = inputStream.read(buffer);
                    handler.obtainMessage(MESSAGE_RECEIVED, bytes, -1, buffer).sendToTarget();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        public void write(byte[] byteString) {
            try {
                outputStream.write(byteString);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (message.what == MESSAGE_RECEIVED) {
                byte[] readBuffer = (byte[]) message.obj;
                String TempMsg = new String(readBuffer, 0, message.arg1);
                messageBox.setText(TempMsg);

            } else if (message.what == 0) {
                messageBox.setText("server found");

            } else if (message.what == 1) {
                messageBox.setText("dicovery started");

            } else if (message.what == 2) {
                messageBox.setText("saw  " + RECEIVER_IDENTITY);

            } else if (message.what == 3) {
                messageBox.setText("could not reach on received");

            }

            return true;


        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private void unpairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass()
                    .getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);

            Log.e("nnnnnn"  ,"removed "+RECEIVER_IDENTITY);
        } catch (Exception e) {
            Log.e("nnnnnn", e.getMessage());
        }
    }

class RemoveIfAlreadyPaired extends Thread{
        private RemoveIfAlreadyPaired(){
            try{
                Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();

                Log.d("nnnnnnnn", "size of paired device " + pairedDevices.size());
                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice device : pairedDevices) {

                        if (device.getName() != null) {
                            if (device.getName().equals(RECEIVER_IDENTITY)) {

                                unpairDevice(device);

                            }
                        }
                    }
                }

            }catch (Exception e){
                Log.e("nnnnn", "removeIfAlreadyPaired: "+e.getMessage() );

            }
        }}

}
