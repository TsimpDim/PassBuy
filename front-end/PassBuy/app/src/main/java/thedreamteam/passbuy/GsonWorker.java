package thedreamteam.passbuy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

public class GsonWorker {

    private final String url = "http://snf-812693.vm.okeanos.grnet.gr:8080/api";
    private final String[] categories = {"beverages", "dairy", "delicatessen", "frozen",
            "fruits_veggies", "snacks", "health_beauty", "home", "meat_fish", "misc"};
    private Gson gson = new GsonBuilder().create();
    private String json;

    /**
     * Returns all stores
     * @return List<Store> if no error occurs, null otherwise
     */
    public List<Store> getStores() {
        List<Store> stores = null;

        // Get JSON string
        json = this.getJSON("/stores");

        // Convert JSON to a List<Store> object if nothing ugly happened
        if (json != null)
            stores = Arrays.asList(gson.fromJson(json, Store[].class));

        // Return List<Store> if no error occurs, null otherwise
        return stores;
    }

    /**
     * Returns all categories
     * @return List<Category> if no error occurs, null otherwise
     */
    public List<Category> getCategories() {
        List<Category> categories = null;

        // Get JSON string
        json = this.getJSON("/categories");

        // Convert JSON to a List<Category> object if nothing ugly happened
        if (json != null)
            categories = Arrays.asList(gson.fromJson(json, Category[].class));

        // Return List<Category> if no error occurs, null otherwise
        return categories;
    }

    /**
     * Returns all store prices for a product
     * @param productId The ID of the product
     * @return List<Price> if no error occurs, null otherwise
     */
    public List<Price> getProductPrices(Integer productId) {
        List<Price> prices = null;

        // Get JSON string
        json = this.getJSON("/prices/" + productId);

        // Convert JSON to a List<Price> object if nothing ugly happened
        if (json != null)
            prices = Arrays.asList(gson.fromJson(json, Price[].class));

        // Return List<Price> if no error occurs, null otherwise
        return prices;
    }

    /**
     * Returns information for a product
     * @param productId The ID of the product
     * @return Product object if no error occurs, null otherwise
     */
    public Product getProduct(Integer productId) {
        Product product = null;

        // Get JSON string
        json = this.getJSON("/products/" + productId);

        // Convert JSON to a Product object if nothing ugly happened
        if (json != null)
            product = gson.fromJson(json, Product.class);

        // Return the product if no error occurs, null otherwise
        return product;
    }

    /**
     * Returns products with names matching the search argument
     * @param searcharg The search argument
     * @return List<Product> if no error occurs, null otherwise
     */
    public List<Product> getSearchResults(String searcharg) {
        List<Product> products = null;

        // We need to encode greek letters
        // Spaces are converted to '+', we need "%20" instead
        try {
            searcharg = URLEncoder.encode(searcharg, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            // Never occurs, totally useless but required
        }

        // Get JSON string
        json = this.getJSON("/products/search/" + searcharg);

        // Convert JSON to a List<Product> object if nothing ugly happened
        if (json != null)
            products = Arrays.asList(gson.fromJson(json, Product[].class));

        // Return List<Product> if no error occurs, null otherwise
        return products;
    }

    /**
     * Returns products that belong to a certain category
     * @param category_id The category id from the /api/categories endpoint
     * @return List<Product> if no error occurs, null otherwise
     */
    public List<Product> getProductsByCategory(Integer category_id) {
        List<Product> products = null;

        // Get JSON string
        json = this.getJSON("/products/" + categories[category_id]);

        // Convert JSON to a List<Product> object if nothing ugly happened
        if (json != null)
            products = Arrays.asList(gson.fromJson(json, Product[].class));

        // Return List<Product> if no error occurs, null otherwise
        return products;
    }

    /**
     * Returns the JSON string fron an API endpoint
     * @param endpoint The API endpoint to work on
     * @return The JSON string if no error occurred, null otherwise
     */
    public String getJSON(String endpoint) {
        HttpURLConnection conn = null;

        // Create a StringBuilder for the final URL
        StringBuilder finalURL = new StringBuilder();
        // Append API URL
        finalURL.append(url);
        // Append endpoint
        finalURL.append(endpoint);

        // Create a StringBuilder to store the JSON string
        StringBuilder result = new StringBuilder();
        try {
            // Make a connection with the API
            URL url = new URL(finalURL.toString());
            conn = (HttpURLConnection) url.openConnection();

            // Begin streaming the JSON
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            // Read the first line
            String line = reader.readLine();

            // If an error is thrown, we're done here
            if (line.contains("error"))
                return null;

            // Append the first line to the builder
            result.append(line);

            // Read the remaining stream until we are done
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            // Usually indicates a lack of Internet connection
            return null;
        } finally {
            // Close connection
            conn.disconnect();
        }

        // Return the JSON string
        return result.toString();
    }
}


