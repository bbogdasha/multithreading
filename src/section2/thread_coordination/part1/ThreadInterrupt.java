package section2.thread_coordination.part1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThreadInterrupt {

    public static void main(String[] args) throws IOException {
        Thread thread = new InterruptedClass();
        thread.start();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        reader.readLine();

        thread.interrupt();
    }

    //The sleep() method already has a built-in check for the interrupt var,
    //so the manual check is skipped. If sleep() reads the existence of an
    //interrupt, it generates an exception.
    static class InterruptedClass extends Thread {

        @Override
        public void run() {
            while (true) {
                System.out.print("a");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Exception");
                    break;
                }
            }
        }
    }
}
