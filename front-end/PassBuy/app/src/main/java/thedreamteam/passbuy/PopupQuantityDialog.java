package thedreamteam.passbuy;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.DialogFragment;
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
    private Product product;
    private Basket basket;
    private DialogListener dialogListener;

    private TextView item_name;
    private TextView description;
    private TextView count;
    private ImageButton plus;
    private ImageButton minus;
    private ImageView image;
    private Button cancel;
    private Button add;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view
        View view = inflater.inflate(R.layout.popup_quantity, container, false);

        // Set transparent background
        this.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        basket = (Basket) getArguments().getSerializable("basket");
        product = (Product) getArguments().getSerializable("product");
        Product product = (Product) getArguments().getSerializable("product");


        item_name  = (TextView) view.findViewById(R.id.item_name);
        description = (TextView) view.findViewById(R.id.prodDesc);
        count = (TextView) view.findViewById(R.id.count);
        plus = (ImageButton) view.findViewById(R.id.plus);
        minus = (ImageButton) view.findViewById(R.id.minusButton);
        image = (ImageView) view.findViewById(R.id.prodImage);
        cancel = (Button) view.findViewById(R.id.cancel);
        add = (Button) view.findViewById(R.id.add);

        item_name.setText(product.getName());
        description.setText(product.getDescription());
        count.setText(String.valueOf(quantity));

        if(basket.getProducts().contains(product)){
            quantity = basket.getQuantities().get(basket.getProducts().indexOf(product));
            count.setText(String.valueOf(basket.getQuantities().get(basket.getProducts().indexOf(product))));
        }


        //Better Way to do the thing above.
        /*for (Product prod : basket.getProducts()){
            if(prod.getName().equals(product.getName())){
                quantity = basket.getQuantities().get(basket.getProducts().indexOf(product));
                count.setText(String.valueOf(basket.getQuantities().get(basket.getProducts().indexOf(product))));
            }
        }
        */


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity ++;
                count.setText(String.valueOf(quantity));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity!=0){
                    quantity--;
                    count.setText(String.valueOf(quantity));
                }
            }
        });


        if (!product.getImageUrl().isEmpty())
            Picasso.get().load(product.getImageUrl()).into(image);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.getQuantity(product , quantity);
                dismiss();
            }
        });


        return view;
    }

    public interface DialogListener{
        void getQuantity(Product product, int q);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            dialogListener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogListener");
        }
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
}