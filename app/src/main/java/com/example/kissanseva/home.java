package com.example.kissanseva;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ImageButton ib = (ImageButton) findViewById(R.id.imageButton1);
        ImageButton cropPrediction = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton mandiBuy=(ImageButton) findViewById(R.id.imageButton4);
        ImageButton mandiSell=(ImageButton) findViewById(R.id.imageButton5);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), login.class);
                view.getContext().startActivity(i);
            }
        });
        cropPrediction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CropPrediction.class);
                view.getContext().startActivity(i);
            }
        });
        mandiBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), buy.class);
                view.getContext().startActivity(i);
            }
        });
        mandiSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), sell.class);
                view.getContext().startActivity(i);
            }
        });
        ImageButton ib1 = (ImageButton) findViewById(R.id.imageButton2);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), map.class);
                view.getContext().startActivity(i);
            }
        });
    }
}