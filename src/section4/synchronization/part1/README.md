# Synchronization

Synchronization is a process that organizes access from different threads to a common resource. 

In Java, synchronization applies to entire methods or code fragments. On this basis, there are two ways to synchronize the program code:

* by using the access modifier synchronized;
* by using operator synchronized () {}.

---

Important:

1. If several threads try to call methods of the same object of this class, only one will get access. 
The other threads will not be able to access methods 1 and 2 because synchronization is applied to **each object**.

```java
public class ClassWithSynchMethod {

  public synchronized void method1() {  //Thread2 can't access
    ...(Thread1)
  }
  
  public synchronized void method2() {  //Thread2 can't access
    ...
  }
  
}
```


2. But if Thread1 and Thread2 are operating on two different, independent objects.

```java
ClassWithSynchMethod class1 = new ClassWithSynchMethod();
ClassWithSynchMethod class2 = new ClassWithSynchMethod();

Thread1 -> class1.method1()
Thread2 -> class2.method2()

public class ClassWithSynchMethod {

  public synchronized void method1() { 
    ...(Thread1)
  }
  
  public synchronized void method2() { 
    ...(Thread2)
  }
  
}
```


3. If Thread1 is executing method1, thread2 can execute method2, becouse the synchronized blocks inside those methods 
synchronized on different lock objects.

```java
public class ClassWithSynchBlock {

  Object lock1 = new Object();
  Object lock2 = new Object();

  public void method1() { 
    synchronized(lock1) {
      ...(Thread1)
    }
  }
  
  public void method2() { 
    synchronized(lock2) {
      ...(Thread2)
    }
  }
  
}
```
