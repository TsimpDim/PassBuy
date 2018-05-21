package thedreamteam.passbuy;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MoreInfoActivity extends PortraitActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_info);
        mRecyclerView = findViewById(R.id.rv);

        // Layout size remains fixed, improve performance
        mRecyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        // A vertically-scrollable collection of views
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Use an adapter to feed data into the RecyclerView
        mAdapter = new MoreInfoAdapter();
        mRecyclerView.setAdapter(mAdapter);

        // Draw line divider
        DividerItemDecoration lineDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(lineDivider);
    }
}