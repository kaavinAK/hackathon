package com.example.four;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link googlemap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class googlemap extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  View rootview;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LatLng coordinates;

    public googlemap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment googlemap.
     */
    // TODO: Rename and change types and number of parameters
    public static googlemap newInstance(String param1, String param2) {
        googlemap fragment = new googlemap();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview=inflater.inflate(R.layout.fragment_googlemap, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment)
        getChildFragmentManager().findFragmentById(R.id.googlemapfrag);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        coordinates=latLng;
                        viewmodel shareddata=new ViewModelProvider(getActivity()).get(viewmodel.class);
                        shareddata.setdata(latLng);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);

                        markerOptions.title(latLng.latitude+":"+ latLng.longitude);

                        googleMap.clear();
                        //animationg the zoom marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng,10
                        ));

                        //adding marker on map
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });
        return rootview;
    }
}