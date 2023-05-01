package section4.synchronization.exercise;

public class Main {

    public static void main(String[] args) {
        MinMaxMetrics metrics = new MinMaxMetrics();

        BusinessLogicThread businessLogicThread1 = new BusinessLogicThread(metrics, 2100);
        BusinessLogicThread businessLogicThread2 = new BusinessLogicThread(metrics, -4);
        BusinessLogicThread businessLogicThread3 = new BusinessLogicThread(metrics, 10000);
        BusinessLogicThread businessLogicThread4 = new BusinessLogicThread(metrics, -40);
        BusinessLogicThread businessLogicThread5 = new BusinessLogicThread(metrics, 1);

        MetricsPrinter printer = new MetricsPrinter(metrics);

        businessLogicThread1.start();
        businessLogicThread2.start();
        businessLogicThread3.start();
        businessLogicThread4.start();
        businessLogicThread5.start();
        printer.start();
    }

    public static class MetricsPrinter extends Thread {

        private MinMaxMetrics metrics;

        public MetricsPrinter(MinMaxMetrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            System.out.println("Max value: " + metrics.maxValue);
            System.out.println("Min value: " + metrics.minValue);
        }
    }

    public static class BusinessLogicThread extends Thread {
        private MinMaxMetrics metrics;
        private long value;

        public BusinessLogicThread(MinMaxMetrics metrics, long value) {
            this.metrics = metrics;
            this.value = value;
        }

        @Override
        public void run() {
            metrics.addSample(value);
        }
    }

    public static class MinMaxMetrics {

        private long minValue;
        private long maxValue;

        /**
         * Initializes all member variables
         */
        public MinMaxMetrics() {
            this.maxValue = Long.MIN_VALUE;
            this.minValue = Long.MAX_VALUE;
        }

        /**
         * Adds a new sample to our metrics.
         */
        public void addSample(long newSample) {
            synchronized (this) {
                this.minValue = Math.min(newSample, this.minValue);
                this.maxValue = Math.max(newSample, this.maxValue);
            }
        }

        /**
         * Returns the smallest sample we've seen so far.
         */
        public long getMin() {
            return this.minValue;
        }

        /**
         * Returns the biggest sample we've seen so far.
         */
        public long getMax() {
            return this.maxValue;
        }
    }
}