package com.bixspace.ciclodevida.presentation.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bixspace.ciclodevida.R;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by junior on 28/11/16.
 */

public class MapFragment extends Fragment {


    MapView mapView;
    private GoogleMap map;

    public MapFragment() {
        // Requires empty public constructor
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) root.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;


                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                map.setMyLocationEnabled(true);


                //añade un marcador a un punto del mapa
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(-12.174552, -77.033692))
                        .title("Playa la Herradura"));



                //aplica un zoom determinado a cierto punto
                CameraUpdate center =
                        CameraUpdateFactory.newLatLng(new LatLng(-12.174552, -77.033692));
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(26);

                if (center != null)
                    map.moveCamera(center);
                if (zoom != null)
                    map.animateCamera(zoom);



            }
        });
        return root;


    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        mapView.onDestroy();

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //obsoleto
        // map =mapView.getMap();




    }


}
