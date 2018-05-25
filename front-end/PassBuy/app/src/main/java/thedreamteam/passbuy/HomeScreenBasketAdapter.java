package thedreamteam.passbuy;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomeScreenBasketAdapter extends RecyclerView.Adapter<HomeScreenBasketAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private List<Product> Products;
    private ArrayList<Integer> Quantities;
    private Context mContext;


    public HomeScreenBasketAdapter(Context mContext, List<Product> products, ArrayList<Integer> quantities) {
        this.Products = products;
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
    }

    @Override
    public int getItemCount() {
        return Products.size();
    }

    public void replaceList(List<Product> newList) {
        Products.clear();
        Products.addAll(newList);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView item_name;
        TextView item_q;
        ConstraintLayout parent_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_name.setSelected(true);
            item_q = itemView.findViewById(R.id.quantity_text);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}