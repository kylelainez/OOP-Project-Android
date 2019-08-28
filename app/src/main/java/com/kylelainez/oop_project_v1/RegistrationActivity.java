package com.kylelainez.oop_project_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    EditText emailAddress, password, confirmPassword;
    ImageButton mNextButton;
    TextView mAlreadyHaveAcctBtn;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen1);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailAddress = findViewById(R.id.emailAddress);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.password2);
        mAlreadyHaveAcctBtn = findViewById(R.id.alreadyHaveAccountBtn);
        mNextButton = findViewById(R.id.nextBtn);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View view) {
                String email =  emailAddress.getText().toString();
                String pw = password.getText().toString();
                String pwConfirm = confirmPassword.getText().toString();

                if (email.isEmpty()){
                    emailAddress.setError("Please enter valid email address");
                    emailAddress.requestFocus();
                }
                else if (pw.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                }
                else if (pwConfirm.isEmpty()){
                    confirmPassword.setError("Please confirm password");
                    confirmPassword.requestFocus();
                }

                else if (email.isEmpty() && pw.isEmpty() && pwConfirm.isEmpty()){
                    Toast.makeText(RegistrationActivity.this,"Fields are empty!",Toast.LENGTH_SHORT);
                }
                else if (!(email.isEmpty() && pw.isEmpty() && pwConfirm.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(RegistrationActivity.this,"Sign Up Unsuccessful",Toast.LENGTH_SHORT);
                            }else{
                                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                            }
                        }
                    });
                } else{
                    Toast.makeText(RegistrationActivity.this,"Error Occurred!",Toast.LENGTH_SHORT);
                }
            }
        });

        mAlreadyHaveAcctBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
