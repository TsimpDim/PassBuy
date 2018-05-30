package thedreamteam.passbuy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Basket implements Serializable {

    private List<Product> products = new ArrayList<>();
    private ArrayList<Integer> quantities = new ArrayList<>();
    private List<Price> total_prices = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public ArrayList<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(ArrayList<Integer> quantities) {
        this.quantities = quantities;
    }

    public List<Price> getTotalprices() {
        return total_prices;
    }

    public void setTotalprices(List<Price> total_prices) {
        this.total_prices = total_prices;
    }
}