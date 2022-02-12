package team.singularity.scoutapp2022;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Teams extends AppCompatActivity {
    private final String tag = "Teams";
    private ListView view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_teams);

        view = findViewById(R.id.teams_layout);

        view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view1, int i, long l) {
                // start MatchInformation page
                Log.i(Teams.this.tag, "Clicked teams view");
            }
        });
    }

    public void onResume() {
        super.onResume();

        List list = (List)(new ArrayList());

        SimpleAdapter adapter = new SimpleAdapter(this,
                list,
                R.layout.teams_layout,
                new String[]{"name", "number"},
                new int[]{R.id.teamName, R.id.teamNumber});

        view.setAdapter((ListAdapter)adapter);

        view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String teamNumber = ((TextView)findViewById(R.id.teamNumber)).getText().toString();
                Intent match = new Intent(Teams.this.getApplicationContext(), MatchActivity.class);
                match.putExtra("Team Number", teamNumber);
                startActivity(match);
                finish();
            }
        });

        // bluetooth code

        // iterate over all teams in the database
        for (int i = 0; i < DatabaseClass.teamData.length(); i++) {

            // set name and number
            HashMap resultsMap = new HashMap();
            ((Map)resultsMap).put("name", DatabaseClass.getTeamName(i));
            ((Map)resultsMap).put("number", String.valueOf(DatabaseClass.getTeamNumber(i)));
            list.add(resultsMap);
        }

        // iterate over local teams
        /*
        (0 until Database.tempTeamData().length()).forEach {
            val resultsMap = HashMap<String, String>()

            resultsMap["name"] = getLocalTeamName(i)
            resultsMap["number"] = "${getLocalTeamNumber(i)}"
            list.add(resultsMap)
        }
         */
    }
}
