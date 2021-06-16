package huji.postpc.y2021.reutk.rachelsfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Observer;

public class OrderActivity  extends AppCompatActivity {

    FirestoreHelper firestore;
    private SharedPreferences sp;
    Button save_order;
    EditText comment;
    EditText name;
    Switch is_hummus;
    Switch is_tahini;
    Spinner pickels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firestore = new FirestoreHelper(sp);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        Button save_order = findViewById(R.id.save);
        EditText comment = findViewById(R.id.personRequests);
        EditText name = findViewById(R.id.personName);
        Switch is_hummus = findViewById(R.id.is_hummus);
        Switch is_tahini = findViewById(R.id.is_tahini);
        Spinner pickels = findViewById(R.id.pickels);

        save_order.setOnClickListener(v -> {
            Order new_order = new Order();
            if (TextUtils.isEmpty(name.getText())) {
                name.setError("Please enter your name");
                return;
            }
            new_order.setHummus(is_hummus.isChecked());
            new_order.setTahini(is_tahini.isChecked());
            new_order.setNum_of_pickels((Integer)pickels.getSelectedItem());
            new_order.setCustomer_name(name.getText().toString());
            new_order.setComment(comment.getText().toString());

            firestore.firestore.collection("orders").document(new_order.getId()).set(new_order).addOnSuccessListener(
                    documentReference -> {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                        preferences.edit().putString("orderId", new_order.getId()).apply();
                        Intent intent = new Intent(this, EditOrderActivity.class);
                        intent.putExtra("order", new_order);
                        startActivity(intent);
                        finish();
                    }
            ).addOnFailureListener(e -> {
                Toast.makeText(this, "Error creating order:\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });
    }
}
