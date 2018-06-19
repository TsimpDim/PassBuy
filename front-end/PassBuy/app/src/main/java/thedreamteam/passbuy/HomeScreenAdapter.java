package thedreamteam.passbuy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class HomeScreenAdapter extends RecyclerView.Adapter<HomeScreenAdapter.ViewHolder> {

    private List<Product> products;
    private Basket basket;
    private List<Integer> quantities;
    private Context mContext;

    public HomeScreenAdapter(Context mContext, List<Product> products, List<Integer> quantities, Basket basket) {
        this.mContext = mContext;
        this.products = products;
        this.quantities = quantities;
        this.basket = basket;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_screen_basket_adapter, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.edit.setOnClickListener(v -> {
            PopupQuantityDialog dialogFragment = new PopupQuantityDialog();

            android.app.FragmentManager fm = ((Activity) mContext).getFragmentManager();

            Bundle bundle = new Bundle();
            bundle.putSerializable("product", products.get(holder.getAdapterPosition()));
            bundle.putSerializable("basket", basket);
            dialogFragment.setArguments(bundle);
            dialogFragment.show(fm, "Sample Fragment");
        });

        holder.delete.setOnClickListener(v -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, decision) -> {
                if (decision == DialogInterface.BUTTON_POSITIVE) {
                    basket.getQuantities().remove(holder.getAdapterPosition());
                    basket.getProducts().remove(holder.getAdapterPosition());

                    Intent intent = new Intent(mContext, HomeScreen.class);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("basket", basket);
                    intent.putExtra("bundle", bundle);

                    mContext.startActivity(intent);
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("Θέλεις να διαγραφεί το προϊόν;").setPositiveButton("ΔΙΑΓΡΑΦΗ", dialogClickListener)
                    .setNegativeButton("ΑΚΥΡΟ", dialogClickListener).show();
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemName.setText(products.get(position).getName());
        holder.itemQ.setText(quantities.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void replaceList(Basket basket) {
        products.clear();
        products.addAll(basket.getProducts());
        quantities.clear();
        quantities.addAll(basket.getQuantities());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemQ;
        ImageButton edit;
        ImageButton delete;
        ConstraintLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemName.setSelected(true);
            itemQ= itemView.findViewById(R.id.quantity_text);
            edit = itemView.findViewById(R.id.edit_button);
            delete = itemView.findViewById(R.id.delete_button_recycler);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}