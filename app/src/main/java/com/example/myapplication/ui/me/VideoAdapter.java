package com.example.myapplication.ui.me;

import com.example.myapplication.R; // Replace with your app's package name
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myapplication.model.WatchHistory;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final Context context;
    private final List<WatchHistory> videoList;

    public VideoAdapter(Context context, List<WatchHistory> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        WatchHistory watchHistory = videoList.get(position);
        holder.titleTextView.setText(watchHistory.getTitle());
        holder.timestampTextView.setText(watchHistory.getWatchData());

        // Load image using Glide (or Picasso)
        Glide.with(context)
                .load(watchHistory.getThumbnailUrl())
                .placeholder(R.drawable.placeholder_thumbnail)
                .into(holder.thumbnailImageView);

        // Set click listener for each item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition(); // Get the current position
                if (currentPosition != RecyclerView.NO_POSITION) {
                    WatchHistory clickedItem = videoList.get(currentPosition);
                    Toast.makeText(context, clickedItem.getTitle(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailImageView;
        TextView titleTextView;
        TextView timestampTextView;

        VideoViewHolder(View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.video_thumbnail);
            titleTextView = itemView.findViewById(R.id.video_title);
            timestampTextView = itemView.findViewById(R.id.video_timestamp);
        }
    }
}