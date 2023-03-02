package com.example.mongotestapi.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("joke")
public class Joke {
    private String ct; //current time
    private String id;
    private String text; //文本内容
    private String title;
    private String type;

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "ct='" + ct + '\'' +
                ", id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

