package com.example.liveradioplayer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liveradioplayer.Models.channel_info;
import com.example.liveradioplayer.R;

import java.util.ArrayList;

public class HomeScreenRvAdapter extends RecyclerView.Adapter<HomeScreenRvAdapter.HomeViewHolder> {

    private Context mContext;
    private ArrayList<channel_info> mUploads;
    private OnItemClickListener onItemClickListener;

    public HomeScreenRvAdapter(Context mContext, ArrayList<channel_info> mUploads , OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.mUploads = mUploads;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_home_screen_recycler_view, parent, false);
        return new HomeScreenRvAdapter.HomeViewHolder(view , onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
            channel_info info  = mUploads.get(position);
            holder.imageView.setImageResource(info.getImageID());
            holder.titleTv.setText(info.getChanel_name());
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public  class  HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView titleTv;
        OnItemClickListener onItemClickListener;

        public HomeViewHolder(@NonNull View itemView , OnItemClickListener onItemClickListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewHomeScreenItemID);
            titleTv = itemView.findViewById(R.id.textviewHomeScreenItemID);
            this.onItemClickListener = onItemClickListener;
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
