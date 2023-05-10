# Condition

Applying conditions in locks allows you to achieve control over access control to threads. Using Condition objects is much similar to using wait/notify/notifyAll methods of Object class

* `await`: a thread waits until some condition is met and another thread calls the signal/signalAll methods. In many ways similar to the **wait()** method of the Object class

* `signal`: signals that the thread that called the await() method earlier can continue working. The usage is similar to the **notify()** method of the Object class

* `signalAll`: signals to all threads with an await() call that they can continue working. Similar to the **notifyAll()** method of the Object class

**Compare with Object:**

|         Object         |       Condition       |
|:----------------------:|:---------------------:|
| synchronized(object) { |      lock.lock()      |
|           }            |     lock.unlock()     |
|     object.wait()      |   condition.await()   |
|    object.notify()     |  condition.signal()   |
|   object.notifyAll()   | condition.signalAll() |

---

These methods are called from a block of code that falls under the ReentrantLock. First, using this lock, we need to get a Condition object:

```java
ReentrantLock locker = new ReentrantLock();
Condition condition = locker.newCondition();
```

first the access condition is checked. If the condition is met, the thread waits until the condition changes:

```java
while (condition){
    condition.await();
}
```

and

```java
condition.signalAll();
```