package huji.postpc.y2021.reutk.rachelsfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Observer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirestoreHelper firestore = new FirestoreHelper();
        firestore.collection("orders").get();
//        firestore.collection("orders").get()
        LiveData<Order> ordersLiveData = firestore.downloadOrders("");
        ordersLiveData.observe(this, (Observer<Order>) order -> {
            if (order == null) {
                // no wallet yet… this is invoked from the first "null" value inside the LiveData...
                // TODO: set UI to "loading" state
            } else {
                // someone put the wallet Inside the LiveData! And we got it! Yay :D
                // TODO: set UI based on the wallet data
            }
        });

        Spinner dropdown = findViewById(R.id.pickels);
        String[] items = new String[]{"1 pickle", "2 pickles", "3 pickles", "4 pickles", "5 pickles", "6 pickles", "7 pickles", "8 pickles", "9 pickles", "10 pickles"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }
}
