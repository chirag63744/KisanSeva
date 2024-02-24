package com.example.kissanseva;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private ImageView photoImageView;
    private Button uploadButton;
    private Button cameraUpload;

    private Bitmap uploadedImage;
    private static final int pic_id = 123;


    @SuppressLint({"ClickableViewAccessibility", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photoImageView=findViewById(R.id.photoImageView);
        uploadButton=findViewById(R.id.uploadButton);
        cameraUpload=findViewById(R.id.cameraUpload);


        cameraUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, pic_id);

            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

    }

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            // Pass the image URI to DisplayImageActivity
            Intent displayIntent = new Intent(MainActivity.this, DisplayImage.class);
            displayIntent.putExtra("imageUri", selectedImageUri.toString());
            startActivity(displayIntent);
        }
        if (requestCode == pic_id && resultCode == RESULT_OK && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            // Convert the Bitmap to a Uri and pass it to DisplayImageActivity
            Uri photoUri = getImageUri(photo);
            Intent displayIntent = new Intent(MainActivity.this, DisplayImage.class);
            displayIntent.putExtra("imageUri", photoUri.toString());
            startActivity(displayIntent);
        }
    }

    // Helper method to convert Bitmap to Uri
    private Uri getImageUri(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }
    }




