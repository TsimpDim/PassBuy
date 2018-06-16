package thedreamteam.passbuy;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MoreInfo extends PortraitActivity {

    private MoreInfoAdapter mAdapter;
    private GsonWorker gson = new GsonWorker();
    private List<Store> stores = new ArrayList<>();
    private List<StoreLocation> storeLocations = new ArrayList<>();
    private Coordinates userCoordinates = new Coordinates();
    private LocationManager locationManager;
    private Basket basket;
    private String bestStore;
    private Double bestPrice;
    private ImageButton homeScreen;
    private ImageButton backButton;
    private TextView bestPriceText;
    private TextView bestSupermarket;

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

        backButton = findViewById(R.id.backButton);
        homeScreen = findViewById(R.id.homeButton);



        //get basket from previous activity
        Bundle bundle = getIntent().getBundleExtra("bundle");
        basket = (Basket) bundle.getSerializable("basket");
        bestPrice = (Double) bundle.getDouble("best_price");
        bestStore = (String) bundle.getCharSequence("best_super");
        stores = (List<Store>) bundle.getSerializable("stores");

        Collections.sort(basket.getTotalPrices(), new IdsComparator());


        bestPriceText = findViewById(R.id.best_price);
        bestSupermarket = findViewById(R.id.best_supermarket);

        bestPriceText.setText(String.format("%.2f €", bestPrice));
        bestSupermarket.setText(bestStore);

        bestSupermarket.setSelected(true);
        bestPriceText.setSelected(true);


        // Acquire user location
        if (Build.VERSION.SDK_INT > 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            else
            {this.requestLocation();}
        } else
        {this.requestLocation(); }


        initRecyclerView();

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HomeScreen.class);

            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("basket", basket);
            intent.putExtra("bundle", bundle2);

            startActivity(intent);
        });

        homeScreen.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HomeScreen.class);

            Bundle bundle12 = new Bundle();
            bundle12.putSerializable("basket", basket);
            intent.putExtra("bundle", bundle12);

            startActivity(intent);
        });


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

        if (location != null) {
            long timeDelta = System.currentTimeMillis() - location.getTime();

            // Refresh location if our cached one is older than 2 minutes
            if (timeDelta > (1000 * 60 * 2))
                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, mLocationListener, null);
            else
                this.updateUserLocation(location);
        } else
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, mLocationListener, null);
    }

    private void initRecyclerView(){

       RecyclerView mRecyclerView;
       mRecyclerView = findViewById(R.id.rv);

        // Layout size remains fixed, improve performance
        mRecyclerView.setHasFixedSize(true);

        // Use an adapter to feed data into the RecyclerView
        mAdapter = new MoreInfoAdapter(this, basket,stores, storeLocations, userCoordinates);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Draw line divider
        DividerItemDecoration lineDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(lineDivider);
    }

    public void updateUserLocation(Location location) {
        userCoordinates.setLat(location.getLatitude());
        userCoordinates.setLng(location.getLongitude());
        locationManager.removeUpdates(mLocationListener);

        // Demo
        Runnable r = () -> {
            // Remove this, we will already have the store list
            storeLocations = gson.getNearbyStores(stores, userCoordinates);
            mAdapter.replaceUserLocation(userCoordinates);
            mAdapter.replaceLocations(storeLocations);
            runOnUiThread(new Thread(() -> mAdapter.notifyDataSetChanged()));
            Log.i("hey i got urstores", "updateUserLocation: yo");
        };

        Thread t = new Thread(r);
        t.start();}
        }


//Comparator that compares prices
//THIS SHOULD CHANGE WITH JAVA 8 WAY
class IdsComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        //Store s1=(Store)o1;
        //Price s2=(Price)o2;

        if (((StorePrice) o1).getStoreId() == ((StorePrice) o2).getStoreId())
            return 0;
        else if (((StorePrice) o1).getStoreId() > ((StorePrice) o2).getStoreId())
            return 1;
        else
            return -1;
    }

}
