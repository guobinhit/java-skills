package com.hit.thought.chapter18;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * author:Charies Gavin
 * date:2019/4/14,20:30
 * https:github.com/guobinhit
 * description:哲学家进餐问题
 */
public class DiningPhilosophers {
    public static void main(String[] args) throws Exception {
        // 死锁测试
        deadlockingDiningPhilosophers(args);

        // 破坏死锁
//        fixedDiningPhilosophers(args);
    }

    private static void deadlockingDiningPhilosophers(String[] args) throws Exception {
        int ponder = 3;
        if (args.length > 0) {
            ponder = Integer.parseInt(args[0]);
        }

        int size = 3;
        if (args.length > 1) {
            size = Integer.parseInt(args[1]);
        }

        ExecutorService executors = Executors.newCachedThreadPool();

        Chopstick[] sticks = new Chopstick[size];

        for (int i = 0; i < size; i++) {
            sticks[i] = new Chopstick();
        }

        for (int i = 0; i < size; i++) {
            executors.execute(new Philosopher(
                    sticks[i], sticks[(i + 1) % size], i, ponder
            ));
        }

        if (args.length == 3 && args[2].equals("timeout")) {
            TimeUnit.SECONDS.sleep(5);
        } else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }
        executors.shutdownNow();
    }

    private static void fixedDiningPhilosophers(String[] args) throws Exception {
        int ponder = 3;
        if (args.length > 0) {
            ponder = Integer.parseInt(args[0]);
        }

        int size = 3;
        if (args.length > 1) {
            size = Integer.parseInt(args[1]);
        }

        ExecutorService executors = Executors.newCachedThreadPool();

        Chopstick[] sticks = new Chopstick[size];

        for (int i = 0; i < size; i++) {
            sticks[i] = new Chopstick();
        }

        for (int i = 0; i < size; i++) {
            // 破坏循环
            if (i < (size - 1)) {
                executors.execute(new Philosopher(
                        sticks[i], sticks[i + 1], i, ponder
                ));
            } else {
                executors.execute(new Philosopher(
                        sticks[0], sticks[i], i, ponder
                ));
            }
        }

        if (args.length == 3 && args[2].equals("timeout")) {
            TimeUnit.SECONDS.sleep(5);
        } else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }
        executors.shutdownNow();
    }
}


class Chopstick {
    private boolean taken = false;

    public synchronized void take() throws InterruptedException {
        while (taken) {
            wait();
        }
        taken = true;
    }

    public synchronized void drop() {
        taken = false;
        notifyAll();
    }
}

class Philosopher implements Runnable {
    private Chopstick left;
    private Chopstick right;

    private final int id;
    private final int ponderFactor;

    private Random rand = new Random(47);

    private void pause() throws InterruptedException {
        if (ponderFactor == 0) {
            return;
        }
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
    }

    public Philosopher(Chopstick left, Chopstick right, int ident, int ponder) {
        this.left = left;
        this.right = right;
        this.id = ident;
        this.ponderFactor = ponder;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println(this + " " + "thinking");
                pause();

                System.out.println(this + " " + "grabbing right");
                right.take();

                System.out.println(this + " " + "grabbing left");
                left.take();

                System.out.println(this + " " + "eating");
                pause();

                right.drop();
                left.drop();
            }
        } catch (InterruptedException e) {
            System.out.println(this + " " + "exiting via interrupt");
        }
    }

    @Override
    public String toString() {
        return "Philosopher " + id;
    }
}
