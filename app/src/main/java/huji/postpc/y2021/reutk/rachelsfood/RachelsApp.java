package huji.postpc.y2021.reutk.rachelsfood;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class RachelsApp extends Application {

    private String order_id;
    private SharedPreferences sp;

    public RachelsApp(Context context){
        this.order_id="";
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        load_id();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getOrder_id() {
        return order_id;
    }

    public void load_id() {
        order_id= sp.getString("order", "");
    }

    public void save_id(String id) {
        order_id=id;
        sp.edit().putString("order", this.order_id).apply();
    }
}
