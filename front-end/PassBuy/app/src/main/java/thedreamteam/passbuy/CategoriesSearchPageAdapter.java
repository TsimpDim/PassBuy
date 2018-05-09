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

public class CategoriesSearchPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mCategoriesNames = new ArrayList<>();
    private Context mContext;

    public CategoriesSearchPageAdapter(Context mContext, ArrayList<String> mCategoriesNames) {
        this.mCategoriesNames = mCategoriesNames;
        this.mContext = mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_search_page_recycler,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        //todo
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView category;
        ImageButton go_to;
        ConstraintLayout parent_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category_name);
            go_to = itemView.findViewById(R.id.next_arrow_button);
            parent_layout = itemView.findViewById(R.id.parent_layout3);
        }
    }
}

