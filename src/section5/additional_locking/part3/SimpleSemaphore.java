package section5.additional_locking.part3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SimpleSemaphore {

    public static void main(String[] args) {
        //binarySemaphore();
        countingSemaphore();
    }

    private static void binarySemaphore() {
        Semaphore binarySemaphore = new Semaphore(1);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new MyRunnable(binarySemaphore));
        executorService.execute(new MyRunnable(binarySemaphore));

        executorService.shutdown();
    }

    private static void countingSemaphore() {
        Semaphore countingSemaphore = new Semaphore(3);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(new MyRunnable(countingSemaphore));
        executorService.execute(new MyRunnable(countingSemaphore));
        executorService.execute(new MyRunnable(countingSemaphore));
        executorService.execute(new MyRunnable(countingSemaphore));
        executorService.execute(new MyRunnable(countingSemaphore));

        executorService.shutdown();
    }

    public static class MyRunnable implements Runnable {
        private final Semaphore semaphore;

        public MyRunnable(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();

            try {
                System.out.println(name + " semaphore available: " + semaphore.availablePermits());

                semaphore.acquire();
                System.out.println(name + " semaphore.acquire()");

                System.out.println(name + " semaphore available: " + semaphore.availablePermits());
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println(name + " semaphore.release()");
                semaphore.release();

                System.out.println(name + " semaphore available: " + semaphore.availablePermits());
            }
        }
    }
}
