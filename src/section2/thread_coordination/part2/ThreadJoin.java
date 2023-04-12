package section2.thread_coordination.part2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadJoin {

    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(0L, 5L, 100000000L, 10L, 23451L, 2000L, 23457L, 25L, 5000L);

        List<FactorialThread> threads = new ArrayList<>();

        for (long number : inputNumbers) {
            threads.add(new FactorialThread(number));
        }

        //Daemon threads which will be enough to lead the app terminal
        for (Thread thread : threads) {
            thread.setDaemon(true);
            thread.start();
        }

        //If after three seconds a thread has not terminated the join method with return
        for (Thread thread : threads) {
            thread.join(3000);
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if (factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("The calculation for " + inputNumbers.get(i) + " is still in progress");
            }
        }
    }

    public static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        public BigInteger factorial(long number) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = number; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }

            return tempResult;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
