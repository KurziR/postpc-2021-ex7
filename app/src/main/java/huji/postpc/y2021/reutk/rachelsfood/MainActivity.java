package huji.postpc.y2021.reutk.rachelsfood;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String order_id = new RachelsApp(this).getOrder_id();
        if(!order_id.equals("")) {
            firestore.collection("orders").document(order_id).get().addOnSuccessListener(v -> {
                        if (v == null){
                            newOrderActivity();
                            return;
                        }
                        Order order = v.toObject(Order.class);
                        if (order == null){
                            newOrderActivity();
                            return;
                        }
                        if (order.getStatus().equals("waiting")){
                            alreadyOrderActivity(EditActivity.class, order);
                            return;
                        }
                        if (order.getStatus().equals("in-progress")){
                            alreadyOrderActivity(InProgressActivity.class, order);
                            return;
                        }
                        if (order.getStatus().equals("ready")){
                            alreadyOrderActivity(IsReadyActivity.class, order);
                        }
                        if (order.getStatus().equals("done")){
                            newOrderActivity();
                        }
                    });
            return;
        }

        newOrderActivity();
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
}
