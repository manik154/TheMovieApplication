package com.example.a13877.themovieapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a13877.themovieapplication.Model.TvShow;
import com.example.a13877.themovieapplication.R;

import java.util.ArrayList;
import java.util.List;


public class GenreListAdapter extends RecyclerView.Adapter<GenreListAdapter.GenreListViewHolder> {

    private List<TvShow.TvGenreTypes> tvGenreTypes;
    private Context context;
    private OnMovieItemSelectedListener onMovieItemSelectedListener;


    public GenreListAdapter(Context context) {
        this.context = context;
        tvGenreTypes = new ArrayList<>();
    }

    private void add(TvShow.TvGenreTypes item) {
        tvGenreTypes.add(item);
        notifyItemInserted(tvGenreTypes.size() - 1);
    }

    public void addAll(List<TvShow.TvGenreTypes> tvGenreTypes) {
        for (TvShow.TvGenreTypes movieDatas : tvGenreTypes) {
            add(movieDatas);
        }
    }

    public void remove(TvShow.TvGenreTypes item) {
        int position = tvGenreTypes.indexOf(item);
        if (position > -1) {
            tvGenreTypes.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public TvShow.TvGenreTypes getItem(int position) {
        return tvGenreTypes.get(position);
    }

    @Override
    public GenreListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_genre_movie_list, parent, false);
        final GenreListViewHolder genreListViewHolder = new GenreListViewHolder(view);
        genreListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPos = genreListViewHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (onMovieItemSelectedListener != null) {
                        Log.v("gir","1");
                        onMovieItemSelectedListener.onItemClick(genreListViewHolder.itemView, adapterPos);
                    }
                }
            }
        });

        return genreListViewHolder;
    }

    @Override
    public void onBindViewHolder(GenreListViewHolder holder, int position) {
        final TvShow.TvGenreTypes tvGenreTypes1 = tvGenreTypes.get(position);
        holder.bind(tvGenreTypes1);
    }

    @Override
    public int getItemCount() {
        Log.v("result", "" + tvGenreTypes.size());
        return tvGenreTypes.size();
    }

     public void setOnMovieItemSelectedListener(OnMovieItemSelectedListener onMovieItemSelectedListener) {
         this.onMovieItemSelectedListener = onMovieItemSelectedListener;
     }

    public class GenreListViewHolder extends RecyclerView.ViewHolder {


        TextView text_genre_name;

        public GenreListViewHolder(View itemView) {
            super(itemView);

            text_genre_name = itemView.findViewById(R.id.textview);

        }

        public void bind(TvShow.TvGenreTypes tvGenreTypes) {


            text_genre_name.setText(tvGenreTypes.getGenreType());

        }
    }

    public interface OnMovieItemSelectedListener {
        void onItemClick(View v, int position);
    }


}
