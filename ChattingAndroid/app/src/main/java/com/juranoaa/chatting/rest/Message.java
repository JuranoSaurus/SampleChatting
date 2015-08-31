package com.juranoaa.chatting.rest;

import java.util.Map;

/**
 * Created by SungGeun on 2015-08-31.
 */
public class Message {
    private String name;
    private String data;
    private Map<String, Object> map;
    public Message() { }

    public Message(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Message{" +
                "name='" + name + '\'' +
                ", data='" + data + '\'' +
                ", map='" + map + '\'' +
                '}';
    }
}
