package com.example.findmyfirsthome.Boundary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findmyfirsthome.Controller.HDBDevelopmentController;
import com.example.findmyfirsthome.R;

import java.util.ArrayList;
import java.util.List;


public class HDBFragment_recommended extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hdb_list, container, false); //inflates Recycler_View into fragment
        recyclerView = (RecyclerView) v.findViewById(R.id.display_development);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        HDBDevelopmentController hdbControl = new HDBDevelopmentController(getContext());
        HDBAdapter hdbAdapter = new HDBAdapter(hdbControl.getRecHDBnames(), getContext());
        recyclerView.setAdapter(hdbAdapter);
        return recyclerView;
    }
}
