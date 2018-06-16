package thedreamteam.passbuy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BasketInfo extends PortraitActivity {


    private List<Product> products;
    private List<Integer> quantities;
    private Coordinates userCoordinates;
    private Basket basket;
    private int store_id;
    private ImageButton maps;
    private StoreLocation storeLoc;
    private ImageButton homeScreen;
    private ImageButton backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket_info);


        maps = findViewById(R.id.imageButton2);
        backButton = findViewById(R.id.backButton);
        homeScreen = findViewById(R.id.homeButton);



        Bundle bundle = getIntent().getBundleExtra("bundle");
        basket = (Basket) bundle.getSerializable("basket");
        //products = (List<Product>) bundle.getSerializable("products");
        //quantities = (List<Integer>) bundle.getSerializable("quantities");
        userCoordinates = (Coordinates) bundle.getSerializable("userCo");
        storeLoc = (StoreLocation) bundle.getSerializable("storeLoc");
        store_id = bundle.getInt(" store_id");
        products = basket.getProducts();
        quantities = basket.getQuantities();


        initRecyclerView();


        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uri = String.format(Locale.ENGLISH, "google.navigation:q=%f,%f", storeLoc.getLat(), storeLoc.getLng());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                v.getContext().startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        homeScreen.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HomeScreen.class);

            Bundle bundle12 = new Bundle();
            bundle12.putSerializable("basket", basket);
            intent.putExtra("bundle", bundle12);

            startActivity(intent);
        });




    }



    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view22);

        recyclerView.setHasFixedSize(true);
        BasketInfoAdapter adapter = new BasketInfoAdapter(this, products, quantities,store_id);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
