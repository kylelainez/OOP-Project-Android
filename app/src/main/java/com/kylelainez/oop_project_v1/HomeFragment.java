package com.kylelainez.oop_project_v1;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    ViewPager viewPager;
    CustomSwipeAdapter customSwipeAdapter;
    private Timer timer;
    private int current_position = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_screen, container, false);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        customSwipeAdapter = new CustomSwipeAdapter(getActivity());
        viewPager.setAdapter(customSwipeAdapter);
        createSlideShow();
        return view;
    }

    private void createSlideShow(){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(current_position == Integer.MAX_VALUE){
                    current_position = 0;
                    viewPager.setCurrentItem(current_position++,true);
                }
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },250,2500);
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        viewPager = getView().findViewById(R.id.view_pager);
//        customSwipeAdapter = new CustomSwipeAdapter(getActivity());
//        viewPager.setAdapter(customSwipeAdapter);
//    }
}
