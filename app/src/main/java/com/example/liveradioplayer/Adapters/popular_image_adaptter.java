package com.example.liveradioplayer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liveradioplayer.Models.channel_info;
import com.example.liveradioplayer.R;

import java.util.ArrayList;
import java.util.List;

public class popular_image_adaptter extends RecyclerView.Adapter<popular_image_adaptter.ImageViewHolder> {
    private Context mContext;
    private ArrayList<channel_info> mUploads;

    public popular_image_adaptter(Context mContext, ArrayList<channel_info> mUploads) {
        this.mContext = mContext;
        this.mUploads = mUploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popular_item, parent, false);
        return new popular_image_adaptter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int position) {
        channel_info info=mUploads.get(position);
        holder.image_channel.setImageResource(info.getPopularImageID());
        holder.image_channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext.getApplicationContext(), "Position "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image_channel;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            image_channel=itemView.findViewById(R.id.popular_channel_image);

        }

    }
}
