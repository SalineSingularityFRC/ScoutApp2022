package team.singularity.scoutapp2022;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public final class MatchActivity extends AppCompatActivity {

    private static final String TAG = "Match Activity";
    CheckBox taxiCb;
    Counter  lowerHubCounterAuton;
    Counter  upperHubCounterAuton;
    Counter  lowerHubCounterTele;
    Counter  upperHubCounterTele;
    Spinner  hangarSp;
    TextView team;
    String   number;
    Button   submitBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_match);

        //if (/*if this stuff is null*/) {
        //    startActivity(); //go back to the last activity
        //}

        final ViewGroup VIEW = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        taxiCb               = VIEW.findViewById(R.id.taxiCb);
        lowerHubCounterAuton = new Counter(VIEW.findViewById(R.id.lowerHubCounterAuton));
        upperHubCounterAuton = new Counter(VIEW.findViewById(R.id.upperHubCounterAuton));
        lowerHubCounterTele  = new Counter(VIEW.findViewById(R.id.lowerHubCounterTele));
        upperHubCounterTele  = new Counter(VIEW.findViewById(R.id.upperHubCounterTele));
        hangarSp             = VIEW.findViewById(R.id.monkeyBarThingSp);
        team                 = this.findViewById(R.id.team);
        number               = String.valueOf(this.getIntent().getExtras().get("Team Number"));
        submitBtn            = VIEW.findViewById(R.id.submitBtn);

        //set spinner values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hanger_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hangarSp.setAdapter(adapter);

        Log.i(TAG, "Team Number: " + number);
        team.setText(number);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //write data to pi here:

                //go back to MainActivity
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        });
    }
}
