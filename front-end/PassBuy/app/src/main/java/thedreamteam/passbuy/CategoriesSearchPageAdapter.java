package thedreamteam.passbuy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CategoriesSearchPageAdapter extends RecyclerView.Adapter<CategoriesSearchPageAdapter.ViewHolder> {

    private List<Category> mCategoriesNames;
    private Basket basket;
    private Context mContext;

    public CategoriesSearchPageAdapter(Context mContext, List<Category> mCategoriesNames, Basket basket) {
        this.mContext = mContext;
        this.mCategoriesNames = mCategoriesNames;
        this.basket = basket;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_search_page_recycler, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        view.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, SearchResults.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("basket", basket);
            bundle.putCharSequence("search_text", "");
            bundle.putInt("category_id", mCategoriesNames.get(holder.getAdapterPosition()).getCategoryId());
            intent.putExtra("bundle", bundle);

            mContext.startActivity(intent);
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.category.setText(mCategoriesNames.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mCategoriesNames.size();
    }

    public void replaceList(List<Category> newList) {
        mCategoriesNames.clear();
        mCategoriesNames.addAll(newList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView category;
        ImageView goTo;
        ConstraintLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category_name);
            category.setSelected(true);
            goTo = itemView.findViewById(R.id.next_arrow);
            parentLayout = itemView.findViewById(R.id.parent_layout3);
        }
    }
}

