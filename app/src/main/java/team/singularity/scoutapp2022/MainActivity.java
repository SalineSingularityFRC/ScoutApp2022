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
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;

public final class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity";
    Database database;
    private final BluetoothClass BLUETOOTH = new BluetoothClass(this);

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        Button newMatchBtn = findViewById(R.id.newMatchBtn);
        TextView versionTv = findViewById(R.id.version);

        //TODO: don't hardcode that, something broke when translated to java
        versionTv.setText((CharSequence)"version 0.0.2");

        newMatchBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent teams = new Intent(MainActivity.this.getApplicationContext(), Teams.class);
                MainActivity.this.startActivity(teams);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Started MainActivity");
        Log.i(TAG, "Setting up bluetooth");
        BLUETOOTH.setup();
        String data = "{\"teamData\":[],\"matchData\":[]}";
        Log.i(TAG, "Sending data '" + data + '\'');
        BLUETOOTH.send(data);
    }
}
