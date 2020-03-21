package com.example.mymeal.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymeal.Bean.Food;
import com.example.mymeal.Fragment.AllFood;
import com.example.mymeal.Home;
import com.example.mymeal.R;
import com.example.mymeal.Bean.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>
{
    Context context;
    ArrayList<Category>al;
    RecyclerView rv;
    FragmentManager manager;
    ArrayList<Food>al2;
    FirebaseDatabase database;
    DatabaseReference reference;
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>adapter2;

    public CategoryAdapter(Context context, ArrayList<Category> al,FragmentManager fragmentManager,RecyclerView rv) {
        this.context = context;
        this.al = al;
        this.manager=fragmentManager;
        this.rv=rv;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View v= LayoutInflater.from(context).inflate(R.layout.category_list,viewGroup,false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i)
    {
       final Category c=al.get(i);
       final String categoryId=c.getId();
        Picasso.with(context).load(c.getImageUrl()).into(categoryViewHolder.iv);
        categoryViewHolder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                database= FirebaseDatabase.getInstance();
                reference=database.getReference().child("food");
                final ProgressDialog pd=new ProgressDialog(context);
                pd.setTitle("Food List");
                pd.setMessage("Progressing....");
                pd.show();
                al2=new ArrayList<>();
                if(categoryId.equals("01")) {
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            pd.dismiss();
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                            {
                                String menuId = dataSnapshot1.child("MenuId").getValue().toString();
                                if(categoryId.equals(menuId))
                                {
                                    String id=dataSnapshot1.getKey();
                                    String name = dataSnapshot1.child("Name").getValue().toString();
                                    String imageUrl = dataSnapshot1.child("Image").getValue().toString();
                                    String desc = dataSnapshot1.child("Description").getValue().toString();
                                    String price = dataSnapshot1.child("Price").getValue().toString();
                                    String discount = dataSnapshot1.child("Discount").getValue().toString();
                                    Food f = new Food(name, imageUrl, desc, price, discount, menuId,id);
                                    al2.add(f);
                                }
                            }
                            adapter2.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(context, "In error", Toast.LENGTH_SHORT).show();
                        }

                    });
                }
                else{
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            pd.dismiss();
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                            {
                                String menuId = dataSnapshot1.child("MenuId").getValue().toString();
                                if(categoryId.equals(menuId))
                                {
                                    String id=dataSnapshot1.getKey();
                                    String name = dataSnapshot1.child("Name").getValue().toString();
                                    String imageUrl = dataSnapshot1.child("Image").getValue().toString();
                                    String desc = dataSnapshot1.child("Description").getValue().toString();
                                    String price = dataSnapshot1.child("Price").getValue().toString();
                                    String discount = dataSnapshot1.child("Discount").getValue().toString();
                                    Food f = new Food(name, imageUrl, desc, price, discount, menuId,id);
                                    al2.add(f);
                                }
                            }
                            adapter2.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(context, "In error", Toast.LENGTH_SHORT).show();
                        }

                    });
                }
                adapter2=new FoodAdapter(context,al2);
                rv.setAdapter(adapter2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

   public class CategoryViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iv;
        CategoryViewHolder(View v)
        {
            super(v);
            iv=v.findViewById(R.id.iv);
        }
    }
}
