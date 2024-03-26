package com.example.poly_truyen_client.models;

public class Comment {
    private String _id, idComic, content, createdAt;
    private User idUser;

    public Comment() {
    }

    public Comment(String _id, String idComic, String content, String createdAt, User idUser) {
        this._id = _id;
        this.idComic = idComic;
        this.content = content;
        this.createdAt = createdAt;
        this.idUser = idUser;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdComic() {
        return idComic;
    }

    public void setIdComic(String idComic) {
        this.idComic = idComic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }
}
