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

        //Counter lowerHubCounter = new Counter(R.id.lowerHubCounter);
        val lowerHubCounter = Counter(view, R.id.lowerHubCounter);
        val upperHubCounter = Counter(view, R.id.upperHubCounter);



        val team = findViewById<TextView>(R.id.team)
        val number = getIntent().getExtras()!!["Team Number"]
        Log.i(tag, "Team Number: $number")
        team.text = "$number"
    }
}
