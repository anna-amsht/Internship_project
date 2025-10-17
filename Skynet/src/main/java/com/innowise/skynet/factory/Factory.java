package com.innowise.skynet.factory;

import com.innowise.skynet.models.Parts;
import com.innowise.skynet.models.PartsFactory;

import java.util.List;
import java.util.concurrent.*;

public class Factory extends Thread{
    private final BlockingQueue<Parts> sharedStorage;
    private volatile boolean running = true;
    private CyclicBarrier day;
    private CyclicBarrier night;
    private final Object lock = new Object();

    public Factory(BlockingQueue<Parts> sharedStorage, CyclicBarrier day, CyclicBarrier night) {
        this.sharedStorage = sharedStorage;
        this.day = day;
        this.night = night;
    }

    @Override
    public void run() {
        int day = 1;
        while (running && day <= 100) {

            List<Parts> newParts = PartsFactory.generateParts();

            for (Parts p : newParts) {
                try {
                    sharedStorage.put(p);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                this.day.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (BrokenBarrierException e) {
               return;
            }

            System.out.println("Factory finished day " + day);

            try {
                night.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (BrokenBarrierException e) {
                return;
            }
            day++;

        }
    }

    public void stopFactory() {
        running = false;
        interrupt();
    }

}