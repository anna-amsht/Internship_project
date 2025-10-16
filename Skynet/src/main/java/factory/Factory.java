package factory;

import models.Parts;
import models.PartsFactory;

import java.util.List;

public class Factory extends Thread{
    private final List<Parts> sharedStorage;
    private final PartsFactory partsFactory;
    private volatile boolean running = true;

    public Factory(List<Parts> sharedStorage, PartsFactory partsFactory) {
        this.sharedStorage = sharedStorage;
        this.partsFactory = partsFactory;
    }

    @Override
    public void run() {
        int day = 1;
        while (running && day <= 100) {
            List<Parts> newParts = partsFactory.generateParts();

            synchronized (sharedStorage) {
                sharedStorage.addAll(newParts);
                sharedStorage.notifyAll();
            }
            System.out.println("Factory finished day " + day);

            day++;

            if (running && day <= 100) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public void stopFactory() {
        running = false;
        synchronized (sharedStorage) {
            sharedStorage.notifyAll();
        }
    }
}