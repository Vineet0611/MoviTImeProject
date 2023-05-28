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


public class ThirdRecyclerAdapter extends RecyclerView.Adapter<ThirdRecyclerAdapter.ViewHolder> {
    Context context;
    String location;
    ArrayList<ThirdRecycler> arrThird;

    ThirdRecyclerAdapter(Context context, ArrayList<ThirdRecycler>arrThird){
        this.context= context;
        this.arrThird = arrThird;
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
        if(!Objects.equals(arrThird.get(position).img, "Image Path not available")) {
            Glide.with(context)
                    .load(arrThird.get(position).img)
                    .placeholder(R.drawable.no_image)
                    .into(holder.movieimg);
            Log.d("volleyholder ", "Third glide img ssuccess: ");
        }else{
            holder.movieimg.setBackgroundResource(R.drawable.no_image);
        }
//      holder.movieimg.setImageResource(arrFirst.get(position).img);
        holder.moviename.setText(arrThird.get(position).mName);
        Log.d("volleyholder ", "Second movie name ssuccess: ");
        holder.moviegenre.setText(arrThird.get(position).mGenre);
        Log.d("volleyholder ", "Second genre ssuccess: ");
   holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Objects.equals(location, "Select City")){
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra("movie_name", arrThird.get(holder.getLayoutPosition()).mName);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(view.getContext(), "Please select a city", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrThird.size();
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

