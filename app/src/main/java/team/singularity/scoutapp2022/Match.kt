package team.singularity.scoutapp2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class Match : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        val team = findViewById<TextView>(R.id.team)
        val number = getIntent().getExtras()!!["Team Number"]
        Log.i(tag, "Team Number: $number")
        team.text = "$number"
    }
}