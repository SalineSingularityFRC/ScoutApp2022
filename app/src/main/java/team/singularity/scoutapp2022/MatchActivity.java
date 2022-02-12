package team.singularity.scoutapp2022;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public final class MatchActivity extends AppCompatActivity {

    private static final String TAG = "Match Activity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_match);

        //if (/*if this stuff is null*/) {
        //    startActivity(); //go back to the last activity
        //}

        final ViewGroup view = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        //should probably have it be able to run before adding in more complexity
        Counter lowerHubCounterAuton = new Counter(view, R.id.lowerHubCounterAuton);
        Counter upperHubCounterAuton = new Counter(view, R.id.upperHubCounterAuton);

        TextView team = (TextView) this.findViewById(R.id.team);
        //casting to an int didn't seem to work
        int number = Integer.parseInt(String.valueOf(this.getIntent().getExtras().get("Team Number")));
        Log.i("7G7 Bluetooth", "Team Number: " + number);
        team.setText(number);
    }
}
