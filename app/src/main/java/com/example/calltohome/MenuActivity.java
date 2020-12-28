package com.example.calltohome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button Call;
    private String UserId;
    private Button History;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        UserId = getIntent().getStringExtra("userid");

        History = (Button) findViewById(R.id.history);
        Call = (Button) findViewById(R.id.call);

        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplication(), CalltechActivity.class).putExtra("sid",UserId));
            }
        });
        //.putExtra("sid",UserId)

        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class).putExtra("sid", UserId));
            }
        });
    }
}
