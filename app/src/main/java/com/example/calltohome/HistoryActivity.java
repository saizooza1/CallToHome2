package com.example.calltohome;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;
import com.example.calltohome.models.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ArrayList<Order> item;
    private RecyclerView mRecyclerView;
    private String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        UserId = getIntent().getStringExtra("userid");


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchOrder();
    }

    private void fetchOrder() {
        UserId = getIntent().getStringExtra("sid");
        Toast.makeText(getBaseContext(), "" + UserId, Toast.LENGTH_SHORT).show();
        String sql = "SELECT order_id,abode,repair_list,date FROM orderl WHERE user_id='" + UserId + "'";
        Dru.connection(Connect.connection())
                .execute(sql)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {

                            item = new ArrayList<Order>();
                            while (resultSet.next()) {
                                Order order = new Order(
                                        resultSet.getString(1),
                                        resultSet.getString(2),
                                        resultSet.getString(3),
                                        resultSet.getString(4));
                                item.add(order);
                            }
                            mRecyclerView.setAdapter(new OrderAdapter());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {
        @NonNull
        @Override
        public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
            return new OrderHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
            Order order = item.get(position);
            holder.Order_id.setText(order.getOrder_id());
            holder.Tv_adode.setText(order.getAdode());
            holder.Repair_list.setText(order.getRepair_list());
            holder.Date.setText(order.getDate());


//            Dru.loadImageCircle(holder.ivImage, ConnectDB.BASE_IMAGE + product.getImage());
        }

        @Override
        public int getItemCount() {
            return item.size();
        }
    }

    private class OrderHolder extends RecyclerView.ViewHolder {


        private final TextView Order_id;
        private final TextView Tv_adode;
        private final TextView Repair_list;
        private final TextView Date;


        public OrderHolder(@NonNull View itemView) {
            super(itemView);


            Order_id = (TextView) itemView.findViewById(R.id.tv_order_id);
            Tv_adode = (TextView) itemView.findViewById(R.id.tv_adode);
            Repair_list = (TextView) itemView.findViewById(R.id.tv_repair_list);
            Date = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }
}
