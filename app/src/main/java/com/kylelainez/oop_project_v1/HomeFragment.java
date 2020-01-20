package com.kylelainez.oop_project_v1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    ViewPager viewPager;
    CustomSwipeAdapter customSwipeAdapter;
    private Timer timer;
    private int current_position = 0;
    private View view;
    private TextView name,wallet,contactNum;
    private static final String TAG = "HomeFragment";
    private String fName, lName, mobileNumber, email,wallets,contact;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        customSwipeAdapter = new CustomSwipeAdapter(getActivity());
        viewPager.setAdapter(customSwipeAdapter);
        name = view.findViewById(R.id.name_profile_home);

        createSlideShow();

        wallet = view.findViewById(R.id.credit_points);
        contactNum = view.findViewById(R.id.phone_number);

        Intent intent = getActivity().getIntent();
        email = intent.getStringExtra(LoginActivity.EXTRA_EMAIL);
        mobileNumber = intent.getStringExtra(LoginActivity.EXTRA_MOBILE);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        firebaseFirestore.collection("UserAuth").document(email).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>(){
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        contact = documentSnapshot.getString("MobileNumber");
                        wallets = documentSnapshot.getLong("Wallet").toString();
                        fName = documentSnapshot.getString("FirstName");
                        lName = documentSnapshot.getString("LastName");
                        wallet.setText(wallets);
                        contactNum.setText(contact);
                        name.setText(fName + " " + lName);


                    }
                });
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

//    private void sliderAdapter() {
//        viewPager = view.findViewById(R.id.viewpager);
//        customSwipeAdapter = new CustomSwipeAdapter(getActivity());
//
//        viewPager.setAdapter(customSwipeAdapter);
//    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        viewPager = view.findViewById(R.id.view_pager);
//        customSwipeAdapter = new CustomSwipeAdapter(getFrag);
//        viewPager.setAdapter(customSwipeAdapter);
//        createSlideShow();
//    }
}
