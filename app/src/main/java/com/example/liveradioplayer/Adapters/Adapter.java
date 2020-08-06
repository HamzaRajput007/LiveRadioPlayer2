package com.example.liveradioplayer.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liveradioplayer.Models.channel_info;
import com.example.liveradioplayer.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ImageViewHolder>  {
    private Context mContext;
    private List<channel_info> mUploads;

    public Adapter(Context mContext,List<channel_info> mUploads) {

        this.mContext = mContext;
        this.mUploads = mUploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        channel_info info=mUploads.get(position);
        holder.image_channel.setImageResource(info.getImageID());
        holder.textViewName.setText(info.getChanel_name());
        holder.image_menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopupMenu(holder.image_menu_icon);



            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext.getApplicationContext(), "Position "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(view.getContext(),view );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.selection_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.remove:
                        Toast.makeText(mContext.getApplicationContext(), "Remove", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.share:
                        Toast.makeText(mContext.getApplicationContext(), "Share", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });
        popup.show();
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout linearLayout;
        TextView textViewName,radio;
        ImageView image_menu_icon,image_channel,heart,stop;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName=itemView.findViewById(R.id.text_view_name);
            radio=itemView.findViewById(R.id.radio);
            image_menu_icon=itemView.findViewById(R.id.dots_menu);
            heart=itemView.findViewById(R.id.heart);
            stop=itemView.findViewById(R.id.stop);
            image_channel=itemView.findViewById(R.id.channel_image);
            linearLayout=itemView.findViewById(R.id.complete_item);

        }

    }
}
