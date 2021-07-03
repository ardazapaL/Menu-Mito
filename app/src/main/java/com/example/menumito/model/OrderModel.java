package com.example.menumito.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderModel implements Parcelable{

    private String id;
    private String url_food;
    private String name_food;
    private String order_count;
    private String date;

    public OrderModel(String id, String url_food, String name_food,
                      String order_count, String date) {
        this.id = id;
        this.url_food = url_food;
        this.name_food = name_food;
        this.order_count = order_count;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl_food() {
        return url_food;
    }

    public void setUrl_food(String url_food) {
        this.url_food = url_food;
    }

    public String getName_food() {
        return name_food;
    }

    public void setName_food(String name_food) {
        this.name_food = name_food;
    }

    public String getOrder_count() {
        return order_count;
    }

    public void setOrder_count(String order_count) {
        this.order_count = order_count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /* PARCELLING PART */
    public OrderModel(Parcel in){
        String[] data = new String[5];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.id = data[0];
        this.url_food = data[1];
        this.name_food = data[2];
        this.order_count = data[3];
        this.date = data[4];
    }

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.id,
                this.url_food,
                this.name_food,
                this.order_count,
                this.date});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public OrderModel createFromParcel(Parcel in) {
            return new OrderModel(in);
        }

        public OrderModel[] newArray(int size) {
            return new OrderModel[size];
        }
    };

}
