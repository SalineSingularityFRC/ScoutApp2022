package team.singularity.scoutapp2022;

import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ViewDataActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_view_data);

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.vibrate(view, VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        });
    }
}
