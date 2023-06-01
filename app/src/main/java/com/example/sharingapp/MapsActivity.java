package com.example.sharingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.sharingapp.databinding.ActivityMapsBinding;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private FusedLocationProviderClient client;
    private double kinhDo;
    private double viDo;
    public static final int cost = 101;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
//        binding = ActivityMapsBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        client = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        onclick();
//        ImageView btn_back = findViewById(R.id.quay_ve);
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MapsActivity.this, MainActivity2.class);
//                startActivity(intent);
//            }
//        });
//
//        ImageButton senMap = findViewById(R.id.send_maps);
//        senMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText editText = findViewById(R.id.input_Search); // Thay thế R.id.edit_text bằng ID của EditText trong layout của bạn
//                String text = editText.getText().toString();
//                List<Address> addressesList=null;
//                if(text!=null){
//                    Geocoder geocoder = new Geocoder(MapsActivity.this);
//                    try {
//                        addressesList = geocoder.getFromLocationName(text, 1);
//
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        // Xử lý khi có lỗi xảy ra trong quá trình tìm kiếm vị trí
//                    }
//                    Address address = addressesList.get(0);
//                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                    mMap.addMarker(new MarkerOptions().position(latLng).title(text));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//                }
//
//
//            }
//        });
//
//        ImageView dialog = findViewById(R.id.dialog);
//        dialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MapsActivity.this, R.style.BottomSheetStyle);
//                View sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottomdialog, findViewById(R.id.dialog_contenner));
//
//                sheetView.findViewById(R.id.menu_caner).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        bottomSheetDialog.dismiss();
//                    }
//                });
//                sheetView.findViewById(R.id.map_md).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                       mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                        bottomSheetDialog.dismiss();
//                    }
//                });
//                sheetView.findViewById(R.id.map_dh).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                       mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//                        bottomSheetDialog.dismiss();
//                    }
//                });
//                sheetView.findViewById(R.id.map_gt).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//                        bottomSheetDialog.dismiss();
//                    }
//                });
//
//
//                bottomSheetDialog.setContentView(sheetView);
//                bottomSheetDialog.show();
//            }
//        });
//
//        ImageView find = findViewById(R.id.ic_gps);
//        find.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LatLng setLng = new LatLng(kinhDo, viDo);
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(setLng, 15));
//            }
//        });
    }


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


    }

    public void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, cost);
            return;
        }
        //lấy vị trí hiện tại
        mMap.setMyLocationEnabled(true);

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    kinhDo = location.getLatitude();// lấy kinh độ hiện tại
                    viDo = location.getLongitude();// lấy vĩ độ hiện tại
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    // di chuyển tới vị trí hiện tại và phóng  to vị trí
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
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


    //quay lại trang chính(MainAtivity2)
    public void onclick(){
        ImageView btn_back = findViewById(R.id.quay_ve);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });


        //tìm kiếm địa điểm
        ImageButton senMap = findViewById(R.id.send_maps);
        senMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = findViewById(R.id.input_Search); // Thay thế R.id.edit_text bằng ID của EditText trong layout của bạn
                String text = editText.getText().toString();
                List<Address> addressesList=null;
                if(text!=null){
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressesList = geocoder.getFromLocationName(text, 1);


                    } catch (IOException e) {
                        e.printStackTrace();
                        // Xử lý khi có lỗi xảy ra trong quá trình tìm kiếm vị trí
                    }
                    Address address = addressesList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(text));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                }


            }
        });

        //xóa nội dung thanh tìm kiêm
        ImageButton clear = findViewById(R.id.clear_text);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = findViewById(R.id.input_Search);
                editText.setText(null);

            }
        });

        // các kiểu hiển thị bản đồ
        ImageView dialog = findViewById(R.id.dialog);
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MapsActivity.this, R.style.BottomSheetStyle);
                View sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottomdialog, findViewById(R.id.dialog_contenner));

                sheetView.findViewById(R.id.menu_caner).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                    }
                });
                sheetView.findViewById(R.id.map_md).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        bottomSheetDialog.dismiss();
                    }
                });
                sheetView.findViewById(R.id.map_vt).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        bottomSheetDialog.dismiss();
                    }
                });
                sheetView.findViewById(R.id.map_dh).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        bottomSheetDialog.dismiss();
                    }
                });


                bottomSheetDialog.setContentView(sheetView);
                bottomSheetDialog.show();
            }
        });
//quay lại vị trí hiện tại
        ImageView find = findViewById(R.id.ic_gps);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng setLng = new LatLng(kinhDo, viDo);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(setLng, 15));
            }
        });
    }

}