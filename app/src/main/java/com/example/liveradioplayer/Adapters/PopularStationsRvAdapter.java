package com.example.liveradioplayer.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liveradioplayer.Models.PopularStationsModel;
import com.example.liveradioplayer.R;
import java.util.ArrayList;

public class PopularStationsRvAdapter extends RecyclerView.Adapter<PopularStationsRvAdapter.PopularStationViewHolder> {
    ArrayList<PopularStationsModel> arrayList;

    public PopularStationsRvAdapter(ArrayList<PopularStationsModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public PopularStationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_popular_stations_recyclerview, parent, false);
        PopularStationViewHolder popularStationsModel = new PopularStationViewHolder(view);
        return popularStationsModel;
    }

    @Override
    public void onBindViewHolder(@NonNull PopularStationViewHolder holder, int position) {
        holder.imageView.setImageResource(arrayList.get(position).getImage());
    }

    //Todo Check why the popular stations recyclerview is not showing any item in it.

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class PopularStationViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView ;
        public PopularStationViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = this.itemView.findViewById(R.id.rowItemPopStationImageViewId);
        }
    }
}
