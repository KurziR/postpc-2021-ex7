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
import android.widget.Toast;

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

//        Intent intent = getIntent();
//        order = (Order) intent.getSerializableExtra("order");

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
//            String num_of_pickles = (String) pickles.getSelectedItem().toString();
            String[] items = new String[]{"1 pickle", "2 pickles", "3 pickles", "4 pickles", "5 pickles", "6 pickles", "7 pickles", "8 pickles", "9 pickles", "10 pickles"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            pickles.setAdapter(adapter);
            is_hummus.setChecked(order.isHummus());
            is_tahini.setChecked(order.isTahini());
            name.setText(order.getCustomer_name());
            comment.setText(order.getComment());
        });

//        is_hummus.setChecked(order.isHummus());
//        is_tahini.setChecked(order.isTahini());
//        name.setText(order.getCustomer_name());
//        comment.setText(order.getComment());
//        pickles = (Spinner) pickles.getSelectedItem();
//        String[] items = new String[]{"1 pickle", "2 pickles", "3 pickles", "4 pickles", "5 pickles", "6 pickles", "7 pickles", "8 pickles", "9 pickles", "10 pickles"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//        pickles.setAdapter(adapter);

        edit_order.setOnClickListener(v -> {
//            if (TextUtils.isEmpty(name.getText())) {
//                name.setError("Please insert your name");
//                return;
//            }
//            order.setHummus(is_hummus.isChecked());
//            order.setTahini(is_tahini.isChecked());
//            order.setNum_of_pickels(pickles.getSelectedItem().toString());
//            order.setCustomer_name(name.getText().toString());
//            order.setComment(comment.getText().toString());

            firestore.collection("orders").document(order_id).update("customer_Name", name.getText().toString());
            firestore.collection("orders").document(order_id).update("hummus", is_hummus.isChecked());
            firestore.collection("orders").document(order_id).update("tahini", is_tahini.isChecked());
            firestore.collection("orders").document(order_id).update("pickles",  pickles.getSelectedItem());
            firestore.collection("orders").document(order_id).update("comment", comment.getText().toString());
        });

        delete_order.setOnClickListener(v -> {
            firestore.collection("orders").document(order_id).delete();
            this.app.save_id("");
            Toast toast = Toast.makeText(this,"your order has been Deleted", Toast.LENGTH_LONG);
            toast.show();
            Intent MainActivityIntent = new Intent(this, MainActivity.class);
            this.startActivity(MainActivityIntent);
//                    .addOnSuccessListener(
//                    documentReference -> {
//                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//                        preferences.edit().putString("order_id", order.getId()).apply();
//                        Intent new_intent = new Intent(this, MainActivity.class);
//                        new_intent.putExtra("order", order);
//                        startActivity(new_intent);
//                        finish();
//            });
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
