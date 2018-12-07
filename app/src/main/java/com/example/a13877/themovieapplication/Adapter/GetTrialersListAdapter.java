package com.example.a13877.themovieapplication.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a13877.themovieapplication.Model.VideoTrailerContent;
import com.example.a13877.themovieapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class GetTrialersListAdapter extends RecyclerView.Adapter<GetTrialersListAdapter.TrailersViewHolder> {

    private List<VideoTrailerContent> videoTrailerContents;
    private Context context;
    //private OnMovieItemSelectedListener onMovieItemSelectedListener;


    public GetTrialersListAdapter(Context context) {
        this.context = context;
        videoTrailerContents = new ArrayList<>();
    }

    private void add(VideoTrailerContent item) {
        videoTrailerContents.add(item);
        notifyItemInserted(videoTrailerContents.size() - 1);
    }

    public void addAll(List<VideoTrailerContent> videoTrailerContents) {
        for (VideoTrailerContent movieDatas : videoTrailerContents) {
            add(movieDatas);
        }
    }

    public void remove(VideoTrailerContent item) {
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

    public VideoTrailerContent getItem(int position) {
        return videoTrailerContents.get(position);
    }

    @Override
    public TrailersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_trialer_list, parent, false);
        final TrailersViewHolder trailersViewHolder = new TrailersViewHolder(view);
        /*movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPos = movieViewHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (onMovieItemSelectedListener != null) {
                        Log.v("gir","1");
                        onMovieItemSelectedListener.onItemClick(movieViewHolder.itemView, adapterPos);
                    }
                }
            }
        });*/

        return trailersViewHolder;
    }

    @Override
    public void onBindViewHolder(TrailersViewHolder holder, int position) {
        final VideoTrailerContent videoTrailerContent = videoTrailerContents.get(position);
        holder.bind(videoTrailerContent);
    }

    @Override
    public int getItemCount() {
        Log.v("result", "" + videoTrailerContents.size());
        return videoTrailerContents.size();
    }

    /* public void setOnMovieItemSelectedListener(OnMovieItemSelectedListener onMovieItemSelectedListener) {
         this.onMovieItemSelectedListener = onMovieItemSelectedListener;
     }
 */
    public class TrailersViewHolder extends RecyclerView.ViewHolder {

        ImageView img_poster;
        TextView text_trialer_name;

        public TrailersViewHolder(View itemView) {
            super(itemView);
            img_poster = itemView.findViewById(R.id.trailer_thumb);
            text_trialer_name = itemView.findViewById(R.id.trailer_name);

        }

        public void bind(VideoTrailerContent videoTrailerContent) {

            Picasso.with(context)
                    .load(R.drawable.moviedb_about)
                    .into(img_poster);
            text_trialer_name.setText(videoTrailerContent.getName());

        }
    }

    /*public interface OnMovieItemSelectedListener {
        void onItemClick(View v, int position);
    }
*/

}
