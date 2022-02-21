package team.singularity.scoutapp2022;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MakeTeamActivity extends AppCompatActivity {

    ImageButton backBtn;
    Button submitBtn;
    EditText numberEt, nameEt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_make_team);

        submitBtn = findViewById(R.id.submitBtn);
        backBtn   = findViewById(R.id.backBtn);
        numberEt  = findViewById(R.id.numberEt);
        nameEt    = findViewById(R.id.nameEt);


    }

    protected void onStart() {
        super.onStart();
        submitBtn.setOnClickListener(view -> {
            DatabaseClass.makeTeam(Integer.parseInt(numberEt.getText().toString()), nameEt.getText().toString());
            startActivity(new Intent(getBaseContext(), TeamsActivity.class));
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.vibrate(view, VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
                startActivity(new Intent(getBaseContext(), TeamsActivity.class));
            }
        });
    }
}
