package section4.synchronization.part3;

public class DeadLock {

    public static final Object Lock1 = new Object();
    public static final Object Lock2 = new Object();


    public static void main(String[] args) {
        DeadThreadOne threadOne = new DeadThreadOne();
        DeadThreadTwo threadTwo = new DeadThreadTwo();

        threadOne.start();
        threadTwo.start();
    }

    private static class DeadThreadOne extends Thread {

        public void run() {
            synchronized (Lock1) {
                System.out.println("DeadThreadOne is holding LOCK 1...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("DeadThreadOne is waiting for Lock 2...");
                synchronized (Lock2) {
                    System.out.println("DeadThreadOne is holding Lock 1 and Lock 2...");
                }
            }
        }
    }

//    DeadLock situation

//    private static class DeadThreadTwo extends Thread {
//
//
//        public void run() {
//            synchronized (Lock2) {
//                System.out.println("DeadThreadOne is holding LOCK 1...");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("DeadThreadOne is waiting for Lock 2...");
//                synchronized (Lock1) {
//                    System.out.println("DeadThreadOne is holding Lock 1 and Lock 2...");
//                }
//            }
//        }
//    }

    private static class DeadThreadTwo extends Thread {


        public void run() {
            synchronized (Lock1) {
                System.out.println("DeadThreadTwo is holding LOCK 1...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("DeadThreadTwo is waiting for Lock 2...");
                synchronized (Lock2) {
                    System.out.println("DeadThreadTwo is holding Lock 1 and Lock 2...");
                }
            }
        }
    }
}