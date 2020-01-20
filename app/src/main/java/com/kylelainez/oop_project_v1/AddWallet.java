package com.kylelainez.oop_project_v1;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddWallet extends AppCompatActivity {
    private EditText addValue, creditCard, month, year, cvv;
    private TextView walletBal, newBal;
    private String email, ffname,flname,fcontactNum,fwallet;
    private ImageButton topup;
    private static final String TAG = "AddWallet";
    private int count = 0,totalValue;
    private HashMap<String,Object> map = new HashMap<>();
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_wallet_window);
        walletBal = findViewById(R.id.current_balance_number);
        newBal = findViewById(R.id.new_balance_number);
        addValue = findViewById(R.id.add_value_input);
        topup = findViewById(R.id.loadButton);
        creditCard = findViewById(R.id.card_number_value);
        month = findViewById(R.id.month_input);
        year = findViewById(R.id.year_input);
        cvv = findViewById(R.id.security_code_input);
        firebaseFirestore = FirebaseFirestore.getInstance();
        email = user.getEmail();

        firebaseFirestore.collection("UserAuth").document(email).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>(){
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ffname = documentSnapshot.getString("FirstName");
                        flname = documentSnapshot.getString("LastName");
                        fcontactNum = documentSnapshot.getString("MobileNumber");
                        fwallet = documentSnapshot.getLong("Wallet").toString();
                        walletBal.setText(fwallet);
                        newBal.setText(fwallet);
                        if (fwallet!=null && !fwallet.isEmpty()){
                            findViewById(R.id.loadingPanelAddWallet).setVisibility(View.GONE);
                        }
                    }
                });




        addValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence == null || charSequence.toString().isEmpty()){
                    newBal.setText(fwallet);
                }else {
                    int walletValue = Integer.parseInt(charSequence.toString());
                    Log.d(TAG, "onTextChanged: " + walletValue);
                    int newValue = Integer.parseInt(fwallet);
                    Log.d(TAG, "onTextChanged: " + fwallet);
                    totalValue = walletValue + newValue;
                    newBal.setText(Long.toString(totalValue));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        creditCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int inputlength = creditCard.getText().toString().length();

                if (count <= inputlength && inputlength == 4 ||
                        inputlength == 9 || inputlength == 14){

                    creditCard.setText(creditCard.getText().toString()+" ");

                    int pos = creditCard.getText().length();
                    creditCard.setSelection(pos);

                } else if(count >= inputlength &&(inputlength == 4 ||
                        inputlength == 9 || inputlength == 14)){
                    creditCard.setText(creditCard.getText().toString()
                            .substring(0,creditCard.getText()
                                    .toString().length()-1));

                    int pos = creditCard.getText().length();
                    creditCard.setSelection(pos);
                }
                count = creditCard.getText().toString().length();
            }
        });

        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitInputValues();
                finish();
            }
        });
    }

    private void submitInputValues(){
        map.put("FirstName",ffname);
        map.put("LastName",flname);
        map.put("MobileNumber",fcontactNum);
        map.put("Wallet",(totalValue));
        map.put("isClient",false);
        firebaseFirestore.collection("UserAuth").document(email)
                .set(map);
    }
}
