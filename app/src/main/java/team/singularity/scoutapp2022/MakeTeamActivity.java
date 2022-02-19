package team.singularity.scoutapp2022;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MakeTeamActivity extends AppCompatActivity {

    Button submitBtn;
    EditText numberEt, nameEt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_make_team);

        submitBtn = findViewById(R.id.submitBtn);
        numberEt  = findViewById(R.id.numberEt);
        nameEt    = findViewById(R.id.nameEt);


    }

    protected void onStart() {
        super.onStart();
        submitBtn.setOnClickListener(view -> {
            DatabaseClass.makeTeam(Integer.parseInt(numberEt.getText().toString()), nameEt.getText().toString());
            startActivity(new Intent(getBaseContext(), TeamsActivity.class));
        });
    }
}
