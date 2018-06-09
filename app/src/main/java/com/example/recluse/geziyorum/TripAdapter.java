package com.example.recluse.geziyorum;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recluse.geziyorum.models.TripModel;
import com.google.gson.Gson;


import java.util.ArrayList;

/**
 * Created by Recluse on 23.04.2018.
 */

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    private final Gson gson = new Gson();
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

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TripModel tripItem = list.get(position);
        TextView id,name,info;
        id = holder.trip_id;
        name = holder.trip_name;
        info = holder.trip_info;
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    Log.d("item_click","LONG---------");
                }else{
                    Log.d("item_click","SHORT---------" + list.get(position).getName());

                    Intent intent = new Intent(view.getContext(), TripDetailActivity.class);
                    intent.putExtra("trip",getItemJSON(position));
                    context.startActivity(intent);

                }
            }
        });


        id.setText(Integer.toString(tripItem.getId()));
        name.setText(tripItem.getName());
        info.setText(tripItem.getAbout());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String getItemJSON(int position){
        TripModel trip = list.get(position);
        return gson.toJson(trip);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        TextView trip_id,trip_name,trip_info;

        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView){
            super(itemView);
            trip_id = itemView.findViewById(R.id.tripCard_id);
            trip_name = itemView.findViewById(R.id.tripCard_name);
            trip_info = itemView.findViewById(R.id.tripCard_info);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),true);
            return true;
        }
    }
}
