package com.example.mymeal.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.example.mymeal.Bean.Food;
import com.example.mymeal.DatabaseHelper;
import com.example.mymeal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>
{
     Context context;
     ArrayList<Food>al;

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
        final Food f=al.get(i);
        Picasso.with(context).load(f.getImage()).into(viewFoodHolder.foodIamge);
        viewFoodHolder.foodName.setText(f.getName());
        viewFoodHolder.foodPrice.setText(f.getPrice()+"/-");
        String id=f.getId();
        DatabaseHelper helper=new DatabaseHelper(context);
        SQLiteDatabase sqLiteDatabase=helper.getReadableDatabase();
        Log.w("food id : ",id);
        Cursor c=sqLiteDatabase.rawQuery("select * from favourite where food_item_id=?",new String[]{id});
        if(c.moveToNext())
        {
            if(c.getString(2).equals(f.getName()))
            {
                viewFoodHolder.addToFavourite.setChecked(true);
                Toast.makeText(context, ""+c.getString(2)+" : "+f.getName(), Toast.LENGTH_SHORT).show();
            }
            helper.close();
        }

        helper=new DatabaseHelper(context);
         sqLiteDatabase=helper.getReadableDatabase();
        c=sqLiteDatabase.rawQuery("select food_name from cart where food_item_id=?",new String[]{id});
        if(c.moveToNext())
        {
            if(c.getString(2).equals(f.getName()))
            {
                viewFoodHolder.addToCart.setChecked(true);
                Toast.makeText(context, ""+c.getString(2)+" : "+f.getName(), Toast.LENGTH_SHORT).show();
            }
            helper.close();
        }
        /*
        viewFoodHolder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                viewFoodHolder.addToFavourite.setBackgroundResource(R.drawable.addtocart_border2);
                ContentValues values=new ContentValues();
                values.put("food_item_id",f.getId());
                values.put("food_name",f.getName());
                values.put("food_price",f.getPrice());
                values.put("image_url",f.getImage());
                DatabaseHelper helper=new DatabaseHelper(context);
                SQLiteDatabase db=helper.getWritableDatabase();
                db.insert("cart",null,values);
                db.close();
            }
        });
*/
        viewFoodHolder.addToCart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    Toast.makeText(context, "Add to Cart", Toast.LENGTH_SHORT).show();
                    ContentValues values=new ContentValues();
                    values.put("food_item_id",f.getId());
                    values.put("food_name",f.getName());
                    values.put("food_price",f.getPrice());
                    values.put("image_url",f.getImage());
                    DatabaseHelper helper=new DatabaseHelper(context);
                    SQLiteDatabase db=helper.getWritableDatabase();
                    db.insert("cart",null,values);
                    db.close();
                }
                else
                {
                    DatabaseHelper helper=new DatabaseHelper(context);
                    SQLiteDatabase db=helper.getWritableDatabase();
                    db.delete("cart","food_item_id=?",new String[]{""+f.getId()});
                    db.close();
                }
            }
        });
        viewFoodHolder.addToFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    Toast.makeText(context, "Add To Favourite", Toast.LENGTH_SHORT).show();
                    ContentValues values=new ContentValues();
                    values.put("food_item_id",f.getId());
                    values.put("food_name",f.getName());
                    values.put("food_price",f.getPrice());
                    values.put("image_url",f.getImage());
                    DatabaseHelper helper=new DatabaseHelper(context);
                    SQLiteDatabase db=helper.getWritableDatabase();
                    db.insert("favourite",null,values);
                    db.close();
                }
                else
                {
                    DatabaseHelper helper=new DatabaseHelper(context);
                    SQLiteDatabase db=helper.getWritableDatabase();
                    db.delete("favourite","food_item_id=?",new String[]{""+f.getId()});
                    db.close();
                }
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
        ToggleButton addToFavourite,addToCart;
        FoodViewHolder(View v)
        {
            super(v);
           foodIamge=v.findViewById(R.id.FoodImage);
           foodName=v.findViewById(R.id.FoodName);
           foodPrice=v.findViewById(R.id.FoodPrice);
           addToCart=v.findViewById(R.id.addToCart);
           addToFavourite=v.findViewById(R.id.addToFavouirte);
        }
    }
}
