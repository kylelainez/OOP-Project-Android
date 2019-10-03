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

public class RegistrationInfoActivity extends AppCompatActivity {

    EditText mLastName, mFirstName, mobileNum;
    ImageButton mNextButton;
    TextView mAlreadyHaveAcctBtn;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen2);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mLastName = findViewById(R.id.lastName);
        mFirstName = findViewById(R.id.firstName);
        mobileNum = findViewById(R.id.mobileNumber);
        mAlreadyHaveAcctBtn = findViewById(R.id.alreadyHaveAccountBtn);
        mNextButton = findViewById(R.id.nextBtn);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View view) {
                String lastname =  mLastName.getText().toString();
                String firstname = mFirstName.getText().toString();
                String mobilenum = mobileNum.getText().toString();

                if (lastname.isEmpty()){
                    mLastName.setError("Please enter your last name");
                    mLastName.requestFocus();
                }
                else if (firstname.isEmpty()){
                    mFirstName.setError("Please enter your first name");
                    mFirstName.requestFocus();
                }
                else if (mobilenum.isEmpty()){
                    mobileNum.setError("Please enter your mobile number");
                    mobileNum.requestFocus();
                }

                else if (lastname.isEmpty() && firstname.isEmpty() && mobilenum.isEmpty()){
                    Toast.makeText(RegistrationInfoActivity.this,"Fields are empty!",Toast.LENGTH_SHORT);
                }
                else if (!(lastname.isEmpty() && firstname.isEmpty() && mobilenum.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(lastname,firstname).addOnCompleteListener(RegistrationInfoActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(RegistrationInfoActivity.this,"Sign Up Unsuccessful",Toast.LENGTH_SHORT);
                            }else{
                                startActivity(new Intent(RegistrationInfoActivity.this,LoginActivity.class));
                            }
                        }
                    });
                } else{
                    Toast.makeText(RegistrationInfoActivity.this,"Error Occurred!",Toast.LENGTH_SHORT);
                }
            }
        });

        mAlreadyHaveAcctBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrationInfoActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
