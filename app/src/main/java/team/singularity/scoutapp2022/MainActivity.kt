package team.singularity.scoutapp2022

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"
    var database: Database? = null
    private var bluetooth: BluetoothClass = BluetoothClass(this)
    var started = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val match = findViewById<Button>(R.id.newMatchBtn)
        match.setOnClickListener {
            /* Log.i(tag, "Clicked new match button") */
            val teams = Intent(applicationContext, Teams::class.java)
            startActivity(teams)
        }
        /* hide the button until onStart() */
        match.visibility = View.GONE;

        findViewById<TextView>(R.id.version).text = "version $VERSION"

        this.database = Database()
    }

    override fun onStart() {
        super.onStart()
        Log.i(tag, "Started MainActivity")
        Log.i(tag, "Setting up bluetooth")
        bluetooth.setup()
        val data = "{\"teamData\":[],\"matchData\":[]}"
        Log.i(tag, "Sending data '$data'")
        bluetooth.send(data)
        started = true
        findViewById<Button>(R.id.newMatchBtn).visibility = (View.VISIBLE);
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetooth.end()
    }
}
