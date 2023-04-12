package section2.thread_coordination.part2;

public class ThreadJoin2 {

    public static void main(String[] args) {

        int[] AI = {2, 5, 4, 9, -2, 7, 0, -11};

        Thread1 t1 = new Thread1(AI);

        //Waiting for completion of thread t1 - is necessary, otherwise it is possible to get zero
        try {
            t1.getThread().join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Max = " + t1.getMaximum());
    }

    public static class Thread1 implements Runnable {
        private Thread thread;
        private int[] AI;
        private int maximum;

        public Thread1(int[] AI) {
            this.AI = AI;
            thread = new Thread(this, "Thread1.");
            thread.start();
        }

        @Override
        public void run() {
            int max = AI[0];
            for (int i = 1; i < AI.length; i++) {
                if (max < AI[i]) {
                    max = AI[i];
                }
            }
            maximum = max;
        }

        public Thread getThread() {
            return thread;
        }

        public int getMaximum() {
            return maximum;
        }
    }
}
