package thedreamteam.passbuy;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BasketInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mItemNames;
    private List<String> mItemPrices;
    private List<String> mItemQ;
    private Context mContext;


    public BasketInfoAdapter(Context mContext, List<String> mItemNames, List<String> mItemQ, List<String> mItemPrices) {
        this.mItemNames = mItemNames;
        this.mItemPrices = mItemPrices;
        this.mItemQ = mItemQ;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_info_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //todo
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_price;
        TextView item_name;
        TextView item_q;
        ConstraintLayout parent_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            item_price = itemView.findViewById(R.id.item_price);
            item_name = itemView.findViewById(R.id.item_name);
            item_q = itemView.findViewById(R.id.item_q);
            parent_layout = itemView.findViewById(R.id.parent_layout2);
        }
    }
}

