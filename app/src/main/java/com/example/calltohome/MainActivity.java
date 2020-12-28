package com.example.calltohome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private Button Login;
    private TextView Username;
    private TextView Password;
    private Button Sing_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login = (Button) findViewById(R.id.login);
        Sing_up = (Button) findViewById(R.id.Singup);
        Username = (TextView) findViewById(R.id.Username);
        Password = (TextView) findViewById(R.id.Password);

        if (Connect.connection() == null) {
            Dru.failed(getBaseContext());
        } else {
            Dru.completed(getBaseContext());
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = "SELECT * FROM user WHERE username='" + Username.getText().toString().trim() + "'AND password='" + Password.getText().toString().trim() + "'";

                Dru.connection(Connect.connection())
                        .execute(sql)
                        .commit(new ExecuteQuery() {
                            @Override
                            public void onComplete(ResultSet resultSet) {
                                try {
                                    if (resultSet.next()) {
                                        String user_id = resultSet.getString(1);
                                        Toast.makeText(getBaseContext(), "ok", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplication(), MenuActivity.class)
                                                .putExtra("userid", user_id));

                                    } else {
                                        Toast.makeText(getBaseContext(), "no", Toast.LENGTH_LONG).show();
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });

        Sing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), Sing_upActivity.class));

            }
        });


    }
}
