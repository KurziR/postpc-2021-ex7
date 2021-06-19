package huji.postpc.y2021.reutk.rachelsfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;


public class IsReadyActivity extends AppCompatActivity {

    private RachelsApp app;
    private Order order;
    private FirebaseFirestore firestore;
    private Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.is_ready_order_activity);

        app = new RachelsApp(this);
        firestore = FirebaseFirestore.getInstance();
//        Intent intent = getIntent();
//        order = (Order) intent.getSerializableExtra("order");

        done = findViewById(R.id.done);
//        done.setOnClickListener(v -> {
//            order.setStatus("done");
//            firestore.collection("orders").document(order.getId()).
//                    update("status", order.getStatus()).addOnSuccessListener(u -> {
//                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
//                sp.edit().putString("order_id", null).apply();
//                Intent new_intent = new Intent(IsReadyActivity.this, MainActivity.class);
//                startActivity(new_intent);
//                finish();
//            });
//        });

        done.setOnClickListener(view -> {
            firestore.collection("orders").document(app.getOrder_id()).update("status", "done");
            Toast toast = Toast.makeText(this,"bon appetit:)", Toast.LENGTH_LONG);
            toast.show();
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            this.startActivity(mainActivityIntent);
            finish();
        });
    }
}
