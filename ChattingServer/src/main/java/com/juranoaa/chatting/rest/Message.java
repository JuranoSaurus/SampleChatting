package com.juranoaa.chatting.rest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SungGeun on 2015-08-31.
 */
public class Message {

    private String name;

    private String data;

    public Message() {

    }

    public Message(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public Map<String, Object> getMap(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("data", data);
        return map;
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

    @Override
    public String toString() {
        return "Message{" +
                "name='" + name + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
