package com.example.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

import java.util.Objects;


public class BottomSheetFragment extends BottomSheetDialogFragment {

    ImageButton ahemdabad, banglore, chandigarh, chennai, delhi, hyderabad, kolkata, mumbai,pune;
    TextView location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        location = requireActivity().findViewById(R.id.location);

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
//                selectedButton("ahemdabad");
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
//                selectedButton("banglore");
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
//                selectedButton("chandigarh");
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
//                selectedButton("chennai");
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
//                selectedButton("delhi");
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
//                selectedButton("hyderabad");
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
//                selectedButton("kolkata");
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
//                selectedButton("mumbai");
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
//                selectedButton("pune");
            }
        });
        return view;
    }

//    public void selectedButton(String button) {
//
//        ahemdabad.setBackgroundColor(R.drawable.background_white);
//        banglore.setBackgroundColor(R.drawable.background_white);
//        chandigarh.setBackgroundColor(R.drawable.background_white);
//        chennai.setBackgroundColor(R.drawable.background_white);
//        delhi.setBackgroundColor(R.drawable.background_white);
//        hyderabad.setBackgroundColor(R.drawable.background_white);
//        mumbai.setBackgroundColor(R.drawable.background_white);
//        kolkata.setBackgroundColor(R.drawable.background_white);
//        pune.setBackgroundColor(R.drawable.background_white);
//
//
//
//        if (button.equals("ahemdabad")) {
//            ahemdabad.setBackgroundColor(R.drawable.background_colored);
//        }
//        else if (button.equals("banglore")) {
//            banglore.setBackgroundColor(R.drawable.background_colored);
//        }
//        else if (button.equals("chandigarh")) {
//            banglore.setBackgroundColor(R.drawable.background_colored);
//        }
//        else if (button.equals("chennai")) {
//            banglore.setBackgroundColor(R.drawable.background_colored);
//        }
//        else if (button.equals("delhi")) {
//            banglore.setBackgroundColor(R.drawable.background_colored);
//        }
//        else if (button.equals("hyderabad")) {
//            banglore.setBackgroundColor(R.drawable.background_colored);
//        }
//        else if (button.equals("mumbai")) {
//            banglore.setBackgroundColor(R.drawable.background_colored);
//        }
//        else if (button.equals("pune")) {
//            banglore.setBackgroundColor(R.drawable.background_colored);
//        }
//        else if (button.equals("kolkata")) {
//            banglore.setBackgroundColor(R.drawable.background_colored);
//        }
//
//    }



}