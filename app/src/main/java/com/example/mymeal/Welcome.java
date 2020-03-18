package com.example.mymeal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;

import java.util.List;

public class Welcome extends AppCompatActivity
{

    Button btnSingin,btnSingup;
    SharedPreferences sp=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        btnSingup=findViewById(R.id.signup);
        btnSingin=findViewById(R.id.singin);
        btnSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Welcome.this,SingUp.class);
                startActivity(in);
            }
        });
        btnSingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Welcome.this,SingIn.class);
                startActivity(in);
            }
        });

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        sp=getSharedPreferences("user",MODE_PRIVATE);
        String id=sp.getString("mobileNo","0");
        if(!id.equals("0"))
        {
            Intent in=new Intent(this,Home.class);
            startActivity(in);
            finish();
        }
    }
}
