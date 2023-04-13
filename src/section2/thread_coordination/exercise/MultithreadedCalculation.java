package section2.thread_coordination.exercise;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MultithreadedCalculation {

    public static void main(String[] args) {
        BigInteger result = calculateResult(new BigInteger(Long.toString(2L)), new BigInteger(Long.toString(10L)),
                new BigInteger(Long.toString(2L)), new BigInteger(Long.toString(10L)));

        System.out.println(result);
    }

    public static BigInteger calculateResult(BigInteger base1, BigInteger power1,
                                             BigInteger base2, BigInteger power2) {
        BigInteger result = BigInteger.ZERO;

        List<PowerCalculatingThread> threads = new ArrayList<>();
        threads.add(new PowerCalculatingThread(base1, power1));
        threads.add(new PowerCalculatingThread(base2, power2));

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Error");
            }
        }

        for (PowerCalculatingThread thread : threads) {
            result = result.add(thread.getResult());
        }

        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }

            this.result = result;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
