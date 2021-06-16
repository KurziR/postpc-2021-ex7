package huji.postpc.y2021.reutk.rachelsfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Objects;
import java.util.Observer;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;
    FirestoreHelper firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (sp == null){
            sp = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            firestore = new FirestoreHelper(sp);
        }

        Button save_order = findViewById(R.id.save);
        EditText comment = findViewById(R.id.personRequests);
        Switch is_hummus = findViewById(R.id.is_hummus);
        Switch is_tahini = findViewById(R.id.is_tahini);
        TextView hummus = findViewById(R.id.hummus);
        TextView tahini = findViewById(R.id.tahini);
        Spinner pickels = findViewById(R.id.pickels);

        save_order.setOnClickListener(v -> {
            String user_comment = comment.getText().toString();
            boolean hummus = is_hummus.isChecked();
            boolean tahini = is_tahini.isChecked();
            int pickels = pickels
            Order new_order = Order(name, pickels, boolean hummus, boolean tahini)
        });

        firestore.collection("orders").get();
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
