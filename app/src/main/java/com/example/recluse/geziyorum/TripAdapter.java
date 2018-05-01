package com.example.recluse.geziyorum;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recluse.geziyorum.models.TripModel;


import java.util.ArrayList;

/**
 * Created by Recluse on 23.04.2018.
 */

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TripModel> list;
    public TripAdapter(Context context, ArrayList<TripModel> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.rv_trip_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TripModel tripItem = list.get(position);
        TextView id,name,info;
        id = holder.trip_id;
        name = holder.trip_name;
        info = holder.trip_info;

        id.setText(Integer.toString(tripItem.getTrip_id()));
        name.setText(tripItem.getTrip_name());
        info.setText(tripItem.getAbout());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView trip_id,trip_name,trip_info;
        public ViewHolder(View itemView){
            super(itemView);
            trip_id = itemView.findViewById(R.id.tripCard_id);
            trip_name = itemView.findViewById(R.id.tripCard_name);
            trip_info = itemView.findViewById(R.id.tripCard_info);
        }
    }
}
