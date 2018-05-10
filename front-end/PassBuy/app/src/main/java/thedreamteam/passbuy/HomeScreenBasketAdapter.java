package thedreamteam.passbuy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

public class HomeScreenBasketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mItemNames = new ArrayList<>();
    private ArrayList<String> mItemQ = new ArrayList<>();
    private Context mContext;


    public HomeScreenBasketAdapter( Context mContext, ArrayList<String> mItemNames, ArrayList<String> mItemQ) {
        this.mItemNames = mItemNames;
        this.mItemQ = mItemQ;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_screen_basket_adapter,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        //todo
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView item_name;
        TextView item_q;
        ConstraintLayout parent_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_q = itemView.findViewById(R.id.quantity_text);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}

