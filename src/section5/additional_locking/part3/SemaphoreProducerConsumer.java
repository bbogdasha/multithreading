package section5.additional_locking.part3;

import java.util.concurrent.Semaphore;

public class SemaphoreProducerConsumer {

    public static void main(String[] args) {
        ControlSemaphore controlSemaphore = new ControlSemaphore();
        new Consumer(controlSemaphore);
        new Producer(controlSemaphore);
    }

    public static class ControlSemaphore {

        int n;

        static Semaphore semaphoreCon = new Semaphore(0);
        static Semaphore semaphoreProd = new Semaphore(1);

        public void get() {
            try {
                semaphoreCon.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("Got: " + n);
                semaphoreProd.release();
            }
        }

        public void put(int n) {
            try {
                semaphoreProd.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                this.n = n;
                System.out.println("Send: " + n);
                semaphoreCon.release();
            }
        }
    }

    public static class Producer implements Runnable {
        ControlSemaphore controlSemaphore;

        public Producer(ControlSemaphore controlSemaphore) {
            this.controlSemaphore = controlSemaphore;
            new Thread(this, "Producer").start();
        }

        @Override
        public void run() {
            for (int i = 0; i < 7; i++) {
                controlSemaphore.put(i);
            }
        }
    }

    public static class Consumer implements Runnable {
        ControlSemaphore controlSemaphore;

        public Consumer(ControlSemaphore controlSemaphore) {
            this.controlSemaphore = controlSemaphore;
            new Thread(this, "Consumer").start();
        }

        @Override
        public void run() {
            for (int i = 0; i < 7; i++) {
                controlSemaphore.get();
            }
        }
    }
}
