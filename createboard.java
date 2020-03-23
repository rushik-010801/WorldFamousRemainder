package com.example.worldfamousremainder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class createboard extends AppCompatActivity {
    static Toolbar t;
    static Button b;
    static EditText e;
    static FirebaseAuth fAuth;
    static DocumentReference temp;
    static String userid,bname;
    static long num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createboard);
        t = findViewById(R.id.toolbar);
        t.setTitle("Create Board");
        setSupportActionBar(t);
        e = findViewById(R.id.editText3);
        bname = e.getText().toString();
        b = findViewById(R.id.button2);
        fAuth = FirebaseAuth.getInstance();
        userid = fAuth.getCurrentUser().getUid();
        clicked();
    }
    public void clicked(){
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference temp = FirebaseFirestore.getInstance().collection("users").document(userid);
                temp.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        num = (long) documentSnapshot.get("boards");
                    }
                });
                temp.update("boards", num + 1);
                Map<String, Object> user1 = new HashMap<>();
                user1.put("board" + num, e.getText().toString());
                temp.set(user1, SetOptions.merge());


            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.example2,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1:
                DocumentReference good = FirebaseFirestore.getInstance().collection("users").document(userid).collection("Boardc").document("dummy");
                Map<String,Object> work=new HashMap<>();
                work.put("Dummy data","dummy data");
                good.set(work);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                return true;

            case R.id.item2:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
                return true;
        }
        return true;
    }

}
