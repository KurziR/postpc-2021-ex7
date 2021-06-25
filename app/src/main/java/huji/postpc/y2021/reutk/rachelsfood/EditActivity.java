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
import com.google.firebase.firestore.ListenerRegistration;


public class EditActivity extends AppCompatActivity {

    private RachelsApp app;
    private Order order;
    private FirebaseFirestore firestore;
    private SharedPreferences sp;
    private Button edit_order;
    private Button delete_order;
    private EditText comment;
    private EditText name;
    private Switch is_hummus;
    private Switch is_tahini;
    private Spinner pickles;

    private ListenerRegistration listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_order_activity);


        app = new RachelsApp(this);
        firestore = FirebaseFirestore.getInstance();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        edit_order = findViewById(R.id.edit);
        delete_order = findViewById(R.id.delete);
        comment = findViewById(R.id.edit_personRequests);
        name = findViewById(R.id.edit_personName);
        is_hummus = findViewById(R.id.edit_is_hummus);
        is_tahini = findViewById(R.id.edit_is_tahini);
        pickles = findViewById(R.id.edit_pickels);

        app.load_id();
        String order_id = app.getOrder_id();

        firestore.collection("orders").document(order_id).get().addOnSuccessListener(documentSnapshot -> {
            order = documentSnapshot.toObject(Order.class);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.status_options, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            pickles.setAdapter(adapter);
            pickles.setSelection(order.getNum_of_pickles());
            is_hummus.setChecked(order.isHummus());
            is_tahini.setChecked(order.isTahini());
            name.setText(order.getCustomer_name());
            comment.setText(order.getComment());
        });

        edit_order.setOnClickListener(v -> {
            if (TextUtils.isEmpty(name.getText())) {
                name.setError("Please insert your name");
                return;
            }
            order.setHummus(is_hummus.isChecked());
            order.setTahini(is_tahini.isChecked());
            order.setNum_of_pickels(pickles.getSelectedItemPosition());
            order.setCustomer_name(name.getText().toString());
            order.setComment(comment.getText().toString());
            order.setStatus("in-progress");
            firestore.collection("orders").document(order_id).set(order);
        });

        delete_order.setOnClickListener(v -> {
            order.setStatus("deleted");
            firestore.collection("orders").document(order_id).set(order);
            this.app.save_id("");
        });

        listener = firestore.collection("orders").document(order_id).addSnapshotListener((val, error) -> {
            if(val != null) {
                Order order = val.toObject(Order.class);
                if (order.getStatus().equals("done")){
                    newOrderActivity();
                    return;
                }
                if(order.getStatus().equals("in-progress")) {
                    alreadyOrderActivity(InProgressActivity.class, order);
                    return;
                }
                if(order.getStatus().equals("ready")) {
                    alreadyOrderActivity(IsReadyActivity.class, order);
                }
                if(order.getStatus().equals("deleted")) {
                    alreadyOrderActivity(OrderActivity.class, order);
                }
            }
            else {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
                sp.edit().putString("order_id", null).apply();
                startActivity(new Intent(EditActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void newOrderActivity(){
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
        finish();
    }

    private void alreadyOrderActivity(Class<?> activity, Order order){
        Intent intent = new Intent(this, activity);
        intent.putExtra("order", order);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        listener.remove();
    }
}
