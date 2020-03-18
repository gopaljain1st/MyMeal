package com.example.mymeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymeal.Bean.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingUp extends AppCompatActivity
{
    EditText etName,etPassword,etEmail,etMobile;
    Button btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        initComponent();
        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
                public void onClick(View view)
                {
                    boolean flag;
                    if(etName.getText().toString().length()==0)
                    {
                        etName.setError("Username Required");
                        flag=false;
                    }
                    if(etEmail.getText().toString().length()==0)
                    {
                        etEmail.setError("Email Required");
                        flag=false;
                    }
                    if(etMobile.getText().toString().length()==0)
                    {
                        etMobile.setError("Mobile Required");
                        flag=false;
                    }
                    if(etPassword.getText().toString().length()==0)
                    {
                        etPassword.setError("Password Required");
                        flag=false;
                    }

                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    final DatabaseReference reference=database.getReference("user");
                    final ProgressDialog pd = new ProgressDialog(SingUp.this);
                    pd.setMessage("Authenticating...");
                    pd.show();
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                           pd.dismiss();
                           if(dataSnapshot.child(etMobile.getText().toString()).exists())
                           {
                               Toast.makeText(SingUp.this, "User name already register", Toast.LENGTH_SHORT).show();
                           }
                           else
                           {
                               pd.dismiss();
                               User user=new User(etEmail.getText().toString(),etName.getText().toString(),etPassword.getText().toString());
                               reference.child(etMobile.getText().toString()).setValue(user);
                               Toast.makeText(SingUp.this, "User register successfully", Toast.LENGTH_SHORT).show();
                               finish();
                           }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
        });


    }
    private void initComponent()
    {
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etMobile=findViewById(R.id.etMobile);
        btnRegister=findViewById(R.id.btnsingup);
    }
}
