package com.example.apple.calendargo;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences pref;

    Firebase myFirebaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        /*if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }*/

        pref = getSharedPreferences("login.conf", Context.MODE_PRIVATE);

        database = FirebaseDatabase.getInstance();

        setContentView(R.layout.activity_login);

        final TextView registerLink = (TextView) findViewById(R.id.tvRegNow);
        final TextView skipLink = (TextView) findViewById(R.id.tvSkip);
        final Button login = (Button) findViewById(R.id.bLogin);
        final EditText etEmail = (EditText) findViewById(R.id.etEmailLogin);
        final EditText etPassword = (EditText) findViewById(R.id.etPasswordLogin);

        auth = FirebaseAuth.getInstance();

        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);

        registerLink.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent registerPage = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerPage);
            }
        });

        skipLink.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent mainPage = new Intent(LoginActivity.this, MainActivity.class);
                mainPage.putExtra("login",false);
                LoginActivity.this.startActivity(mainPage);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailAddress = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                if (TextUtils.isEmpty(emailAddress)){
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(emailAddress, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {
                                    // there was an error
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                } else {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String uid = user.getUid();
                                    myRef = database.getReference("Users").child(uid);

                                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot == null && dataSnapshot.getValue() == null){
                                                Toast.makeText(LoginActivity.this,"No Record",Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                //System.out.println("LogIn Successfully\n");
                                                List<String> profile = (List<String>) dataSnapshot.getValue();
                                                Toast.makeText(LoginActivity.this,profile.toString(),Toast.LENGTH_LONG).show();
                                                SharedPreferences.Editor editor = pref.edit();

                                                editor.putString("emailAddress",emailAddress);
                                                editor.putString("password",password);
                                                editor.apply();


                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                intent.putExtra("hasLoggedIn",true);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });




                                }
                            }
                        });

            }
        });

    }
}
