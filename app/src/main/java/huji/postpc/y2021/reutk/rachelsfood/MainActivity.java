package huji.postpc.y2021.reutk.rachelsfood;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String order_id = sp.getString("order_id", "");
        if(!order_id.equals("")) { firestore.collection("orders").document(order_id).get().
                    addOnSuccessListener(v -> {
                        if (v == null){
                            newOrderActivity();
                            return;
                        }
                        Order order = v.toObject(Order.class);
                        if (order == null){
                            newOrderActivity();
                            return;
                        }
                        if (order.getStatus().equals("done")){
                            newOrderActivity();
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
                    });
            return;
        }
        newOrderActivity();
    }

    private void newOrderActivity(){
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    private void alreadyOrderActivity(Class<?> activity, Order order){
        Intent intent = new Intent(this, activity);
        intent.putExtra("order", order);
        startActivity(intent);
    }
}
