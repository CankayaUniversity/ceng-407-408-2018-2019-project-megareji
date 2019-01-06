package com.example.berkcan.megareji;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.List;

public class MainMenuActivity extends Activity {
    private ListView listViewMainMenu;
    private String[] menu={"Enter Actor","Show Actor","Enter Place","Show Place","Send Notification","Make Plan","Show Plans","Write Scenario","Show Scenarios","Create or Change Request","Leader Board","Create or Change Post Prodution Report","Enter Set List"};

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageRef;
    DatabaseReference myRef;
    boolean[] accessableMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        mStorageRef= FirebaseStorage.getInstance().getReference();
        myRef=firebaseDatabase.getReference();
        accessableMenu=new boolean[16];
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String accessable =  dataSnapshot.child("Users").child(mAuth.getCurrentUser().getUid()).child("accessablemenu").getValue().toString();
               accessable=accessable.replaceAll(",","");
                accessable=accessable.replaceAll("\\[","");
                accessable=accessable.replaceAll("\\]","");

               String[] accesableString= accessable.split(" ");

                for (int i = 0; i < accesableString.length; i++)
                    accessableMenu[i] = Boolean.parseBoolean(accesableString[i]);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        listViewMainMenu=findViewById(R.id.listViewMainMenu);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,menu);
        listViewMainMenu.setAdapter(adapter);
        listViewMainMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(menu[position].equals("Enter Actor")){
                    Intent intent=new Intent(getApplicationContext(),EnterActor.class);
                    startActivity(intent);
                }
                else if(menu[position].equals("Show Actor")){
                    Intent intent=new Intent(getApplicationContext(),ShowActor.class);
                    startActivity(intent);
                }
                else if(menu[position].equals("Enter Place")){
                    Intent intent=new Intent(getApplicationContext(),EnterPlace.class);
                    startActivity(intent);
                }
                else if(menu[position].equals("Show Place")){
                    Intent intent=new Intent(getApplicationContext(),ShowPlace.class);
                    startActivity(intent);
                }
                else if(menu[position].equals("Send Notification")){
                    Intent intent=new Intent(getApplicationContext(),SendNotification.class);
                    startActivity(intent);
                }
                else if(menu[position].equals("Make Plan")){
                    Intent intent=new Intent(getApplicationContext(),EnterPlan.class);
                    startActivity(intent);
                }
                else if(menu[position].equals("Show Plans")){
                    Intent intent=new Intent(getApplicationContext(),ShowPlan.class);
                    startActivity(intent);
                }
                else if(menu[position].equals("Write Scenario")){
                    Intent intent=new Intent(getApplicationContext(),WriteScenario.class);
                    startActivity(intent);
                }
                else if(menu[position].equals("Show Scenarios")){
                    Intent intent=new Intent(getApplicationContext(),ShowScenario.class);
                    startActivity(intent);
                }

                else if(menu[position].equals("Create or Change Request")){
                    Intent intent=new Intent(getApplicationContext(),CreateorChangeRequestReport.class);
                    startActivity(intent);
                }
                else if(menu[position].equals("Leader Board")){
                    Intent intent=new Intent(getApplicationContext(),LeaderBoard.class);
                    startActivity(intent);
                }
                else if(menu[position].equals("Create or Change Post Prodution Report")){
                    Intent intent=new Intent(getApplicationContext(),CreateorChangePostProReport.class);
                    startActivity(intent);
                }
                else if(menu[position].equals("Enter Set List")){
                    Intent intent=new Intent(getApplicationContext(),EnterSetList.class);
                    startActivity(intent);
                }


            }
        });
    }
}
