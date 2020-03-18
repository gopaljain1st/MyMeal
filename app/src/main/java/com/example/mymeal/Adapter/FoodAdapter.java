package com.example.mymeal.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.example.mymeal.Bean.Food;
import com.example.mymeal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>
{
     Context context;
     ArrayList<Food>al;
     int count=0;

    public FoodAdapter(Context context, ArrayList<Food> al)
    {
        this.context = context;
        this.al = al;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View v= LayoutInflater.from(context).inflate(R.layout.foodlist,viewGroup,false);
        return new FoodViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodViewHolder viewFoodHolder, int i)
    {

        Food f=al.get(i);
        Picasso.with(context).load(f.getImage()).into(viewFoodHolder.foodIamge);
        viewFoodHolder.foodName.setText(f.getName());
        viewFoodHolder.foodPrice.setText(f.getPrice()+"/-");
        viewFoodHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                viewFoodHolder.qty.setText(""+count);
            }
        });
        viewFoodHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(count>0)
                count--;
                viewFoodHolder.qty.setText(""+count);
            }
        });
     }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder
    {
        ImageView foodIamge;
        TextView foodName,foodPrice;
        ImageView add,remove;
        TextView qty;
        FoodViewHolder(View v)
        {
            super(v);
           foodIamge=v.findViewById(R.id.FoodImage);
           foodName=v.findViewById(R.id.FoodName);
           foodPrice=v.findViewById(R.id.FoodPrice);
           add=v.findViewById(R.id.add);
           remove=v.findViewById(R.id.remove);
           qty=v.findViewById(R.id.qty);
        }
    }
}
