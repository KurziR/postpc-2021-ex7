package huji.postpc.y2021.reutk.rachelsfood;

import android.content.Context;
import android.os.Build;
import android.widget.EditText;
import android.widget.Switch;
import androidx.test.platform.app.InstrumentationRegistry;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(value = RobolectricTestRunner.class)

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class ExampleUnitTest {

    private Order order;
    private String order_id;
    private FirebaseFirestore firestore;

    @Before
    public void setup() {
        order = new Order("Reit Kurzweil", 2, true, true, "The tahini on the top please");
        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class);
        order_id = order.getId();
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FirebaseApp.initializeApp(context);
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("orders").document(order_id).set(order);
    }

    @Test
    public void update_customer_name_Test()
    {
        order.setCustomer_name("Reut Kurzweil");
        firestore.collection("orders").document(order_id).update("customer_name", order.getCustomer_name());
        firestore.collection("orders").document(order_id).get().addOnSuccessListener(documentSnapshot -> {
            assertEquals(order.getCustomer_name(), documentSnapshot.getString("customer_name"));
        });
    }

    @Test
    public void saved_in_DB_Test()
    {
        firestore.collection("orders").document(order_id).get().addOnSuccessListener(documentSnapshot -> {
            order = documentSnapshot.toObject(Order.class);
            assertEquals("Reut Kurzweil", documentSnapshot.getString("customer_name"));
            assertTrue(documentSnapshot.getBoolean("hummus"));
            assertTrue(documentSnapshot.getBoolean("tahini"));
            assertEquals(2, documentSnapshot.get("pickles"));
            assertEquals("The tahini on the top please", documentSnapshot.getString("comment"));
        });
    }

    @Test
    public void default_params_Test() {
        OrderActivity orderActivity = Robolectric.buildActivity(OrderActivity.class).create().visible().get();
        EditText customer_name = orderActivity.findViewById(R.id.personName);
        Switch hummmus = orderActivity.findViewById(R.id.is_hummus);
        Switch thaini = orderActivity.findViewById(R.id.is_tahini);
        EditText comment = orderActivity.findViewById(R.id.personRequests);

        assertEquals("", customer_name.getText().toString());
        assertFalse(hummmus.isChecked());
        assertFalse(thaini.isChecked());
        assertEquals(comment.getText().toString(), "");
    }
}