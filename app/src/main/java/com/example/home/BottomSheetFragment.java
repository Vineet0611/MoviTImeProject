package com.example.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomSheetFragment extends BottomSheetDialogFragment {

    ImageButton ahemdabad, banglore, chandigarh, chennai, delhi, hyderabad, kolkata, mumbai,pune;
    TextView location;


    public BottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        location = getActivity().findViewById(R.id.location);

        ahemdabad = (ImageButton) view.findViewById(R.id.ahamdabad);
        banglore = (ImageButton) view.findViewById(R.id.banglore);
        chandigarh = (ImageButton) view.findViewById(R.id.chandigarh);
        chennai = (ImageButton) view.findViewById(R.id.chennai);
        delhi = (ImageButton) view.findViewById(R.id.delhi);
        hyderabad = (ImageButton) view.findViewById(R.id.hyderabad);
        mumbai = (ImageButton) view.findViewById(R.id.mumbai);
        pune = (ImageButton) view.findViewById(R.id.pune);
        kolkata = (ImageButton) view.findViewById(R.id.kolkata);

        ahemdabad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefLocation = getActivity().getSharedPreferences("location", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefLocation.edit();
                prefEditor.putString("city","Ahemdabad");
                prefEditor.apply();
                dismiss();
                location.setText("Ahemdabad");
            }
        });
        banglore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefLocation = getActivity().getSharedPreferences("location", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefLocation.edit();
                prefEditor.putString("city","Banglore");
                prefEditor.apply();
                dismiss();
                location.setText("Banglore");
            }
        });
        chandigarh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefLocation = getActivity().getSharedPreferences("location", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefLocation.edit();
                prefEditor.putString("city","Chandigarh");
                prefEditor.apply();
                dismiss();
                location.setText("Chandigarh");
            }
        });
        chennai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefLocation = getActivity().getSharedPreferences("location", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefLocation.edit();
                prefEditor.putString("city","Chennai");
                prefEditor.apply();
                dismiss();
                location.setText("Chennai");
            }
        });
        delhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefLocation = getActivity().getSharedPreferences("location", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefLocation.edit();
                prefEditor.putString("city","Delhi");
                prefEditor.apply();
                dismiss();
                location.setText("Delhi");
            }
        });
        hyderabad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefLocation = getActivity().getSharedPreferences("location", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefLocation.edit();
                prefEditor.putString("city","Hyderabad");
                prefEditor.apply();
                dismiss();
                location.setText("Hyderabad");
            }
        });
        kolkata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefLocation = getActivity().getSharedPreferences("location", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefLocation.edit();
                prefEditor.putString("city","Kolkata");
                prefEditor.apply();
                dismiss();
                location.setText("Kolkata");
            }
        });
        mumbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefLocation = getActivity().getSharedPreferences("location", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefLocation.edit();
                prefEditor.putString("city","Mumbai");
                prefEditor.apply();
                dismiss();
                location.setText("Mumbai");
            }
        });
        pune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefLocation = getActivity().getSharedPreferences("location", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefLocation.edit();
                prefEditor.putString("city","Pune");
                prefEditor.apply();
                dismiss();
                location.setText("Pune");
            }
        });


        return view;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }



}