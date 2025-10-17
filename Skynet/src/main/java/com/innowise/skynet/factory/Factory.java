package com.innowise.skynet.factory;

import com.innowise.skynet.models.Parts;
import com.innowise.skynet.models.PartsFactory;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Factory extends Thread{
    private final BlockingQueue<Parts> sharedStorage;
    private volatile boolean running = true;
    private CountDownLatch dayLatch;
    private final Object lock = new Object();

    public Factory(BlockingQueue<Parts> sharedStorage) {
        this.sharedStorage = sharedStorage;
    }

    @Override
    public void run() {
        int day = 1;
        while (running && day <= 100) {

            synchronized (lock) {
                dayLatch = new CountDownLatch(2);
            }
            List<Parts> newParts = PartsFactory.generateParts();

            for (Parts p : newParts) {
                try {
                  sharedStorage.put(p);
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }
            }
            System.out.println("Factory finished day " + day);
            CountDownLatch currentLatch;
            synchronized (lock) {
                currentLatch = dayLatch;
            }
            try {
                currentLatch.await();
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
            day++;

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

    public CountDownLatch getCurrentLatch() {
        synchronized (lock) {
            return dayLatch;
        }
    }
}