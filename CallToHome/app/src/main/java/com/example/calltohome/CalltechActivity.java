package com.example.calltohome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;
import com.adedom.library.ExecuteUpdate;
import com.example.calltohome.models.Typepay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CalltechActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "CalltechActivity";

    private Spinner Spinnerpay;
    private ArrayList<Typepay> payitem;
    private String spinnerpay;
    private Button mBtOK;
    private EditText eTjobname;
    private EditText eTabode;
    private String uId;
    private static String UserId;
    private Button bTCalendar;
    private TextView tVdate;
    private String setData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calltech);

        UserId = getIntent().getStringExtra("sid");


        Spinnerpay = (Spinner) findViewById(R.id.spinnerpay);

        mBtOK = (Button) findViewById(R.id.Bt_ok);
        eTjobname = (EditText) findViewById(R.id.et_jobname);
        tVdate = (TextView) findViewById(R.id.tv_date);
        eTabode = (EditText) findViewById(R.id.et_abode);
        bTCalendar = (Button) findViewById(R.id.btCalendar);


        bTCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }
        });


        mBtOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sql = "INSERT INTO `orderl` (`user_id`, `abode`, `repair_list`, `pay_type`, `date`) VALUES ('" + UserId + "', '" + eTabode.getText().toString().trim() + "', '" + eTjobname.getText().toString().trim() + "', '" + spinnerpay + "', '" + setData + "')";

                Dru.connection(Connect.connection())
                        .execute(sql)
                        .commit(new ExecuteUpdate() {
                            @Override
                            public void onComplete() {

                                Toast.makeText(getBaseContext(), "Insert success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getBaseContext(), LocationActivity.class));

                            }
                        });

            }
        });
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //String currentDateString= DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        String currentDateString = formatter.format(c.getTime());
        setData = currentDateString;

        tVdate.setText(currentDateString);
        Toast.makeText(getBaseContext(), "" + currentDateString, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSpinnerpay();
    }

    private void setSpinnerpay() {
        String sql = "SELECT * FROM pay ORDER BY pay_id ASC";
        Dru.connection(Connect.connection())
                .execute(sql)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        payitem = new ArrayList<Typepay>();
                        try {
                            while (resultSet.next()) {
                                Typepay typepay = new Typepay();
                                typepay.setPayId(resultSet.getString("pay_id"));
                                typepay.setPayName(resultSet.getString("pay_type"));
                                payitem.add(typepay);

                            }
                            Spinnerpay.setAdapter(new TypepayAdapter(getBaseContext(), payitem));

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }


                    }
                });

        Spinnerpay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Typepay typepay = (Typepay) parent.getItemAtPosition(position);
                spinnerpay = typepay.getPayId();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private class TypepayAdapter extends ArrayAdapter<Typepay> {
        public TypepayAdapter(Context context, ArrayList<Typepay> payitem) {
            super(context, 0, payitem);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return initView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return initView(position, convertView, parent);
        }

        private View initView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
            }
            TextView payId = (TextView) convertView.findViewById(android.R.id.text1);
            TextView payName = (TextView) convertView.findViewById(android.R.id.text2);

            Typepay typepay = getItem(position);
            payId.setText(typepay.getPayId());
            payName.setText(typepay.getPayName());

            return convertView;

        }
    }
}
