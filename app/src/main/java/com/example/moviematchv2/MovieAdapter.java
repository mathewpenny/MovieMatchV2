package com.example.moviematchv2;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {


    private Context context;
    public List<Movie> moviesList;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

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
        holder.title.setOnLongClickListener(v -> {
            setPosition(holder.getPosition());
            return true;
        });
        holder.title.setOnClickListener(view -> setPosition(holder.getPosition()));

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView title;
        ImageView poster;
        ImageButton playButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);
            title = itemView.findViewById(R.id.textView);
            poster = itemView.findViewById(R.id.imageView);
            playButton = itemView.findViewById(R.id.playButton);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo MenuInfo) {
            menu.setHeaderTitle("Select Action");
            menu.add(0, Menu.NONE,getAdapterPosition(),"netflix");
            menu.add(1, Menu.NONE,getAdapterPosition(),"prime");
            menu.add(2, Menu.NONE,getAdapterPosition(),"disney");
        }
    }
}



