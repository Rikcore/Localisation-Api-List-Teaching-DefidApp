package com.waxym.defibapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecyclerDefibAdapter.ItemClickListener {

    private LocationManager locationManager;
    private LocationListener locationListener;

    private Location currentLocation;

    private RecyclerDefibAdapter recyclerDefibAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.e("LOCATION CHANGED", "NEW LOCATION");
                currentLocation = location;
                getList();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermissions();
    }

    private void checkPermissions(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            initLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initLocation();
                } else {
                    Log.e("ERROR", "DENIED");
                }
            }
        }
    }

    private void initLocation(){
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 5, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 5, locationListener);
            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            getList();
        } catch (SecurityException e){
            Log.e("Location error", "Location error");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    private void getList(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://data.toulouse-metropole.fr")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<ResultModel> call = retrofitInterface.getResult("defibrillateurs", 100);

        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                Log.e("SUCCESS", "SUCCESS");
                if(response.isSuccessful() && response.body() != null){
                    List<Record> list = response.body().getRecords();
                    calculateDistance(list);
                }
            }
            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                Log.e("ERROR", "ERROR");
            }
        });
    }

    private void calculateDistance(List<Record> list){
        for(int i = 0; i < list.size(); i++){
            Location defibLocation = new Location("");
            defibLocation.setLatitude(list.get(i).getFields().getGeoPoint2d().get(0));
            defibLocation.setLongitude(list.get(i).getFields().getGeoPoint2d().get(1));

            int distanceInMeters = (int)currentLocation.distanceTo(defibLocation);
            list.get(i).getFields().setDistance(distanceInMeters);
        }
        filterByDistance(list);
    }

    private void filterByDistance(List<Record> list){
        Collections.sort(list, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return Integer.compare(o1.getFields().getDistance(), o2.getFields().getDistance());
            }
        });
        displayList(list);
    }

    private void displayList(List<Record> list){
        /*final ListView listView = findViewById(R.id.listView);
        final DefibAdapter defibAdapter = new DefibAdapter(this, list);
        listView.setAdapter(defibAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Record record = (Record) listView.getAdapter().getItem(position);
                double latitude = record.getFields().getGeoPoint2d().get(0);
                double longitude = record.getFields().getGeoPoint2d().get(1);
                String url = "https://www.google.com/maps/dir/?api=1&destination=" + latitude + "," + longitude + "&travelmode=walking";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });*/

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerDefibAdapter = new RecyclerDefibAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerDefibAdapter.setClickListener(this);
        recyclerView.setAdapter(recyclerDefibAdapter);


    }

    @Override
    public void onItemClick(View view, int position) {
        Record record = recyclerDefibAdapter.getItem(position);
        double latitude = record.getFields().getGeoPoint2d().get(0);
        double longitude = record.getFields().getGeoPoint2d().get(1);
        String url = "https://www.google.com/maps/dir/?api=1&destination=" + latitude + "," + longitude + "&travelmode=walking";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
