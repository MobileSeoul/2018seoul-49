package kr.co.mikarusoft.findbookstore;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by KokoroNihonn3DGame on 2018-08-07.
 */

public class List_Item {

    private String id;


    private Bitmap bitmapimg;
    private String imgURL;
    private String content;

    private Drawable picture;
    private String name;

    private String gpsLat;
    private String gpsLng;

    private String category;
    private String ex;
    private String address;

    public List_Item(String id, Bitmap bitmapimg, String imgURL, String content, Drawable picture, String name, String gpsLat, String gpsLng, String category, String ex, String address) {
        this.id = id;
        this.bitmapimg = bitmapimg;
        this.imgURL = imgURL;
        this.content = content;
        this.picture = picture;
        this.name = name;
        this.gpsLat = gpsLat;
        this.gpsLng = gpsLng;
        this.category = category;
        this.ex = ex;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getBitmapimg() {
        return bitmapimg;
    }

    public void setBitmapimg(Bitmap bitmapimg) {
        this.bitmapimg = bitmapimg;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Drawable getPicture() {
        return picture;
    }

    public void setPicture(Drawable picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(String gpsLat) {
        this.gpsLat = gpsLat;
    }

    public String getGpsLng() {
        return gpsLng;
    }

    public void setGpsLng(String gpsLng) {
        this.gpsLng = gpsLng;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
