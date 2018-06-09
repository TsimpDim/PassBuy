package thedreamteam.passbuy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GsonWorker {

    private final String pbUrl = "http://snf-812693.vm.okeanos.grnet.gr:8080/api";
    private final String placesUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private final String placesKey = "YOUR_KEY";

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
     * Returns the nearest supermarket in each chain
     * @param stores The List of all store chains
     * @param userCoordinates The user's current location
     * @return List<StoreLocation> if no error occurs, null otherwise
     */
    public List<StoreLocation> getNearbyStores(List<Store> stores, Coordinates userCoordinates) {
        List<StoreLocation> locations = new ArrayList<>();

        // Iterate over all store chains
        for (Store store : stores) {
            StoreLocation storeLocation;

            // Get JSON results for our current chain
            json = this.getPlacesJSON(store.getName(), userCoordinates);

            // Retrieve lat, lng, vicinity if nothing ugly happened
            if (json != null) {
                JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

                // Check the status response
                String status = jsonObject.getAsJsonPrimitive("status").getAsString();
                if (status.equals("OK")) {
                    // Get the first element of the "results" array, the nearest store
                    JsonObject result = jsonObject.getAsJsonArray("results").get(0).getAsJsonObject();

                    // Get vicinity
                    String vicinityResponse = result.getAsJsonPrimitive("vicinity").getAsString();
                    // Remove city from the response
                    String vicinity = vicinityResponse.substring( 0, vicinityResponse.indexOf(","));

                    // We need to move in geometry -> location to get the coordinates
                    JsonElement location = result.get("geometry").getAsJsonObject().get("location");

                    // Create the Location object and add it to the list
                    storeLocation = gson.fromJson(location, StoreLocation.class);
                    storeLocation.setVicinity(vicinity);
                    storeLocation.setStoreId(store.getId());
                    locations.add(storeLocation);
                }
            }
            else
                return null;
        }
        return locations;
    }

    /**
     * Returns products with names matching the search argument
     * @param searcharg The search argument
     * @return List<Product> if no error occurs, null otherwise
     */
    public List<Product> getSearchResults(String searcharg) {
        List<Product> products = null;

        // We need to encode greek letters
        this.encode(searcharg);

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
     * Returns UTF-8 encoded text for use in URLs
     * @param str The string to encode
     * @return The encoded string
     */
    private String encode(String str) {
        try {
            // Spaces are converted to '+', we need "%20" instead
            str = URLEncoder.encode(str, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            // Never occurs, totally useless but required
        }
        return str;
    }

    /**
     * Returns the JSON string from a PassBuy API endpoint
     * @param endpoint The API endpoint to work on
     * @return The JSON string if no error occurred, null otherwise
     */
    public String getJSON(String endpoint) {
        HttpURLConnection conn = null;

        // Create a StringBuilder for the final URL
        StringBuilder finalURL = new StringBuilder();
        // Append API URL
        finalURL.append(pbUrl);
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
            if (conn != null)
                conn.disconnect();
        }

        // Return the JSON string
        return result.toString();
    }

    /**
     * Returns the JSON string from Google's Places API
     * @param storeName The store chain to work on
     * @param userCoordinates The user's location provided as a parameter
     * @return The JSON string if no error occurred, null otherwise
     */
    public String getPlacesJSON(String storeName, Coordinates userCoordinates) {
        HttpURLConnection conn = null;
        String line;

        // We have to encode the store name
        this.encode(storeName);

        // Create a StringBuilder for the final URL
        StringBuilder finalURL = new StringBuilder();
        // Append API URL
        finalURL.append(placesUrl);
        // Append location
        finalURL.append("location=" + userCoordinates.getLat() + "%2C" + userCoordinates.getLng());
        // Append parameters
        finalURL.append("&rankby=distance&type=supermarket&keyword=");
        // Append name parameter
        finalURL.append(storeName);
        // Append API KEY
        finalURL.append("&key=" + placesKey);

        // Create a StringBuilder to store the JSON string
        StringBuilder result = new StringBuilder();

        try {
            // Make a connection with the API
            URL url = new URL(finalURL.toString());

            conn = (HttpURLConnection) url.openConnection();

            // Begin streaming the JSON
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            // Usually indicates a lack of Internet connection
            return null;
        } finally {
            // Close connection
            if (conn != null)
                conn.disconnect();
        }

        // Return the JSON string
        return result.toString();
    }
}


