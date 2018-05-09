package thedreamteam.passbuy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class CategoriesSearchPage extends AppCompatActivity {


    private ArrayList<String> categoryname = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_search_page);
        initImageBitmaps();

    }


    private void initImageBitmaps(){

        categoryname.add("Πορτοκάλια Ημαθίας");

        initRecyclerView();

    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view33);

        recyclerView.setHasFixedSize(true);
        CategoriesSearchPageAdapter adapter = new CategoriesSearchPageAdapter(this, categoryname);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}

