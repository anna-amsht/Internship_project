package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PartsFactory {
    private static final int MAX_COUNT_PER_DAY = 10;
    private static final Random random = new Random();

    public static List<Parts> generateParts() {
        List<Parts> partsList = new ArrayList<>();
        Parts[] parts = Parts.values();
        for (int i = 0; i < MAX_COUNT_PER_DAY; i++) {
            Parts randomPart = parts[random.nextInt(parts.length)];
            partsList.add(randomPart);
        }
        System.out.println("Generated " + partsList.size() + " parts, parts: " + partsList);
        return partsList;
    }

}
