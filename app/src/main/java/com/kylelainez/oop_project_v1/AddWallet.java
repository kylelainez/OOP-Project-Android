package com.kylelainez.oop_project_v1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddWallet extends AppCompatActivity {
    EditText addValue;
     private static TextView walletBal, newBal;
    String value;
    private static final String TAG = "AddWallet";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_wallet_window);
        walletBal = findViewById(R.id.current_balance_number);
        newBal = this.findViewById(R.id.new_balance_number);
        addValue = findViewById(R.id.add_value_input);
        value = getIntent().getStringExtra("WALLET");
        walletBal.setText(value);

        addValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int walletValue = Integer.parseInt(charSequence.toString());
                Log.d(TAG, "onTextChanged: " + walletValue);
                int newValue = Integer.parseInt(value);
                int totalValue = walletValue + newValue;
                newBal.setText(totalValue);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
