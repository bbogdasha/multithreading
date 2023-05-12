# Atomic Integer

The **java.concurrent.atomic** package contains many useful classes for performing atomic operations. An operation is called atomic when it can be safely executed in parallel computation across multiple threads without using locks or **synchronized** as it was before.

---

```java
AtomicInteger atomicInt = new AtomicInteger(0);

ExecutorService executor = Executors.newFixedThreadPool(2);

IntStream.range(0, 1000)
    .forEach(i -> executor.submit(atomicInt::incrementAndGet));

stop(executor);

System.out.println(atomicInt.get());    // => 1000
```

AtomicInteger instead of the usual Integer allowed to increment the number correctly, distributing the work over two threads at once. We don't have to worry about security, because incrementAndGet() is an atomic operation.

---

Operations:

|            Operations            | Equivalent                      |
|:--------------------------------:|:--------------------------------|
|       1. y = atomic.get();       | equivalent to y = i;            |
| 2. y = atomic.incrementAndGet(); | equivalent to y = ++i;          |
| 3. y = atomic.getAndIncrement(); | equivalent to y = i++;          |
| 4. y = atomic.decrementAndGet(); | equivalent to y = --i;          |
| 5. y = atomic.getAndDecrement(); | equivalent to y = i--;          |
|   6. y = atomic.addAndGet(x);    | equivalent to i = i + x; y = i; |
|   7. y = atomic.getAndAdd(x);    | equivalent to y = i; i = i + x; |
|        8. atomic.set(x);         | equivalent to i = x;            |
|   9. y = atomic.getAndSet(x);    | equivalent to y = i; i = i + x; |

---

AtomicInteger works better than synchronized int.

---

Similar to `AtomicInteger`, the java.util.concurrent.atomic package also provides an `AtomicBoolean` as well as an `AtomicLong` class representing the boolean and long values that can be updated atomically, respectively.