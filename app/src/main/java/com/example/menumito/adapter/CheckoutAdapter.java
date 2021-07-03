package com.example.menumito.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menumito.R;
import com.example.menumito.model.OrderModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {

    private ArrayList<OrderModel> list;
    private View view;

    public CheckoutAdapter(ArrayList<OrderModel> list) {

        this.list = list;
    }

    @NonNull
    @Override
    public CheckoutAdapter.CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.layout_checkout, parent, false);

        return new CheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutAdapter.CheckoutViewHolder holder, int position) {
        Picasso.get().load(list.get(position).getUrl_food()).into(holder.food_img);
        holder.food_id.setText(list.get(position).getId());
        holder.food_name.setText(list.get(position).getName_food());
        holder.food_count.setText(list.get(position).getOrder_count());
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    public class CheckoutViewHolder extends RecyclerView.ViewHolder {

        private ImageView food_img;
        private TextView food_id, food_name, food_count;

        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);

            food_id = itemView.findViewById(R.id.checkout_food_id);
            food_img = itemView.findViewById(R.id.checkout_food_img);
            food_name = itemView.findViewById(R.id.checkout_food_name);
            food_count = itemView.findViewById(R.id.checkout_food_count);
        }
    }
}
