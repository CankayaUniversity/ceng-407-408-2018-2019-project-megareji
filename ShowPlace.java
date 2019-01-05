package com.example.arzuk.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowPlace extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> useremailFromFB;
    ArrayList<String>ids;
    PlacePostClass customAdapter;
    ListView listviewShowPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_place);



        listviewShowPlace= findViewById(R.id.listviewShowPlace);

        useremailFromFB =new ArrayList<String>();
        ids=new ArrayList<String>();
        firebaseDatabase=firebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();



        customAdapter=new PlacePostClass(useremailFromFB,ids,this);
        listviewShowPlace.setAdapter(customAdapter);
        getDataFromFirebase();
        listviewShowPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getApplicationContext(),ShowPlaceEnd.class);
                intent.putExtra("id",ids.get(position).toString());


                startActivity(intent);
            }
        });
    }


    public void getDataFromFirebase(){

        DatabaseReference newReference=firebaseDatabase.getReference("places");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String,String> )ds.getValue();

                    useremailFromFB.add(hashMap.get("Email")+"_"+hashMap.get("Scene"));
                    ids.add( ds.getKey());
                    customAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
