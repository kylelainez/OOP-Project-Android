package com.kylelainez.oop_project_v1;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText emailId, password;
    ImageButton btnSignIn;
    TextView tvSignUp, clientSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private static final String TAG = "LoginActivity";
    public static final String EXTRA_FNAME = "com.kylelainez.oop_project_v1.EXTRA_FNAME";
    public static final String EXTRA_EMAIL = "com.kylelainez.oop_project_v1.EXTRA_EMAIL";
    public static final String EXTRA_ESTABLISHMENT = "com.kylelainez.oop_project_v1.EXTRA_ESTABLISHMENT";
    public static final String EXTRA_LNAME = "com.kylelainez.oop_project_v1.EXTRA_LNAME";
    public static final String EXTRA_MOBILE= "com.kylelainez.oop_project_v1.EXTRA_MOBILE";
    public static final String EXTRA_TYPE = "com.kylelainez.oop_project_v1.EXTRA_TYPE";
    private String fName, lName, mobileNumber, establishmentName, establismentType;
    private Boolean isClient = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.emailAddress);
        password = findViewById(R.id.password);
        btnSignIn = findViewById(R.id.login_button);
        tvSignUp = findViewById(R.id.create_account);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                final String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                findViewById(R.id.loadingPanellogin).setVisibility(View.VISIBLE);
                if (email.isEmpty()) {
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter your password");
                    password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                findViewById(R.id.loadingPanellogin).setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Login Error, Please Login Again",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                firebaseFirestore.collection("UserAuth").document(email).get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                fName = documentSnapshot.getString("FirstName");
                                                lName = documentSnapshot.getString("LastName");
                                                mobileNumber = documentSnapshot.getString("MobileNumber");
                                                establishmentName = documentSnapshot.getString("Establishment");
                                                establismentType = documentSnapshot.getString("Type");

                                                isClient = documentSnapshot.getBoolean("isClient");
                                                if (!isClient) {
                                                    Intent intToHome = new Intent(LoginActivity.this, MainActivity.class);
                                                    intToHome.putExtra(EXTRA_FNAME, fName);
                                                    intToHome.putExtra(EXTRA_LNAME, lName);
                                                    intToHome.putExtra(EXTRA_EMAIL, email);
                                                    intToHome.putExtra(EXTRA_MOBILE, mobileNumber);
                                                    startActivity(intToHome);
                                                    finish();
                                                }else{
                                                    //Start Client Activity
                                                    Intent intToHome = new Intent(LoginActivity.this, MainActivity.class);
                                                    intToHome.putExtra(EXTRA_ESTABLISHMENT,establishmentName );
                                                    intToHome.putExtra(EXTRA_MOBILE, mobileNumber);
                                                    intToHome.putExtra(EXTRA_TYPE,establismentType);
                                                    startActivity(intToHome);
                                                }
                                            }
                                        });
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intSignUp);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}