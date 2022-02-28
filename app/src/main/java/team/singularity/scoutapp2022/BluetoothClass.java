package team.singularity.scoutapp2022;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.util.Log;

import bluetooth.Bluetooth;
import bluetooth.CommunicationCallback;

//the thing that makes bluetooth work (or not given the situation)

public class BluetoothClass {
    //declare variables
    private final Bluetooth BLUETOOTH;
    public MainActivity activity;
    private boolean setup = false;
    private final Handler HANDLER = new Handler();
    private static final String TAG = "Bluetooth Class";
    private String pendingData="";
    private final CommunicationCallback CCB;

    public BluetoothClass(MainActivity a) {
        //define the activity as the main activity
        activity = a;
        //make a new bluetooth
        BLUETOOTH = new Bluetooth(activity);
        //uhh probably magic
        CCB = new CommunicationCallback() {
            @Override
            public void onConnect(BluetoothDevice device) {
                //log when connected
                Log.i(TAG, "Connected to device " + device.getName() + " at " + device.getAddress() + "!");
                //send data to target
                BLUETOOTH.send(pendingData);
            }

            @Override
            public void onDisconnect(BluetoothDevice device, String message) {
                //log when disconnected
                Log.i(TAG, "Disconnected from device " + device.getName() + " at " + device.getAddress() + "!");
                //try to reconnect if this was unexpected/not intentional
                if (pendingData.length() != 0) {
                    reconnect();
                }
            }

            @Override
            public void onMessage(String message) {
                //when we get data
                pendingData = "";
                //send it over to DatabaseClass to handle
                DatabaseClass.dataSent(message);
                Log.i(TAG, "Data transfer complete!");
            }

            @Override
            //just log it and ignore it
            public void onError(String message) {
                Log.e(TAG, "Generic error: " + message);
            }

            @Override
            public void onConnectError(BluetoothDevice device, String message) {
                //same as onError, but this time we try to reconnect
                Log.e(TAG, "Caught connection error: " + message);
                reconnect();
            }
        };
        //that probably does something
        BLUETOOTH.setCommunicationCallback(CCB);
    }

    public void setup(){
        //only setup stuff if it needs to be setup
        if(!setup) {
            BLUETOOTH.onStart();
        }
        //well it's good when we actually turn the bluetooth on, but we might not have permissions
        //to do that TODO: ?
        if(!BLUETOOTH.isEnabled()){
            BLUETOOTH.enable();
        }
        setup=true;
    }

    private void reconnect(){
        //does what it says on the tin
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                BLUETOOTH.connectToAddress(Util.PI_MAC_ADDRESS);
            }
        },3000);
    }

    public void send(String data){
        //it just makes pendingData = to what we want to send
        Log.i(TAG,"Entered the send method in bluetooth");
        if(pendingData.length()==0)
            BLUETOOTH.connectToAddress(Util.PI_MAC_ADDRESS);
        //pendingData+=data;
        pendingData=data;

    }

    public void send_byte(byte[] data) {
        //in case we want to send byte data
        //should probably make it so this gets called when we send a string but I'm sure it's fine
        if (pendingData.length() == 0)
            BLUETOOTH.connectToAddress(Util.PI_MAC_ADDRESS);

        pendingData += new String(data);
        Log.i(TAG, "Disconnecting");
        BLUETOOTH.disconnect();
    }

    /// Set the pending data without actually sending to the pi
    public void set_pending_data(String data) {
        //we don't ever bother using this, but we do set pendingData directly, so like
        if (this.pendingData.length() == 0) BLUETOOTH.connectToAddress(Util.PI_MAC_ADDRESS);
        this.pendingData = data;
    }

    public void end(){
        //stops it
        BLUETOOTH.onStop();
    }

}
