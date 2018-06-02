package thedreamteam.passbuy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CategoriesSearchPage extends PortraitActivity {


    private List<Category> categorynames = new ArrayList<>();
    private Basket basket = new Basket();
    private GsonWorker gson = new GsonWorker();
    private CategoriesSearchPageAdapter mAdapter = new CategoriesSearchPageAdapter(this, categorynames, basket);

    private TextView search_text;
    private ImageButton search_button;
    private ImageButton back_button;
    private ImageButton home_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_search_page);

        search_text = findViewById(R.id.search_text);
        search_button = findViewById(R.id.search_button);
        back_button = findViewById(R.id.backButton);
        home_screen = findViewById(R.id.homeButton);


        //get basket from previous activity
        Bundle bundle = getIntent().getBundleExtra("bundle");
        basket = (Basket) bundle.getSerializable("basket");


        initRecyclerView();

        Thread t = new Thread(() -> {
            categorynames = gson.getCategories();
            Log.i("Hey","Categories Obtained");
            if(categorynames!= null){
                mAdapter.replaceList(categorynames);
                runOnUiThread(new Thread(() -> mAdapter.notifyDataSetChanged()));}
            Log.i("runOnUi","Success");

        });
        t.start();


        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), SearchResults.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("basket", basket);
                bundle.putCharSequence("search_text",search_text.getText().toString());
                bundle.putInt("category_id", -1);
                intent.putExtra("bundle",bundle);

                startActivity(intent);
            }
        });

        home_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(v.getContext() , HomeScreen.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("basket", basket);
                intent.putExtra("bundle",bundle);

                startActivity(intent);
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(v.getContext() , HomeScreen.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("basket", basket);
                intent.putExtra("bundle",bundle);

                startActivity(intent);
            }
        });




    }

    public void onBackPressed(){

        back_button.performClick();

    }


    private void initImageBitmaps(){

        initRecyclerView();

    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view33);

        recyclerView.setHasFixedSize(true);
        CategoriesSearchPageAdapter adapter = new CategoriesSearchPageAdapter(this, categorynames,basket);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}

