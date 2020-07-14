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
import com.adedom.library.ExecuteUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Sing_upActivity extends AppCompatActivity {

    private TextView Password;
    private TextView RePassword;
    private TextView Username;
    private TextView Fullname;
    private TextView PhoneNumber;
    private Button Sing_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);


        Username = (TextView) findViewById(R.id.Username);
        Fullname = (TextView) findViewById(R.id.Fullname);
        PhoneNumber = (TextView) findViewById(R.id.Phone);
        Password = (TextView) findViewById(R.id.Password);
        RePassword = (TextView) findViewById(R.id.RePassword);
        Sing_up = (Button) findViewById(R.id.Singup);

        Sing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Username.getText().toString().trim();
                if (username.isEmpty()) {
                    Toast.makeText(getBaseContext(), "NO UserName", Toast.LENGTH_LONG).show();
                    return;
                }
                String fullname = Fullname.getText().toString().trim();
                if (fullname.isEmpty()) {
                    Toast.makeText(getBaseContext(), "No Fullname", Toast.LENGTH_LONG).show();
                    return;
                }
                String password = Password.getText().toString().trim();
                if (password.isEmpty()) {
                    Toast.makeText(getBaseContext(), "No password", Toast.LENGTH_LONG).show();
                    return;
                }
                String repassword = RePassword.getText().toString().trim();
                if (repassword.isEmpty()) {
                    Toast.makeText(getBaseContext(), "No Repassword ", Toast.LENGTH_LONG).show();
                    return;
                }
                String phone = PhoneNumber.getText().toString().trim();
                if (phone.isEmpty()) {
                    Toast.makeText(getBaseContext(), "No phone", Toast.LENGTH_LONG).show();
                    return;
                } else if (PhoneNumber.length() != 10) {
                    Toast.makeText(getBaseContext(), "10 only", Toast.LENGTH_LONG).show();
                    return;
                }

                String sql = "SELECT * FROM user WHERE username='" + Username.getText().toString().trim() + "'";
                Dru.connection(Connect.connection()).execute(sql).commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {
                            if (resultSet.next()) {
                                Toast.makeText(getBaseContext(), "This Username is already in the system", Toast.LENGTH_SHORT).show();
                            } else {
                                String sql = "INSERT INTO user(username,password,fullname,phone) VALUES ('" + Username.getText().toString().trim() + "','" + Password.getText().toString().trim() + "','" + Fullname.getText().toString().trim()+ "','" + PhoneNumber.getText().toString().trim() + "');";
                                Dru.connection(Connect.connection())
                                        .execute(sql)
                                        .commit(new ExecuteUpdate() {
                                            public void onComplete() {
                                                Toast.makeText(getBaseContext(), "Sing Up Success", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplication(), MenuActivity.class));
                                            }
                                        });
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();

                        }
                    }
                });
            }
        });
    }
}
