public class Main {

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            System.out.println("We are now in thread " + Thread.currentThread().getName());
            System.out.println("Current thread priority is " + Thread.currentThread().getPriority());
        });

        thread.setName("New worker Thread");
        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("We are in thread: " + Thread.currentThread().getName() + " before starting a new thread");
        thread.start();
        System.out.println("We are in thread: " + Thread.currentThread().getName() + " after starting a new thread");

        
        //Exception handler
        Thread thread2 = new Thread(() -> {
            throw new RuntimeException("Intentional Exception");
        });

        thread2.setName("Misbehaving thread");

        thread2.setUncaughtExceptionHandler((Thread t, Throwable e) -> {
            System.out.println("A critical error happened in thread " + t.getName() +
                    " the error is " + e.getMessage());
        });

        thread2.start();
    }
}