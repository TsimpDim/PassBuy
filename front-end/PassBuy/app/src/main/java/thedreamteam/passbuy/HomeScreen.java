package thedreamteam.passbuy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeScreen extends PortraitActivity implements PopupQuantityDialog.DialogListener {

    public static final int super_market_number = 4;

    private Basket basket = new Basket();
    private List<Product> products = new ArrayList<Product>();
    private GsonWorker gson = new GsonWorker();
    private HomeScreenBasketAdapter mAdapter = new HomeScreenBasketAdapter(this, basket.getProducts(), basket.getQuantities(), basket);
    private List<Store> stores = new ArrayList<Store>();
    private String best_store;
    private double best_price ;
    private Context mContext;

    private ImageButton delete_button;
    private ImageButton more_info_button;
    private ImageButton search_button;
    private TextView best_sum;
    private TextView best_supermarket;
    private TextView empty_basket_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        mContext = this.getBaseContext();

        delete_button = findViewById(R.id.delete_button);
        more_info_button = findViewById(R.id.more_info_button);
        best_sum = findViewById(R.id.sum_price_text);
        best_supermarket = findViewById(R.id.supermarket_text);
        search_button = findViewById(R.id.searchButton);
        empty_basket_text = findViewById(R.id.empty_basket_text);


        best_sum.setSelected(true);
        best_supermarket.setSelected(true);

        //Will work when popup is ready
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if(bundle !=null){
            basket = (Basket) bundle.getSerializable("basket");
        }

        //for now we have these
//DUMMY DATA
        //WILL DELETE.
        /*List<Price> prices = new ArrayList<Price>();
        Price price1 = new Price();
        Price price2 = new Price();
        Price price3 = new Price();
        Price price4 = new Price();
        price1.setStoreId(1);
        price1.setPrice(6.60);
        price2.setStoreId(2);
        price2.setPrice(6.1);
        price3.setStoreId(3);
        price3.setPrice(6.4);
        price4.setStoreId(4);
        price4.setPrice(2.43);
        prices.add(price1);
        prices.add(price2);
        prices.add(price3);
        prices.add(price4);
        Product product = new Product("ΠΟΡΤΟΚΑΛΙΑ ΗΜΑΘΙΑΣ", "Kafes me plousio gala", "http://dummy", 2,1230, prices);
        products.add(product);
        ArrayList<Integer> qs = new ArrayList<Integer>();
        qs.add(5);
        //Get your products into your basket
        basket.setProducts(products);
        basket.setQuantities(qs);
*/


        initRecyclerView();
        mAdapter.replaceList(basket);
        mAdapter.notifyDataSetChanged();




        if(!basket.getProducts().isEmpty()){
            //UPDATE BEST PRICE and SUPERMARKET NAME
            empty_basket_text.setText("");
            updateBestSum();

        }
        else{
            //if basket is empty, do nothing.
            empty_basket_text.setText("Το καλάθι σου είναι άδειο :(");
            best_sum.setText("-");
            best_supermarket.setText("");
        }



        //Open next activity (CategoriesSearchPage) when is clicked.
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =  new Intent(view.getContext() , CategoriesSearchPage.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("basket", basket);
                intent.putExtra("bundle",bundle);

                startActivity(intent);

            }
        });



        //Delete Button Functions Onclick
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Delete Yes or No Dialog
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                basket.getProducts().clear();
                                basket.getQuantities().clear();
                                basket.getTotalprices().clear();
                                mAdapter.replaceList(basket);
                                mAdapter.notifyDataSetChanged();
                                empty_basket_text.setText("Το καλάθι σου είναι άδειο :(");
                                best_sum.setText("-");
                                best_supermarket.setText("");
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //do nothing
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this);
                LayoutInflater inflater = HomeScreen.this.getLayoutInflater();
                builder.setMessage("Θέλεις να διαγραφεί το υπάρχων καλάθι?").setPositiveButton("ΔΙΑΓΡΑΦΗ", dialogClickListener)
                        .setNegativeButton("ΑΚΥΡΟ", dialogClickListener).show();

            }
        });




    }



    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_home_screen);

        //RecyclerView List constructor
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void updateBestSum(){

        //initialize total_prices
        //DUMMY DATA AS WELL
        List<Price> total_prices = new ArrayList<Price>();
        for(int i=0 ; i<super_market_number ; i++){
            Price dummy = new Price();
            dummy.setPrice(0.00);
            dummy.setStoreId(i+1);
            total_prices.add(dummy);
        }


        //Get the sum for every store
        for(int y=0 ; y<super_market_number; y++){

            total_prices.get(y).setStoreId(y+1);

            for(int i=0 ; i<basket.getProducts().size() ; i++){
                double temp_price = total_prices.get(y).getPrice() + (basket.getProducts().get(i).getPrices().get(y).getPrice())*(basket.getQuantities().get(i));

                total_prices.get(y).setPrice(temp_price);
            }
        }


        //Sort Total Prices
        //THIS SHOULD CHANGE WITH JAVA 8 WAY
        Collections.sort(total_prices, new PricesComparator());



        //Get Store names
        new Thread() {
            public void run(){
                try {
                    stores = gson.getStores();
                    Log.i("Hey", "Stores Obtained");
                    best_store = stores.get(0).getName();
                    //Get best store name
                    for (Store store : stores) {
                        if (total_prices.get(0).getStoreId() == store.getId()) {
                            best_store = store.getName();
                        }
                    }
                    //run on UI thread cause its a TextView
                    runOnUiThread( new Runnable() {
                        @Override
                        public void run() {
                            best_supermarket.setText(best_store);
                            Log.i("Hey", "SUPERMARKET TEXT SET");
                        }
                    });
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();





        //Best price (double)
        best_price = total_prices.get(0).getPrice();

        //Replace this with a String , its double
        best_sum.setText(String.format(" %.2f", total_prices.get(0).getPrice()) + "€");


    }

    @Override
    public void getQuantity(Product product, int q) {
        if(q==0){
            basket.getQuantities().remove(basket.getProducts().indexOf(product));
            basket.getProducts().remove(product);
            mAdapter.replaceList(basket);
            mAdapter.notifyDataSetChanged();
            updateBestSum();
        }
        else{
            basket.getQuantities().set(basket.getProducts().indexOf(product),q);
            mAdapter.replaceList(basket);
            mAdapter.notifyDataSetChanged();
            updateBestSum();
        }
    }


}


//Comparator that compares prices
//THIS SHOULD CHANGE WITH JAVA 8 WAY
class PricesComparator implements Comparator {
    public int compare(Object o1,Object o2){
        Price s1=(Price)o1;
        Price s2=(Price)o2;

        if(((Price) o1).getPrice()==((Price) o2).getPrice())
            return 0;
        else if(((Price) o1).getPrice()>((Price) o2).getPrice())
            return 1;
        else
            return -1;
    }



}