package thedreamteam.passbuy;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import thedreamteam.passbuy.R;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private List<Product> products;
    private Basket basket = new Basket();
    private Context mContext;
    private Product mproduct;

    public SearchResultsAdapter(Context mContext, List<Product> products, Basket basket) {
        this.products = products;
        this.basket = basket;
        this.mContext = mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_results_recycler,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.item_name.setText(products.get(position).getName());

        if (!products.get(position).getImageUrl().isEmpty())
            Picasso.get().load(products.get(position).getImageUrl()).into(holder.image);

        //Experimental. Set tick on added items (that are currently in basket)
        //This should use HashCode() maybe . It's a better solution.
        /*if(!(basket.getProducts().isEmpty())) {
            for (Product prod : basket.getProducts()) {
                if (prod.getProductId().equals(products.get(position).getProductId())) {
                    holder.add_item.setPressed(true);
                    break;
                } else {
                    holder.add_item.setPressed(false);
                }
            }
        }*/

        if(!(basket.getProducts().isEmpty())) {
            if(basket.getProducts().contains(products.get(position))){
                holder.add_item.setPressed(true);
            }
            else
                holder.add_item.setPressed(false);}

        holder.add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupQuantityDialog dialogFragment = new PopupQuantityDialog ();

                FragmentManager fm = ((Activity) mContext).getFragmentManager();

                Bundle bundle = new Bundle();
                bundle.putSerializable("product",products.get(position));
                bundle.putSerializable("basket",basket);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(fm, "Sample Fragment");

            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void replaceList(List<Product> newList) {
        products.clear();
        products.addAll(newList);
    }

    public void replaceBasket(Basket basket){
        this.basket = basket;
    }



    public Basket getBasket(){
        return basket;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{


        ImageView image;
        TextView item_name;
        ImageButton add_item;
        ConstraintLayout parent_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            item_name = itemView.findViewById(R.id.textView);
            item_name.setSelected(true);
            add_item = itemView.findViewById(R.id.add_button);
            parent_layout = itemView.findViewById(R.id.parent_layout2);
        }
    }
}