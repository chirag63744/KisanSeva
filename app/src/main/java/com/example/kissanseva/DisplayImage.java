package com.example.kissanseva;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DisplayImage extends AppCompatActivity {

    private ImageView displayImageView;
    private Button buttonCrop;
    private Button plot_point;
    private Button calculate_distance;
    Uri imageUri;
    AreaSelectionView areaSelectionView;
    boolean isPointPlottingEnabled = false;

    @SuppressLint({"ClickableViewAccessibility", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        areaSelectionView=findViewById(R.id.areaSelectionView);
        displayImageView = findViewById(R.id.displayImage);
        buttonCrop = findViewById(R.id.crop_btn);
        plot_point = findViewById(R.id.plot_points);
        calculate_distance = findViewById(R.id.calculate_distance);

        String imageUriString = getIntent().getStringExtra("imageUri");
        if (imageUriString != null) {
            imageUri = Uri.parse(imageUriString);
            displayImageView.setImageURI(imageUri);
        }

        buttonCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropImage(imageUri);
            }
        });
        plot_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle the state of point plotting
                isPointPlottingEnabled = !isPointPlottingEnabled;

                if (isPointPlottingEnabled) {
                    plot_point.setText("Disable Point Plotting");
                } else {
                    plot_point.setText("Enable Point Plotting");
                }
            }
        });

        displayImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                List<PointF> initialPoints = new ArrayList<>();
                initialPoints.add(new PointF(500, 100));
                initialPoints.add(new PointF(100, 100));
                if (isPointPlottingEnabled) {
                    // User is allowed to plot points
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        // Update points based on touch event
                        List<PointF> updatedPoints = new ArrayList<>();
                        for (int i = 0; i < 2; i++) {
                            updatedPoints.add(new PointF(event.getX() + i * 50, event.getY() + i * 50));
                        }
                        areaSelectionView.setPoints(updatedPoints);
                    }
                    return true;
                }
                return false; // Ignore touch events if point plotting is disabled
            }
        });
        calculate_distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<PointF> currentPoints = areaSelectionView.getPoints();
                double distance = calculateDistance(currentPoints);

                if (currentPoints.size() >= 2) {
                    Toast.makeText(DisplayImage.this, "Distance: " + distance, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DisplayImage.this, "Please plot two points first.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cropImage(Uri sourceUri) {
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_image.jpg"));

        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(80); // Set compression quality for the cropped image

        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri croppedUri = UCrop.getOutput(data);

            if (croppedUri != null) {
                displayImageView.setImageURI(croppedUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Throwable cropError = UCrop.getError(data);
            // Handle the crop error
        }
    }

    private double calculateDistance(List<PointF> points) {
        if (points.size() >= 2) {
            PointF p1 = points.get(0);
            PointF p2 = points.get(1);
            float dx = p2.x - p1.x;
            float dy = p2.y - p1.y;
            double pixelDistance = Math.sqrt(dx * dx + dy * dy);

            // Convert pixel distance to real-world distance in meters
            double distanceInMeters = pixelDistance * 1.24;

            return distanceInMeters;
        } else {
            return 0.0; // Handle case where there are insufficient points
        }
    }
}
