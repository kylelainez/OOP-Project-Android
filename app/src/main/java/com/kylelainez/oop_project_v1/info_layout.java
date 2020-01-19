package com.kylelainez.oop_project_v1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class info_layout extends Activity implements RecyclerViewAdapter.ItemClickListener{
    private TextView titleText, snippetText;
    private ImageView imageView;

    private static String title, snippet;
    RecyclerViewAdapter adapter;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mconditionRef = mRootRef.child("condition");
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    ArrayList<String> animalNames;
    ArrayList<Object> values;
    Map<String,Object> map = new HashMap<>();
    private static final String TAG = "info_layout";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout_gia);
        titleText = findViewById(R.id.titleText);
        snippetText = findViewById(R.id.snippetText);
        imageView = findViewById(R.id.menu_image);
        setValues();
        firebaseFirestore.collection("Menu").document(title).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d(TAG, "onSuccess: " + title);
                        map = documentSnapshot.getData();
                        animalNames = new ArrayList<>(map.keySet());
                        values = new ArrayList<>(map.values());
                        RecyclerView recyclerView = findViewById(R.id.menu_recycler);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adapter = new RecyclerViewAdapter(getApplicationContext(),animalNames,values);
                        recyclerView.setAdapter(adapter);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    }
                });


    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        mconditionRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                String text = dataSnapshot.getValue(String.class);
//                animalNames.add(text);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public static void getMarkerInfo(String title, String snippet){
       info_layout.title = title;
       info_layout.snippet = snippet;
    }
    public void setValues(){
        titleText.setText(title);
        snippetText.setText(snippet);

        // Restaurants

        if (title.equals("Jollibee"))
            imageView.setImageResource(R.drawable.il_jb);
        else if (title.equals("Chowking"))
            imageView.setImageResource(R.drawable.il_ck);
        else if (title.equals("Aling Banang"))
            imageView.setImageResource(R.drawable.il_ab);
        else if (title.equals("KFC Pureza"))
            imageView.setImageResource(R.drawable.il_kfc);

        else if (title.equals("Dunkin' Donuts"))
            imageView.setImageResource(R.drawable.il_dd);

        // Stores

        else if (title.equals("7-Eleven"))
            imageView.setImageResource(R.drawable.il_711);
        else if (title.equals("Easy Vape Shop Manila"))
            imageView.setImageResource(R.drawable.il_vs);
        else if (title.equals("Ajay's Puregold Mini-mart"))
            imageView.setImageResource(R.drawable.il_pg);
        else if (title.equals("Williard Enterprise"))
            imageView.setImageResource(R.drawable.il_we);
        else if (title.equals("Sampaloc Diamond Hardware"))
            imageView.setImageResource(R.drawable.il_sdh);

        //Internet shop

        else if (title.equals("MOR3LUCK Internet Cafe"))
            imageView.setImageResource(R.drawable.il_cs);
        else if (title.equals("Log-Me-In Internet Cafe"))
            imageView.setImageResource(R.drawable.il_cs);
        else if (title.equals("263 Computer Shop"))
            imageView.setImageResource(R.drawable.il_cs);
        else if (title.equals("Infinity"))
            imageView.setImageResource(R.drawable.il_cs);
        else if (title.equals("ORB Internet Cafe"))
            imageView.setImageResource(R.drawable.il_cs);

        //Laundry

        else if (title.equals("Laundry Shop"))
            imageView.setImageResource(R.drawable.il_laundry);
        else if (title.equals("Labahan ni Juan Laundry Shop"))
            imageView.setImageResource(R.drawable.il_laundry);
        else if (title.equals("Labada King"))
            imageView.setImageResource(R.drawable.il_laundry);
        else if (title.equals("Laundry Dry Clean"))
            imageView.setImageResource(R.drawable.il_laundry);
        else if (title.equals("Laundryhaus"))
            imageView.setImageResource(R.drawable.il_laundry);


    }
}
