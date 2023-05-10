package section6.block_condition.part1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {

    public static void main(String[] args) {
        Store store = new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);

        new Thread(producer).start();
        new Thread(consumer).start();
    }

    public static class Store {

        private int product = 0;
        ReentrantLock lock;
        Condition condition;

        public Store() {
            this.lock = new ReentrantLock();
            this.condition = lock.newCondition();
        }

        public void get() {
            lock.lock();
            try {
                while (product < 1) {
                    System.out.println("... The store is empty");
                    condition.await();
                }

                product--;
                System.out.println("--- The customer bought 1 item");
                System.out.println("Items on storage: " + product);

                condition.signalAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }

        public void put() {
            lock.lock();
            try {
                while (product >= 3) {
                    System.out.println("!!! The store is full");
                    condition.await();
                }

                product++;
                System.out.println("+++ The customer added 1 item");
                System.out.println("Items on storage: " + product);

                condition.signalAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }

    public static class Producer implements Runnable {
        Store store;

        public Producer(Store store) {
            this.store = store;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                store.put();
            }
        }
    }

    public static class Consumer implements Runnable {
        Store store;

        public Consumer(Store store) {
            this.store = store;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                store.get();
            }
        }
    }
}
