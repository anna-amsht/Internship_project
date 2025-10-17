package com.innowise.skynet;

import com.innowise.skynet.factory.Faction;
import com.innowise.skynet.factory.Factory;
import com.innowise.skynet.models.Parts;
import com.innowise.skynet.models.PartsFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Parts> sharedStorage = new LinkedBlockingQueue<Parts>();

        CyclicBarrier day = new CyclicBarrier(3);
        CyclicBarrier night = new CyclicBarrier(3);

        PartsFactory partsFactory = new PartsFactory();
        Factory factory = new Factory(sharedStorage, day, night);

        Faction worldFaction = new Faction("World", sharedStorage, day, night);
        Faction wednesdayFaction = new Faction("Wednesday", sharedStorage, day, night);

        factory.start();
        worldFaction.start();
        wednesdayFaction.start();

        factory.join();

        worldFaction.stopFaction();
        wednesdayFaction.stopFaction();

        worldFaction.join();
        wednesdayFaction.join();

        System.out.println("World Faction " + worldFaction.getRobotCount());
        System.out.println("Wednesday Faction " + wednesdayFaction.getRobotCount());


        int worldRobots = worldFaction.getRobotCount();
        int wednesdayRobots = wednesdayFaction.getRobotCount();

        System.out.println("\n Results:\n");
        System.out.println("World faction robots: " + worldRobots);
        System.out.println("Wednesday faction robots: " + wednesdayRobots);

        if (worldRobots > wednesdayRobots) {
            System.out.println("World faction has the strongest army!");
        } else if (wednesdayRobots > worldRobots) {
            System.out.println("Wednesday faction has the strongest army!");
        } else {
            System.out.println("It's a tie! Both factions have equally strong armies.");
        }
    }
}
