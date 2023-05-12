package section6.block_condition.part3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Exercise {

    public static class ExampleOne {
        private int count;

        public ExampleOne(int count) {
            this.count = count;
            if (count < 0) {
                throw new IllegalArgumentException("count cannot be negative");
            }
        }

        /**
         * Causes the current thread to wait until the latch has counted down to zero.
         * If the current count is already zero then this method returns immediately.
         */
        public void await() throws InterruptedException {
            synchronized (this) {
                while (count > 0) {
                    this.wait();
                }
            }
        }

        /**
         * Decrements the count of the latch, releasing all waiting threads when the count reaches zero.
         * If the current count already equals zero then nothing happens.
         */
        public void countDown() {
            synchronized (this) {
                if (count > 0) {
                    count--;

                    if (count == 0) {
                        this.notifyAll();
                    }
                }
            }
        }

        /**
         * Returns the current count.
         */
        public int getCount() {
            return count;
        }
    }


    /**
     * =================================
     */

    public static class ExampleTwo {
        private int count;
        private ReentrantLock lock;
        private Condition condition;

        public ExampleTwo(int count) {
            this.count = count;
            if (count < 0) {
                throw new IllegalArgumentException("count cannot be negative");
            }
            this.lock = new ReentrantLock();
            this.condition = lock.newCondition();
        }

        /**
         * Causes the current thread to wait until the latch has counted down to zero.
         * If the current count is already zero then this method returns immediately.
         */
        public void await() throws InterruptedException {
            lock.lock();
            try {
                while (count > 0) {
                    condition.await();
                }
            } finally {
                lock.unlock();
            }
        }

        /**
         * Decrements the count of the latch, releasing all waiting threads when the count reaches zero.
         * If the current count already equals zero then nothing happens.
         */
        public void countDown() {
            lock.lock();
            try {
                if (count > 0) {
                    count--;

                    if (count == 0) {
                        condition.signalAll();
                    }
                }
            } finally {
                lock.unlock();
            }
        }

        /**
         * Returns the current count.
         */
        public int getCount() {
            return count;
        }
    }

}
