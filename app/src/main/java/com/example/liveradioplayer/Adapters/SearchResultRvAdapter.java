package com.example.liveradioplayer.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liveradioplayer.Models.ChannelsModelClasses.Channel;
import com.example.liveradioplayer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchResultRvAdapter extends RecyclerView.Adapter<SearchResultRvAdapter.SearchViewHolder> {
    ArrayList<Channel> arrayList ;
    SearchResultRvAdapter.SearchStationInterface searchStationInterface;
    Context mContext;

    public SearchResultRvAdapter(ArrayList<Channel> arrayList, SearchResultRvAdapter.SearchStationInterface searchStationInterface, Context mContext) {
        this.arrayList = arrayList;
        this.searchStationInterface = searchStationInterface;
        this.mContext = mContext;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_more_stations_recyclerview, parent, false);
        SearchViewHolder searchViewHolder = new SearchViewHolder(view ,searchStationInterface );
        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.titleTv.setText(arrayList.get(position).getTitle().getRendered());
//        Picasso.with(mContext).load(arrayList.get(position).getThumbnailUrl().get(0)).placeholder(R.drawable.image_not_found).error(R.drawable.image_not_found).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  class  SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView titleTv;
        SearchResultRvAdapter.SearchStationInterface onItemClickListener;

        public TextView getTitleTv() {
            return titleTv;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public SearchViewHolder(@NonNull View itemView , SearchResultRvAdapter.SearchStationInterface onItemClickListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewHomeScreenItemID);
            titleTv = itemView.findViewById(R.id.textviewHomeScreenItemID);
            this.onItemClickListener = onItemClickListener;
//            titleTv.setTransitionName("ChannelNameFromHome");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onChannelClicked(getAdapterPosition());
        }
    }
    public interface SearchStationInterface{
        public void onChannelClicked(int position);
    }
}
