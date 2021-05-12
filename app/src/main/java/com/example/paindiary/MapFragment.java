package com.example.paindiary;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.paindiary.databinding.FragmentMapBinding;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapFragment extends Fragment {

    private FragmentMapBinding binding;
    private MapView mapView;
    private Symbol symbol;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String token = getString(R.string.mapbox_access_token);
        Mapbox.getInstance(getContext().getApplicationContext(), token);
        binding = FragmentMapBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // default mapview
        final LatLng latLng= new LatLng(35.652832, 139.839478);
        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        //TODO mapbox marker
//                        SymbolManager symbolManager = new SymbolManager(binding.mapView, mapboxMap, style);
//                        symbolManager.setIconAllowOverlap(true);
//                        symbolManager.setTextAllowOverlap(true);
//                        SymbolOptions symbolOptions = new SymbolOptions()
//                                .withLatLng(new LatLng(latLng))
//                                .withIconImage(symbol.getIconImage())
//                                .withIconSize(1.3f);
//                        symbol = symbolManager.create(symbolOptions);

                        CameraPosition position = new CameraPosition.Builder()
                                .target(latLng)
                                .zoom(13)
                                .build();
                        mapboxMap.setCameraPosition(position);

                    }
                });
            }
        });

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get userInput
                String address = binding.etAddress.getText().toString();
                LatLng newLatLng = getLatLong(address);

                binding.mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                            @Override
                            public void onStyleLoaded(@NonNull Style style) {
                                CameraPosition position = new CameraPosition.Builder()
                                        .target(newLatLng)
                                        .zoom(13)
                                        .build();
                                mapboxMap.setCameraPosition(position);

                            }
                        });
                    }
                });
            }
        });

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        binding.mapView.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }
    @Override
    public void onStop() {
        super.onStop();
        binding.mapView.onStop();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.mapView.onSaveInstanceState(outState);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public LatLng getLatLong(String newAddress){
        double latitude = 0.0;
        double longitude = 0.0;
        // String address = "322 Coventry St, South Melbourne VIC 3205";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(newAddress, 1);
            if (addresses.size() > 0) {
                latitude = addresses.get(0).getLatitude();
                longitude = addresses.get(0).getLongitude();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LatLng latLng= new LatLng(latitude, longitude);
        return latLng;
    }

}