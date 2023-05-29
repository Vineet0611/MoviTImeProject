package com.example.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import java.util.Objects;


public class CrewRecyclerAdapter extends RecyclerView.Adapter<CrewRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<CrewRecycler> arrCrew;

    CrewRecyclerAdapter(Context context, ArrayList<CrewRecycler> arrCrew){
        this.context= context;
        this.arrCrew = arrCrew;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cast_crew_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.d("movie", "Crew viewholder success: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(!Objects.equals(arrCrew.get(position).cImg, "Image not available")) {
            Glide.with(context)
                    .load(arrCrew.get(position).cImg)
                    .placeholder(R.drawable.no_user_img)
                    .into(holder.crewimg);

            Log.d("movie", "Crew glide img success: ");
        }else{
            holder.crewimg.setBackgroundResource(R.drawable.no_user_img);
        }
        holder.crewname.setText(arrCrew.get(position).cName);
        Log.d("movie", "Cast name success: ");
        holder.crewrole.setText(arrCrew.get(position).cRole);
        Log.d("movie", "Cast role success: ");
    }

    @Override
    public int getItemCount() {
        return arrCrew.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView crewimg;
        TextView crewname, crewrole;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            crewimg = itemView.findViewById(R.id.charImg);
            crewname = itemView.findViewById(R.id.charName);
            crewrole = itemView.findViewById(R.id.charRole);
        }
    }
}

