package thedreamteam.passbuy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import java.util.List;
import java.util.Locale;

public class BasketInfo extends PortraitActivity {

    private List<Product> products;
    private List<Integer> quantities;
    private int storeId;
    private StoreLocation storeLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket_info);

        ImageButton maps = findViewById(R.id.imageButton2);
        ImageButton backButton = findViewById(R.id.backButton);
        ImageButton homeScreen = findViewById(R.id.homeButton);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        Basket basket = (Basket) bundle.getSerializable("basket");
        storeLoc = (StoreLocation) bundle.getSerializable("storeLoc");
        storeId = bundle.getInt("store_id");
        products = basket.getProducts();
        quantities = basket.getQuantities();

        initRecyclerView();

        maps.setOnClickListener(v -> {
            String uri = String.format(Locale.ENGLISH, "google.navigation:q=%f,%f", storeLoc.getLat(), storeLoc.getLng());
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");
            v.getContext().startActivity(intent);
        });

        backButton.setOnClickListener(v -> onBackPressed());

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
        BasketInfoAdapter adapter = new BasketInfoAdapter(products, quantities, storeId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
