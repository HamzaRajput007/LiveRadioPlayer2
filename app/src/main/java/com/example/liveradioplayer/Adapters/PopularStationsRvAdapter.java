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
    PopularStationInterface popularStationInterface;

    public PopularStationsRvAdapter(ArrayList<PopularStationsModel> arrayList , PopularStationInterface popularStationInterface ) {
        this.arrayList = arrayList;
        this.popularStationInterface = popularStationInterface;
    }

    @NonNull
    @Override
    public PopularStationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_popular_stations_recyclerview, parent, false);
        PopularStationViewHolder popularStationsModel = new PopularStationViewHolder(view , popularStationInterface);
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

    public static class PopularStationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView ;
        PopularStationInterface popularStationInterface;
        public PopularStationViewHolder(@NonNull View itemView , PopularStationInterface popularStationInterface) {
            super(itemView);
            this.popularStationInterface = popularStationInterface;
            imageView = this.itemView.findViewById(R.id.rowItemPopStationImageViewId);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            popularStationInterface.popularStationClicked(getAdapterPosition());
        }
    }

    public interface PopularStationInterface {
        public void popularStationClicked(int position);
    }
}
