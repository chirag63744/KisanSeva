
package com.example.kissanseva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sell extends AppCompatActivity {
    private EditText cropName;
    private EditText cropQuantity;
    private EditText cropDate;
    private EditText cropPrice;
    private Button saveButton;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        cropName = findViewById(R.id.enterCropName);
        cropQuantity = findViewById(R.id.quantity);
        cropDate = findViewById(R.id.date);
        cropPrice = findViewById(R.id.price);
        saveButton = findViewById(R.id.submit);

        // Initialize the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("crops");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read values from EditText fields
                String name = cropName.getText().toString();
                int quantity = Integer.parseInt(cropQuantity.getText().toString());
                String date = cropDate.getText().toString();
                double price = Double.parseDouble(cropPrice.getText().toString());

                // Create a Crop object with collected data
                Crop crop = new Crop(name, quantity, date, price);

                // Push the crop object to Firebase Realtime Database
                databaseReference.push().setValue(crop)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(sell.this, "Crop details saved successfully", Toast.LENGTH_SHORT).show();
                                clearFields();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(sell.this, "Failed to save crop details", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void clearFields() {
        cropName.setText("");
        cropQuantity.setText("");
        cropDate.setText("");
        cropPrice.setText("");
    }
}

