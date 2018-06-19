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

    private static final String BUNDLE_NAME = "bundle";
    private static final String BASKET_NAME = "basket";
    private List<Category> categoryNames = new ArrayList<>();
    private Basket basket = new Basket();
    private GsonWorker gson = new GsonWorker();
    private CategoriesSearchPageAdapter mAdapter;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_search_page);

        TextView searchText = findViewById(R.id.search_text);
        ImageButton searchButton = findViewById(R.id.search_button);
        ImageButton homeScreen = findViewById(R.id.homeButton);
        backButton = findViewById(R.id.backButton);

        //get basket from previous activity
        Bundle bundle = getIntent().getBundleExtra(BUNDLE_NAME);
        basket = (Basket) bundle.getSerializable(BASKET_NAME);

        initRecyclerView();

        new Thread(() -> {
            categoryNames = gson.getCategories();

            if (categoryNames != null) {
                mAdapter.replaceList(categoryNames);
                runOnUiThread((mAdapter::notifyDataSetChanged));
            }
        }).start();

        searchButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SearchResults.class);

            Bundle bundle1 = new Bundle();
            bundle1.putSerializable(BASKET_NAME, basket);
            bundle1.putCharSequence("search_text", searchText.getText().toString());
            bundle1.putInt("category_id", -1);
            intent.putExtra(BUNDLE_NAME, bundle1);

            startActivity(intent);
        });

        homeScreen.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HomeScreen.class);

            Bundle bundle12 = new Bundle();
            bundle12.putSerializable(BASKET_NAME, basket);
            intent.putExtra(BUNDLE_NAME, bundle12);

            startActivity(intent);
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HomeScreen.class);

            Bundle bundle2 = new Bundle();
            bundle2.putSerializable(BASKET_NAME, basket);
            intent.putExtra(BUNDLE_NAME, bundle2);

            startActivity(intent);
        });

        searchText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchButton.performClick();
                return true;
            }
            return false;
        });
    }

    @Override
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

