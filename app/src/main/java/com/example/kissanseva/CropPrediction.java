package com.example.kissanseva;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class CropPrediction extends AppCompatActivity {

    EditText locationEditText, soilTypeEditText, rainfallEditText, areaEditText, investmentEditText;
    Button submitButton;
    TextView recommendationTextView;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_prediction);

        locationEditText = findViewById(R.id.location);
        soilTypeEditText = findViewById(R.id.soilType);
        rainfallEditText = findViewById(R.id.rainfall);
        areaEditText = findViewById(R.id.area);
        investmentEditText = findViewById(R.id.investment);
        submitButton = findViewById(R.id.predict);
        recommendationTextView = findViewById(R.id.recommendation);
        back=findViewById(R.id.imageButton1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CropPrediction.this,home.class);
                startActivity(i);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String location = locationEditText.getText().toString();
                    String soilType = soilTypeEditText.getText().toString();
                    int rainfall = Integer.parseInt(String.valueOf(rainfallEditText.getText()));
                    int area = Integer.parseInt(areaEditText.getText().toString());
                    int investment = Integer.parseInt(investmentEditText.getText().toString());

                    // Create form body with form fields
                    RequestBody formBody = new FormBody.Builder()
                            .add("location", location)
                            .add("soil_type", soilType)
                            .add("rainfall", String.valueOf(rainfall))
                            .add("area", String.valueOf(area))
                            .add("investment", String.valueOf(investment))
                            .build();

                    Request request = new Request.Builder()
                            .url("http://172.16.51.179:5000/") // Replace with your Flask app URL
                            .post(formBody)
                            .build();

                    OkHttpClient client = new OkHttpClient();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String responseBody = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    handleResponse(responseBody);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CropPrediction.this, "Failed to send data", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void handleResponse(String responseBody) {
        try {
            String[] responseParts = responseBody.split(" ");

            if (responseParts.length >= 2) {
                String cropRecommendation = responseParts[0];
                String profit = responseParts[1];

                recommendationTextView.setText("Recommended Crop: " + cropRecommendation + "\nProfit: " + profit);
            } else {
                recommendationTextView.setText("Error: Unable to retrieve recommendation");
            }
        } catch (Exception e) {
            e.printStackTrace();
            recommendationTextView.setText("Error: Unable to retrieve recommendation");
        }
    }






}
