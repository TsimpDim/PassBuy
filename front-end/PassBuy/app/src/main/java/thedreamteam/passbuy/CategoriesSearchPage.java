package thedreamteam.passbuy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CategoriesSearchPage extends PortraitActivity {


    private List<Category> categoryNames = new ArrayList<>();
    private Basket basket = new Basket();
    private GsonWorker gson = new GsonWorker();

    private CategoriesSearchPageAdapter mAdapter;
    private TextView searchText;
    private ImageButton searchButton;
    private ImageButton backButton;
    private ImageButton homeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_search_page);

        searchText = findViewById(R.id.search_text);
        searchButton = findViewById(R.id.search_button);
        backButton = findViewById(R.id.backButton);
        homeScreen = findViewById(R.id.homeButton);


        //get basket from previous activity
        Bundle bundle = getIntent().getBundleExtra("bundle");
        basket = (Basket) bundle.getSerializable("basket");


        initRecyclerView();

        new Thread(() -> {
            categoryNames = gson.getCategories();

            if (categoryNames != null) {
                mAdapter.replaceList(categoryNames);
                runOnUiThread(new Thread(() -> mAdapter.notifyDataSetChanged()));
            }
        }).start();


        searchButton.setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext(), SearchResults.class);

            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("basket", basket);
            bundle1.putCharSequence("search_text", searchText.getText().toString());
            bundle1.putInt("category_id", -1);
            intent.putExtra("bundle", bundle1);

            startActivity(intent);
        });

        homeScreen.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HomeScreen.class);

            Bundle bundle12 = new Bundle();
            bundle12.putSerializable("basket", basket);
            intent.putExtra("bundle", bundle12);

            startActivity(intent);
        });

        searchText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchButton.performClick();
                return true;
            }
            return false;
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HomeScreen.class);

            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("basket", basket);
            intent.putExtra("bundle", bundle2);

            startActivity(intent);
        });
    }

    public void onBackPressed() {
        backButton.performClick();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view33);

        recyclerView.setHasFixedSize(true);

        mAdapter = new CategoriesSearchPageAdapter(this, categoryNames, basket);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}

