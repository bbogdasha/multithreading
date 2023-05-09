# ReadWriteLock

ReadWriteLock allows multiple threads to read simultaneously, but only one thread can write at a time.

* ReadLock - if no threads have ReadWriteLock for writing, then multiple threads can get read lock access.

* WriteLock - if no thread is reading or writing, then one thread can access a write lock.
