package com.example.four;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    FusedLocationProviderClient fusedLocationProviderClient;
    int Latitude;
    int Longitude;
    int yLatitude;
    int yLongitude;
    viewmodel shareddata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment googlemap = new googlemap();
        getSupportFragmentManager().beginTransaction().replace(R.id.googlemapfragment,googlemap).commit();
        shareddata=new ViewModelProvider(this).get(viewmodel.class);
        shareddata.getShareddata().observe(this,item->{
           Latitude=(int)item.latitude;
           Longitude=(int)item.longitude;

        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
findViewById(R.id.getlocation).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,weatherdisplay.class);

        intent.putExtra("latitude",String.valueOf(Latitude));
        intent.putExtra("longitude",String.valueOf(Longitude));
        startActivity(intent);
    }
});
findViewById(R.id.yourweather).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,weatherdisplay.class);

        intent.putExtra("latitude",String.valueOf(yLatitude));
        intent.putExtra("longitude",String.valueOf(yLongitude));
        startActivity(intent);



    }



}

);
 /*findViewById(R.id.getlocation).setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {*/
        if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)
         {
             Log.d("getting", "onClick: ");
           getLocation();
         }
        else
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
    /* }*/
 /*});*/
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      //  super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            getLocation();
        } else {
            Toast.makeText(getApplicationContext(), "permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE
        );
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            Log.d("providerbefore", "getLocation: ");
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                    Log.d("beforelocation", "onComplete: "+location);
                        if(location!=null)
                        {
                            yLatitude=(int)location.getLatitude();
                            yLongitude=(int)location.getLongitude();
                            Log.d("locationlat", "onComplete: "+String.valueOf(location.getLatitude()));
                        }
                        else
                        {
                            LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                    .setInterval(10000)
                                    .setFastestInterval(1000)
                                    .setNumUpdates(1);
                            LocationCallback locationCallback = new LocationCallback(){
                                @Override
                                public void onLocationResult(LocationResult locationResult) {
                                    Location location1 = locationResult.getLastLocation();
                                    yLatitude=(int)location1.getLatitude();
                                    yLongitude=(int)location1.getLongitude();
                                    Log.d("location ", "onLocationResult: "+location1.getLatitude());
                                }
                            };
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
                        }
                }
            });
        }else
        {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK ));
        }
    }


}