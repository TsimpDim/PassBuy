package thedreamteam.passbuy;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MoreInfoActivity extends PortraitActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private GsonWorker gson = new GsonWorker();
    private List<Store> stores = new ArrayList<>();
    private List<StoreLocation> storeLocations = new ArrayList<>();
    private Coordinates userCoordinates = new Coordinates();
    private LocationManager locationManager;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateUserLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {
            // todo?
        }

        @Override
        public void onProviderDisabled(String s) {
            // todo?
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_info);
        mRecyclerView = findViewById(R.id.rv);

        // Layout size remains fixed, improve performance
        mRecyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        // A vertically-scrollable collection of views
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Use an adapter to feed data into the RecyclerView
        mAdapter = new MoreInfoAdapter();
        mRecyclerView.setAdapter(mAdapter);

        // Draw line divider
        DividerItemDecoration lineDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(lineDivider);

        // Acquire user location
        if (Build.VERSION.SDK_INT > 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            else
                this.requestLocation();
        }
        else
            this.requestLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            this.requestLocation();
        }
    }

    public void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        long timeDelta = System.currentTimeMillis() - location.getTime();

        // Refresh location if our cached one is older than 2 minutes
        if (timeDelta > (1000 * 60 * 2)) {
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, mLocationListener, null);
            // onLocationChanged called
        }
        else
            this.updateUserLocation(location);
    }

    public void updateUserLocation(Location location) {
        userCoordinates.setLat(location.getLatitude());
        userCoordinates.setLng(location.getLongitude());
        locationManager.removeUpdates(mLocationListener);

        // Demo
        Runnable r = () -> {
            // Remove this, we will already have the store list
            stores = gson.getStores();
            storeLocations = gson.getNearbyStores(stores, userCoordinates);
        };

        Thread t = new Thread(r);
        t.start();
    }
}