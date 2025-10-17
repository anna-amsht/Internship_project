package com.innowise.skynet.models;

public class Robot {
    private final int id;
    private final String factionName;
    private static int nextId = 1;

    public Robot(String factionName) {
        this.id = nextId++;
        this.factionName = factionName;
    }

    public int getId() {
        return id;
    }

    public String getFactionName() {
        return factionName;
    }


    @Override
    public String toString() {
        return "Robot{" +
                "id=" + id +
                ", factionName='" + factionName + '\'' +
                '}';
    }
}
