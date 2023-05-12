package section7.atomic_operations.part2;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferences {

    /**
     * It is not thread safe!
     * -
     * Some operations became thread safe, like sum.addAndGet(sample) or count.incrementAndGet();.
     * However if read or modify 2 variables, even if each individual operation on each variable is atomic,
     * the aggregate (all) operation is not
     */
    public static class MetricOne {
        private AtomicLong count = new AtomicLong(0);
        private AtomicLong sum = new AtomicLong(0);

        public void addSample(long sample) {
            sum.addAndGet(sample);
            count.incrementAndGet();
        }

        public double getAverage() {
            double average = (double)sum.get()/count.get();
            reset();
            return average;
        }

        private void reset() {
            count.set(0);
            sum.set(0);
        }
    }


    /**
     * ======================================
     */


    /**
     * It is thread safe!
     * -
     * The class is now thread-safe. Keep in mind that with this approach, we allocate slightly more objects.
     * So if your application is memory-constrained, keep that in mind.
     */
    private static class MetricTwo {
        private static class InternalMetric{
            public long count;
            public long sum;
        }

        private AtomicReference<InternalMetric> internalMetric = new AtomicReference<>(new InternalMetric());

        public void addSample(long sample) {
            InternalMetric currentState;
            InternalMetric newState;
            do {
                currentState = internalMetric.get();
                newState = new InternalMetric();
                newState.sum = currentState.sum + sample;
                newState.count = currentState.count + 1;
            } while (!internalMetric.compareAndSet(currentState, newState));
        }

        public double getAverage() {
            InternalMetric newResetState = new InternalMetric();
            InternalMetric currentState;
            double average;
            do {
                currentState = internalMetric.get();
                average = (double)currentState.sum / currentState.count;
            } while (!internalMetric.compareAndSet(currentState,  newResetState));

            return average;
        }
    }
}
