package com.kylelainez.oop_project_v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    private EditText fname, lname, contactNum;
    private String ffname,flname,fcontactNum,fwallet, fclient,
            inputfname,inputlname,inputcontactNum;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String email;
    private Map<String,Object> map = new HashMap<>();
    private FirebaseFirestore firebaseFirestore;
    private ImageButton buttton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        firebaseFirestore = FirebaseFirestore.getInstance();
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        contactNum = findViewById(R.id.contactNum);
        buttton = findViewById(R.id.saveButton);
        email = user.getEmail();

        firebaseFirestore.collection("UserAuth").document(email).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>(){
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ffname = documentSnapshot.getString("FirstName");
                        flname = documentSnapshot.getString("LastName");
                        fcontactNum = documentSnapshot.getString("MobileNumber");
                        fwallet = documentSnapshot.getLong("Wallet").toString();
                        fname.setText(ffname);
                        lname.setText(flname);
                        contactNum.setText(fcontactNum);
                    }
                });

        buttton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitInputValues();
                finish();
            }
        });
    }

    private void submitInputValues(){
        inputfname = fname.getText().toString();
        inputlname = lname.getText().toString();
        inputcontactNum = contactNum.getText().toString();
        map.put("FirstName",inputfname);
        map.put("LastName",inputlname);
        map.put("MobileNumber",inputcontactNum);
        map.put("Wallet",Integer.parseInt(fwallet));
        map.put("isClient",false);
        firebaseFirestore.collection("UserAuth").document(email)
                .set(map);
    }
}
