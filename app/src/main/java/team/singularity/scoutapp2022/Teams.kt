package team.singularity.scoutapp2022

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Teams : AppCompatActivity() {
    private val tag = "Teams"
    private lateinit var view: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)

        this.view = findViewById(R.id.teams_layout)
        this.view.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            // start MatchInformation page
            Log.i(tag, "Clicked teams view")
        }
    }

    override fun onResume() {
        super.onResume()

        val list = mutableListOf<HashMap<String, String>>()

        val adapter = SimpleAdapter(this,
            list,
            R.layout.teams_layout,
            arrayOf("name", "number"),
            intArrayOf(R.id.teamName, R.id.teamNumber))

        view.adapter = adapter

        view.setOnItemClickListener { adapter, view, i, l ->
            /* TODO : make this more direct */
            val teamNumber = (view.findViewById<View>(R.id.teamNumber) as TextView).text.toString()
            val match = Intent(applicationContext, Match::class.java)
            match.putExtra("Team Number", teamNumber)
            startActivity(match)
            finish()
        }

        // bluetooth code

        // iterate over all teams in the database
        // for (int i = 0; i < teamData.length(); i++)
        for (i in 0 until Database.teamData().length()) {
            val resultsMap = HashMap<String, String>()

            // set name and number
            resultsMap["name"] = getTeamName(i)
            resultsMap["number"] = "${getTeamNumber(i)}"
            list.add(resultsMap)
        }

        // iterate over local teams
        for (i in 0 until Database.tempTeamData.length()) {
            val resultsMap = HashMap<String, String>()

            resultsMap["name"] = getLocalTeamName(i)
            resultsMap["number"] = "${getLocalTeamNumber(i)}"
            list.add(resultsMap)
        }
    }
}
