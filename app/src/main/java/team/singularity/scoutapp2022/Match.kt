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

        /* store the ids of all the objects used in counters */
        val ids = arrayOf(
            arrayOf( R.id.autoLowerHubInc, R.id.autoLowerHubDec, R.id.autoLowerHubCount )
            , arrayOf( R.id.autoUpperHubInc, R.id.autoUpperHubDec, R.id.autoUpperHubCount )
        )

        /* create the counters and find the views */
        for (i in ids) {
            Counter(findViewById(i[0]),findViewById(i[1]),findViewById(i[2]))
        }
    }
}