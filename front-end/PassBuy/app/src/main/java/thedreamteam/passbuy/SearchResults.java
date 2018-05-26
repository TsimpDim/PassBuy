package thedreamteam.passbuy;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchResults extends PortraitActivity {


    private GsonWorker gson = new GsonWorker();
    private List<Product> products = new ArrayList<>();
    private Basket basket = new Basket();
    private SearchResultsAdapter mAdapter = new SearchResultsAdapter(this, products,basket);
    private ImageButton search_button;
    private EditText search_text;
    private Thread t = new Thread();
    private String searched_text;
    private int category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);

        search_button = findViewById(R.id.search_button);
        search_text = findViewById(R.id.search_text);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        basket = (Basket) bundle.getSerializable("basket");
        searched_text = (String) bundle.getCharSequence("search_text");
        category_id = (Integer) bundle.getInt("category_id");

        initRecyclerView();

        if(!(searched_text.isEmpty())){
            search_text.setText(searched_text);}
        else{
            search_text.setText("");
        }

        if(category_id == -1){
            getProductsByName();
        }
        else{
            getProductsByCategory();
        }

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProductsByName();
            }
        });


    }


    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_searched_items);

        recyclerView.setHasFixedSize(true);
        //SearchResultsAdapter adapter = new SearchResultsAdapter(this, products,basket);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void getProductsByName(){

        Runnable r = () -> {
            products = gson.getSearchResults(search_text.getText().toString());
            if (products != null) {
                mAdapter.replaceList(products);
                runOnUiThread(new Thread(() -> mAdapter.notifyDataSetChanged()));
            }
            else{
                runOnUiThread(new Thread(() -> Toast.makeText(getApplicationContext(), "Δεν υπάρχει προϊόν με αυτή την ονομασία.",
                        Toast.LENGTH_LONG).show()));
            }
        };

        if(t.isAlive()){
            t.interrupt();
        }
        t = new Thread(r);
        t.start();

    }

    private void getProductsByCategory(){

        Runnable r = () -> {
            products = gson.getProductsByCategory(category_id);
            if (products != null) {
                mAdapter.replaceList(products);
                runOnUiThread(new Thread(() -> mAdapter.notifyDataSetChanged()));
            }
            else{
                Toast.makeText(getApplicationContext(), "Δεν υπάρχει προϊόν με αυτή την ονομασία.",
                        Toast.LENGTH_LONG).show();
            }
        };

        if(t.isAlive()){
            t.interrupt();
        }
        t = new Thread(r);
        t.start();

    }

}
