package com.example.mymeal.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.example.mymeal.Bean.Cart;
import com.example.mymeal.DatabaseHelper;
import com.example.mymeal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>
{
    Context context;
    ArrayList<Cart> al;

    public FavouriteAdapter(Context context, ArrayList<Cart> al)
    {
        this.context = context;
        this.al = al;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.favourite_list,viewGroup,false);
        return new FavouriteAdapter.FavouriteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder favouriteViewHolder, int i)
     {
        final Cart cart=al.get(i);
        final int pos=i;
        favouriteViewHolder.foodName.setText(cart.getFoodName());
        favouriteViewHolder.foodPrice.setText(""+cart.getFoodPrice()+"/-");
        favouriteViewHolder.btnAddToFavorite.setChecked(true);
        Picasso.with(context).load(cart.getImageUrl()).into(favouriteViewHolder.foodImage);

         favouriteViewHolder.btnAddToCart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    Toast.makeText(context, "Add to Cart", Toast.LENGTH_SHORT).show();
                    ContentValues values=new ContentValues();
                    values.put("food_item_id",cart.getFoodItemId());
                    values.put("food_name",cart.getFoodName());
                    values.put("food_price",cart.getFoodPrice());
                    values.put("image_url",cart.getImageUrl());
                    DatabaseHelper helper=new DatabaseHelper(context);
                    SQLiteDatabase db=helper.getWritableDatabase();
                    db.insert("cart",null,values);
                    db.close();
                }
                else
                {
                    DatabaseHelper helper=new DatabaseHelper(context);
                    SQLiteDatabase db=helper.getWritableDatabase();
                    db.delete("cart","food_item_id=?",new String[]{""+cart.getFoodItemId()});
                    db.close();
                }
            }
        });
        favouriteViewHolder.btnAddToFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b)
                {
                    DatabaseHelper helper=new DatabaseHelper(context);
                    SQLiteDatabase db=helper.getWritableDatabase();
                    db.delete("favourite","id=?",new String[]{""+cart.getId()});
                    Toast.makeText(context, "Item Removed From Favourite", Toast.LENGTH_SHORT).show();
                    db.close();
                    al.remove(pos);
                    notifyDataSetChanged();
                }
            }
        });
        favouriteViewHolder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DatabaseHelper helper=new DatabaseHelper(context);
                final SQLiteDatabase db=helper.getWritableDatabase();
                db.delete("favourite","id=?",new String[]{""+cart.getId()});
                al.remove(pos);
                db.close();
                notifyDataSetChanged();
                Toast.makeText(context, "Item Removed From Favourite", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class FavouriteViewHolder extends RecyclerView.ViewHolder
    {
        TextView foodName,foodPrice;
        ImageView close,foodImage;
        ToggleButton btnAddToCart,btnAddToFavorite;
        FavouriteViewHolder(View v)
        {
            super(v);
            foodImage=v.findViewById(R.id.foodImage);
            close=v.findViewById(R.id.close);
            foodPrice=v.findViewById(R.id.foodPrice);
            foodName=v.findViewById(R.id.foodName);
            btnAddToCart=v.findViewById(R.id.addToCart);
            btnAddToFavorite=v.findViewById(R.id.addToFavouirte);
        }
    }
}
