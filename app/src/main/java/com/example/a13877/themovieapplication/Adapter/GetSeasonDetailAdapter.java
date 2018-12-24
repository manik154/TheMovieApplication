package com.example.a13877.themovieapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a13877.themovieapplication.Model.EpisodeDetails;
import com.example.a13877.themovieapplication.Model.MovieData;
import com.example.a13877.themovieapplication.R;

import java.util.ArrayList;
import java.util.List;

public class GetSeasonDetailAdapter extends RecyclerView.Adapter<GetSeasonDetailAdapter.SeasonViewHolder> {

    private List<EpisodeDetails> episodeDetails;
    private Context context;
    private int count = 0;

    public GetSeasonDetailAdapter(Context context) {
        this.context = context;
        episodeDetails = new ArrayList<>();
    }

    private void add(EpisodeDetails item) {
        episodeDetails.add(item);
        notifyItemInserted(episodeDetails.size() - 1);
    }

    public void addAll(List<EpisodeDetails> episodeDetails) {
        for (EpisodeDetails episodeDetails1 : episodeDetails) {
            add(episodeDetails1);
        }
    }

    public void remove(MovieData item) {
        int position = episodeDetails.indexOf(item);
        if (position > -1) {
            episodeDetails.remove(position);
            notifyItemRemoved(position);
        }
    }

    public EpisodeDetails getItem(int position) {
        return episodeDetails.get(position);
    }

    @Override
    public SeasonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_detail_season_list, parent, false);
        final SeasonViewHolder seasonViewHolder = new SeasonViewHolder(view);
        return seasonViewHolder;
    }

    @Override
    public void onBindViewHolder(final SeasonViewHolder holder, final int position) {
        final EpisodeDetails episodeDetails1 = episodeDetails.get(position);
        holder.bind(episodeDetails1);
        holder.relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (count == 0) {
                    holder.imageview.setImageResource(R.drawable.arrow_up);
                    holder.relativeLayout.setVisibility(View.VISIBLE);
                    count++;
                } else {
                    holder.imageview.setImageResource(R.drawable.arrow_down);
                    holder.relativeLayout.setVisibility(View.GONE);
                    count = 0;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return episodeDetails.size();
    }


    public class SeasonViewHolder extends RecyclerView.ViewHolder {
        TextView episodenumber;
        TextView episodename;
        TextView overviewdetails;
        TextView voteaverage;
        TextView airdate;
        ImageView imageview;
        RelativeLayout relativeLayout;
        RelativeLayout relativeLayout2;

        public SeasonViewHolder(View itemView) {
            super(itemView);
            episodenumber = itemView.findViewById(R.id.episodenumber);
            episodename = itemView.findViewById(R.id.episodename);
            overviewdetails = itemView.findViewById(R.id.overviewDetails);
            voteaverage = itemView.findViewById(R.id.voteaverage);
            imageview = itemView.findViewById(R.id.imageview);
            airdate = itemView.findViewById(R.id.airdate);
            relativeLayout = itemView.findViewById(R.id.relative2);
            relativeLayout2=itemView.findViewById(R.id.relative1);
        }

        public void bind(EpisodeDetails episodeDetails) {
            episodenumber.setText(String.valueOf(episodeDetails.getEpisode_number()) + ". ");
            episodename.setText(episodeDetails.getName());
            overviewdetails.setText(episodeDetails.getOverview());
            voteaverage.setText("Average Votes:- " + episodeDetails.getVote_average());
            airdate.setText("AirDate:- " + episodeDetails.getAir_date());
        }
    }


}