package section1.thread_creation.exercise;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Runnable> runnables = new ArrayList<>();

        Runnable runnable1 = () -> System.out.println("Run1");
        Runnable runnable2 = () -> System.out.println("Run2");
        Runnable runnable3 = () -> System.out.println("Run3");

        runnables.add(runnable1);
        runnables.add(runnable2);
        runnables.add(runnable3);

        MultiExecutor multiExecutor = new MultiExecutor(runnables);
        multiExecutor.executeAll();

    }

    public static class MultiExecutor {
        private final List<Runnable> runnableList;

        public MultiExecutor(List<Runnable> runnableList) {
            this.runnableList = runnableList;
        }

        public void executeAll() {
            for (Runnable runnable : runnableList) {
                new Thread(runnable).start();
            }
        }
    }
}
