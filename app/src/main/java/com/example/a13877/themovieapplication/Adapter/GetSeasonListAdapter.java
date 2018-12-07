package com.example.a13877.themovieapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a13877.themovieapplication.Model.TvSeasonList;
import com.example.a13877.themovieapplication.R;
import com.example.a13877.themovieapplication.api.ApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GetSeasonListAdapter extends RecyclerView.Adapter<GetSeasonListAdapter.SeasonListHolder> {

    private List<TvSeasonList> tvSeasonLists;
    private Context context;
    //private OnMovieItemSelectedListener onMovieItemSelectedListener;


    public GetSeasonListAdapter(Context context) {
        this.context = context;
        tvSeasonLists = new ArrayList<>();
    }

    private void add(TvSeasonList item) {
        tvSeasonLists.add(item);
        notifyItemInserted(tvSeasonLists.size() - 1);
    }

    public void addAll(List<TvSeasonList> tvSeasonLists) {
        for (TvSeasonList tvSeasonLists2 : tvSeasonLists) {
            add(tvSeasonLists2);
        }
    }

    public void remove(TvSeasonList item) {
        int position = tvSeasonLists.indexOf(item);
        if (position > -1) {
            tvSeasonLists.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public TvSeasonList getItem(int position) {
        return tvSeasonLists.get(position);
    }

    @Override
    public SeasonListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_season_list, parent, false);
        final SeasonListHolder seasonListHolder = new SeasonListHolder(view);
       /* seasonListHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPos = seasonListHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (onMovieItemSelectedListener != null) {
                        Log.v("gir","1");
                        onMovieItemSelectedListener.onItemClick(movieViewHolder.itemView, adapterPos);
                    }
                }
            }
        });*/

        return seasonListHolder;
    }

    @Override
    public void onBindViewHolder(SeasonListHolder holder, int position) {
        final TvSeasonList tvSeasonList = tvSeasonLists.get(position);
        holder.bind(tvSeasonList);
    }

    @Override
    public int getItemCount() {

        return tvSeasonLists.size();
    }

    /*public void setOnMovieItemSelectedListener(OnMovieItemSelectedListener onMovieItemSelectedListener) {
        this.onMovieItemSelectedListener = onMovieItemSelectedListener;
    }
*/
    public class SeasonListHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView seasonNumber;
        TextView totalepisode;

        public SeasonListHolder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img_thumb);
            seasonNumber = itemView.findViewById(R.id.seasonNumber);
            totalepisode = itemView.findViewById(R.id.totalEpisode);
        }

        public void bind(TvSeasonList tvSeasonList) {
            Picasso.with(context)
                    .load(ApiService.IMG_URL + tvSeasonList.getPoster_path())
                    .into(img);
            seasonNumber.setText("Season:- "+String.valueOf(tvSeasonList.getSeason_number()));
            totalepisode.setText("Total Episodes:- "+String.valueOf(tvSeasonList.getEpisode_count()));
        }
    }

   /* public interface OnMovieItemSelectedListener {
        void onItemClick(View v, int position);
    }
*/

}
