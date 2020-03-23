package com.example.worldfamousremainder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
TextView tvb;
EditText e1,e2,e4,e5,e6;
Button b1;
ProgressBar p;
FirebaseAuth fAuth;
FirebaseFirestore fstore;
String userid,fullname,ph,username;
int phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        e1 =findViewById(R.id.editText);
        e2=findViewById(R.id.editText2);
        e4=findViewById(R.id.editText5);
        e5=findViewById(R.id.editText6);
        e6=findViewById(R.id.editText7);
        b1=findViewById(R.id.button);
        fAuth =FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        if(fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        p=findViewById(R.id.progressBar);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=e2.getText().toString().trim();
                String pass=e4.getText().toString();
                String cpass=e5.getText().toString();
                fullname=e1.getText().toString();
                ph=e6.getText().toString();
                //if(ph.length()==10){
                   // phone=Integer.parseInt(ph);
                //}
                //else{
                   // e6.setError("Enter valid Phone Number.");
                //}

                if(pass.equals(cpass)){
                    p.setVisibility(View.VISIBLE);
                    fAuth.createUserWithEmailAndPassword(username,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Register.this,"User Created",Toast.LENGTH_LONG).show();
                                userid=fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference=fstore.collection("users").document(userid);
                                Map<String,Object> user = new HashMap<>();
                                user.put("fname",fullname);
                                user.put("email",username);
                                user.put("phone",ph);
                                user.put("boards",0);
                                documentReference.set(user);
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(Register.this,"Error! Try Again"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                p.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(Register.this,"Password Does't Matched",Toast.LENGTH_LONG).show();

                }
            }
        });
        tvb=findViewById(R.id.textView6);
        tvb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
    }
}
