package thedreamteam.passbuy;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import java.util.ArrayList;

    public class SearchResults extends AppCompatActivity {


        private ArrayList<String> mitems = new ArrayList<>();
        private ArrayList<String> mimages = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.search_results);
            initImageBitmaps();

        }


        private void initImageBitmaps(){
            mimages.add("https://www.medicalland.gr/wp-content/uploads/2018/03/portokali.jpg");
            mitems.add("Πορτοκάλια Ημαθίας");

            mimages.add("https://www.bambakia.gr/wp-content/uploads/2013/06/ingredient395_peponi.jpg");
            mitems.add("Πεπόνια Ουγγαρίας");

            mimages.add("https://www.bambakia.gr/wp-content/uploads/2013/06/ingredient395_peponi.jpg");
            mitems.add("Πεπόνια Ουγγαρίας");
            mimages.add("https://www.bambakia.gr/wp-content/uploads/2013/06/ingredient395_peponi.jpg");
            mitems.add("Πεπόνια Ουγγαρίας");
            mimages.add("https://www.bambakia.gr/wp-content/uploads/2013/06/ingredient395_peponi.jpg");
            mitems.add("Πεπόνια Ουγγαρίας");
            mimages.add("https://www.bambakia.gr/wp-content/uploads/2013/06/ingredient395_peponi.jpg");
            mitems.add("Πεπόνια Ουγγαρίας");
            mimages.add("https://www.bambakia.gr/wp-content/uploads/2013/06/ingredient395_peponi.jpg");
            mitems.add("Πεπόνια Ουγγαρίας");
            mimages.add("https://www.bambakia.gr/wp-content/uploads/2013/06/ingredient395_peponi.jpg");
            mitems.add("Πεπόνια Ουγγαρίας");
            mimages.add("https://www.bambakia.gr/wp-content/uploads/2013/06/ingredient395_peponi.jpg");
            mitems.add("Πεπόνια Ουγγαρίας");

            initRecyclerView();

        }

        private void initRecyclerView(){
            RecyclerView recyclerView = findViewById(R.id.recycler_view_searched_items);

            recyclerView.setHasFixedSize(true);
            SearchResultsAdapter adapter = new SearchResultsAdapter(this, mitems, mimages);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }
    }
