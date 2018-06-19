package thedreamteam.passbuy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BasketInfoAdapter extends RecyclerView.Adapter<BasketInfoAdapter.ViewHolder> {

    private List<Product> products;
    private List<Integer> quantities;
    private int storeId;

    public BasketInfoAdapter(List<Product> products, List<Integer> quantities, int storeId) {
        this.products = products;
        this.quantities = quantities;
        this.storeId = storeId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_info_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemQ.setText(String.valueOf(quantities.get(position)));
        holder.itemName.setText(products.get(position).getName());
        holder.itemPrice.setText(String.format("%.2f €", products.get(position).getPrices().get(storeId).getPrice() * quantities.get(position)));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemPrice;
        TextView itemName;
        TextView itemQ;

        public ViewHolder(View itemView) {
            super(itemView);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemName = itemView.findViewById(R.id.item_name);
            itemName.setSelected(true);
            itemQ = itemView.findViewById(R.id.item_q);
        }
    }
}

