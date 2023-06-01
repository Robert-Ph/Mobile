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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private FusedLocationProviderClient client;
    private double kinhDo;
    private double viDo;
    public static final int cost = 101;
    String filePath = "com//example//sharingapp//map//bookstore.txt";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);;
        client = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        onclickEVent();

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        getCurrentLocation();
//        readBookStoreMap();
        getBookStore();
//        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(10.868275219378184,106.77664353001234);
        LatLng book = new LatLng(10.851148291089487,106.76336243684284);
        LatLng eon = new LatLng(10.851637015893548,106.75450270243417);
        LatLng fahasan_1 = new LatLng(10.84756541620791,106.77631707606174);

        LatLng fahasan_2 = new LatLng(10.853563945888684,106.79124885700689);
        LatLng fahasan_3 = new LatLng(10.85927260671776,106.80083825468152);
        LatLng fahasan_4 = new LatLng(10.85863881488239,106.80102454153116);
        LatLng fahasan_5 = new LatLng(10.879218376840639,106.76677821665379);

        LatLng fahasan_6 = new LatLng(10.855295595494981,106.74731837007317);
        LatLng fahasan_7 = new LatLng(10.864806852339632,106.74472019810479);
        LatLng fahasan_8 = new LatLng(10.86746512797234,106.73339478493419);
        LatLng fahasan_9 = new LatLng(10.868811268754051,106.73457574883658);

        LatLng fahasan_10 = new LatLng(10.890386897914958,106.77607718705333);
        LatLng fahasan_11 = new LatLng(10.890812146896756,106.77954157329405);
        LatLng fahasan_12 = new LatLng(10.867913593968133,106.80940202238897);
        LatLng fahasan_13 = new LatLng(10.859742506281366,106.81345506151521);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Nhà sách Linh Trung")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(book).title("Nhà sách Nguyễn Văn Cừ")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(eon).title("Nhà sách Thủ Đức")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(fahasan_1).title("Nhà sách Phương Nam")).setVisible(true);

        mMap.addMarker(new MarkerOptions().position(fahasan_2).title("Nhà sách Bút Chì Vàng")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(fahasan_3).title("Nhà sách Tân Phú")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(fahasan_4).title("Nhà sách Như Trúc")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(fahasan_5).title("Nhà sách Linh Xuân")).setVisible(true);

        mMap.addMarker(new MarkerOptions().position(fahasan_6).title("Nhà sách Huyền Trang")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(fahasan_7).title("Nhà sách Hồng Bách")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(fahasan_8).title("Nhà sách Đăng Nguyên")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(fahasan_9).title("Nhà sách Nguyễn Văn Cừ")).setVisible(true);

        mMap.addMarker(new MarkerOptions().position(fahasan_10).title("Nhà sách FAHASA Dĩ An")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(fahasan_11).title("Nhà sách Sahara Book")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(fahasan_12).title("Nhà sách Văn Minh")).setVisible(true);
        mMap.addMarker(new MarkerOptions().position(fahasan_13).title("Nhà sách Thanh Phong")).setVisible(true);


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
    public void onclickEVent(){
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

    public void getBookStore(){
        Map<String, String> map = new HashMap<>();
        List<String> listMap = new ArrayList<>();


        try{
            FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bfr = new BufferedReader(isr);
            String line = bfr.readLine();
            while(line != null){
                String[] array = line.split(":");
                if(array.length == 2){
                   String[] todo = array[1].split(",");
                    LatLng latLng = new LatLng(Double.parseDouble(todo[0]) , Double.parseDouble(todo[1]));
                    mMap.addMarker(new MarkerOptions().position(latLng).title(array[0])).setVisible(true);



                }
                line = bfr.readLine();
            }
            fis.close();
            isr.close();
            bfr.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

//        try () {
//            FileInputStream input = new FileInputStream(filePath);
//            String line;
//            while ((line = br.readLine()) != null) {
//                // Xử lý dữ liệu trong từng hàng
//                listMap.add(line);
////                String[] list = line.split(":");
////                map.put(list[0], list[1]);
////                System.out.println(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
     public void readBookStoreMap(){
//        Map<String, String> map = getBookStore();
//         List<String> map = getBookStore();
//        Set<String> keySet = map.keySet();
//        for (String key: keySet){
////            String[] toaDo = map.get(key).split(",");
//            for (String l:map){
//                String[] i = l.split(":");
//                String[] toaDo = i[1].split(",");
//                LatLng latLng = new LatLng(Double.parseDouble(toaDo[0]) , Double.parseDouble(toaDo[1]));
//                mMap.addMarker(new MarkerOptions().position(latLng).title(i[0])).setVisible(true);
//            }
//            LatLng latLng = new LatLng(Double.parseDouble(toaDo[0]) , Double.parseDouble(toaDo[1]));
//            mMap.addMarker(new MarkerOptions().position(latLng).title(key)).setVisible(true);
//        }
     }
}