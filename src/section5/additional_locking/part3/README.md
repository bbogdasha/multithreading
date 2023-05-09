# Semaphore

To control access to the resource the semaphore uses a counter representing the number of permissions. If the value of the counter is greater than zero the thread accesses the resource and the counter is decremented by one. When the thread has finished working with the resource the semaphore is released and the counter is incremented by one. If the counter is zero the thread locks and waits until it gets permission from the semaphore.

* To get permission from the semaphore, call the `acquire()` method

* When you have finished working with the resource, you must release it using the `release()` method

![Alt Text](https://github.com/bbogdasha/multithreading/blob/master/resources/screenshots/semaphore.gif)

---

The `SimpleSemaphore` example shows: 

A binary semaphore where it can be 1 or 0. This means that it protects access to the shared resource and provides a mutual disconnect, which allows only one because access to a critical resource at a time (As a regular block synchronized).

And a counting semaphore can have more than 1 permission.

---

In `SemaphoreProducerConsumer` is a redesigned version of the program implementing the supplier and consumer functions . This version uses two semaphores that regulate the execution flows of the supplier and the consumer and ensure that each call to the put() method is followed by a corresponding call to the get() method.
