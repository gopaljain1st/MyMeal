package com.example.mymeal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
   public DatabaseHelper(Context context)
    {
        super(context,"MyMeal.sqlite",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
          String sql="create table cart(id integer primary key AUTOINCREMENT,food_item_id varchar(100),food_name varchar(100),food_price varchar(100),food_quantity integer default 1,image_url varchar(200))";
          sqLiteDatabase.execSQL(sql);
            sql="create table favourite(id integer primary key AUTOINCREMENT,food_item_id varchar(100),food_name varchar(100),food_price varchar(100),food_quantity integer default 1,image_url varchar(200))";
          sqLiteDatabase.execSQL(sql);
          //sql="create table order_history(id integer primary Key AUTOINCREMENT, )"
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
