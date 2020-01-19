package com.kylelainez.oop_project_v1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapLocations {
    public int getRestaurants() {
        return restaurants.length;
    }

    private Marker restaurants[] = new Marker[5];
    private Marker internetCafe[] = new Marker[5];
    private Marker laundry[] = new Marker[5];
    private Marker convenienceStore[] = new Marker[5];
    private GoogleMap googleMap;
    private MarkerOptions asd = new MarkerOptions()
            .position(new LatLng(14.601379, 121.004622))
            .anchor(0.5f, 0.5f)
            .title("Jollibee")
            .snippet("Open 24 Hours");

//    public int getRestaurants(){
//        return restaurants.length;
//    }
    public MapLocations(){

    }
    public MapLocations(String string, GoogleMap maps, boolean test) {
        googleMap = maps;
        if (string.equals("restaurants")) {
            googleMap.clear();
            restaurants[0] = googleMap.addMarker(asd);
            restaurants[1] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.601455, 121.004779))
                    .anchor(0.5f, 0.5f)
                    .title("Chowking")
                    .snippet("Serving hot & fresh Chinese food straight to you!"));

            restaurants[2] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.601409, 121.003948))
                    .anchor(0.5f, 0.5f)
                    .title("KFC Pureza")
                    .snippet("Hungry? Enjoy your KFC!"));

            restaurants[3] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.601389, 121.003869))
                    .anchor(0.5f, 0.5f)
                    .title("Dunkin' Donuts")
                    .snippet("Hello, Guest! Welcome to Dunkin'!"));

            restaurants[4] = googleMap.addMarker((new MarkerOptions())
                    .position(new LatLng(14.601109, 121.004461))
                    .anchor(0.5f, 0.5f)
                    .title("Aling Banang")
                    .snippet("Taste the newly cook silog!"));



        } else if (string.equals("internetCafe")) {
            googleMap.clear();
            internetCafe[0] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.598644, 121.005308))
                    .anchor(0.5f, 0.5f)
                    .title("Infinity")
                    .snippet("Enjoy our high-speed internet here at Infinity!"));

            internetCafe[1] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.599959, 121.004814))
                    .anchor(0.5f, 0.5f)
                    .title("263 Computer Shop")
                    .snippet("Everyday, everynight play tight here at 263!"));

            internetCafe[2] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.598546, 121.005202))
                    .anchor(0.5f, 0.5f)
                    .title("ORB Internet Cafe")
                    .snippet("ORB fans play at ORB Cafe!"));

            internetCafe[3] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.601071, 121.004438))
                    .anchor(0.5f, 0.5f)
                    .title("MOR3LUCK Internet Cafe")
                    .snippet("Play 24/7 for more luck in life!"));

            internetCafe[4] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.600775, 121.004718))
                    .anchor(0.5f, 0.5f)
                    .title("Log-Me-In Internet Cafe")
                    .snippet("Non-stop playing, always log in here in Log-Me-In Cafe!"));



        } else if (string.equals("laundry")) {
            googleMap.clear();
            laundry[0] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.601105, 121.004802))
                    .anchor(0.5f, 0.5f)
                    .title("Labahan Ni Juan Laundry Shop")
                    .snippet("This is the best option for laundring your clothes!"));

            laundry[1] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.600947, 121.004489))
                    .anchor(0.5f, 0.5f)
                    .title("Laundry Dry Clean")
                    .snippet("Wash your clothes here 24/7"));

            laundry[2] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.600189, 121.004739))
                    .anchor(0.5f, 0.5f)
                    .title("Labada King")
                    .snippet("The King of all Laundry."));

            laundry[3] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.602158, 121.004199))
                    .anchor(0.5f, 0.5f)
                    .title("Laundryhaus")
                    .snippet("We care for your clothes here at Laundryhaus!"));

            laundry[4] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.601208, 121.006343))
                    .anchor(0.5f, 0.5f)
                    .title("Laundry Shop")
                    .snippet("People's Best Laundry Store!"));


        } else {
            googleMap.clear();
            convenienceStore[0] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.600480, 121.004642))
                    .anchor(0.5f, 0.5f)
                    .title("7-Eleven")
                    .snippet("One of the stores near PUP-CEA."));

            convenienceStore[1] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.598461, 121.005050))
                    .anchor(0.5f, 0.5f)
                    .title("Ajay's Puregold Mini-Mart")
                    .snippet("One of the stores near PUP-CEA."));

            convenienceStore[2] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.598697, 121.005787))
                    .anchor(0.5f, 0.5f)
                    .title("Williard Enterprise")
                    .snippet("One of the stores near PUP-CEA."));

            convenienceStore[3] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.601449, 121.005526))
                    .anchor(0.5f, 0.5f)
                    .title("Sampaloc Diamond Hardware")
                    .snippet("One of the stores near PUP-CEA."));

            convenienceStore[4] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.599006, 121.004656))
                    .anchor(0.5f, 0.5f)
                    .title("Easy Vape Shop Manila")
                    .snippet("One of the stores near PUP-CEA."));

        }
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static int getImage(String title){
        int resID = R.drawable.eat_button;



        if (title.equals("Jollibee"))
            resID = R.drawable.mp_jabe;
        else if (title.equals("Chowking"))
            resID = R.drawable.mp_chowking;
        else if (title.equals("KFC Pureza"))
            resID = R.drawable.mp_kfc;
        else if (title.equals("Dunkin' Donuts"))
            resID = R.drawable.mp_dd;
        else if (title.equals("Aling Banang"))
            resID = R.drawable.mp_ab;


            //CONVIENCE STORES

        else if (title.equals("7-Eleven"))
            resID = R.drawable.mp_711;
        else if (title.equals("Easy Vape Shop Manila"))
            resID = R.drawable.mp_vs;
        else if (title.equals("Ajay's Puregold Mini-Mart"))
            resID = R.drawable.mp_pg;
        else if (title.equals("Williard Enterprise"))
            resID = R.drawable.mp_ws;
        else if (title.equals("Sampaloc Diamond Hardware"))
            resID = R.drawable.mp_sd;

            // COMPUTER SHOPS

        else if (title.equals("Infinity"))
            resID = R.drawable.mp_i;
        else if (title.equals("ORB Internet Cafe"))
            resID = R.drawable.mp_orb;
        else if (title.equals("263 Computer Shop"))
            resID = R.drawable.mp_263;
        else if (title.equals("Log-Me-In Internet Cafe"))
            resID = R.drawable.mp_lmi;
        else if (title.equals("MOR3LUCK Internet Cafe"))
            resID = R.drawable.mp_ml;

            // OTHERS

        else if (title.equals("Labahan Ni Juan Laundry Shop"))
            resID = R.drawable.mp_laundry;
        else if (title.equals("Laundryhaus"))
            resID = R.drawable.mp_laundry;
        else if (title.equals("Laundry Dry Clean"))
            resID = R.drawable.mp_laundry;
        else if (title.equals("Labada King"))
            resID = R.drawable.mp_laundry;
        else if (title.equals("Laundry Shop"))
            resID = R.drawable.mp_laundry;

            // PUP

        else if (title.equals("PUP - CEA Building"))
            resID = R.drawable.ic_pup_logo;

        return resID;
    }
}
