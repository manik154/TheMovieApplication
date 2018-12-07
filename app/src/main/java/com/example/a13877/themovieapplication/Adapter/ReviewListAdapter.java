package com.example.a13877.themovieapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a13877.themovieapplication.Model.MovieData;
import com.example.a13877.themovieapplication.Model.ReviewResultTypes;
import com.example.a13877.themovieapplication.R;
import com.example.a13877.themovieapplication.api.ApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewViewHolder> {

    private List<ReviewResultTypes> reviewResultTypes;
    private Context context;


    public ReviewListAdapter(Context context) {
        this.context = context;
        reviewResultTypes = new ArrayList<>();
    }

    private void add(ReviewResultTypes item) {
        reviewResultTypes.add(item);
        notifyItemInserted(reviewResultTypes.size() - 1);
    }

    public void addAll(List<ReviewResultTypes> reviewResultTypes) {
        for (ReviewResultTypes reviewResultTypes1 : reviewResultTypes) {
            add(reviewResultTypes1);
        }
    }

    public void remove(MovieData item) {
        int position = reviewResultTypes.indexOf(item);
        if (position > -1) {
            reviewResultTypes.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ReviewResultTypes getItem(int position) {
        return reviewResultTypes.get(position);
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_review_list, parent, false);
        final ReviewViewHolder reviewViewHolder = new ReviewViewHolder(view);

        return reviewViewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        final ReviewResultTypes reviewResultTypes1 = reviewResultTypes.get(position);
        holder.bind(reviewResultTypes1);
    }

    @Override
    public int getItemCount() {
        return reviewResultTypes.size();
    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView author;
        TextView review;


        public ReviewViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            review = itemView.findViewById(R.id.reviews);


        }

        public void bind(ReviewResultTypes reviewResultTypes) {
            author.setText(reviewResultTypes.getAuthor());
            review.setText(reviewResultTypes.getContent());

        }
    }


}