package com.example.mapsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMaps;
    SearchView mapSearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapSearchView=findViewById(R.id.mapSearch);
        SupportMapFragment mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location=mapSearchView.getQuery().toString();
                List<Address>addressList=null;
                if (location!=null){
                    Geocoder geocoder=new Geocoder(MainActivity.this);
                    try {
                        addressList=geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Address address=addressList.get(0);
                    LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
                    googleMaps.addMarker(new MarkerOptions().position(latLng).title(location));
                    googleMaps.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        mapFragment.getMapAsync(MainActivity.this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMaps=googleMap;

    }
}