# Object conditions

In this app shows how to implement back pressure to guard against OutOfMemory Exception. Apply back-pressure and limit the size of the queue.

---

* `wait()` - Causes the current thread to wait until another thread wakes it up.

Two ways to wake up the waiting thread:

* `notify()` - Wakes up a single thread waiting on that object. 
* `notifyAlI()` - Wakes up all the threads waiting on that object

To call wait(), notify() or notifyAll) we need to acquire the monitor of that object (use synchronized on that object)

**Compare with Object:**

|         Object         |       Condition       |
|:----------------------:|:---------------------:|
| synchronized(object) { |      lock.lock()      |
|           }            |     lock.unlock()     |
|     object.wait()      |   condition.await()   |
|    object.notify()     |  condition.signal()   |
|   object.notifyAll()   | condition.signalAll() |