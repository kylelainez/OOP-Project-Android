package com.kylelainez.oop_project_v1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private List<String> foodName;
    private List<Object> foodPrice;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    RecyclerViewAdapter(Context context, List<String> foodName,List<Object> foodPrice){
        this.mInflater = LayoutInflater.from(context);
        this.foodName = foodName;
        this.foodPrice = foodPrice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.menu_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String food = foodName.get(position);
        Object price = foodPrice.get(position);
        holder.menu_food.setText(food);
        String foodPrice = price.toString();
        holder.menu_price.setText("P" + foodPrice);
    }


    @Override
    public int getItemCount() {
        return foodName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView menu_food, menu_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menu_food = itemView.findViewById(R.id.menu_food);
            menu_price = itemView.findViewById(R.id.menu_price);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    String getItem(int id){
        return foodName.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener){
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener{
        void onItemClick(View view,int position);
    }
}


