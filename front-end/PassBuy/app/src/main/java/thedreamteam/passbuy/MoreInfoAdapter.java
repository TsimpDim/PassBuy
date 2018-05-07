package thedreamteam.passbuy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MoreInfoAdapter extends RecyclerView.Adapter<MoreInfoAdapter.ViewHolder> {
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_info_recycler, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // todo
    }

    @Override
    public int getItemCount() {
        // Decides how many shops will appear
        // Hardcoded for demo, should be replaced with a Length function
        return 10;
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