package com.kylelainez.oop_project_v1;

import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class WalletBuy {
    public FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public String email, ffname,flname,fcontactNum,fwallet;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private HashMap<String,Object> map = new HashMap<>();
    private int newValue;

    public void subtract(final Object value){
        email = user.getEmail();

        firebaseFirestore.collection("UserAuth").document(email).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>(){
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ffname = documentSnapshot.getString("FirstName");
                        flname = documentSnapshot.getString("LastName");
                        fcontactNum = documentSnapshot.getString("MobileNumber");
                        fwallet = documentSnapshot.getLong("Wallet").toString();
                        long longValue = (long) value;
                        int intValue = (int) longValue;
                        if (intValue < Integer.parseInt(fwallet)){
                            newValue = Integer.parseInt(fwallet) - intValue;
                            submitInputValues();
                        }
                    }
                });

    }

    private void submitInputValues(){
        map.put("FirstName",ffname);
        map.put("LastName",flname);
        map.put("MobileNumber",fcontactNum);
        map.put("Wallet",(newValue));
        map.put("isClient",false);
        firebaseFirestore.collection("UserAuth").document(email)
                .set(map);
    }


}
