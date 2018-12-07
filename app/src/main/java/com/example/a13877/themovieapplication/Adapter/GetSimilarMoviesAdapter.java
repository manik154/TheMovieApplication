package com.example.a13877.themovieapplication.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a13877.themovieapplication.Model.GetSimilarmovies;
import com.example.a13877.themovieapplication.R;
import com.example.a13877.themovieapplication.api.ApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;


public class GetSimilarMoviesAdapter extends RecyclerView.Adapter<GetSimilarMoviesAdapter.GetSimilarViewHolder> {

    private List<GetSimilarmovies> getSimilarmovies;
    private Context context;
    private OnImageClickedListener imageClickedListener;

    public void setImageClickedListener(OnImageClickedListener imageClickedListener) {
        this.imageClickedListener = imageClickedListener;
    }

    public GetSimilarMoviesAdapter(Context context) {
        this.context = context;
        getSimilarmovies = new ArrayList<>();
    }

    private void add(GetSimilarmovies item) {
        getSimilarmovies.add(item);
        notifyItemInserted(getSimilarmovies.size() - 1);
    }

    public void addAll(List<GetSimilarmovies> getSimilarmovies) {
        for (GetSimilarmovies getSimilarmovies1 : getSimilarmovies) {
            add(getSimilarmovies1);
        }
    }
    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public void remove(GetSimilarmovies item) {
        int position = getSimilarmovies.indexOf(item);
        if (position > -1) {
            getSimilarmovies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public GetSimilarmovies getItem(int position) {
        return getSimilarmovies.get(position);
    }

    @Override
    public GetSimilarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_similar_movies, parent, false);
        final GetSimilarViewHolder reviewViewHolder = new GetSimilarViewHolder(view);
        reviewViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = reviewViewHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (imageClickedListener != null) {
                        imageClickedListener.onItemClick(reviewViewHolder.itemView, adapterPos);
                    }
                }
            }
        });
        return reviewViewHolder;
    }


    @Override
    public void onBindViewHolder(GetSimilarViewHolder holder, int position) {
        final GetSimilarmovies getSimilarmovies1 = getSimilarmovies.get(position);
        holder.bind(getSimilarmovies1);
    }

    @Override
    public int getItemCount() {
        Log.v("result", "" + getSimilarmovies.size());
        return getSimilarmovies.size();
    }


    public class GetSimilarViewHolder extends RecyclerView.ViewHolder {
        //  TextView title;
        TextView voteAverage;
        ImageView posterPath;


        public GetSimilarViewHolder(View itemView) {
            super(itemView);
            // title = itemView.findViewById(R.id.title);
            voteAverage = itemView.findViewById(R.id.voteAverage);
            posterPath = itemView.findViewById(R.id.img_thumb);

        }

        public void bind(GetSimilarmovies getSimilarmovies) {
            voteAverage.setText("Average Rating " + String.valueOf(getSimilarmovies.getVoteAverage()));
            // title.setText(getSimilarmovies.getTitle());
            Picasso.with(context)
                    .load(ApiService.IMG_URL + getSimilarmovies.getPosterPath())
                    .into(posterPath);
        }
    }

    public interface OnImageClickedListener {
        void onItemClick(View v, int position);
    }


}