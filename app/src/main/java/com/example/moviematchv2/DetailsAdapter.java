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

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder> {
    private Context context;
    private List<Movie> moviesList;
    int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public DetailsAdapter(Context context, List<Movie> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public DetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.details_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsAdapter.MyViewHolder holder, int position) {
        holder.title.setText(moviesList.get(position).getTitle());
        String year = String.valueOf((moviesList.get(position).getYear()));
        holder.year.setText(year);
        String runtime = String.valueOf((moviesList.get(position).getRuntime()));
        holder.runtime.setText(runtime);
        holder.overview.setText(moviesList.get(position).getOverview());
        holder.language.setText(moviesList.get(position).getOriginalLanguage());
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
        TextView year;
        TextView runtime;
        TextView overview;
        TextView language;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            poster = itemView.findViewById(R.id.image);
            year = itemView.findViewById(R.id.year);
            runtime = itemView.findViewById(R.id.runtime);
            overview = itemView.findViewById(R.id.overview);
            language = itemView.findViewById(R.id.language);

        }
    }
}
