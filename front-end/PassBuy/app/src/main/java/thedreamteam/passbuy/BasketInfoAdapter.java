package thedreamteam.passbuy;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BasketInfoAdapter extends RecyclerView.Adapter<BasketInfoAdapter.ViewHolder> {

    private List<Product> products;
    private List<Integer> quantities;
    private int store_id;
    private Context mContext;


    public BasketInfoAdapter(Context mContext, List<Product> products, List<Integer> quantities, int store_id) {
        this.products = products;
        this.quantities = quantities;
        this.store_id = store_id;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_info_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.item_name.setSelected(true);
        holder.item_q.setText(String.valueOf(quantities.get(position)));
        holder.item_name.setText(products.get(position).getName());
        holder.item_price.setText(String.format("%.2f €", products.get(position).getPrices().get(store_id).getPrice()));
    }


        @Override
        public int getItemCount () {
            return products.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView item_price;
            TextView item_name;
            TextView item_q;

            public ViewHolder(View itemView) {
                super(itemView);
                item_price = itemView.findViewById(R.id.item_price);
                item_name = itemView.findViewById(R.id.item_name);
                item_q = itemView.findViewById(R.id.item_q);
            }
        }
    }

