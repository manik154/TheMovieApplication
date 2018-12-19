package com.example.a13877.themovieapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a13877.themovieapplication.Model.MovieData;
import com.example.a13877.themovieapplication.R;
import com.example.a13877.themovieapplication.api.ApiService;
import java.util.ArrayList;
import java.util.List;


public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> implements Filterable {

    private List<MovieData> movieDatas;
    private Context context;
    private List<MovieData> movieFilteredDatas;
    private OnMovieItemSelectedListener onMovieItemSelectedListener;


    public MovieListAdapter(Context context) {
        this.context = context;
        movieDatas = new ArrayList<>();
        movieFilteredDatas=new ArrayList<>();
    }

    private void add(MovieData item) {

        movieDatas.add(item);
        this.movieFilteredDatas=movieDatas;
        notifyItemInserted(movieFilteredDatas.size() - 1);
    }

    public void addAll(List<MovieData> movieDatas) {
        for (MovieData movieData : movieDatas) {
            add(movieData);
        }
    }

    public void remove(MovieData item) {
        int position = movieDatas.indexOf(item);
        if (position > -1) {
            movieFilteredDatas.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    movieFilteredDatas = movieDatas;
                } else {
                    List<MovieData> filteredList = new ArrayList<>();
                    for (MovieData row : movieDatas) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    movieFilteredDatas = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = movieFilteredDatas;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                movieFilteredDatas = (ArrayList<MovieData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public MovieData getItem(int position) {
        return movieFilteredDatas.get(position);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_movielist, parent, false);
        final MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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
        });

        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final MovieData movieData = movieFilteredDatas.get(position);
        holder.bind(movieData);
    }

    @Override
    public int getItemCount() {
        return movieFilteredDatas.size();
    }

    public void setOnMovieItemSelectedListener(OnMovieItemSelectedListener onMovieItemSelectedListener) {
        this.onMovieItemSelectedListener = onMovieItemSelectedListener;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public MovieViewHolder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img_thumb);
        }

        public void bind(MovieData movieData) {
        /*   Picasso.with(context)
                    .load()
                    .into(img);*/
            Glide.with(context)
                    .load(ApiService.IMG_URL + movieData.getPosterPath())
                    .into(img);
        }
    }

    public interface OnMovieItemSelectedListener {
        void onItemClick(View v, int position);
    }


}
