package com.example.sharingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.sharingapp.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient client;
    public static final int cost =101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        client = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ImageView btn_back =  findViewById(R.id.quay_ve);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
getCurrentLocation();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(10.850715233747286, 106.76343219675508);
        LatLng NL = new LatLng(10.871487115007739, 106.7917831540491);
        LatLng eon = new LatLng(10.870638122326845, 106.77825703131286);
        LatLng fahasan_1 = new LatLng(10.868005204049679, 106.77666093539985);

        LatLng fahasan_6 = new LatLng(10.859758090280957, 106.80090410358655);
        LatLng fahasan_2 = new LatLng(10.860601024784136, 106.81373579055806);
        LatLng fahasan_3 = new LatLng(10.84805472945149, 106.7762324142909);
        LatLng fahasan_4 = new LatLng(10.845799176081746, 106.78016212691695);
        LatLng fahasan_5 = new LatLng(10.85181394732622, 106.75449140537279);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(NL).title("Marker in Sydney")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(eon).title("Marker in Sydney")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(fahasan_1).title("Nhà sách Linh Trung")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(fahasan_2).title("Nhà sách Linh Trung")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(fahasan_3).title("Nhà sách Linh Trung")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(fahasan_4).title("Nhà sách Linh Trung")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(fahasan_5).title("Nhà sách Linh Trung")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(fahasan_6).title("Nhà sách Linh Trung")).setVisible(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(NL));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eon));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fahasan_1));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fahasan_2));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fahasan_3));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fahasan_4));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fahasan_5));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fahasan_6));

    }

    public void getCurrentLocation(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, cost);
            return;
        }

//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setInterval(60000);
//        locationRequest.setPriority(locationRequest.PARCELABLE_WRITE_RETURN_VALUE);
//        locationRequest.setFastestInterval(5000);
//        LocationCallback locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(@NonNull LocationResult locationResult) {
//
//                Toast.makeText(getApplicationContext(),"location"+ locationRequest, Toast.LENGTH_LONG).show();
//
//                if(locationRequest==null){
//                    Toast.makeText(getApplicationContext(),"location"+ locationRequest, Toast.LENGTH_LONG).show();
//                    return;
//                }
//                for(Location location: locationRequest.getLocations()){
//                    if(location!=null){
//                        Toast.makeText(getApplicationContext(),"location"+ location.getLongitude(), Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        };
//        client.requestLocationUpdates(locationRequest, locationCallback,null );
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(latLng) // Vị trí của marker
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)); // Chọn màu sắc
                    mMap.addMarker(markerOptions).setVisible(true);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
            }
        });

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (cost){
            case cost:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getCurrentLocation();
                }
        }
    }
}