package team.singularity.scoutapp2022;

import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
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
    private ListView    view;
    private Button      newTeamBtn;
    private ImageButton backBtn, reloadBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_teams);

        backBtn = findViewById(R.id.backBtn);
        reloadBtn = findViewById(R.id.reloadBtn);
        view = findViewById(R.id.teams_layout);
        newTeamBtn = findViewById(R.id.newTeamButton);

        reloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.vibrate(view, VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
                DatabaseClass.send("[]", "[]");
                finishActivity(1);
                startActivity(new Intent(getBaseContext(), TeamsActivity.class));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.vibrate(view, VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        });

        newTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.vibrate(view, VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
                startActivity(new Intent(getBaseContext(), MakeTeamActivity.class));
            }
        });

        view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view1, int i, long l) {
                Util.vibrate(view, VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
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
                Util.vibrate(view, VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
                startActivity(new Intent(getBaseContext(), MakeTeamActivity.class));
            }
        });

        view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Util.vibrate(view, VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
                String teamNumber = ((TextView)view.findViewById(R.id.teamNumber)).getText().toString();
                String teamName = ((TextView)view.findViewById(R.id.teamName)).getText().toString();
                Intent match = new Intent(TeamsActivity.this.getApplicationContext(), MatchActivity.class);
                match.putExtra("Team Number", teamNumber);
                match.putExtra("Team Name", teamName);
                startActivity(match);
                finish();
            }
        });

        // bluetooth code

        // iterate over all teams in the database
        if (DatabaseClass.teamData != null) {
            for (int i = 0; i < DatabaseClass.teamData.length(); i++) {
                String[] name =  DatabaseClass.getTeamName(i).split("_");
                String   name2electricBoogaloo = "";
                for (int j = 0; j < name.length; j++) {
                    name2electricBoogaloo += name[j] + " ";
                }

                // set name and number
                HashMap resultsMap = new HashMap();
                //((Map) resultsMap).put("name", DatabaseClass.getTeamName(i));
                //((Map) resultsMap).put("number", String.valueOf(DatabaseClass.getTeamNumber(i)));
                ((Map) resultsMap).put("name", name2electricBoogaloo);
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
            //((Map) resultsMap).put("name", "bluetoothTest");
            //((Map) resultsMap).put("number", "69");
            list.add(resultsMap);

            Log.e(TAG, "Database.teamData is NULL!");
        }
    }
}
