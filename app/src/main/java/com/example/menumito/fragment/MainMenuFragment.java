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

    private ArrayList<MainMenuModel> mainMenuModels;
    private RecyclerView recyclerView;
    private MainMenuAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_home);

        addDataSample();
        /* RECYCLERVIEW */
        adapter = new MainMenuAdapter(mainMenuModels);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void addDataSample() {
        mainMenuModels = new ArrayList<>();
        mainMenuModels.add(new MainMenuModel(R.drawable.ic_baseline_fastfood_24, "Makanan 1"));
        mainMenuModels.add(new MainMenuModel(R.drawable.ic_baseline_fastfood_24, "Makanan 2"));
        mainMenuModels.add(new MainMenuModel(R.drawable.ic_baseline_fastfood_24, "Makanan 3"));
    }
}
