package com.kylelainez.oop_project_v1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationClientActivity extends AppCompatActivity {

    EditText emailAddress, password, confirmPassword, establishmentName, mobile;
    ImageButton mNextButton;
    TextView mAlreadyHaveAcctBtn;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    Map<String,Object> map = new HashMap<>();
    private String establishment, lastName, mobileNumber, radioButtonText;
    private static final String TAG = "RegistrationClientActivity";
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen_client);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailAddress = findViewById(R.id.email_address);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        mAlreadyHaveAcctBtn = findViewById(R.id.alreadyHaveAccountBtn);
        mNextButton = findViewById(R.id.continueBtn);
        establishmentName = findViewById(R.id.establishment_name);
        mobile = findViewById(R.id.phone_number);
        radioGroup = findViewById(R.id.radioGroup);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View view) {
                final String email =  emailAddress.getText().toString();
                final String pw = password.getText().toString();
                String pwConfirm = confirmPassword.getText().toString();
                establishment = establishmentName.getText().toString();
                mobileNumber = mobile.getText().toString();
                int radioID = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioID);
                radioButtonText = radioButton.getText().toString();

                if (email.isEmpty()){
                    emailAddress.setError("Please enter email address");
                    emailAddress.requestFocus();
                }
                if (pw.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                }
                if (establishment.isEmpty()){
                    establishmentName.setError("Please Enter Establishment Name");
                    establishmentName.requestFocus();
                }
                if (mobileNumber.isEmpty()){
                    mobile.setError("Please Enter Phone Number");
                    mobile.requestFocus();
                }
                if (pwConfirm.isEmpty()){
                    confirmPassword.setError("Please confirm password");
                    confirmPassword.requestFocus();
                }
                if (email.isEmpty() || pw.isEmpty() || pwConfirm.isEmpty() || establishment.isEmpty()
                     || mobileNumber.isEmpty()){
                    Toast.makeText(RegistrationClientActivity.this,"Fields are empty!",Toast.LENGTH_SHORT);
                }

                 if (!(email.isEmpty()|| pw.isEmpty() || pwConfirm.isEmpty()|| establishment.isEmpty()
                         || mobileNumber.isEmpty())){
                    if (pw.equals(pwConfirm)) {
                        mFirebaseAuth.createUserWithEmailAndPassword(email, pw)
                                .addOnCompleteListener(RegistrationClientActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(RegistrationClientActivity.this, "Sign Up Unsuccessful",
                                                    Toast.LENGTH_SHORT);
                                        } else {
                                            map.put("Establishment", establishment);
                                            map.put("MobileNumber", mobileNumber);
                                            map.put("Type",radioButtonText);
                                            map.put("isClient", true);
                                            mFirebaseFirestore.collection("UserAuth").document(email)
                                                    .set(map);
                                            startActivity(new Intent(RegistrationClientActivity.this, LoginActivity.class));
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                emailAddress.setError("Email already Used!!!");
                                emailAddress.requestFocus();
                            }
                        });
                    }else {
                        confirmPassword.setError("Password does not match!");
                        confirmPassword.requestFocus();
                    }
                } else{
                    Toast.makeText(RegistrationClientActivity.this,"Error Occurred!",Toast.LENGTH_SHORT);
                }
            }
        });

        mAlreadyHaveAcctBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrationClientActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
