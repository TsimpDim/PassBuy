package thedreamteam.passbuy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MoreInfoAdapter extends RecyclerView.Adapter<MoreInfoAdapter.ViewHolder> {

    private List<Store> stores = new ArrayList<>();
    private Basket basket = new Basket();
    private List<StoreLocation> storeLocations = new ArrayList<>();
    private Context mContext;

    public MoreInfoAdapter(Context mContext, Basket basket, List<Store> stores, List<StoreLocation> storeLocations) {
        this.mContext = mContext;
        this.basket = basket;
        this.stores = stores;
        this.storeLocations = storeLocations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_info_recycler, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(!storeLocations.isEmpty()){
            holder.name.setFocusable(true);
            holder.name.setSelected(true);
            holder.total.setFocusable(true);
            holder.total.setSelected(true);
            holder.area.setFocusable(true);
            holder.area.setSelected(true);
            holder.name.setText(stores.get(storeLocations.get(position).getStoreId()-1).getName());
            holder.area.setText(storeLocations.get(position).getVicinity());
            //String tempprice = String.valueOf(basket.getTotalPrices().get(storeLocations.get(position).getStoreId()-1).getPrice());
            holder.total.setText(String.format("%.2f €", basket.getTotalPrices().get(storeLocations.get(position).getStoreId()-1).getPrice()));
        }

    }

    @Override
    public int getItemCount() {
        // Decides how many shops will appear
        // Hardcoded for demo, should be replaced with a Length function
        return storeLocations.size();
    }

    public void replaceStores(List<Store> stores) {
        this.stores.clear();
        this.stores.addAll(stores);

    }

    public void replaceLocations(List<StoreLocation> storeLocations){
        this.storeLocations.clear();
        this.storeLocations.addAll(storeLocations);
    }

    public void replaceTotalPrices(List<StorePrice> totalPrices){
        basket.getTotalPrices().clear();
        basket.getTotalPrices().addAll(totalPrices);
    }

    public void replaceBasket(Basket basket){
        this.basket = basket;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView area;
        public TextView total;

        //TODO: Speed up rendering by reducing findViewById calls

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            area = itemView.findViewById(R.id.area);
            total = itemView.findViewById(R.id.total);
        }
    }
}