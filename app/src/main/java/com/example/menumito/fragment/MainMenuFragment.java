    package com.example.menumito.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menumito.R;

import java.util.ArrayList;

import com.example.menumito.adapter.MainMenuAdapter;
import com.example.menumito.model.MainMenuModel;

public class MainMenuFragment extends Fragment {

    private ArrayList<MainMenuModel> listMain;
    private RecyclerView recyclerView;
    private MainMenuAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest_entry, container, false);

        /* initialize */
        recyclerView = view.findViewById(R.id.recyclerView_home);

        /* recycler view */
        adapter = new MainMenuAdapter(listMain);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        listMain.add(new MainMenuModel(R.drawable.ic_baseline_fastfood_24, "tes1"));
        listMain.add(new MainMenuModel(R.drawable.ic_baseline_fastfood_24, "tes2"));
        return view;
    }
}
