# ReadWriteLock

ReadWriteLock allows multiple threads to read simultaneously, but only one thread can write at a time.

* ReadLock - if no threads have ReadWriteLock for writing, then multiple threads can get read lock access.

* WriteLock - if no thread is reading or writing, then one thread can access a write lock.

Example:
```java
ReadWriteLock lock = new ReentrantReadWriteLock();

Lock readLock = lock.readLock();
Lock writeLock = lock.writeLock();
```

---

Compare with single lock:

10 threads have access to this class object.

Only 1 thread can execute update() and read() methods
```java
public class SomeClass {
    ReentrantLock lock = new ReentrantLock();
    
    public void update(...) {
        lock.lock();
        try {
            someWriteMethod(...);
        } finally {
            lock.unlock();
        }
    }

    public void read(...) {
        lock.lock();
        try {
            someReadMethod(...);
        } finally {
            lock.unlock();
        }
    }
}
```

---

Only 1 thread can execute update()

**All threads can execute read()**
```java
public class SomeClass {
    ReadWriteLock lock = new ReentrantReadWriteLock();

    Lock readLock = lock.readLock();
    Lock writeLock = lock.writeLock();
    
    public void update(...) {
        writeLock.lock();
        try {
            someWriteMethod(...);
        } finally {
            writeLock.unlock();
        }
    }

    public void read(...) {
        readLock.lock();
        try {
            someReadMethod(...);
        } finally {
            readLock.unlock();
        }
    }
}
```