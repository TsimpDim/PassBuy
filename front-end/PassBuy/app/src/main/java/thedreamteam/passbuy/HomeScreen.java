package thedreamteam.passbuy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeScreen extends PortraitActivity implements PopupQuantityDialog.DialogListener {

    public static final int supermarketNumber = 4;

    private Basket basket = new Basket();
    private GsonWorker gson = new GsonWorker();
    private GsonFileWorker gsonfw = new GsonFileWorker();
    private HomeScreenAdapter mAdapter = new HomeScreenAdapter(this, basket.getProducts(), basket.getQuantities(), basket);
    private List<Store> stores = new ArrayList<>();
    private String bestStore;
    private double bestPrice;
    private Context mContext;

    private ImageButton deleteButton;
    private ImageButton moreInfoButton;
    private ImageButton searchButton;
    private TextView bestSum;
    private TextView bestSupermarket;
    private TextView emptyBasketText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        mContext = this.getBaseContext();

        deleteButton = findViewById(R.id.delete_button);
        moreInfoButton = findViewById(R.id.more_info_button);
        bestSum = findViewById(R.id.sum_price_text);
        bestSupermarket = findViewById(R.id.supermarket_text);
        searchButton = findViewById(R.id.searchButton);
        emptyBasketText = findViewById(R.id.empty_basket_text);


        bestSum.setSelected(true);
        bestSupermarket.setSelected(true);

        //Will work when popup is ready
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null) {
            basket = (Basket) bundle.getSerializable("basket");
            gsonfw.saveToFile(basket, mContext);
        }
        else
            basket = gsonfw.loadFromFile(mContext);


        initRecyclerView();
        mAdapter.replaceList(basket);
        mAdapter.notifyDataSetChanged();

        if (!basket.getProducts().isEmpty()) {
            //UPDATE BEST PRICE and SUPERMARKET NAME
            emptyBasketText.setText("");
            updateBestSum();

        } else {
            //if basket is empty, do nothing.
            emptyBasketText.setText("Το καλάθι σου είναι άδειο :(");
            bestSum.setText("-");
            bestSupermarket.setText("");
        }


        //Open next activity (CategoriesSearchPage) when is clicked.
        searchButton.setOnClickListener(view -> {
            Intent intent1 = new Intent(view.getContext(), CategoriesSearchPage.class);

            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("basket", basket);
            intent1.putExtra("bundle", bundle1);

            startActivity(intent1);
        });


        moreInfoButton.setOnClickListener(v -> {
            if(!basket.getProducts().isEmpty()) {
                Intent intent12 =  new Intent(v.getContext() , MoreInfo.class);
                Bundle bundle12 = new Bundle();
                bundle12.putSerializable("basket", basket);
                bundle12.putSerializable("stores", (Serializable) stores);
                bundle12.putCharSequence("best_super",bestStore);
                bundle12.putDouble("best_price",bestPrice);
                intent12.putExtra("bundle", bundle12);
                startActivity(intent12);
            }
            else
                Toast.makeText(v.getContext(), "Το καλάθι σου είναι άδειο. Πρόσθεσε προϊόντα.", Toast.LENGTH_SHORT).show();
        });

        //Delete Button Functions Onclick
        deleteButton.setOnClickListener(view -> {

            //Delete Yes or No Dialog
            DialogInterface.OnClickListener dialogClickListener = (dialog, decision) -> {
                if (decision == DialogInterface.BUTTON_POSITIVE) {
                    basket.getProducts().clear();
                    basket.getQuantities().clear();
                    basket.getTotalPrices().clear();
                    mAdapter.replaceList(basket);
                    mAdapter.notifyDataSetChanged();
                    emptyBasketText.setText("Το καλάθι σου είναι άδειο :(");
                    bestSum.setText("-");
                    bestSupermarket.setText("");
                    gsonfw.saveToFile(basket, mContext);
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this);
            builder.setMessage("Θέλεις να διαγραφεί το υπάρχων καλάθι?").setPositiveButton("ΔΙΑΓΡΑΦΗ", dialogClickListener)
                    .setNegativeButton("ΑΚΥΡΟ", dialogClickListener).show();
        });
    }


    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_home_screen);

        //RecyclerView List constructor
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void updateBestSum() {

        //initialize total_prices
        List<StorePrice> totalPrices = new ArrayList<>();
        for (int i = 0; i < supermarketNumber; i++) {
            StorePrice dummy = new StorePrice();
            dummy.setPrice(0.00);
            dummy.setStoreId(i + 1);
            totalPrices.add(dummy);
        }


        //Get the sum for every store
        for (int y = 0; y < supermarketNumber; y++) {

            totalPrices.get(y).setStoreId(y + 1);

            for (int i = 0; i < basket.getProducts().size(); i++) {
                double temp_price = totalPrices.get(y).getPrice() + (basket.getProducts().get(i).getPrices().get(y).getPrice()) * (basket.getQuantities().get(i));

                totalPrices.get(y).setPrice(temp_price);
            }
        }


        basket.setTotalPrices(totalPrices);

        //Sort Total Prices
        //THIS SHOULD CHANGE WITH JAVA 8 WAY
        Collections.sort(totalPrices, new PricesComparator());


        //Get Store names
        new Thread(() -> {
            stores = gson.getStores();
            if (stores != null) {
                bestStore = stores.get(0).getName();
                //Get best store name
                for (Store store : stores) {
                    if (totalPrices.get(0).getStoreId() == store.getId()) {
                        bestStore = store.getName();
                    }
                }
                //run on UI thread cause its a TextView
                runOnUiThread(() -> bestSupermarket.setText(bestStore));
            }
        }).start();


        //Best price (double)
        bestPrice = totalPrices.get(0).getPrice();

        //Replace this with a String , its double
        bestSum.setText(String.format(" %.2f", totalPrices.get(0).getPrice()) + "€");


    }

    @Override
    public void getQuantity(Product product, int q) {
        if (q == 0) {
            basket.getQuantities().remove(basket.getProducts().indexOf(product));
            basket.getProducts().remove(product);
            mAdapter.replaceList(basket);
            mAdapter.notifyDataSetChanged();
            updateBestSum();
        } else {
            basket.getQuantities().set(basket.getProducts().indexOf(product), q);
            mAdapter.replaceList(basket);
            mAdapter.notifyDataSetChanged();
            updateBestSum();
        }
        gsonfw.saveToFile(basket, mContext);
    }


}


//Comparator that compares prices
class PricesComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        if (((StorePrice) o1).getPrice() == ((StorePrice) o2).getPrice())
            return 0;
        else if (((StorePrice) o1).getPrice() > ((StorePrice) o2).getPrice())
            return 1;
        else
            return -1;
    }
}