package thedreamteam.passbuy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Basket implements Serializable {

    private List<Product> products = new ArrayList<>();
    private List<Integer> quantities = new ArrayList<>();
    private List<StorePrice> totalPrices = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }

    public List<StorePrice> getTotalPrices() {
        return totalPrices;
    }

    public void setTotalPrices(List<StorePrice> totalPrices) {
        this.totalPrices = totalPrices;
    }
}