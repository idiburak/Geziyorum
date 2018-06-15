package com.example.recluse.geziyorum;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private TripModel selectedTrip;
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
        Button sync;
        sync = holder.button_sync;
        id = holder.trip_id;
        name = holder.trip_name;
        info = holder.trip_info;
        holder.setItemClickListener((view, position1, isLongClick) -> {

            if(isLongClick){
                selectedTrip = list.get(position1);
                view.showContextMenu();
            }else{
                Intent intent = new Intent(view.getContext(), TripDetailActivity.class);
                intent.putExtra("trip",getItemJSON(position1));
                context.startActivity(intent);
            }
        });


        id.setText(Integer.toString(tripItem.getId()));
        name.setText(tripItem.getName());
        info.setText(tripItem.getAbout());

        if(tripItem.getServer_id() == 0){
            sync.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_sync_disabled));
        }else{
            sync.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_sync));

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String getItemJSON(int position){
        TripModel trip = list.get(position);
        return gson.toJson(trip);
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener,View.OnCreateContextMenuListener{

        TextView trip_id,trip_name,trip_info;
        Button button_sync;

        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView){
            super(itemView);
            trip_id = itemView.findViewById(R.id.tripCard_id);
            trip_name = itemView.findViewById(R.id.tripCard_name);
            trip_info = itemView.findViewById(R.id.tripCard_info);
            button_sync = itemView.findViewById(R.id.tripCard_sync);

            itemView.setOnCreateContextMenuListener(this);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(this);


        }




        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String name;
                switch (item.getItemId()) {
                    case 0:
                        //Do stuff
                        name = selectedTrip.getName();
                        System.out.println();
                        break;

                    case 1:
                        //Do stuff
                        name = selectedTrip.getName();
                        System.out.println();
                        break;
                }
                return true;
            }
        };


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

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            menu.setHeaderTitle("Choose an Action for " + selectedTrip.getName());
            MenuItem sync;
            if(selectedTrip.getServer_id() == 0){
                sync = menu.add(0,0,0,"Synchronize");

            }else{
                sync = menu.add(0,0,0,"Desynchronize");
            }
            MenuItem del = menu.add(0,1,0,"Delete");
            sync.setOnMenuItemClickListener(onEditMenu);
            del.setOnMenuItemClickListener(onEditMenu);
        }
    }
}
