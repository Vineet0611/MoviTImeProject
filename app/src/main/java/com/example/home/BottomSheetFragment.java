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
    LinearLayout back_ahemdabad, back_banglore,  back_chandigarh,  back_chennai,  back_delhi,  back_hyderabad,  back_kolkata,  back_mumbai, back_pune;
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

        back_ahemdabad= (LinearLayout)view.findViewById(R.id.back_ahamdabad);
        back_banglore= (LinearLayout)view.findViewById(R.id.back_banglore);
        back_chennai= (LinearLayout)view.findViewById(R.id.back_chennai);
        back_chandigarh= (LinearLayout)view.findViewById(R.id.back_chandigarh);
        back_delhi= (LinearLayout)view.findViewById(R.id.back_delhi);
        back_hyderabad = (LinearLayout)view.findViewById(R.id.back_hyderabad);
        back_mumbai= (LinearLayout)view.findViewById(R.id.back_mumbai);
        back_pune= (LinearLayout)view.findViewById(R.id.back_pune);
        back_kolkata= (LinearLayout)view.findViewById(R.id.back_kolkata);


        ahemdabad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setText("Ahemdabad");
               selectedButton("ahemdabad");
            }
        });
        banglore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("Banglore");
                selectedButton("banglore");
            }
        });
        chandigarh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setText("Chandigarh");
               selectedButton("chandigarh");
            }
        });
        chennai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("Chennai");
                selectedButton("chennai");
            }
        });
        delhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setText("Delhi");
               selectedButton("delhi");
            }
        });
        hyderabad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("Hyderabad");
                selectedButton("hyderabad");
            }
        });
        kolkata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("Kolkata");
                selectedButton("kolkata");
            }
        });
        mumbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("Mumbai");
                selectedButton("mumbai");
            }
        });
        pune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setText("Pune");
               selectedButton("pune");
            }
        });
        return view;
    }

    private void setText(String loc){
        SharedPreferences prefLocation = getActivity().getSharedPreferences("location", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefLocation.edit();
        prefEditor.putString("city",loc);
        prefEditor.apply();
        dismiss();
        location.setText(loc);
    }


    public void selectedButton(String button) {

        ahemdabad.setBackgroundColor(R.drawable.background_white);
        back_ahemdabad.setBackgroundColor(R.drawable.background_white);
        banglore.setBackgroundColor(R.drawable.background_white);
        back_banglore.setBackgroundColor(R.drawable.background_white);
        chandigarh.setBackgroundColor(R.drawable.background_white);
        back_chandigarh.setBackgroundColor(R.drawable.background_white);
        chennai.setBackgroundColor(R.drawable.background_white);
        back_chennai.setBackgroundColor(R.drawable.background_white);
        delhi.setBackgroundColor(R.drawable.background_white);
        back_delhi.setBackgroundColor(R.drawable.background_white);
        hyderabad.setBackgroundColor(R.drawable.background_white);
        back_hyderabad.setBackgroundColor(R.drawable.background_white);
        mumbai.setBackgroundColor(R.drawable.background_white);
        back_mumbai.setBackgroundColor(R.drawable.background_white);
        kolkata.setBackgroundColor(R.drawable.background_white);
        back_kolkata.setBackgroundColor(R.drawable.background_white);
        pune.setBackgroundColor(R.drawable.background_white);
        back_pune.setBackgroundColor(R.drawable.background_white);



        if (button.equals("ahemdabad")) {
            ahemdabad.setBackgroundColor(R.drawable.background_colored);
            back_ahemdabad.setBackgroundColor(R.drawable.background_colored);
        }
        else if (button.equals("banglore")) {
            banglore.setBackgroundColor(R.drawable.background_colored);
            back_banglore.setBackgroundColor(R.drawable.background_colored);
        }
        else if (button.equals("chandigarh")) {
            chandigarh.setBackgroundColor(R.drawable.background_colored);
            back_chandigarh.setBackgroundColor(R.drawable.background_colored);
        }
        else if (button.equals("chennai")) {
            chennai.setBackgroundColor(R.drawable.background_colored);
            back_chennai.setBackgroundColor(R.drawable.background_colored);
        }
        else if (button.equals("delhi")) {
            delhi.setBackgroundColor(R.drawable.background_colored);
            back_delhi.setBackgroundColor(R.drawable.background_colored);
        }
        else if (button.equals("hyderabad")) {
            hyderabad.setBackgroundColor(R.drawable.background_colored);
            back_hyderabad.setBackgroundColor(R.drawable.background_colored);
        }
        else if (button.equals("mumbai")) {
            mumbai.setBackgroundColor(R.drawable.background_colored);
            back_mumbai.setBackgroundColor(R.drawable.background_colored);
        }
        else if (button.equals("pune")) {
            pune.setBackgroundColor(R.drawable.background_colored);
            back_pune.setBackgroundColor(R.drawable.background_colored);
        }
        else if (button.equals("kolkata")) {
            kolkata.setBackgroundColor(R.drawable.background_colored);
            back_kolkata.setBackgroundColor(R.drawable.background_colored);
        }

    }



}