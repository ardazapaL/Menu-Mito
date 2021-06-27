package com.example.menumito.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menumito.R;

import java.util.ArrayList;

import com.example.menumito.model.MainMenuModel;

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
        holder.img_food.setImageResource(mainMenuModelArrayList.get(position).getImg_food());
        holder.name_food.setText(mainMenuModelArrayList.get(position).getName_food());

    }

    @Override
    public int getItemCount() { return (mainMenuModelArrayList != null) ? mainMenuModelArrayList.size() : 0;}

    public class MainMenuViewHolder extends RecyclerView.ViewHolder {

        private TextView name_food;
        private ImageView img_food;

        public MainMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            name_food = itemView.findViewById(R.id.txt_food_home);
            img_food = itemView.findViewById(R.id.img_food_home);
        }
    }
}
