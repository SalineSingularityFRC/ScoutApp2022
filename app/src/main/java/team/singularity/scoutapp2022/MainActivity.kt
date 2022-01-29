package team.singularity.scoutapp2022

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.newMatchBtn).setOnClickListener {
            /* Log.i(tag, "Clicked new match button") */

            val teams = Intent(applicationContext, Teams::class.java)
            startActivity(teams)
        }
    }
}
