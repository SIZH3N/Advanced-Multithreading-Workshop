package Workshop.HandlingRaceConditions;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedWorkshop {
    public static int counter = 0;
    public static class MyRunnable implements Runnable
    {
        //TODO: use `synchronized` to prevent race condition
        @Override
        public void run() {
            int i;
            for (i = 0; i < 1_000_000; i++) {
                synchronized (SynchronizedWorkshop.class) {
                    counter += 1;
                }
            }
            System.out.println("number of increments in " + Thread.currentThread().getName() + ": " + i);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new MyRunnable());
        Thread thread2 = new Thread(new MyRunnable());
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("the value of counter at the end: " + counter);
    }
}