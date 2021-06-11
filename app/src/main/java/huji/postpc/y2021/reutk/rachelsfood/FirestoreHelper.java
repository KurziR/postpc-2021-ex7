package huji.postpc.y2021.reutk.rachelsfood;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreHelper {

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public LiveData<Order> downloadOrders(String orderId) {
        MutableLiveData<Order> liveData = new MutableLiveData<>();
        liveData.setValue(null);
        firestore.collection("orders").document(orderId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Order order = documentSnapshot.toObject(Order.class);
                    liveData.setValue(order);
                }
            }
        });
        return liveData;
    }

    firestore.collection("users")
            .add(user)
        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
        @Override
        public void onSuccess(DocumentSnapshot documentReference) {
            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
        }

    public void initialize() {
        firestore.collection("orders").get();

        for (DocumentSnapshot documentSnapshot:) {
            Order order = documentSnapshot.toObject(Order.class);

        }
    }

}
