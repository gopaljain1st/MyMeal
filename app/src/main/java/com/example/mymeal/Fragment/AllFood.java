package com.example.mymeal.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mymeal.Adapter.CategoryAdapter;
import com.example.mymeal.Adapter.FoodAdapter;
import com.example.mymeal.Bean.Category;
import com.example.mymeal.Bean.Food;
import com.example.mymeal.Home;
import com.example.mymeal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllFood extends Fragment
{
    RecyclerView rv;
    LinearLayoutManager manager;
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>adapter;
    ArrayList<Food> al;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          View v=inflater.inflate(R.layout.list_fragment,container,false);
        rv=v.findViewById(R.id.rv);
        manager=new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("food");
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setTitle("Food List");
        pd.setMessage("Progressing....");
        pd.show();
        al=new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                pd.dismiss();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    String id=dataSnapshot.getKey();
                    String name= dataSnapshot1.child("Name").getValue().toString();
                    String imageUrl=dataSnapshot1.child("Image").getValue().toString();
                    String desc=dataSnapshot1.child("Description").getValue().toString();
                    String price=dataSnapshot1.child("Price").getValue().toString();
                    String menuId=dataSnapshot1.child("MenuId").getValue().toString();
                    String discount=dataSnapshot1.child("Discount").getValue().toString();
                    Food f=new Food(name,imageUrl,desc,price,discount,menuId,id);
                    al.add(f);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "In error", Toast.LENGTH_SHORT).show();
            }
        });
        adapter=new FoodAdapter(getContext(),al);
        rv.setAdapter(adapter);
        return v;
    }
}
