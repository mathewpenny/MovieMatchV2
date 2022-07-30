package com.example.moviematchv2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UsersViewHolder> {


    private Context context;
    public List<User> usersList;

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public UserAdapter(Context context, List<User> usersList) {
        this.context = context;
        this.usersList = usersList;
    }


    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.matches_item, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {

      //  holder.movieTitle.setText(usersList.get(position).getUserName());

/*
     Glide.with(context).
                load(moviesList.get(position)
                        .getPosterURLs().getOriginal())
                .into(holder.poster);
*/


        holder.userName.setText(usersList.get(position).getUserName());
        holder.userPhone.setText(usersList.get(position).getUserPhone());
        //holder.movieTitle.setText(usersList.get(position).getMovieTitle());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView movieTitle, userName, userPhone;
        ImageView posterImg;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            //movieTitle = itemView.findViewById(R.id.movieTitle);
            posterImg = itemView.findViewById(R.id.imageView);

            userName = itemView.findViewById(R.id.userName);
            userPhone = itemView.findViewById(R.id.userPhone);

        }
    }
}

