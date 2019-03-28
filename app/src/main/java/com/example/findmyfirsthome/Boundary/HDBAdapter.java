package com.example.findmyfirsthome.Boundary;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.findmyfirsthome.R;

import java.util.ArrayList;
import java.util.List;

public class HDBAdapter extends RecyclerView.Adapter<HDBAdapter.MyViewHolder> {

    private Context mContext;
    private static List<String> hdbList = new ArrayList<>();
    private static List<String> hdbUrl = new ArrayList<>();

    public HDBAdapter(Context mContext, List<String> hdbList, List<String> hdbUrl) {
        this.mContext = mContext;
        this.hdbList = hdbList;
        this.hdbUrl = hdbUrl;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_rec, viewGroup, false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        vHolder.chooseHDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hdbname = hdbList.get(vHolder.getAdapterPosition());
                Toast.makeText(mContext, "You clicked on " + hdbname,
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, DevelopmentDetailUI.class);
                intent.putExtra("HDBName", hdbname);
                mContext.startActivity(intent);
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        //Still got image not done
        final String name = hdbList.get(position);
        final String Url = hdbUrl.get(position);
        viewHolder.textViewHDBName.setText(name);
        Glide.with(mContext).load(Url).into(viewHolder.imageViewHDB);
    }

    @Override
    public int getItemCount() {
        return hdbList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageViewHDB;
        private TextView textViewHDBName;
        private LinearLayout chooseHDB;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewHDB = (ImageView) itemView.findViewById(R.id.hdbImage);
            textViewHDBName = (TextView) itemView.findViewById(R.id.hdbName);
            chooseHDB = (LinearLayout) itemView.findViewById(R.id.hdb_id);
        }


    }
}
