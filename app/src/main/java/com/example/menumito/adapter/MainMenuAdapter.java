package com.example.menumito.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menumito.R;

import java.util.ArrayList;

import com.example.menumito.activity.SendNotif;
import com.example.menumito.activity.SingleMenuActivity;
import com.example.menumito.model.MainMenuModel;
import com.squareup.picasso.Picasso;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.MainMenuViewHolder> {

    private ArrayList<MainMenuModel> mainMenuModelArrayList;
    private View view;

    public MainMenuAdapter(ArrayList<MainMenuModel> list){
        this.mainMenuModelArrayList = list;
    }

    @NonNull
    @Override
    public MainMenuAdapter.MainMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.layout_menu_home, parent, false);

        return new MainMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainMenuAdapter.MainMenuViewHolder holder, int position) {
        Picasso.get().load(mainMenuModelArrayList.get(position).getUrl()).into(holder.img_food);
        holder.id_food.setText(mainMenuModelArrayList.get(position).getId());
        holder.name_food.setText(mainMenuModelArrayList.get(position).getName());
        holder.priority_food.setText(String.valueOf(mainMenuModelArrayList.get(position).getPriority()));

        MainMenuViewHolder viewHolder = new MainMenuViewHolder(view);

        /* ONCLICK LISTENER BUTTON ORDER */
        viewHolder.btn_order.setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext(), SingleMenuActivity.class);
            intent.putExtra("food_id", mainMenuModelArrayList.get(position).getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return (mainMenuModelArrayList != null) ? mainMenuModelArrayList.size() : 0;}

    public class MainMenuViewHolder extends RecyclerView.ViewHolder {

        private TextView name_food, priority_food, id_food;
        private ImageView img_food;
        private Button btn_order;

        public MainMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            id_food = itemView.findViewById(R.id.id_food);
            name_food = itemView.findViewById(R.id.txt_food_home);
            img_food = itemView.findViewById(R.id.img_food_home);
            priority_food = itemView.findViewById(R.id.txt_priority);
            btn_order = itemView.findViewById(R.id.btn_order);
        }
    }
}
