package thedreamteam.passbuy;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BasketInfo extends PortraitActivity {


    private List<String> itemNames = new ArrayList<>();
    private List<String> itemq = new ArrayList<>();
    private List<String> itemPrices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket_info);
        initImageBitmaps();
    }


    private void initImageBitmaps() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view22);

        recyclerView.setHasFixedSize(true);
        BasketInfoAdapter adapter = new BasketInfoAdapter(this, itemNames, itemq, itemPrices);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
