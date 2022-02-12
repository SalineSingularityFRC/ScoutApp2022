package team.singularity.scoutapp2022;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.util.Log;

import bluetooth.Bluetooth;
import bluetooth.CommunicationCallback;

public class BluetoothClass {
    private final Bluetooth BLUETOOTH;
    public MainActivity activity;
    private boolean setup = false;
    private final Handler HANDLER = new Handler();
    private static final String TAG = "7G7 Bluetooth";
    private String pendingData="";
    private final CommunicationCallback CCB;

    public BluetoothClass(MainActivity a) {
        activity=a;
        BLUETOOTH = new Bluetooth(activity);
        CCB = new CommunicationCallback() {
            @Override
            public void onConnect(BluetoothDevice device) {
                Log.i(TAG, "Connected to device " + device.getName() + " at " + device.getAddress() + "!");
                BLUETOOTH.send(pendingData);
            }

            @Override
            public void onDisconnect(BluetoothDevice device, String message) {
                Log.i(TAG, "Disconnected from device " + device.getName() + " at " + device.getAddress() + "!");
                if (pendingData.length() != 0) {
                    reconnect();
                }
            }

            @Override
            public void onMessage(String message) {
                pendingData = "";
                DatabaseClass.dataSent(message);
                Log.i(TAG, "Data transfer complete!");
            }

            @Override
            public void onError(String message) {
                Log.e(TAG, "Generic error: " + message);
            }

            @Override
            public void onConnectError(BluetoothDevice device, String message) {
                Log.e(TAG, "Caught connection error: " + message);
                reconnect();
            }
        };
        BLUETOOTH.setCommunicationCallback(CCB);
    }

    public void setup(){
        if(!setup) {
            BLUETOOTH.onStart();
        }
        if(!BLUETOOTH.isEnabled()){
            BLUETOOTH.enable();
        }
        setup=true;
    }

    private void reconnect(){
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                BLUETOOTH.connectToAddress(Util.PI_MAC_ADDRESS);
            }
        },3000);
    }

    public void send(String data){
        Log.i(TAG,"Entered the send method in bluetooth");
        if(pendingData.length()==0)
            BLUETOOTH.connectToAddress(Util.PI_MAC_ADDRESS);
        //pendingData+=data;
        pendingData=data;

    }

    public void send_byte(byte[] data) {
        if (pendingData.length() == 0)
            BLUETOOTH.connectToAddress(Util.PI_MAC_ADDRESS);

        pendingData += new String(data);
        Log.i(TAG, "Disconnecting");
        BLUETOOTH.disconnect();
    }

    /// Set the pending data without actually sending to the pi
    public void set_pending_data(String data) {
        if (this.pendingData.length() == 0) BLUETOOTH.connectToAddress(Util.PI_MAC_ADDRESS);
        this.pendingData = data;
    }

    public void end(){
        BLUETOOTH.onStop();
    }

}
