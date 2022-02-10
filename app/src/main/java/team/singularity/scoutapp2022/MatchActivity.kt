package team.singularity.scoutapp2022

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MatchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        val view = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(
            0
        ) as ViewGroup

        //should probably have it be able to run before adding in more complexity

        val lowerHubCounter = Counter(view, R.id.lowerHubCounter);
        val upperHubCounter = Counter(view, R.id.upperHubCounter);

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
