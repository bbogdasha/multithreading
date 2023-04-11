package section2.thread_coordination.part1;

public class ThreadInterrupt2 {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new WithoutSleep();
        thread.start();
        Thread.sleep(10);
        thread.interrupt();
    }

    //If sleep() is not used, interrupt is manually checked with the isInterrupted() method
    static class WithoutSleep extends Thread {
        @Override
        public void run() {
            while (!this.isInterrupted()) {
                System.out.println("a");
            }
        }
    }
}
