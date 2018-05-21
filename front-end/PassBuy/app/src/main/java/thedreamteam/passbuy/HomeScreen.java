package thedreamteam.passbuy;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class HomeScreen extends PortraitActivity {


    private ArrayList<String> itemnames = new ArrayList<>();
    private ArrayList<String> itemq = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        initImageBitmaps();

    }


    private void initImageBitmaps(){

        //add items

        initRecyclerView();

    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_home_screen);

        recyclerView.setHasFixedSize(true);
        HomeScreenBasketAdapter adapter = new HomeScreenBasketAdapter(this, itemnames, itemq);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}

