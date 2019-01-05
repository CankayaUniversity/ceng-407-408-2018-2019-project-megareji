package com.example.arzuk.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.UUID;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText emailText;
    EditText passwordText;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    StorageReference mStorageRef;
    boolean[] accessableMenu;
    int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mStorageRef= FirebaseStorage.getInstance().getReference();
        accessableMenu=new boolean[16];


    }
    public void signIn(View view){
        mAuth.signInWithEmailAndPassword(emailText.getText().toString(),passwordText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String email=emailText.getText().toString();
                            if(email.equals("berkcang_82@hotmail.com")){
                                Intent intent1 = new Intent(getApplicationContext(), AdminActivity.class);
                                startActivity(intent1);
                            }
                            else if(email.equals("superuser@super.com")){
                                Intent intent1 = new Intent(getApplicationContext(), MainMenuActivity.class);
                                startActivity(intent1);
                            }
                            else {

                                Intent intent2 = new Intent(getApplicationContext(), ProfileActivity.class);
                                startActivity(intent2);
                            }
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void signUp(View view){
        mAuth.createUserWithEmailAndPassword(emailText.getText().toString(),passwordText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user=mAuth.getCurrentUser();
                            String email= user.getEmail();
                            String uid=user.getUid();
                            String positionOfUser="null";

                            UUID uuid1=UUID.randomUUID();
                            String uuidString=uuid1.toString();

                            myRef.child("Users").child(uid).child("useremail").setValue(email);
                            myRef.child("Users").child(uid).child("uid").setValue(uid);
                            myRef.child("Users").child(uid).child("positionofuser").setValue(positionOfUser);
                            myRef.child("Users").child(uid).child("accessablemenu").setValue(Arrays.toString(accessableMenu));
                            myRef.child("Users").child(uid).child("score").setValue(score);

                            if(email.equals("berkcang_82@hotmail.com")){
                                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                                startActivity(intent);
                            }
                            else {

                                Toast.makeText(LoginActivity.this, "User Created!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                startActivity(intent);
                            }
                        }

                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
