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

import com.example.liveradioplayer.Models.ChannelThumbnailModel;
import com.example.liveradioplayer.Models.ChannelsModelClasses.Channel;
import com.example.liveradioplayer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeScreenRvAdapter extends RecyclerView.Adapter<HomeScreenRvAdapter.HomeViewHolder> {

    private Context mContext;
    private ArrayList<ChannelThumbnailModel> mUploads;
    private OnItemClickListener onItemClickListener;

    public HomeScreenRvAdapter(Context mContext, ArrayList<ChannelThumbnailModel> mUploads , OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.mUploads = mUploads;
//        int size = this.mUploads.size();
        this.onItemClickListener = onItemClickListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_home_screen_recycler_view, parent, false);
        return new HomeScreenRvAdapter.HomeViewHolder(view , onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        ChannelThumbnailModel info  = mUploads.get(position);
            Picasso.with(mContext).load(mUploads.get(position).getThumbnailUrl().get(0)).placeholder(R.drawable.image_not_found).error(R.drawable.image_not_found).into(holder.imageView);
//            holder.imageView.setImageResource(R.drawable.group1);
            holder.titleTv.setText(info.getTitle());
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public  class  HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView titleTv;
        OnItemClickListener onItemClickListener;

        public TextView getTitleTv() {
            return titleTv;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public HomeViewHolder(@NonNull View itemView , OnItemClickListener onItemClickListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewHomeScreenItemID);
            titleTv = itemView.findViewById(R.id.textviewHomeScreenItemID);
            this.onItemClickListener = onItemClickListener;
            titleTv.setTransitionName("ChannelNameFromHome");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnItemClickListener{
        void onItemClicked(int position);
    }
}
