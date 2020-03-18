package com.example.mymeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymeal.Adapter.CategoryAdapter;
import com.example.mymeal.Adapter.FoodAdapter;
import com.example.mymeal.Bean.Category;
import com.example.mymeal.Bean.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView rv,rv2;
    ArrayList<Category> al;
    SharedPreferences sp=null;
    ArrayList<Food>al2;
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>adapter;
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>adapter2;
    RecyclerView.LayoutManager manager,manager2;
    FirebaseDatabase database;
    DatabaseReference reference;
    FragmentManager fragmentManager;
    TextView profileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rv=findViewById(R.id.homeRv);
        rv2=findViewById(R.id.foodRv);
        sp=getSharedPreferences("user",MODE_PRIVATE);
        fragmentManager=getSupportFragmentManager();
        manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(manager);
        manager2=new LinearLayoutManager(this);
        rv2.setLayoutManager(manager2);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("category");
        al=new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    String id=dataSnapshot1.getKey();
                    String name= dataSnapshot1.child("name").getValue().toString();
                    String imageUrl=dataSnapshot1.child("image").getValue().toString();
                    Category c=new Category(imageUrl,name,id);
                    al.add(c);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Home.this, "In error", Toast.LENGTH_SHORT).show();
            }
        });
        adapter=new CategoryAdapter(this,al,fragmentManager,rv2);
        rv.setAdapter(adapter);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("food");
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Food List");
        pd.setMessage("Progressing....");
        pd.show();
        al2=new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                pd.dismiss();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    String name= dataSnapshot1.child("Name").getValue().toString();
                    String imageUrl=dataSnapshot1.child("Image").getValue().toString();
                    String desc=dataSnapshot1.child("Description").getValue().toString();
                    String price=dataSnapshot1.child("Price").getValue().toString();
                    String menuId=dataSnapshot1.child("MenuId").getValue().toString();
                    String discount=dataSnapshot1.child("Discount").getValue().toString();
                    Food f=new Food(name,imageUrl,desc,price,discount,menuId);
                    al2.add(f);
                }
                adapter2.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Home.this, "In error", Toast.LENGTH_SHORT).show();
            }
        });
        adapter2=new FoodAdapter(this,al2);
        rv2.setAdapter(adapter2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);
        profileName=headerView.findViewById(R.id.profileName);
        profileName.setText(sp.getString("name","Gopal").toUpperCase());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.order)
        {
            // Handle the camera action
        }
        else if (id == R.id.orderHistory)
        {

        }
        else if (id == R.id.logout)
        {
             SharedPreferences.Editor editor=sp.edit();
             editor.remove("mobileNo");
             editor.remove("emailId");
             editor.remove("name");
             editor.remove("password");
             editor.commit();
            Intent in=new Intent(this,Welcome.class);
            startActivity(in);
            finish();
        }
        else if (id == R.id.notification) {

        } else if (id == R.id.offerList) {

        } else if (id == R.id.contact) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
