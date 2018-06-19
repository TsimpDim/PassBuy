package thedreamteam.passbuy;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class GsonFileWorker {
    private Gson gson = new GsonBuilder().create();

    public void saveToFile(Basket basket, Context mContext) {
        String json = gson.toJson(basket);

        FileOutputStream outputStream;

        try {
            outputStream = mContext.openFileOutput("basket.txt", Context.MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (Exception e) {
            // Can't occur
            return;
        }
    }

    public Basket loadFromFile(Context mContext) {
        Basket basket;
        FileInputStream fis;

        try {
            fis = mContext.openFileInput("basket.txt");

            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            String json = sb.toString();
            basket = gson.fromJson(json, Basket.class);
        } catch (Exception e) {
            // Something has gone horribly wrong if this occurs
            // Return an empty Basket
            return new Basket();
        }
        return basket;
    }
}
