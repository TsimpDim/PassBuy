package thedreamteam.passbuy;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private List<Product> products;
    private Basket basket;
    private Context mContext;

    public SearchResultsAdapter(Context mContext, List<Product> products, Basket basket) {
        this.products = products;
        this.basket = basket;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_results_recycler, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.addItem.setOnClickListener(v -> {
            PopupQuantityDialog dialogFragment = new PopupQuantityDialog();

            FragmentManager fm = ((Activity) mContext).getFragmentManager();

            Bundle bundle = new Bundle();
            bundle.putSerializable("product", products.get(holder.getAdapterPosition()));
            bundle.putSerializable("basket", basket);
            dialogFragment.setArguments(bundle);

            dialogFragment.show(fm, "Sample Fragment");

        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemName.setText(products.get(position).getName());

        if (!products.get(position).getImageUrl().isEmpty())
            Picasso.get().load(products.get(position).getImageUrl()).into(holder.image);

        if (!basket.getProducts().isEmpty()) {
            if (basket.getProducts().contains(products.get(position)))
                holder.addItem.setPressed(true);
            else
                holder.addItem.setPressed(false);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void replaceList(List<Product> newList) {
        products.clear();
        products.addAll(newList);
    }

    public void replaceBasket(Basket basket) {
        this.basket = basket;
    }


    public Basket getBasket() {
        return basket;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView itemName;
        ImageButton addItem;
        ConstraintLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            itemName = itemView.findViewById(R.id.textView);
            itemName.setSelected(true);
            addItem = itemView.findViewById(R.id.add_button);
            parentLayout = itemView.findViewById(R.id.parent_layout2);
        }
    }
}