package section2.thread_coordination.part1;

import java.math.BigInteger;

public class DaemonThread {

    //In this example, after the main and only USER thread is terminated,
    //it forces the all application to terminate.
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = new LongComputationTask(new BigInteger("20000"), new BigInteger("10000000"));
        Thread thread = new Thread(runnable);

        thread.setDaemon(true); //user thread -> daemon thread
        thread.start();
        Thread.sleep(100);
        thread.interrupt();
    }

    private static class LongComputationTask implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
            return result;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }
    }
}
