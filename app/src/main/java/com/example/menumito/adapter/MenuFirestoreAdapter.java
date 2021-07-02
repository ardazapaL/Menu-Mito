package com.example.menumito.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menumito.R;
import com.example.menumito.model.MainMenuModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MenuFirestoreAdapter extends FirestoreRecyclerAdapter<MainMenuModel, MenuFirestoreAdapter.MenuHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MenuFirestoreAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MenuHolder holder, int position, @NonNull MainMenuModel model) {

        holder.foodImg.setText(model.getUrl());
        holder.foodName.setText(model.getName());
        holder.foodPriority.setText(String.valueOf(model.getPriority()));
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_menu_home,
                parent, false);
        return new MenuHolder(view);
    }

    class MenuHolder extends RecyclerView.ViewHolder {

        TextView foodImg;
        TextView foodName;
        TextView foodPriority;
        public MenuHolder(@NonNull View itemView) {
            super(itemView);
            foodImg = itemView.findViewById(R.id.img_food_home);
            foodName = itemView.findViewById(R.id.txt_food_home);
            foodPriority = itemView.findViewById(R.id.txt_priority);
        }
    }
}
