package com.example.c347_l08_locatingaplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {
    Button btnNorth, btnCentral, btnEast;
    private GoogleMap map;
    Spinner spinner;
    String[] location = {"Choose a location", "North", "Central", "East"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        btnNorth = findViewById(R.id.btnNorth);
//        btnCentral = findViewById(R.id.btnCentral);
//        btnEast = findViewById(R.id.btnEast);
        spinner = findViewById(R.id.spinner2);

        ArrayAdapter aa = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,location);
        aa.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                LatLng poi_Singapore = new LatLng(1.3546,103.8672);
                LatLng poi_HQNorth = new LatLng(1.4406, 103.8010);
                //Add marker for poi_HQNorth
                Marker hqNorth = map.addMarker(new MarkerOptions()
                        .position(poi_HQNorth)
                        .title("HQ - North")
                        .snippet("Block 333, Admiralty Ave 3, 765654 \n" + "Operating hours: 10am-5pm\n" +
                                "Tel:65433456")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.star)));

                LatLng poi_HQCentral = new LatLng(1.3040, 103.8318);
                // Add marker for poi_HQCentral
                Marker hqCentral = map.addMarker(new MarkerOptions()
                        .position(poi_HQCentral)
                        .title("HQ - Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542\n"+
                                "Operating hours: 11am-8pm\n" +
                                "Tel:67788652")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


                LatLng poi_HQEast = new LatLng(1.3532, 103.9452);
                // Add marker for poi_HQTempines
                Marker hqEast = map.addMarker(new MarkerOptions()
                        .position(poi_HQEast)
                        .title("HQ - East")
                        .snippet("Block 555, Tampines Ave 3, 287788 \n" +
                                "Operating hours: 9am-5pm \n" +
                "Tel:66776677")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                // Add toast for marker
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String markerTitle = marker.getTitle();
                        Toast.makeText(MainActivity.this,markerTitle,Toast.LENGTH_LONG).show();
                        return false;
                    }
                });

                // 11 is the zoom lvl.
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Singapore,
                        11));
                UiSettings ui = map.getUiSettings();
                ui.setZoomControlsEnabled(true);

                UiSettings ui2 = map.getUiSettings();
                ui2.setCompassEnabled(true);

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }

            }
        });

        // set spinner
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(position == 0){
                   LatLng poi_Singapore = new LatLng(1.3546,103.8672);
                   map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Singapore,
                           11));
               }
               else if(position == 1){
                   LatLng pos = new LatLng(1.4406, 103.8010);;
                   CameraUpdate update = CameraUpdateFactory.newLatLngZoom(pos, 15);
                   map.moveCamera(update);
               }else if(position == 2){
                   LatLng pos = new LatLng(1.3040, 103.8318);
                   CameraUpdate update = CameraUpdateFactory.newLatLngZoom(pos, 15);
                   map.moveCamera(update);
               }else if(position == 3){
                   LatLng pos = new LatLng(1.3532, 103.9452);
                   CameraUpdate update = CameraUpdateFactory.newLatLngZoom(pos, 15);
                   map.moveCamera(update);
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

//        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                String markerTitle = marker.getTitle();
//                Toast.makeText(MainActivity.this,markerTitle,Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });




//        btnNorth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getBaseContext(), "HQ - North" , Toast.LENGTH_SHORT ).show();
//                LatLng pos = new LatLng(1.4406, 103.8010);;
//                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(pos, 15);
//                map.moveCamera(update);
//            }
//        });
//        btnCentral.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getBaseContext(), "HQ - Central" , Toast.LENGTH_SHORT ).show();
//                LatLng pos = new LatLng(1.3040, 103.8318);
//                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(pos, 15);
//                map.moveCamera(update);
//            }
//        });
//        btnEast.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getBaseContext(), "HQ - East" , Toast.LENGTH_SHORT ).show();
//                LatLng pos = new LatLng(1.3532, 103.9452);
//                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(pos, 15);
//                map.moveCamera(update);
//            }
//        });
    }
}