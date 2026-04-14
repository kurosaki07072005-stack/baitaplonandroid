package com.example.testaplication.Admin;

public class ImageList {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageList(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "StoreImage{" +
                "id=" + id +
                '}';
    }
}
