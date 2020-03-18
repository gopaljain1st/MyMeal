package com.example.mymeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymeal.Bean.User;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingIn extends AppCompatActivity
{
    private static final int MY_REQUEST_CODE =1008 ;
    EditText etMobile,etPassword;
    TextView tvForgetPassword;
    Button btnSingIn;
    SharedPreferences sp=null;
    List<AuthUI.IdpConfig> providers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        initComponent();
        ActionBar ab = getSupportActionBar();
        // ab.setBackgroundDrawable(new ColorDrawable(getColor(R.color.dark)));
        sp = getSharedPreferences("user",MODE_PRIVATE);
        btnSingIn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view) {
                boolean flag = true;
                if (etMobile.getText().toString().length() == 0) {
                    etMobile.setError("Mobile No Required");
                    flag = false;
                }
                if (etPassword.getText().toString().length() == 0) {
                    etPassword.setError("Password Required");
                    flag = false;
                }
                    Pattern p = Pattern.compile("(0|91)?[6789][0-9]{9}");
                    Matcher m = p.matcher(etMobile.getText().toString().trim());

                    Pattern p1 = Pattern.compile("[0-9a-zA-Z@!#$%&^*?.+_*/]{4,12}");
                    Matcher m1 = p1.matcher(etPassword.getText().toString().trim());
                    if ((m.find() && m.group().equals(etMobile.getText().toString().trim())) && (m1.find() && m1.group().equals(etPassword.getText().toString().trim())))
                    {
                        final ProgressDialog pd = new ProgressDialog(SingIn.this);
                        pd.setMessage("Authenticating...");
                        pd.show();

                        FirebaseDatabase database=FirebaseDatabase.getInstance();
                        DatabaseReference reference=database.getReference("user");
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                pd.dismiss();
                                if (dataSnapshot.child(etMobile.getText().toString()).exists()) {
                                    User user = dataSnapshot.child(etMobile.getText().toString()).getValue(User.class);
                                    if (user.getPassword().equals(etPassword.getText().toString()))
                                    {
                                        Toast.makeText(SingIn.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                                        SharedPreferences.Editor editor=sp.edit();
                                        editor.putString("mobileNo",dataSnapshot.getKey());
                                        editor.putString("name",user.getName());
                                        editor.putString("emailId",user.getEmail());
                                        editor.putString("password",user.getPassword());
                                        editor.commit();
                                        Intent in=new Intent(SingIn.this,Home.class);
                                        startActivity(in);
                                        finish();
                                    }
                                    else
                                        Toast.makeText(SingIn.this, "Failed", Toast.LENGTH_SHORT).show();

                                }
                                else Toast.makeText(SingIn.this, "User Not Exist", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    else if (!(m.find() && m.group().equals(etMobile.getText().toString().trim())))
                        etMobile.setError("Inavlid Mobile No or Password");

                    else if(!(m1.find() && m1.group().equals(etPassword.getText().toString().trim())))
                        etPassword.setError("Invalid Mobile No or Password");

            }
        });
        providers= Arrays.asList(
          new AuthUI.IdpConfig.EmailBuilder().build(),
          new AuthUI.IdpConfig.FacebookBuilder().build(),
          new AuthUI.IdpConfig.PhoneBuilder().build(),
          new AuthUI.IdpConfig.GoogleBuilder().build()
        );
        showSingInOpption();
    }
    private void showSingInOpption()
    {
     startActivityForResult(

        AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).setTheme(R.style.MyTheme).build(),MY_REQUEST_CODE
      );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MY_REQUEST_CODE)
        {
            IdpResponse response =IdpResponse.fromResultIntent(data);
            if(resultCode==RESULT_OK)
            {
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initComponent()
    {
        etMobile=findViewById(R.id.etMobile);
        etPassword=findViewById(R.id.etPassword);
        tvForgetPassword=findViewById(R.id.tvForgetPassword);
        btnSingIn=findViewById(R.id.btnsingin);
    }
}
