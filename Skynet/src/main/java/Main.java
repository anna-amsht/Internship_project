import factory.Faction;
import factory.Factory;
import models.Parts;
import models.PartsFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Parts> sharedStorage = new ArrayList<>();

        PartsFactory partsFactory = new PartsFactory();
        Factory factory = new Factory(sharedStorage, partsFactory);

        Faction worldFaction = new Faction("World", sharedStorage);
        Faction wednesdayFaction = new Faction("Wednesday", sharedStorage);

        factory.start();
        worldFaction.start();
        wednesdayFaction.start();

        for (int day = 1; day <= 100; day++) {
            Thread.sleep(100);
            System.out.println("Day " + day + " completed");
        }

        factory.stopFactory();
        worldFaction.stopFaction();
        wednesdayFaction.stopFaction();

        factory.join();
        worldFaction.join();
        wednesdayFaction.join();

        int worldRobots = worldFaction.getRobotCount();
        int wednesdayRobots = wednesdayFaction.getRobotCount();

        System.out.println("\n=== SIMULATION RESULTS ===");
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
