package com.example.moviematchv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {


    private Context context;
    public List<Movie> moviesList;

    public MovieAdapter(Context context, List<Movie> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    public void removeItem(int position) {
        moviesList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, moviesList.size());
    }
    public void restoreItem(Movie movie, int position) {
        moviesList.add(position, movie);
        // notify item added by position
        notifyItemInserted(position);
    }

    @NonNull
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.movie_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MyViewHolder holder, int position) {
        holder.title.setText(moviesList.get(position).getTitle());

        Glide.with(context).
                load(moviesList.get(position)
                        .getPosterURLs().getOriginal())
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView poster;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView);
            poster = itemView.findViewById(R.id.imageView);
        }
    }
}
