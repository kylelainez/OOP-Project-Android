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
    private Marker restaurants[] = new Marker[5];
    private Marker internetCafe[] = new Marker[1];
    private Marker laundry[] = new Marker[1];
    private Marker convenienceStore[] = new Marker[1];
    private GoogleMap googleMap;
    private MarkerOptions asd = new MarkerOptions()
            .position(new LatLng(14.601379, 121.004622))
            .anchor(0.5f, 0.5f)
            .title("Jollibee Pureza")
            .snippet("Open 24 Hours");

    public MapLocations(String string, GoogleMap maps, boolean test) {
        googleMap = maps;

        if (string.equals("restaurants")) {
            googleMap.clear();
            restaurants[0] = googleMap.addMarker(asd);
            restaurants[1] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.601455, 121.004779))
                    .anchor(0.5f, 0.5f)
                    .title("chowking")
                    .snippet("Open 24 Hours"));

            restaurants[2] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.601409, 121.003948))
                    .anchor(0.5f, 0.5f)
                    .title("KFC Pureza")
                    .snippet("Open 24 Hours"));

            restaurants[3] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.601389, 121.003869))
                    .anchor(0.5f, 0.5f)
                    .title("Dunkin Donut")
                    .snippet("Open 24 Hours"));
        } else if (string.equals("internetCafe")) {
            googleMap.clear();
            internetCafe[0] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.598644, 121.005308))
                    .anchor(0.5f, 0.5f)
                    .title("Infinity")
                    .snippet("Open 24 Hours"));
        } else if (string.equals("laundry")) {
            googleMap.clear();
            laundry[0] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.601105, 121.004802))
                    .anchor(0.5f, 0.5f)
                    .title("Labahan Ni Juan Laundry Shop")
                    .snippet("Laundry service"));
        } else {
            googleMap.clear();
            convenienceStore[0] = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(14.600480, 121.004642))
                    .anchor(0.5f, 0.5f)
                    .title("7-Eleven")
                    .snippet("Open 24 Hours"));
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

        if (title.equals("Jollibee Pureza"))
            resID = R.drawable.jollibee;
        else if (title.equals("chowking"))
            resID = R.drawable.chowking;
//        else if (title.equals("KFC Pureza"))
//            resID = R.drawable.kfc;
//        else if (title.equals("Dunkin Donut"))
//            resID = R.drawable.dunkin;
//        else if (title.equals("Infinity"))
//            resID = R.drawable.infinity;
//        else if (title.equals("Labahab ni Juan Laundry Shop"))
//            resID = R.drawable.labahan_ni_juan;
//        else if (title.equals("7-Eleven"))
//            resID = R.drawable.seven_eleven;

        return resID;
    }
}
