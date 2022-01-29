package team.singularity.scoutapp2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter

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

        // bluetooth code

        for (i in 0 until 10) {
            var map = HashMap<String, String>();

            map["name"] = i.toString()
            map["number"] = i.toString()
            list.add(map)
        }
    }
}
