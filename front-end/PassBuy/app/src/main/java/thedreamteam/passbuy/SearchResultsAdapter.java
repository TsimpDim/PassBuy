package thedreamteam.passbuy;

import android.content.Context;
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


import java.util.ArrayList;

import thedreamteam.passbuy.R;

public class SearchResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mItemNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    public SearchResultsAdapter(Context mContext, ArrayList<String> mItemNames, ArrayList<String> mImages) {
        this.mItemNames = mItemNames;
        this.mImages = mImages;
        this.mContext = mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_results_recycler,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        //todo
    }

    @Override
    public int getItemCount() {
        return mItemNames.size();
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
            add_item = itemView.findViewById(R.id.add_button);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}

