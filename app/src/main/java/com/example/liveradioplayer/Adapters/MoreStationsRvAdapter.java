package com.example.liveradioplayer.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liveradioplayer.Models.MoreStationRvItemModel;
import com.example.liveradioplayer.R;

import java.util.ArrayList;

public class MoreStationsRvAdapter extends RecyclerView.Adapter<MoreStationsRvAdapter.MoreStationsViewHolder> {

    ArrayList<MoreStationRvItemModel> arrayList ;

    public MoreStationsRvAdapter(ArrayList<MoreStationRvItemModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MoreStationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_more_stations_recyclerview, parent, false);
        MoreStationsViewHolder moreStationsViewHolder = new MoreStationsViewHolder(view);
        return moreStationsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoreStationsViewHolder holder, int position) {
            holder.stationTitle.setText(arrayList.get(position).getTitle());
            holder.imageView.setImageResource(arrayList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  static class MoreStationsViewHolder extends RecyclerView.ViewHolder{

        TextView stationTitle;
        ImageView imageView;
        public MoreStationsViewHolder(@NonNull View itemView) {
                super(itemView);
                stationTitle = this.itemView.findViewById(R.id.textViewRowItemMoreStationsRvId);
                imageView = this.itemView.findViewById(R.id.imageView);
            }
        }
}
