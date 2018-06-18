package thedreamteam.passbuy;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PopupQuantityDialog extends DialogFragment {

    private int quantity = 0;
    private DialogListener dialogListener;

    private TextView count;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view
        View view = inflater.inflate(R.layout.popup_quantity, container, false);

        // Set transparent background
        this.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);



        Basket basket = (Basket) getArguments().getSerializable("basket");
        Product product = (Product) getArguments().getSerializable("product");


        count = view.findViewById(R.id.count);
        count.setText(String.valueOf(quantity));

        TextView itemName = view.findViewById(R.id.item_name);
        itemName.setSelected(true);

        TextView description = view.findViewById(R.id.prodDesc);
        ImageButton plus = view.findViewById(R.id.plus);
        ImageButton minus = view.findViewById(R.id.minusButton);
        ImageView image = view.findViewById(R.id.prodImage);
        Button cancel = view.findViewById(R.id.cancel);
        Button add = view.findViewById(R.id.add);

        itemName.setText(product.getName());
        description.setText(product.getDescription());

        if (basket.getProducts().contains(product)) {
            quantity = basket.getQuantities().get(basket.getProducts().indexOf(product));
            count.setText(String.valueOf(basket.getQuantities().get(basket.getProducts().indexOf(product))));
        }

        plus.setOnClickListener(v -> {
            quantity++;
            count.setText(String.valueOf(quantity));
        });

        minus.setOnClickListener(v -> {
            if (quantity != 0) {
                quantity--;
                count.setText(String.valueOf(quantity));
            }
        });

        if (!product.getImageUrl().isEmpty())
            Picasso.get().load(product.getImageUrl()).into(image);

        cancel.setOnClickListener(v -> dismiss());

        add.setOnClickListener(v -> {
            dialogListener.getQuantity(product, quantity);
            dismiss();
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dialogListener = (DialogListener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dialogListener = (DialogListener) activity;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Get window
        Window window = getDialog().getWindow();

        // Get window attributes
        WindowManager.LayoutParams windowParams = window.getAttributes();

        // Reduce background dim effect
        windowParams.dimAmount = 0.2f;

        // Add params
        windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;

        // Set window attributes
        window.setAttributes(windowParams);
    }

    public interface DialogListener {
        void getQuantity(Product product, int q);
    }
}
