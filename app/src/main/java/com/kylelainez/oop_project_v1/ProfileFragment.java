package com.kylelainez.oop_project_v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {
    private TextView mobile, emailAdd, wallet,name;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String email ,fname,lname,fullname,contact, wallets;
    private int walletValue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.profile_fragment, container, false);
        ImageButton topup = view.findViewById(R.id.topup_button);
        ImageButton editProfile = view.findViewById(R.id.edit_profile_button);
        email = user.getEmail();
        mobile = view.findViewById(R.id.phone_number);
        emailAdd = view.findViewById(R.id.email);
        wallet = view.findViewById(R.id.credit_points);
        name = view.findViewById(R.id.name_profile_home);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();



        firebaseFirestore.collection("UserAuth").document(email).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>(){
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                       fname = documentSnapshot.getString("FirstName");
                       lname = documentSnapshot.getString("LastName");
                       contact = documentSnapshot.getString("MobileNumber");
                       wallets = documentSnapshot.getLong("Wallet").toString();
                       fullname = fname + " " + lname;
                       name.setText(fullname);
                       mobile.setText(contact);
                       emailAdd.setText(email);
                       wallet.setText(wallets);
                       if (wallets!=null && !wallets.isEmpty()){
                           view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                       }

                    }
                });


        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddWallet.class);
                startActivity(intent);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfile.class );
                startActivity(intent);
            }
        });
        return view;
    }
}
