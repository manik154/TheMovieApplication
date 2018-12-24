package com.example.a13877.themovieapplication.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a13877.themovieapplication.Model.NewsList;
import com.example.a13877.themovieapplication.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsList> newsLists;
    private Context context;
    private OnMovieItemSelectedListener onMovieItemSelectedListener;


    public NewsAdapter(Context context) {
        this.context = context;
        newsLists = new ArrayList<>();

    }

    private void add(NewsList item) {
        newsLists.add(item);
        notifyItemInserted(newsLists.size() - 1);
    }

    public void addAll(List<NewsList> newsLists) {
        for (NewsList newsList : newsLists) {
            add(newsList);
        }
    }

    public void setOnMovieItemSelectedListener(OnMovieItemSelectedListener onMovieItemSelectedListener) {
        this.onMovieItemSelectedListener = onMovieItemSelectedListener;
    }


    public void remove(NewsList item) {
        int position = newsLists.indexOf(item);
        if (position > -1) {
            newsLists.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }


    public NewsList getItem(int position) {
        return newsLists.get(position);
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usernews_fragment, parent, false);
        final NewsViewHolder newsViewHolder = new NewsViewHolder(view);

        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, final int position) {
        final NewsList newsList = newsLists.get(position);
        holder.bind(newsList);
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("hope", "" + position);
                onMovieItemSelectedListener.onItemClick(newsLists.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {

        return newsLists.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        LinearLayout linear;
        TextView description;
        TextView sourcename;
        TextView postedBy;

        public NewsViewHolder(View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            linear = itemView.findViewById(R.id.linearlayout1);
            postedBy = itemView.findViewById(R.id.postedby);
            sourcename = itemView.findViewById(R.id.sourcename);

        }

        public void bind(NewsList newsList) {
            Glide.with(context)
                    .load(newsList.getUrlToImage())
                    .into(imageView);

            Spanned htmlAsSpanned = Html.fromHtml(newsList.getTitle());
            title.setText(htmlAsSpanned);

            description.setText(newsList.getContent());
            postedBy.setText("Posted By:- " + newsList.getAuthor());
            sourcename.setText("Source:- " + newsList.getSource().getName());
        }
    }

    public interface OnMovieItemSelectedListener {
        void onItemClick(NewsList newsList);
    }

}
