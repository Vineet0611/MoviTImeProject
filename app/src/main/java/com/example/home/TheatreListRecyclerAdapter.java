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


public class TheatreListRecyclerAdapter extends RecyclerView.Adapter<TheatreListRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<TheatreRecycler> theatreList;

    TheatreListRecyclerAdapter(Context context, ArrayList<TheatreRecycler> theatreList){
        this.context= context;
        this.theatreList = theatreList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.theatrelist_element, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tName.setText(theatreList.get(position).tName);
        Log.d("volleyholder ", "tname name ssuccess:");
        holder.language.setText(theatreList.get(position).language);
        Log.d("volleyholder ", "language ssuccess:");
        holder.screen.setText(theatreList.get(position).screen);
        Log.d("volleyholder ", "screen name ssuccess:");
        holder.time.setText(theatreList.get(position).time);
        Log.d("volleyholder ", "time ssuccess:");
        holder.showid.setText(theatreList.get(position).showid);
        Log.d("volleyholder ", "showid name ssuccess:");
        holder.tId.setText(theatreList.get(position).tId);
        Log.d("volleyholder ", "tid ssuccess:");
        holder.date.setText(theatreList.get(position).date);
        Log.d("volleyholder ", "date ssuccess:");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelecteSeat.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //-> Added this line to solve the theater list adapter bug
                intent.putExtra("theatre_name", theatreList.get(holder.getLayoutPosition()).tName);
                intent.putExtra("show_language", theatreList.get(holder.getLayoutPosition()).language);
                intent.putExtra("screen_type", theatreList.get(holder.getLayoutPosition()).screen);
                intent.putExtra("show_time", theatreList.get(holder.getLayoutPosition()).time);
                intent.putExtra("theatre_id", theatreList.get(holder.getLayoutPosition()).tId);
                intent.putExtra("show_id", theatreList.get(holder.getLayoutPosition()).showid);
                intent.putExtra("date", theatreList.get(holder.getLayoutPosition()).date);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return theatreList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tName, language, screen, showid, time, tId, date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tName= itemView.findViewById(R.id.theatreName);
            language = itemView.findViewById(R.id.language);
            screen = itemView.findViewById(R.id.screenType);
            time= itemView.findViewById(R.id.time);
            showid = itemView.findViewById(R.id.showId);
            tId = itemView.findViewById(R.id.theatreId);
            date = itemView.findViewById(R.id.date);
        }
    }
}
