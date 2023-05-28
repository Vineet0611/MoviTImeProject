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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Objects;


public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<CalendarRecycler>calendarList;

    CalendarRecyclerAdapter(Context context, ArrayList<CalendarRecycler> calendarList){
        this.context= context;
        this.calendarList = calendarList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.date_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.date.setText(calendarList.get(position).date);
        Log.d("calendar ", "date ssuccess: ");
        holder.day.setText(calendarList.get(position).day);
        Log.d("calendar ", "day ssuccess: ");
        holder.hdate.setText(calendarList.get(position).hiddenDate);
        Log.d("calendar ", "day ssuccess: ");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = calendarList.get(holder.getLayoutPosition()).hiddenDate;
                Toast.makeText(view.getContext(), date, Toast.LENGTH_SHORT).show();
//                holder.itemView.setBackgroundColor(R.drawable.background_colored);
            }
        });
    }

    @Override
    public int getItemCount() {
        return calendarList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView date, day, hdate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            date = itemView.findViewById(R.id.date);
            day = itemView.findViewById(R.id.day);
            hdate = itemView.findViewById(R.id.invisibledate);
        }
    }
}

