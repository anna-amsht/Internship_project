package factory;

import models.Parts;
import models.Robot;
import models.RobotBuilder;

import java.util.ArrayList;
import java.util.List;

public class Faction extends Thread{
    private final String name;
    private final List<Parts> sharedStorage;
    private final RobotBuilder robotBuilder;
    private final List<Robot> robots = new ArrayList<>();
    private volatile boolean running = true;
    private static final int MAX_COUNT_PER_NIGHT = 5;

    public Faction(String name, List<Parts> sharedStorage) {
        this.name = name;
        this.sharedStorage = sharedStorage;
        this.robotBuilder = new RobotBuilder(name);
    }

    @Override
    public void run() {
        while (running) {
            List<Parts> takenParts = new ArrayList<>();

            synchronized (sharedStorage) {
                while (sharedStorage.isEmpty() && running) {
                    try {
                        sharedStorage.wait();
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }

                int count = Math.min(MAX_COUNT_PER_NIGHT, sharedStorage.size());
                for (int i = 0; i < count; i++) {
                    takenParts.add(sharedStorage.remove(0));
                }
            }

            for (Parts part : takenParts) {
                robotBuilder.addPart(part);
            }

            while (robotBuilder.canBuild()) {
                Robot robot = robotBuilder.build();
                if (robot != null) {
                    robots.add(robot);
                }
            }
        }
    }

    public void stopFaction() {
        running = false;
        synchronized (sharedStorage) {
            sharedStorage.notifyAll();
        }
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public int getRobotCount() {
        return robots.size();
    }
    public RobotBuilder getRobotBuilder() {
        return robotBuilder;
    }

}
