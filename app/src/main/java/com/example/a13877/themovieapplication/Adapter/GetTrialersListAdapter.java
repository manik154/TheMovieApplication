package com.example.a13877.themovieapplication.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a13877.themovieapplication.Model.VideoTrailers;
import com.example.a13877.themovieapplication.R;
import java.util.ArrayList;
import java.util.List;


public class GetTrialersListAdapter extends RecyclerView.Adapter<GetTrialersListAdapter.TrailersViewHolder> {

    private List<VideoTrailers.VideoTrailerContent> videoTrailerContents;
    private Context context;
    private OnTrailerItemSelectedListener onTrailerItemSelectedListener;


    public GetTrialersListAdapter(Context context) {
        this.context = context;
        videoTrailerContents = new ArrayList<>();
    }

    private void add(VideoTrailers.VideoTrailerContent item) {
        videoTrailerContents.add(item);
        notifyItemInserted(videoTrailerContents.size() - 1);
    }

    public void addAll(List<VideoTrailers.VideoTrailerContent> videoTrailerContents) {
        for (VideoTrailers.VideoTrailerContent movieDatas : videoTrailerContents) {
            add(movieDatas);
        }
    }

    public void remove(VideoTrailers.VideoTrailerContent item) {
        int position = videoTrailerContents.indexOf(item);
        if (position > -1) {
            videoTrailerContents.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public VideoTrailers.VideoTrailerContent getItem(int position) {
        return videoTrailerContents.get(position);
    }

    @Override
    public TrailersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_trialer_list, parent, false);
        final TrailersViewHolder trailersViewHolder = new TrailersViewHolder(view);
        trailersViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPos = trailersViewHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (onTrailerItemSelectedListener != null) {
                        Log.v("gir","1");
                        onTrailerItemSelectedListener.onItemClick(trailersViewHolder.itemView, adapterPos);
                    }
                }
            }
        });

        return trailersViewHolder;
    }

    @Override
    public void onBindViewHolder(TrailersViewHolder holder, int position) {
        final VideoTrailers.VideoTrailerContent videoTrailerContent = videoTrailerContents.get(position);
        holder.bind(videoTrailerContent);
    }

    @Override
    public int getItemCount() {
        return videoTrailerContents.size();
    }

     public void setOnTrailerItemSelectedListener(OnTrailerItemSelectedListener onTrailerItemSelectedListener) {
         this.onTrailerItemSelectedListener = onTrailerItemSelectedListener;
     }


    public class TrailersViewHolder extends RecyclerView.ViewHolder {

        ImageView img_poster;
        TextView text_trialer_name;

        public TrailersViewHolder(View itemView) {
            super(itemView);
            img_poster = itemView.findViewById(R.id.trailer_thumb);
            text_trialer_name = itemView.findViewById(R.id.trailer_name);

        }

        public void bind(VideoTrailers.VideoTrailerContent videoTrailerContent) {

            /*Picasso.with(context)
                    .load(R.drawable.moviedb_about)
                    .into(img_poster);*/
            text_trialer_name.setText(videoTrailerContent.getName());
            Glide.with(context)
                    .load(R.mipmap.movie_about)
                    .into(img_poster);
        }
    }

    public interface OnTrailerItemSelectedListener {
        void onItemClick(View v, int position);
    }


}
