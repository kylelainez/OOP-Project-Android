package com.kylelainez.oop_project_v1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class info_layout extends Activity implements RecyclerViewAdapter.ItemClickListener{
    private TextView titleText, snippetText;
    private ImageView imageView;

    private static String title, snippet;
    RecyclerViewAdapter adapter;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mconditionRef = mRootRef.child("condition");

    ArrayList<String> animalNames = new ArrayList<>();
    Button asd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);
        titleText = findViewById(R.id.titleText);
        snippetText = findViewById(R.id.snippetText);
        imageView = findViewById(R.id.menu_image);
        asd = findViewById(R.id.asd);
        setValues();


        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");
        RecyclerView recyclerView = findViewById(R.id.menu_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, animalNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mconditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                animalNames.add(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        asd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mconditionRef.setValue("Testing");
            }
        });
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
        if (title.equals("Jollibee Pureza"))
            imageView.setImageResource(R.drawable.jollibee);
        else if (title.equals("chowking"))
            imageView.setImageResource(R.drawable.chowking);
//        else if (title.equals("KFC Pureza"))
//            imageView = R.drawable.kfc;
//        else if (title.equals("Dunkin Donut"))
//            imageView = R.drawable.dunkin;
//        else if (title.equals("Infinity"))
//            imageView = R.drawable.infinity;
//        else if (title.equals("Labahab ni Juan Laundry Shop"))
//            imageView = R.drawable.labahan_ni_juan;
//        else if (title.equals("7-Eleven"))
//            imageView = R.drawable.seven_eleven;
    }
}
