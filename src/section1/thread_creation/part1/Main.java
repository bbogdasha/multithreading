package section1.thread_creation.part1;

public class Main {

    public static void main(String[] args) {

        //Implement the Runnable way
        Thread thread1 = new Thread(() -> {
            System.out.println("We are now in thread " + Thread.currentThread().getName());
            System.out.println("Current thread priority is " + Thread.currentThread().getPriority());
        });

        thread1.setName("New worker Thread");
        thread1.setPriority(Thread.MAX_PRIORITY);

        System.out.println("We are in thread: " + Thread.currentThread().getName() + " before starting a new thread");
        thread1.start();
        System.out.println("We are in thread: " + Thread.currentThread().getName() + " after starting a new thread");


        //Extends thread class way
        Thread thread2 = new NewThread();
        thread2.start();


        //Exception handler
        Thread thread3 = new Thread(() -> {
            throw new RuntimeException("Intentional Exception");
        });

        thread3.setName("Misbehaving thread");

        thread3.setUncaughtExceptionHandler((Thread t, Throwable e) -> {
            System.out.println("A critical error happened in thread " + t.getName() +
                    " the error is " + e.getMessage());
        });

        thread3.start();
    }

    private static class NewThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello from " + this.getName());
        }
    }
}
