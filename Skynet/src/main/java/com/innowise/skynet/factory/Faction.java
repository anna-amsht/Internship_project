package com.innowise.skynet.factory;

import com.innowise.skynet.models.Parts;
import com.innowise.skynet.models.Robot;
import com.innowise.skynet.models.RobotBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Faction extends Thread{
    private final String name;
    private final BlockingQueue<Parts> sharedStorage;
    private final RobotBuilder robotBuilder;
    private final List<Robot> robots = new ArrayList<>();
    private volatile boolean running = true;
    private static final int MAX_COUNT_PER_NIGHT = 5;
    private final CyclicBarrier day;
    private final CyclicBarrier night;


    public Faction(String name, BlockingQueue<Parts> sharedStorage, CyclicBarrier day, CyclicBarrier night) {
        this.name = name;
        this.sharedStorage = sharedStorage;
        this.day = day;
        this.night = night;
        this.robotBuilder = new RobotBuilder(name);
    }

    @Override
    public void run() {
        while (running) {
            try {
                day.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (BrokenBarrierException e) {
                return;
            }

            List<Parts> takenParts = new ArrayList<>();

            for (int i = 0; i < MAX_COUNT_PER_NIGHT; i++) {
                try {
                    Parts parts = sharedStorage.poll(100, TimeUnit.MILLISECONDS);
                    if (parts != null) {
                        takenParts.add(parts);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
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

            System.out.println(name + " finished work for the day");

            try {
                night.await();
            } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
            } catch (BrokenBarrierException e) {
                return;
            }
        }
    }

    public void stopFaction() {
        running = false;
        interrupt();
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
