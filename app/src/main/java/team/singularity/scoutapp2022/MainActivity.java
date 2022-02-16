package team.singularity.scoutapp2022;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.jetbrains.annotations.Nullable;

public final class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity";
    DatabaseClass database;
    private final BluetoothClass BLUETOOTH;

    {
        BLUETOOTH = new BluetoothClass(this);
    }

    @SuppressLint("SetTextI18n")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        Button newMatchBtn = findViewById(R.id.newMatchBtn);
        TextView versionTv = findViewById(R.id.version);

        versionTv.setText((CharSequence)"version " + Util.VERSION);

        newMatchBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent teams = new Intent(MainActivity.this.getApplicationContext(), Teams.class);
                MainActivity.this.startActivity(teams);
            }
        });

        /*(Button)(findViewById(R.id.viewData)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.alert(this, "Not implemented!", "Go away!!", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ;
                    }
                })
            }
        });*/
    }

    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Started MainActivity");
        Log.i(TAG, "Setting up bluetooth");
        BLUETOOTH.setup();
        DatabaseClass.setup(BLUETOOTH);
        String data = "{\"teamData\":[],\"matchData\":[]}";
        Log.i(TAG, "Sending data '" + data + '\'');
        BLUETOOTH.send(data);
    }
}
