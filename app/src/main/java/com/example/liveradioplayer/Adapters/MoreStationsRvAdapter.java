package com.example.liveradioplayer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liveradioplayer.Models.ChannelThumbnailModel;
import com.example.liveradioplayer.Models.ChannelsModelClasses.Channel;
import com.example.liveradioplayer.Models.MoreStationRvItemModel;
import com.example.liveradioplayer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoreStationsRvAdapter extends RecyclerView.Adapter<MoreStationsRvAdapter.MoreStationsViewHolder> {

    ArrayList<ChannelThumbnailModel> arrayList ;
    MoreStationInterface moreStationInterface;
    Context mContext;
    public MoreStationsRvAdapter(Context mContext , ArrayList<ChannelThumbnailModel> arrayList , MoreStationInterface moreStationInterface) {
        this.arrayList = arrayList;
        this.moreStationInterface = moreStationInterface;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MoreStationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_more_stations_recyclerview, parent, false);
        MoreStationsViewHolder moreStationsViewHolder = new MoreStationsViewHolder(view , moreStationInterface);
        return moreStationsViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MoreStationsViewHolder holder, int position) {
        // Todo a null pointer Exception is occering here handle it when you are back [ We are trying the show the channels of the same category ] [DONE]
            holder.stationTitle.setText(arrayList.get(position).getTitle());
        Picasso.with(mContext).load(arrayList.get(position).getThumbnailUrl().get(0)).placeholder(R.drawable.image_not_found).error(R.drawable.image_not_found).into(holder.imageView);

    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public  static class MoreStationsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stationTitle;
        ImageView imageView;
        MoreStationInterface moreStationInterface;
        public MoreStationsViewHolder(@NonNull View itemView , MoreStationInterface moreStationInterface) {
                super(itemView);
                this.moreStationInterface = moreStationInterface;
                stationTitle = this.itemView.findViewById(R.id.textViewRowItemMoreStationsRvId);
                imageView = this.itemView.findViewById(R.id.imageView);
                itemView.setOnClickListener(this);
            }
        @Override
        public void onClick(View view) {
            moreStationInterface.onChannelClicked(getAdapterPosition());
        }
    }
        public interface MoreStationInterface{
        public void onChannelClicked(int position);
        }
}
