package com.innowise.skynet.factory;

import com.innowise.skynet.models.Parts;
import com.innowise.skynet.models.PartsFactory;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Factory extends Thread{
    private final BlockingQueue<Parts> sharedStorage;
    private final PartsFactory partsFactory;
    private volatile boolean running = true;
    private CountDownLatch dayLatch;

    public Factory(BlockingQueue<Parts> sharedStorage, PartsFactory partsFactory) {
        this.sharedStorage = sharedStorage;
        this.partsFactory = partsFactory;
    }

    @Override
    public void run() {
        int day = 1;
        while (running && day <= 100) {
            List<Parts> newParts = partsFactory.generateParts();
            dayLatch = new CountDownLatch(2);

            for (Parts p : newParts) {
                try {
                  sharedStorage.put(p);
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }
            }

            System.out.println("Factory finished day " + day);
            try {
                dayLatch.await();
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
            day++;

            if (running && day <= 100) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void stopFactory() {
        running = false;
        interrupt();
    }
    public void finishDay() {
        if (dayLatch != null) {
            dayLatch.countDown();
        }
    }
}