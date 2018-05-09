package thedreamteam.passbuy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class BasketInfo extends AppCompatActivity {


    private ArrayList<String> itemnames = new ArrayList<>();
    private ArrayList<String> itemq = new ArrayList<>();
    private ArrayList<String> itemprices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket_info);
        initImageBitmaps();

    }


    private void initImageBitmaps(){

        itemnames.add("Πορτοκάλια Ημαθίας");
        itemq.add("KOLLO");
        itemprices.add("Porto");

        itemnames.add("Πορτοκάλια22");
        itemq.add("KOL35LO");
        itemprices.add("Port4o");

        itemnames.add("Πορτοκάλια 43");
        itemq.add("KOL3LO");
        itemprices.add("Por5to");

        initRecyclerView();

    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view22);

        recyclerView.setHasFixedSize(true);
        BasketInfoAdapter adapter = new BasketInfoAdapter(this, itemnames, itemq , itemprices);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
