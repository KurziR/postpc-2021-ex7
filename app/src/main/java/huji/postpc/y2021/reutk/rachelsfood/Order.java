package huji.postpc.y2021.reutk.rachelsfood;

import java.util.UUID;

public class Order {

    private String id;
    private String customer_name;
    private int num_of_pickels = 0;
    private boolean hummus;
    private boolean tahini;
    private String comment;
    private String status;


    public Order(String name, int pickels, boolean hummus, boolean tahini) {
        id = UUID.randomUUID().toString();
        customer_name = name;
        status = "waiting";
        num_of_pickels = pickels;
        if(hummus) {
            hummus = true;
        }
        else {
            hummus = false;
        }
        if(tahini) {
            tahini = true;
        }
        else {
            tahini = false;
        }
    }


    public String getId() {
        return id;
    }

    public int getNum_of_pickels() {
        return num_of_pickels;
    }

    public void setNum_of_pickels(int num_of_pickels) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
