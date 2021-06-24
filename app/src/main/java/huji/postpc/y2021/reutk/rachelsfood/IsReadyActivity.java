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


        done = findViewById(R.id.done);
        done.setOnClickListener(view -> {
            firestore.collection("orders").document(app.getOrder_id()).update("status", "done");
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            this.startActivity(mainActivityIntent);
            finish();
        });
    }
}
