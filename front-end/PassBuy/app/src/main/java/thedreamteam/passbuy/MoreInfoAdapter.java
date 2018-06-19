package thedreamteam.passbuy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MoreInfoAdapter extends RecyclerView.Adapter<MoreInfoAdapter.ViewHolder> {

    private List<Store> stores;
    private Coordinates userCoordinates;
    private Basket basket;
    private List<StoreLocation> storeLocations = new ArrayList<>();
    private Context mContext;

    public MoreInfoAdapter(Context mContext, Basket basket, List<Store> stores) {
        this.mContext = mContext;
        this.basket = basket;
        this.stores = stores;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_info_recycler, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.infoButton.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, BasketInfo.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("basket", basket);
            bundle.putInt("store_id", storeLocations.get(holder.getAdapterPosition()).getStoreId() - 1);
            bundle.putSerializable("userCo", userCoordinates);
            bundle.putSerializable("storeLoc", storeLocations.get(holder.getAdapterPosition()));
            intent.putExtra("bundle", bundle);
            mContext.startActivity(intent);
        });

        holder.mapsButton.setOnClickListener(v -> {
            String uri = String.format(Locale.ENGLISH, "google.navigation:q=%f,%f",
                    storeLocations.get(holder.getAdapterPosition()).getLat(),
                    storeLocations.get(holder.getAdapterPosition()).getLng());
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");
            mContext.startActivity(intent);
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (!storeLocations.isEmpty()) {
            Integer storeId = basket.getTotalPrices().get(position).getStoreId() - 1;
            holder.name.setFocusable(true);
            holder.name.setSelected(true);
            holder.total.setFocusable(true);
            holder.total.setSelected(true);
            holder.area.setFocusable(true);
            holder.area.setSelected(true);
            holder.name.setText(stores.get(storeId).getName());
            holder.area.setText(storeLocations.get(position).getVicinity());

            holder.total.setText(String.format("%.2f €", basket.getTotalPrices().get(storeId).getPrice()));
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

    public void replaceUserLocation(Coordinates co) {
        this.userCoordinates = co;
    }

    public void replaceLocations(List<StoreLocation> storeLocations) {
        this.storeLocations.clear();
        this.storeLocations.addAll(storeLocations);
    }

    public void replaceTotalPrices(List<StorePrice> totalPrices) {
        basket.getTotalPrices().clear();
        basket.getTotalPrices().addAll(totalPrices);
    }

    public void replaceBasket(Basket basket) {
        this.basket = basket;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView area;
        TextView total;
        Button infoButton;
        Button mapsButton;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            area = itemView.findViewById(R.id.area);
            total = itemView.findViewById(R.id.total);
            infoButton = itemView.findViewById(R.id.infoButton);
            mapsButton = itemView.findViewById(R.id.mapsButton);

        }
    }
}