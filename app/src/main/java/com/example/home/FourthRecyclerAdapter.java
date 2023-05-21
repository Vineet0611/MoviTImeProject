package com.example.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class FourthRecyclerAdapter extends RecyclerView.Adapter<FourthRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<FourthRecycler> arrFourth;

    FourthRecyclerAdapter(Context context, ArrayList<FourthRecycler> arrFourth){
        this.context= context;
        this.arrFourth = arrFourth;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.movieposter_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(arrFourth.get(position).img != null) {
            Glide.with(context)
                    .load(arrFourth.get(position).img)
                    .placeholder(R.drawable.no_image)
                    .into(holder.movieimg);
            Log.d("volleyholder ", "Fourth glide img ssuccess: ");
        }else{
            holder.movieimg.setBackgroundResource(R.drawable.no_image);
        }
//      holder.movieimg.setImageResource(arrFirst.get(position).img);
        holder.moviename.setText(arrFourth.get(position).mName);
        Log.d("volleyholder ", "Fourth movie name ssuccess: ");
        holder.moviegenre.setText(arrFourth.get(position).mGenre);
        Log.d("volleyholder ", "Fourth genre ssuccess: ");
    }

    @Override
    public int getItemCount() {
        return arrFourth.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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