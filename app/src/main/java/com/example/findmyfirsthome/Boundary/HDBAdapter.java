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

import com.example.findmyfirsthome.R;

import java.util.List;

public class HDBAdapter extends RecyclerView.Adapter<HDBAdapter.ViewHolder>{
    //initialise these objects
    private List<String> listHDBName;
    private Context context;

    public HDBAdapter(List<String> listHDBName, Context context) {
        this.listHDBName = listHDBName;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.hdb_list, viewGroup, false); //false to not attach layout to root
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final String HDBName = listHDBName.get(position);

        //setting xml id to HDNName
        viewHolder.textViewHDBName.setText(HDBName);

        // onClickListener for each card
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You clicked "  + HDBName, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), HDBDevelopmentUI.class); //Suppose to call next page, HAOJIE!!
                v.getContext().startActivity(intent); //supposed to put intent in ActivityClass instead of Adapter
            }
        });
    }

    @Override
    public int getItemCount() {
        //return #of items in listHDBName
        return listHDBName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //text views from ListItem.xml
        public ImageView imageView;
        public TextView textViewHDBName;
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textViewHDBName = (TextView) itemView.findViewById(R.id.textViewHDBName);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
