package section6.block_condition.part2;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringJoiner;

public class MatrixApp {

    private static final int MATRIX_SIZE = 10;
    private static final String INPUT_FILE = "./resources/matrices.txt";
    private static final String OUTPUT_FILE = "./out/matrices_result.txt";

    public static void main(String[] args) throws IOException {
        ThreadSafeQueue threadSafeQueue = new ThreadSafeQueue();
        File inputFile = new File(INPUT_FILE);
        File outputFile = new File(OUTPUT_FILE);

        MatricesReaderProducer matricesReaderProducer = new MatricesReaderProducer(threadSafeQueue, new FileReader(inputFile));
        MatricesMultiplierConsumer matricesMultiplierConsumer = new MatricesMultiplierConsumer(threadSafeQueue, new FileWriter(outputFile));

        matricesMultiplierConsumer.start();
        matricesReaderProducer.start();
    }

    private static class MatricesMultiplierConsumer extends Thread {
        private ThreadSafeQueue queue;
        private FileWriter fileWriter;

        public MatricesMultiplierConsumer(ThreadSafeQueue queue, FileWriter fileWriter) {
            this.queue = queue;
            this.fileWriter = fileWriter;
        }

        @Override
        public void run() {
            while (true) {
                MatricesPair matricesPair = queue.remove();

                if (matricesPair == null) {
                    System.out.println("No more matrices to read from queue.");
                    break;
                }

                float[][] result = multiplyMatrices(matricesPair.matrix1, matricesPair.matrix2);

                try {
                    saveMatrixToFile(fileWriter, result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private float[][] multiplyMatrices(float[][] matrix1, float[][] matrix2) {
            float[][] result = new float[MATRIX_SIZE][MATRIX_SIZE];
            for (int r = 0; r < MATRIX_SIZE; r++) {
                for (int c = 0; c < MATRIX_SIZE; c++) {
                    for (int k = 0; k < MATRIX_SIZE; k++) {
                        result[r][c] += matrix1[r][k] * matrix2[k][c];
                    }
                }
            }
            return result;
        }

        private static void saveMatrixToFile(FileWriter fileWriter, float[][] matrix) throws IOException {
            for (int r = 0; r < MATRIX_SIZE; r++) {
                StringJoiner joiner = new StringJoiner(", ");
                for (int c = 0; c < MATRIX_SIZE; c++) {
                    joiner.add(String.format("%.2f", matrix[r][c]));
                }
                fileWriter.write(joiner.toString());
                fileWriter.write('\n');
            }
            fileWriter.write('\n');
        }
    }

    private static class MatricesReaderProducer extends Thread {
        private Scanner scanner;
        private ThreadSafeQueue queue;

        public MatricesReaderProducer(ThreadSafeQueue queue, FileReader reader) {
            this.queue = queue;
            this.scanner = new Scanner(reader);
        }

        @Override
        public void run() {
            while (true) {
                float[][] matrix1 = readMatrix();
                float[][] matrix2 = readMatrix();

                if (matrix1 == null || matrix2 == null) {
                    queue.terminate();
                    System.out.println("No more matrices to read.");
                    return;
                }

                MatricesPair matricesPair = new MatricesPair();
                matricesPair.matrix1 = matrix1;
                matricesPair.matrix2 = matrix2;

                queue.add(matricesPair);
            }
        }

        private float[][] readMatrix() {
            float[][] matrix = new float[MATRIX_SIZE][MATRIX_SIZE];

            for (int r = 0; r < MATRIX_SIZE; r++) {
                if (!scanner.hasNext()) {
                    return null;
                }
                String[] line = scanner.nextLine().split(",");
                for (int c = 0; c < MATRIX_SIZE; c++) {
                    matrix[r][c] = Float.parseFloat(line[c]);
                }
            }
            scanner.nextLine();
            return  matrix;
        }
    }

    private static class ThreadSafeQueue {
        private Queue<MatricesPair> queue = new LinkedList<>();
        private boolean isEmpty = true;
        private boolean isTerminate = false;

        //=== Guards app from crushing due to an out of memory exception ===
        private static final int CAPACITY = 5;
        //===

        public synchronized void add(MatricesPair matricesPair) {

            //=== Guards app from crushing due to an out of memory exception ===
            while (queue.size() == CAPACITY) {
                try {
                    System.out.println("Full capacity!");
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //===
            System.out.println("+++ Queue size: " + queue.size());
            queue.add(matricesPair);
            isEmpty = false;
            notify();
        }

        public synchronized MatricesPair remove() {
            MatricesPair matricesPair;
            while (isEmpty && !isTerminate) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if (queue.size() == 1) {
                isEmpty = true;
            }

            if (queue.size() == 0 && isTerminate) {
                return null;
            }

            System.out.println("--- Queue size: " + queue.size());
            matricesPair = queue.remove();

            //=== Guards app from crushing due to an out of memory exception ===
            if (queue.size() == CAPACITY - 1) {
                notifyAll();
            }
            //===

            return matricesPair;
        }

        public synchronized void terminate() {
            isTerminate = true;
            notifyAll();
        }
    }

    private static class MatricesPair {
        public float[][] matrix1;
        public float[][] matrix2;
    }
}
