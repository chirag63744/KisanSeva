package com.example.kissanseva;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsPlot extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<LatLng> markerPositions = new ArrayList<>();
    private List<LatLng> markerCoordinates = new ArrayList<>();
    private static final int MAX_MARKERS = 4;
    private Polyline initialPolyline = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_plot);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        LatLng Raj = new LatLng(24.6749597, 72.3130839);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("First Point"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Raj, 19));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (markerPositions.size() < MAX_MARKERS) {
                    markerPositions.add(latLng);
                    markerCoordinates.add(latLng);
                    googleMap.addMarker(new MarkerOptions().position(latLng).title("Points"));

                    if (initialPolyline != null) {
                        initialPolyline.remove();
                    }

                    PolylineOptions polylineOptions = new PolylineOptions();
                    for (LatLng coordinates : markerCoordinates) {
                        double latitude = coordinates.latitude;
                        double longitude = coordinates.longitude;

                        String message = "Latitude: " + latitude + ", Longitude: " + longitude;
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        polylineOptions.add(coordinates);
                    }
                    initialPolyline = mMap.addPolyline(polylineOptions);
                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {


                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            LatLng firstPosition = markerPositions.get(0);
                            LatLng lastPosition = markerPositions.get(markerPositions.size() - 1);
                            PolylineOptions polylineOptions = new PolylineOptions()
                                    .add(firstPosition)
                                    .add(lastPosition);
                            Polyline polyline = mMap.addPolyline(polylineOptions);

                            return false; // Return true if you want to consume the event, false otherwise
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Maximum markers reached", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
