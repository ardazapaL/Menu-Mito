package com.example.menumito.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menumito.R;
import com.example.menumito.adapter.MainMenuAdapter;
import com.example.menumito.adapter.MenuFirestoreAdapter;
import com.example.menumito.model.MainMenuModel;
import com.example.menumito.model.OrderModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MainMenuFragment extends Fragment {

    private static final String TAG = "MainMenuFragment";
    private ArrayList<MainMenuModel> mainMenuModels;
    private ArrayList<OrderModel> orderModels;
    private RecyclerView recyclerView;
    private MainMenuAdapter adapter;

    private FirebaseFirestore db;
    private CollectionReference reference;
    private MenuFirestoreAdapter adapterFirestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        db = FirebaseFirestore.getInstance();
        reference = db.collection("foods");

        orderModels = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recyclerView_home);

        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mainMenuModels = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    //Log.d(TAG, document.getId() + " => " + document.getData());

                    String id = document.getId();
                    //Log.i(TAG, "ID Document ==> " + id);
                    String url = document.getString("url");
                    //Log.i(TAG, "URL ==> " + url);
                    String name = document.getString("name");
                    //Log.i(TAG, "NAME ==> " + name);
                    double priority = document.getDouble("priority");
                    //Log.i(TAG, "PRIORITY ==> " + priority);

                    mainMenuModels.add(new MainMenuModel(id, name, url, priority));
                }
                //Log.i(TAG, "ARRAY LIST ==> " + mainMenuModels.toArray());

                /* RECYCLERVIEW */
                adapter = new MainMenuAdapter(mainMenuModels);
                adapter.notifyDataSetChanged();

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }else {
                Log.i(TAG, "Error Getting Document " + task.getException());
            }
        });

        //addDataSample();
        //SetUpRecyclerView();
        return view;
    }

    /* METHOD WITH FIRESTORE UI */
    private void SetUpRecyclerView() {
        Query query = reference.orderBy("priority",
                Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<MainMenuModel> options =
                new FirestoreRecyclerOptions.Builder<MainMenuModel>()
                .setQuery(query, MainMenuModel.class)
                .build();

        adapterFirestore = new MenuFirestoreAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterFirestore);
    }

    /* DATA FOR TESTING */
    private void addDataSample() {
        mainMenuModels = new ArrayList<>();
        mainMenuModels.add(new MainMenuModel("1","test","test", 1));
        mainMenuModels.add(new MainMenuModel("2","test","test", 1));
        mainMenuModels.add(new MainMenuModel("3","test","test", 1));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapterFirestore != null) {
            adapterFirestore.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapterFirestore != null) {
            adapterFirestore.stopListening();
        }
    }
}
