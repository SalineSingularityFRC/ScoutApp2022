package team.singularity.scoutapp2022

//
// A class wrapper for bluetooth operations
//

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.os.Handler
import android.util.Log
import bluetooth.Bluetooth
import bluetooth.CommunicationCallback

// If we ever want to get fancy, we can use error codes like this to handle errors with the handler
const val MESSAGE_ERR = 1

class BluetoothClass(a: MainActivity?) {
    val activity: MainActivity? = a
    private val tag = "7G7 Bluetooth"
    private var setup = false
    var pendingData: String = ""
    /* TODO: Handler is deprecated, what's an alternative? */
    private val handler = Handler()
    private val bluetooth: Bluetooth
    /* TODO: How do we avoid hardcoding the MAC address? */
    private val macAddr: String = "B8:27:EB:E8:64:53" //Put the bluetooth address of you Pi server here

    init {
        this.bluetooth = Bluetooth(this.activity)
    }

    // Various bluetooth functions
    private val CCB: CommunicationCallback = object : CommunicationCallback {
        @SuppressLint("MissingPermission")
        override fun onConnect(device: BluetoothDevice) {
            Log.i(tag, "Connected to device ${device.name} at ${device.address}")
            bluetooth.send(pendingData)
            pendingData = ""
        }

        @SuppressLint("MissingPermission")
        override fun onDisconnect(device: BluetoothDevice, message: String) {
            Log.i(tag, "Disconnected from device ${device.name} at ${device.address}!")
            if (pendingData.isNotEmpty()) {
                reconnect()
            }
        }

        override fun onMessage(message: String) {
            Log.i(tag, "Got a message: '$message'")

            Log.i(tag, "Sending message '$message'")

            /* TODO : clean up this if chain */
            if (activity == null) {
                Log.e(tag, "ACTIVITY NULL!")
            } else {
                if (activity.database == null) {
                    Log.e(tag, "DATABASE NULL!")
                } else {
                    activity.database!!.dataSent(message)
                }
            }
            Log.i(tag, "Data transfer complete")
        }

        override fun onError(message: String) {
            Log.e(tag, "Generic error: $message")
        }

        override fun onConnectError(device: BluetoothDevice, message: String) {
            Log.e(tag, "Caught connection error: $message")
            reconnect()
        }
    }

    init {
        bluetooth.setCommunicationCallback(CCB)
    }

    // Connect to the device
    fun setup() {
        if (!setup) {
            bluetooth.onStart()
        }
        if (!bluetooth.isEnabled) {
            bluetooth.enable()
        }
        setup = true
    }

    private fun reconnect() {
        handler.postDelayed({ bluetooth.connectToAddress(macAddr) }, 3000)
    }

    // Send the data over the connection
    fun send(data: String) {
        Log.i(tag, "Entered the send method in bluetooth")
        if (pendingData.isEmpty()) bluetooth.connectToAddress(macAddr)
        pendingData = data
    }

    fun end() {
        bluetooth.onStop()
    }

    fun isConnected(): Boolean = bluetooth.isConnected

    fun connect() {
        bluetooth.connectToAddress(macAddr)
    }
}
