package com.example.findmyfirsthome.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findmyfirsthome.Presenter.HDBAdapter;
import com.example.findmyfirsthome.Presenter.HDBDevelopmentController;
import com.example.findmyfirsthome.R;

import java.util.ArrayList;
import java.util.List;

public class HDBFragment_All extends Fragment {

    View v;
    private RecyclerView myRecyclerView;
    private List<String> hdbNameList = new ArrayList<>();
    private List<String> hdbUrl = new ArrayList<>();

    public HDBFragment_All(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.hdb_recycler_fragment,container,false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.rec_recyclerview); //from hdb_recycler_fragment.xml
        HDBAdapter myRecyclerAdapter = new HDBAdapter(getContext(), hdbNameList, hdbUrl);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(myRecyclerAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchData();
    }

    public void fetchData(){
        HDBDevelopmentController hdbdc = new HDBDevelopmentController(getContext());
        hdbNameList = new ArrayList<>();
        hdbUrl = new ArrayList<>();
        hdbNameList = hdbdc.getAllHDBnames();
        hdbUrl = hdbdc.getAllHDBurl();
    }
}
