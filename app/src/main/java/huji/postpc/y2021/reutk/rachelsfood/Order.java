package huji.postpc.y2021.reutk.rachelsfood;

import java.io.Serializable;
import java.util.UUID;

public class Order implements Serializable {

    private String id;
    private String customer_name;
    private int num_of_pickels = 0;
    private boolean hummus;
    private boolean tahini;
    private String comment;
    private String status;

    public Order() {
        this.customer_name = "";
        this.status = "waiting";
        this.num_of_pickels = 0;
        this.hummus = false;
        this.tahini = false;
    }


    public Order(String name, int pickels, boolean hummus, boolean tahini) {
        this.id = UUID.randomUUID().toString();
        this.customer_name = name;
        this.status = "waiting";
        this.num_of_pickels = pickels;
        this.hummus = hummus;
        this.tahini = tahini;
    }


    public String getId() {
        return id;
    }

    public void setCustomer_name(String name) {
        this.customer_name = name;
    }

    public int getNum_of_pickels() {
        return num_of_pickels;
    }

    public void setNum_of_pickels(Integer num_of_pickels) {
        this.num_of_pickels = num_of_pickels;
    }

    public boolean isHummus() {
        return hummus;
    }

    public boolean isTahini() {
        return tahini;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setHummus(boolean hummus) {
        this.hummus = hummus;
    }

    public void setTahini(boolean tahini) {
        this.tahini = tahini;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
