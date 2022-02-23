package team.singularity.scoutapp2022;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.time.Instant;
import java.util.Date;

public final class MatchActivity extends AppCompatActivity {

    private static final String TAG = "Match Activity";
    ImageButton  backBtn;
    TextView     header;
    Spinner      positions;
    Context      context;
    ToggleButton allianceTb;
    EditText     matchEt;
    CheckBox     taxiCb;
    Counter      lowerHubCounterAuton;
    Counter      upperHubCounterAuton;
    Counter      lowerHubCounterTele;
    Counter      upperHubCounterTele;
    Spinner      hangarSp;
    TextView     team;
    String       number;
    String       name;
    Button       submitBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_match);

        //TODO:
        //if (/*if this stuff is null*/) {
        //    startActivity(); //go back to the last activity
        //}

        final ViewGroup VIEW = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        backBtn              = VIEW.findViewById(R.id.backBtn);
        header               = VIEW.findViewById(R.id.toolbarTv);
        context              = this;
        positions            = VIEW.findViewById(R.id.posSp);
        allianceTb           = VIEW.findViewById(R.id.ehItGoRedBlue);
        matchEt              = VIEW.findViewById(R.id.matchEt);
        taxiCb               = VIEW.findViewById(R.id.taxiCb);
        lowerHubCounterAuton = new Counter(VIEW.findViewById(R.id.lowerHubCounterAuton));
        upperHubCounterAuton = new Counter(VIEW.findViewById(R.id.upperHubCounterAuton));
        lowerHubCounterTele  = new Counter(VIEW.findViewById(R.id.lowerHubCounterTele));
        upperHubCounterTele  = new Counter(VIEW.findViewById(R.id.upperHubCounterTele));
        hangarSp             = VIEW.findViewById(R.id.monkeyBarThingSp);
        team                 = this.findViewById(R.id.team);
        number               = String.valueOf(this.getIntent().getExtras().get("Team Number"));
        name                 = String.valueOf(this.getIntent().getExtras().get("Team Name"));
        submitBtn            = VIEW.findViewById(R.id.submitBtn);

        //set team number at the top
        header.setText(name);

        //set color of the button
        allianceTb.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_corner_blue, null));

        //set spinner values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hanger_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hangarSp.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2electricBoogaloo = ArrayAdapter.createFromResource(this,
                R.array.positions, android.R.layout.simple_spinner_item);
        adapter2electricBoogaloo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        positions.setAdapter(adapter2electricBoogaloo);

        Log.i(TAG, "Team Number: " + number);
        team.setText(number);

        matchEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.vibrate(view, VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
            }
        });

        taxiCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.vibrate(view, VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.vibrate(view, VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
                startActivity(new Intent(getBaseContext(), TeamsActivity.class));
            }
        });

        allianceTb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.vibrate(view, VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
                if (allianceTb.isChecked()) {
                    allianceTb.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_corner_red, null));
                } else {
                    allianceTb.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_corner_blue, null));
                }
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.vibrate(view, VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
                //write data to pi
                //Eh, it just goes off for a bit
                try {
                    Integer.parseInt(number);
                } catch (NumberFormatException nfe) {
                    Log.e(TAG, "Number string is not numeric");
                    number = "-1";
                }
                DatabaseClass.createRobotMatch(System.currentTimeMillis() / 1000L,
                        Integer.parseInt(matchEt.getText().toString()),
                        Integer.parseInt(number),
                        !allianceTb.isChecked(),
                        (positions.getSelectedItemPosition() + 1),
                        // TODO: use these
                        -1,
                        false,
                        false,
                        false,
                        false,
                        false,
                        //
                        taxiCb.isChecked(),
                        lowerHubCounterAuton.getCount(),
                        upperHubCounterAuton.getCount(),
                        lowerHubCounterTele.getCount(),
                        upperHubCounterTele.getCount(),
                        hangarSp.getSelectedItemPosition());
                //go back to MainActivity
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        });
    }
}
