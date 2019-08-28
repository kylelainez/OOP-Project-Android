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
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    ViewPager viewPager;
    CustomSwipeAdapter customSwipeAdapter;
    private Timer timer;
    private int current_position = 0;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_screen, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        customSwipeAdapter = new CustomSwipeAdapter(getActivity());
        viewPager.setAdapter(customSwipeAdapter);
        view = inflater.inflate(R.layout.home_screen, container, false);

        if (getActivity() != null) {
            sliderAdapter();
        }
        createSlideShow();
        return view;
    }
//    private PagerAdapter buildAdapter() {
//        return(new (getActivity(), getChildFragmentManager()));
//    }

    private void createSlideShow() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(current_position == Integer.MAX_VALUE)
                    current_position = 0;
                    viewPager.setCurrentItem(current_position++,true);
                if (current_position == Integer.MAX_VALUE) {
                    current_position = 0;
                    viewPager.setCurrentItem(current_position++, true);
                }
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 250, 2500);
    }

    private void sliderAdapter() {
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        customSwipeAdapter = new CustomSwipeAdapter(getActivity());

        viewPager.setAdapter(customSwipeAdapter);
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        viewPager = view.findViewById(R.id.view_pager);
//        customSwipeAdapter = new CustomSwipeAdapter(getFrag);
//        viewPager.setAdapter(customSwipeAdapter);
//        createSlideShow();
//    }
}
