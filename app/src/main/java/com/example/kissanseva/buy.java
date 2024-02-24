
package com.example.kissanseva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class buy extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CropAdapter cropAdapter;
    private DatabaseReference cropsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cropAdapter = new CropAdapter();
        recyclerView.setAdapter(cropAdapter);

        cropsRef = FirebaseDatabase.getInstance().getReference().child("crops");

        // Fetch crop data
        fetchCrops();
    }

    private void fetchCrops() {
        cropsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Crop> crops = new ArrayList<>();
                for (DataSnapshot cropSnapshot : snapshot.getChildren()) {
                    Crop crop = cropSnapshot.getValue(Crop.class);
                    crops.add(crop);
                }
                cropAdapter.setCrops(crops);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
}

