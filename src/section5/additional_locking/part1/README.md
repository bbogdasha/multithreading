# ReentrantLock

Lock - is the basic interface in the Lock API. It provides all the functions of the synchronized keyword, adding new methods for convenient operation.

* lock() method is used to get the lock to work;
* unlock() method is used to release lock;
* tryLock() method to wait for the lock for a certain amount of time;

With synchronized:
```java
@Override
public void run() {
    synchronized (resource) {
        resource.doSomething();
    }
    resource.doLogging();
}
```

With lock:
```java
@Override
public void run() {
    try {
        // try to take the lock for 10 seconds
        if(lock.tryLock(10, TimeUnit.SECONDS)){
        resource.doSomething();
        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    }finally{
        //kill lock
        lock.unlock();
    }
    resource.doLogging();
}
```

---

1. The Lock API provides more options for locking, unlike the synchronized API where a thread can wait indefinitely for a lock. In the Lock API we can use the tryLock() method to wait for a lock only for a certain amount of time.

2. With the Lock API we have to write a try-finally block to make sure that the lock will be released.

3. Timing blocks can only cover one method, while the Lock API allows you to get a lock in one method and unlock it in another.
