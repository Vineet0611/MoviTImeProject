package com.example.home;

import android.graphics.Color;
import android.graphics.Movie;
import android.nfc.Tag;
import android.nfc.tech.TagTechnology;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class TheatreListFragment extends BottomSheetDialogFragment {

    private static final String TAG = "TheatreListFragment";
    ArrayList<CalendarRecycler> calendarList =new ArrayList<>();
    RecyclerView calendarRecycler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theatre_list, container, false);

        calendarRecycler = (RecyclerView)view.findViewById(R.id.calendarRecycler);
        calendarRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
//        CalendarView calendar = requireActivity().findViewById(R.id.calendar);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCalendarRecycler();
    }

        public void setCalendarRecycler(){

            LocalDate localDate = LocalDate.now();
            LocalDate t =  localDate.minusDays(1);
            for(int i =1 ; i<=15; i++){
                LocalDate date =  t.plusDays(i);
                LocalDate dateparse = LocalDate.parse(""+ date);
                DayOfWeek dayOfWeek = dateparse.getDayOfWeek();
                String s =  dayOfWeek.toString().substring(0, 3);
                int dayOfMonth = dateparse.getDayOfMonth();
                String ss = Integer.toString(dayOfMonth);
                Log.d("calendar" ,s + ss);
                CalendarRecycler calendarRecycler = new CalendarRecycler(s, ss);
                calendarList.add(calendarRecycler);
                Log.d("calendar" ,"List Set");
            }

            CalendarRecyclerAdapter fadapter = new CalendarRecyclerAdapter(getActivity(), calendarList);
            calendarRecycler.setAdapter(fadapter);
            Log.d("calendar ", "set adapter ");

        }
}