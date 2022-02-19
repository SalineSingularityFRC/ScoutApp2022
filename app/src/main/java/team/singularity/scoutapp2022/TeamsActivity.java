package team.singularity.scoutapp2022;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TeamsActivity extends AppCompatActivity {
    private final String TAG = "Teams";
    private ListView view;
    private Button   newTeamBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_teams);

        view = findViewById(R.id.teams_layout);
        newTeamBtn = findViewById(R.id.newTeamButton);

        newTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), MakeTeamActivity.class));
            }
        });

        view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view1, int i, long l) {
                // start MatchInformation page
                Log.i(TeamsActivity.this.TAG, "Clicked teams view");
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

        newTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), MakeTeamActivity.class));
            }
        });

        view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String teamNumber = ((TextView)view.findViewById(R.id.teamNumber)).getText().toString();
                Intent match = new Intent(TeamsActivity.this.getApplicationContext(), MatchActivity.class);
                match.putExtra("Team Number", teamNumber);
                startActivity(match);
                finish();
            }
        });

        // bluetooth code

        // iterate over all teams in the database
        if (DatabaseClass.teamData != null) {
            for (int i = 0; i < DatabaseClass.teamData.length(); i++) {

                // set name and number
                HashMap resultsMap = new HashMap();
                ((Map) resultsMap).put("name", DatabaseClass.getTeamName(i));
                ((Map) resultsMap).put("number", String.valueOf(DatabaseClass.getTeamNumber(i)));
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
        } else {
            //display an error without crashing
            HashMap resultsMap = new HashMap();
            ((Map) resultsMap).put("name", "Database.teamData is NULL!");
            ((Map) resultsMap).put("number", "The PI probably isn't connected");
            list.add(resultsMap);

            Log.e(TAG, "Database.teamData is NULL!");
        }
    }
}
