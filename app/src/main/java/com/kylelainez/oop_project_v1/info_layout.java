package com.kylelainez.oop_project_v1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class info_layout extends Activity {
    private TextView titleText, snippetText;
    private ImageView imageView;

    private static String title, snippet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);
        titleText = findViewById(R.id.titleText);
        snippetText = findViewById(R.id.snippetText);
        imageView = findViewById(R.id.menu_image);
        setValues();
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
