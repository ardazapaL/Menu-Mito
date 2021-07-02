package com.example.menumito.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CheckoutModel {

    private String id_food;
    private String url_food;
    private String name_food;
    private String count_food;

    public CheckoutModel(){


    }
    public CheckoutModel(String id_food, String url_food, String name_food, String count_food) {
        this.id_food = id_food;
        this.url_food = url_food;
        this.name_food = name_food;
        this.count_food = count_food;
    }

    public String getId_food() {
        return id_food;
    }

    public void setId_food(String id_food) {
        this.id_food = id_food;
    }

    public String getName_food() {
        return name_food;
    }

    public void setName_food(String name_food) {
        this.name_food = name_food;
    }

    public String getCount_food() {
        return count_food;
    }

    public void setCount_food(String count_food) {
        this.count_food = count_food;
    }

    public String getUrl_food() {
        return url_food;
    }

    public void setUrl_food(String url_food) {
        this.url_food = url_food;
    }

    // Parcelling part
    public CheckoutModel(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.id_food = data[0];
        this.url_food = data[1];
        this.name_food = data[2];
        this.count_food = data[3];
    }

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.id_food,
                this.url_food,
                this.name_food,
                this.count_food});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CheckoutModel createFromParcel(Parcel in) {
            return new CheckoutModel(in);
        }

        public CheckoutModel[] newArray(int size) {
            return new CheckoutModel[size];
        }
    };
}
