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


public class SecondRecyclerAdapter extends RecyclerView.Adapter<SecondRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<SecondRecycler> arrSecond;

    SecondRecyclerAdapter(Context context, ArrayList<SecondRecycler> arrSecond){
        this.context= context;
        this.arrSecond = arrSecond;
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
        if(arrSecond.get(position).img != null) {
            Glide.with(context)
                    .load(arrSecond.get(position).img)
                    .placeholder(R.drawable.no_image)
                    .into(holder.movieimg);
        Log.d("volleyholder ", "Second glide img ssuccess: ");
        }else{
        holder.movieimg.setBackgroundResource(R.drawable.no_image);
        }
//      holder.movieimg.setImageResource(arrFirst.get(position).img);
        holder.moviename.setText(arrSecond.get(position).mName);
        Log.d("volleyholder ", "Second movie name ssuccess: ");
        holder.moviegenre.setText(arrSecond.get(position).mGenre);
        Log.d("volleyholder ", "Second genre ssuccess: ");
    }

    @Override
    public int getItemCount() {
        return arrSecond.size();
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
