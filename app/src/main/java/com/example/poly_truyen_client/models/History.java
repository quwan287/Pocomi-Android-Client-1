package com.example.poly_truyen_client.models;

import java.util.ArrayList;

public class History {
    private String _id, user, createdAt;
    private ArrayList<Comic> history;

    public History() {
    }

    public History(String _id, String user, String createdAt, ArrayList<Comic> history) {
        this._id = _id;
        this.user = user;
        this.createdAt = createdAt;
        this.history = history;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ArrayList<Comic> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Comic> history) {
        this.history = history;
    }
}
