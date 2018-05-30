package thedreamteam.passbuy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
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


import java.util.ArrayList;
import java.util.List;

public class HomeScreenBasketAdapter extends RecyclerView.Adapter<HomeScreenBasketAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private List<Product> Products;
    private Basket basket = new Basket();
    private ArrayList<Integer> Quantities;
    private Context mContext;


    public HomeScreenBasketAdapter(Context mContext, List<Product> products, ArrayList<Integer> quantities, Basket basket) {
        this.Products = products;
        this.basket = basket;
        this.Quantities = quantities;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_screen_basket_adapter,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.item_name.setText(Products.get(position).getName());
        holder.item_q.setText(Quantities.get(position).toString());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupQuantityDialog dialogFragment = new PopupQuantityDialog ();

                FragmentManager fm = ((Activity) mContext).getFragmentManager();

                Bundle bundle = new Bundle();
                bundle.putSerializable("product",Products.get(position));
                bundle.putSerializable("basket",basket);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(fm, "Sample Fragment");
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete Yes or No Dialog
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                basket.getQuantities().remove(position);
                                basket.getProducts().remove(position);


                                Intent intent = new Intent(mContext, HomeScreen.class);

                                Bundle bundle = new Bundle();
                                bundle.putSerializable("basket", basket);
                                intent.putExtra("bundle",bundle);

                                mContext.startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //do nothing
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Θέλεις να διαγραφεί το υπάρχων καλάθι?").setPositiveButton("ΔΙΑΓΡΑΦΗ", dialogClickListener)
                        .setNegativeButton("ΑΚΥΡΟ", dialogClickListener).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return Products.size();
    }

    public interface BasketReturner{
        void getBasket(Basket basket);
    }


    public void replaceList(Basket basket) {
        Products.clear();
        Products.addAll(basket.getProducts());
        Quantities.clear();
        Quantities.addAll(basket.getQuantities());
    }


    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView item_name;
        TextView item_q;
        ImageButton edit;
        ImageButton delete;
        ConstraintLayout parent_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_name.setSelected(true);
            item_q = itemView.findViewById(R.id.quantity_text);
            edit = itemView.findViewById(R.id.edit_button);
            delete = itemView.findViewById(R.id.delete_button_recycler);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}