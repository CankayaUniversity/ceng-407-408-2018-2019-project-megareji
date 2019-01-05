package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Admin2_Activity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> useremailFromFB;
    PostClass customAdapter;
    Spinner spinnerOfAdmin2;
    TextView textViewOfAdmin2;

    String selectedspinner;
    String positionUser;
     String usermail;

    String uuidString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin2_);

        Bundle extras = getIntent().getExtras();
        usermail = extras.getString("email");

        spinnerOfAdmin2= findViewById(R.id.spinnerOfAdmin2);
        textViewOfAdmin2 = findViewById(R.id.textViewOfAdmin2);
        ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.positions,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOfAdmin2.setAdapter(adapter);

        textViewOfAdmin2.setText(usermail);

        spinnerOfAdmin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] positionsOfString=getResources().getStringArray(R.array.positions);
                selectedspinner =positionsOfString[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        useremailFromFB =new ArrayList<String>();
        AdminActivity admin=new AdminActivity();
        getDataFromFirebase();


    }
    public void getDataFromFirebase(){

        DatabaseReference newReference=firebaseDatabase.getReference("Users");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String,String> )ds.getValue();


                    useremailFromFB.add(hashMap.get("useremail"));
                    if(hashMap.get("useremail").equals(usermail)){
                        positionUser=hashMap.get("positionofuser").toString();
                        uuidString =ds.getKey();


                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void ApplyAdmin2(View view){
       myRef.child("Users").child(uuidString).child("positionofuser").setValue(selectedspinner);
       boolean[] arr=new boolean[16];
       if(selectedspinner.equals("Art Group")){
           arr[8]=true;arr[9]=true;arr[13]=true;
           myRef.child("Users").child(uuidString).child("accessablemenu").setValue(Arrays.toString(arr));
       }

       else if(selectedspinner.equals("Director Group")){
           myRef.child("Users").child(uuidString).child("accessablemenu").setValue(Arrays.toString(arr));
       }
       else if(selectedspinner.equals("Display Group")){
           arr[8]=true;arr[9]=true;arr[13]=true;
           myRef.child("Users").child(uuidString).child("accessablemenu").setValue(Arrays.toString(arr));
       }
       else if(selectedspinner.equals("Light Group")){
           arr[8]=true;arr[9]=true;arr[13]=true;
           myRef.child("Users").child(uuidString).child("accessablemenu").setValue(Arrays.toString(arr));
       }
       else if(selectedspinner.equals("Post Production Group")){
           arr[11]=true;arr[12]=true;arr[13]=true;
           myRef.child("Users").child(uuidString).child("accessablemenu").setValue(Arrays.toString(arr));
       }
       else if(selectedspinner.equals("Producer Group")){
           arr[5]=true;arr[9]=true;arr[12]=true;arr[13]=true;
           myRef.child("Users").child(uuidString).child("accessablemenu").setValue(Arrays.toString(arr));
       }
       else if(selectedspinner.equals("Production Group")){
           myRef.child("Users").child(uuidString).child("accessablemenu").setValue(Arrays.toString(arr));
       }
       else if(selectedspinner.equals("Scenarist Group")){
           myRef.child("Users").child(uuidString).child("accessablemenu").setValue(Arrays.toString(arr));
       }
       else if(selectedspinner.equals("Sound Group")){
           arr[8]=true;arr[9]=true;arr[13]=true;
           myRef.child("Users").child(uuidString).child("accessablemenu").setValue(Arrays.toString(arr));
       }
       else if(selectedspinner.equals("Stage Group")){
           arr[7]=true;arr[13]=true;
           myRef.child("Users").child(uuidString).child("accessablemenu").setValue(Arrays.toString(arr));
       }

        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
        startActivity(intent);
    }



}
