package huji.postpc.y2021.reutk.rachelsfood;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

public class InProgressActivity extends AppCompatActivity {

    private RachelsApp app;
    private Order order;
    private FirebaseFirestore firestore;

    private ListenerRegistration listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_progress_order_activity);

        app = new RachelsApp(this);
        String order_id = app.getOrder_id();
        firestore = FirebaseFirestore.getInstance();
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        order.setStatus("ready");
                        firestore.collection("orders").document(order_id).set(order);
                    }
                },
                5000);

        listener = firestore.collection("orders").document(order_id).addSnapshotListener((val, error) -> {
            if(val != null) {
                order = val.toObject(Order.class);
                if(order.getStatus().equals("ready")) {
                    Intent ready_intent = new Intent(this, IsReadyActivity.class);
                    ready_intent.putExtra("order", order);
                    startActivity(ready_intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        listener.remove();
    }
}
