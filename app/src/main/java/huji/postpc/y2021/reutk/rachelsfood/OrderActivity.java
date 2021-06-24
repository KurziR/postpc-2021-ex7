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
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.annotation.Nullable;

public class OrderActivity extends AppCompatActivity {

    private RachelsApp app;
    private FirebaseFirestore firestore;
    private SharedPreferences sp;
    private Order new_order;
    private Button save_order;
    private EditText comment;
    private EditText name;
    private Switch is_hummus;
    private Switch is_tahini;
    private Spinner pickles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_order_activity);

        app = new RachelsApp(this);
        firestore = FirebaseFirestore.getInstance();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        save_order = findViewById(R.id.save);
        comment = findViewById(R.id.personRequests);
        name = findViewById(R.id.personName);
        is_hummus = findViewById(R.id.is_hummus);
        is_tahini = findViewById(R.id.is_tahini);

        pickles = findViewById(R.id.pickels);
        String[] items = new String[]{"1 pickle", "2 pickles", "3 pickles", "4 pickles", "5 pickles", "6 pickles", "7 pickles", "8 pickles", "9 pickles", "10 pickles"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        pickles.setAdapter(adapter);

        save_order.setOnClickListener(v -> {
            new_order = new Order(name.getText().toString(), pickles.getSelectedItemPosition(),
                    is_hummus.isChecked(), is_tahini.isChecked(), comment.getText().toString());
            if (TextUtils.isEmpty(name.getText())) {
                name.setError("Please insert your name");
                return;
            }
            firestore.collection("orders").document(new_order.getId()).set(new_order);
            this.app.save_id(new_order.getId());
            Intent editActivity = new Intent(this, EditActivity.class);
            editActivity.putExtra("order",new_order);
            this.startActivity(editActivity);
            finish();
        });
    }
}



