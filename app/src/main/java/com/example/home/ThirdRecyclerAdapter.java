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


public class ThirdRecyclerAdapter extends RecyclerView.Adapter<ThirdRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<ThirdRecycler> arrThird;

    ThirdRecyclerAdapter(Context context, ArrayList<ThirdRecycler>arrThird){
        this.context= context;
        this.arrThird = arrThird;
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
        if(arrThird.get(position).img != null) {
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
    }

    @Override
    public int getItemCount() {
        return arrThird.size();
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
