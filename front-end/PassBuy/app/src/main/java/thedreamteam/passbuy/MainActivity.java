package thedreamteam.passbuy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        final ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Actions after logo button press
            }
        });

        final ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Actions after back button press
            }
        });
    }
}
