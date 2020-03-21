package com.example.mymeal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mymeal.Adapter.FavouriteAdapter;
import com.example.mymeal.Bean.Cart;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity
{
    RecyclerView rvFavourite;
    ArrayList<Cart> al;
    RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite);
        rvFavourite=findViewById(R.id.rvFavourite);
        DatabaseHelper helper=new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase=helper.getReadableDatabase();
        Cursor c=sqLiteDatabase.rawQuery("select * from favourite",null);
        al=new ArrayList<>();
        while(c.moveToNext())
        {
            Cart cart=new Cart(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getInt(4),c.getString(5));
            al.add(cart);
        }
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
        adapter=new FavouriteAdapter(this,al);
        rvFavourite.setLayoutManager(manager);
        rvFavourite.setAdapter(adapter);
        sqLiteDatabase.close();
    }
}
