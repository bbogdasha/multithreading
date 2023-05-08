package section5.additional_locking.part2;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock {

    public static final int HIGHEST_PRICE = 1000;

    public static void main(String[] args) throws InterruptedException {
        InventoryDatabase inventoryDatabase = new InventoryDatabase();

        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
        }

        Thread modified = new Thread(() -> {
            while (true) {
                inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
                inventoryDatabase.removeItem(random.nextInt(HIGHEST_PRICE));

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        modified.setDaemon(true);
        modified.start();

        int numberOfReaderThreads = 7;
        List<Thread> readers = new ArrayList<>();

        for (int readerIndex = 0; readerIndex < numberOfReaderThreads; readerIndex++) {
            Thread reader = new Thread(() -> {
                for (int i = 0; i < 100000; i++) {
                    int upperBoundPrice = random.nextInt(HIGHEST_PRICE);
                    int lowerBoundPrice = upperBoundPrice > 0 ? random.nextInt(upperBoundPrice) : 0;

                    inventoryDatabase.getNumberOfItemsInPriceRange(lowerBoundPrice, upperBoundPrice);
                }
            });

            reader.setDaemon(true);
            readers.add(reader);
        }

        long startReadingTime = System.currentTimeMillis();

        for (Thread reader : readers) {
            reader.start();
        }

        for (Thread reader : readers) {
            reader.join();
        }

        long endReadingTime = System.currentTimeMillis();
        long timeResult = endReadingTime - startReadingTime;

        System.out.println("Reading took " + timeResult + " ms");
    }

    public static class InventoryDatabase {
        private TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();

        //Use one lock      (lock)
        private ReentrantLock reentrantLock = new ReentrantLock();

        //Use writeLock and readLock        (write\read)
        private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        private Lock readLock = reentrantReadWriteLock.readLock();
        private Lock writeLock = reentrantReadWriteLock.writeLock();

        public int getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
            //reentrantLock.lock();         //(lock)
            readLock.lock();                //(read)
            try {
                Integer fromKey = priceToCountMap.ceilingKey(lowerBound); //return element with the smallest key (k >= key)
                Integer toKey = priceToCountMap.floorKey(upperBound); //return element with the biggest key (k <= key)

                if (fromKey == null || toKey == null) {
                    return 0;
                }

                NavigableMap<Integer, Integer> rangeOgPrices = priceToCountMap.subMap(fromKey, true, toKey, true);

                int sumItems = 0;
                for (int numberOfItemsForPrice : rangeOgPrices.values()) {
                    sumItems += numberOfItemsForPrice;
                }

                return sumItems;
            } finally {
                //reentrantLock.unlock();   //(lock)
                readLock.unlock();            //(read)
            }
        }

        public void addItem(int priceKey) {
            //reentrantLock.lock();         //(lock)
            writeLock.lock();               //(write)
            try {
                Integer numberOfItemsForPrice = priceToCountMap.get(priceKey);
                if (numberOfItemsForPrice == null) {
                    priceToCountMap.put(priceKey, 1);
                } else {
                    priceToCountMap.put(priceKey, numberOfItemsForPrice + 1);
                }
            } finally {
                //reentrantLock.unlock();   //(lock)
                writeLock.unlock();           //(write)
            }
        }

        public void removeItem(int priceKey) {
            //reentrantLock.lock();         //(lock)
            writeLock.lock();               //(write)
            try {
                Integer numberOfItemsForPrice = priceToCountMap.get(priceKey);
                if (numberOfItemsForPrice == null || numberOfItemsForPrice == 1) {
                    priceToCountMap.put(priceKey, 1);
                } else {
                    priceToCountMap.put(priceKey, numberOfItemsForPrice - 1);
                }
            } finally {
                //reentrantLock.unlock();   //(lock)
                writeLock.unlock();         //(write)
            }
        }
    }
}
