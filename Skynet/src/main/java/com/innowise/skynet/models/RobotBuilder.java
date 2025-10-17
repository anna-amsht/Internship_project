package com.innowise.skynet.models;

import java.util.EnumMap;
import java.util.Map;

public class RobotBuilder {
    private final String ownerName;
    private final Map<Parts, Integer> parts = new EnumMap<>(Parts.class);

    public RobotBuilder(String ownerName) {
        this.ownerName = ownerName;
        for (Parts p : Parts.values()) {
            parts.put(p, 0);
        }
    }

    public void addPart(Parts addedPart) {
        parts.put(addedPart, parts.get(addedPart) + 1);
    }

    public boolean canBuild() {
        return parts.get(Parts.HEAD) >= 1 &&
                parts.get(Parts.TORSO) >= 1 &&
                parts.get(Parts.HAND) >= 2 &&
                parts.get(Parts.FOOT) >= 2;
    }

    public Robot build() {
        if (!canBuild()) {
            return null;
        }
        parts.put(Parts.HEAD, parts.get(Parts.HEAD) - 1);
        parts.put(Parts.TORSO, parts.get(Parts.TORSO) - 1);
        parts.put(Parts.HAND, parts.get(Parts.HAND) - 2);
        parts.put(Parts.FOOT, parts.get(Parts.FOOT) - 2);

        Robot robot = new Robot(ownerName);
        System.out.println(ownerName + "robot: " + robot);
        return robot;
    }
}
