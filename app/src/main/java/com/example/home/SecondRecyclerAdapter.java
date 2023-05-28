package com.example.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Objects;


public class SecondRecyclerAdapter extends RecyclerView.Adapter<SecondRecyclerAdapter.ViewHolder> {
    Context context;
    String location;
    ArrayList<SecondRecycler> arrSecond;

    SecondRecyclerAdapter(Context context, ArrayList<SecondRecycler> arrSecond){
        this.context= context;
        this.arrSecond = arrSecond;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.movieposter_layout, parent, false);
        SharedPreferences prefLocation = view.getContext().getSharedPreferences("location", MODE_PRIVATE);
        location = prefLocation.getString("city", "Select City");
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(!Objects.equals(arrSecond.get(position).img, "Image Path not available")) {
            Glide.with(context)
                    .load(arrSecond.get(position).img)
                    .placeholder(R.drawable.no_image)
                    .into(holder.movieimg);
        Log.d("volleyholder ", "Third glide img success: ");
        }else{
        holder.movieimg.setBackgroundResource(R.drawable.no_image);
        }
//      holder.movieimg.setImageResource(arrFirst.get(position).img);
        holder.moviename.setText(arrSecond.get(position).mName);
        Log.d("volleyholder ", "Third movie name success: ");
        holder.moviegenre.setText(arrSecond.get(position).mGenre);
        Log.d("volleyholder ", "Third genre success: ");
  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Objects.equals(location, "Select City")){
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra("movie_name", arrSecond.get(holder.getLayoutPosition()).mName);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(view.getContext(), "Please select a city", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrSecond.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movieimg;
        TextView moviename, moviegenre;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            movieimg = itemView.findViewById(R.id.movieimg);
            moviename = itemView.findViewById(R.id.moviename);
            moviegenre = itemView.findViewById(R.id.moviegenre);
        }
    }
}
