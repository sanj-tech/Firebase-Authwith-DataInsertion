package com.jsstech.firebaseauthenticationinsertex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText name,email,password,contact;
    Button insert_bt;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name =findViewById(R.id.edtname);
        email=findViewById(R.id.edtemail);
        password=findViewById(R.id.edtpassword);
        contact=findViewById(R.id.edtcontact);
        insert_bt=findViewById(R.id.btInsert);

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Fruits");



        insert_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String NAME=name.getText().toString().trim();
                String EMAIL=email.getText().toString().trim();
                String PASSWORD=password.getText().toString().trim();
                String CONTACT=contact.getText().toString().trim();

                String ID= databaseReference.push().getKey();

               firebaseAuth.createUserWithEmailAndPassword(EMAIL,PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                    DetailModel model=new DetailModel(ID,NAME,EMAIL,PASSWORD,CONTACT);
                    databaseReference.child(ID).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"User Added",Toast.LENGTH_SHORT).show();
                            }

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });


                   }
               })
                       .addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                           }
                       });

            }
        });




    }
}