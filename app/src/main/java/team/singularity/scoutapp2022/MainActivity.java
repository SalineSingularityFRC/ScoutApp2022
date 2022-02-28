package team.singularity.scoutapp2022;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.jetbrains.annotations.Nullable;

public final class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity";
    private final BluetoothClass BLUETOOTH;

    //if someone could explain to me what's happening here that would be great -Edison Bregger 2022
    {
        BLUETOOTH = new BluetoothClass(this);
    }

    @SuppressLint("SetTextI18n") //don't know what this does
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        Button newMatchBtn  = findViewById(R.id.newMatchBtn);
        Button pairDevBtn   = findViewById(R.id.pairDevBtn);
        Button viewDataBtn  = findViewById(R.id.viewData);
        ImageButton backBtn = findViewById(R.id.backBtn);
        TextView versionTv  = findViewById(R.id.version);

        //make it so you can't see the back button on the first screen
        backBtn.setVisibility(View.INVISIBLE);

        //throw the version on there
        versionTv.setText((CharSequence)"version " + Util.VERSION);

        pairDevBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to the pair device activity
                startActivity(new Intent(MainActivity.this.getApplicationContext(), PairDeviceActivity.class));
            }
        });

        viewDataBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to the view data activity, might be removed later as we don't have much of a
                //way of doing this
                startActivity(new Intent(getBaseContext(), ViewDataActivity.class));
            }
        });

        newMatchBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to the new match activity
                startActivity(new Intent(MainActivity.this.getApplicationContext(), TeamsActivity.class));
            }
        });
    }

    protected void onStart() {
        super.onStart();
        //get everything ready

        Log.i(TAG, "Started MainActivity");

        Log.i(TAG, "Setting up bluetooth");
        BLUETOOTH.setup();
        DatabaseClass.setup(BLUETOOTH);

        //send empty data json to the pi so it knows to give us the information on the teams
        String data = "{\"teamData\":[],\"matchData\":[]}";
        Log.i(TAG, "Sending data '" + data + '\'');
        BLUETOOTH.send(data);
    }
}
