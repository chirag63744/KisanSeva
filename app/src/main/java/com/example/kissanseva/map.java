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
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class map extends AppCompatActivity {
    ImageButton CameraButton,GalleryBtn,plotonmap;
    ImageView imageView;
    private static final int pic_id = 123;
    private static final int PICK_IMAGE_REQUEST = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        CameraButton=findViewById(R.id.Camera);
        GalleryBtn=findViewById(R.id.imageButton3);
        imageView=findViewById(R.id.imageView);
        plotonmap=findViewById(R.id.imageButton4);
        CameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, pic_id);

            }
        });
        GalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        plotonmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(map.this,MapsPlot.class);
                startActivity(i);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            // Pass the image URI to DisplayImageActivity
            Intent displayIntent = new Intent(map.this, DisplayImage.class);
            displayIntent.putExtra("imageUri", selectedImageUri.toString());
            startActivity(displayIntent);
        }
        if (requestCode == pic_id && resultCode == RESULT_OK && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            // Convert the Bitmap to a Uri and pass it to DisplayImageActivity
            Uri photoUri = getImageUri(photo);
            Intent displayIntent = new Intent(map.this, DisplayImage.class);
            displayIntent.putExtra("imageUri", photoUri.toString());
            startActivity(displayIntent);
        }}
        private Uri getImageUri(Bitmap bitmap) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
            return Uri.parse(path);
        }


}