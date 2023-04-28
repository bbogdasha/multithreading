# Daemon threads & Sleep operation

Daemon threads are used for background support tasks and are only needed while normal threads are running.
If normal threads are not running and the other threads are daemon threads, **the interpreter terminates**.

```java
thread.setDaemon(true); //user thread -> daemon thread
```

---

The sleep() method already has a built-in check for the interrupt var, so the manual check is skipped. If sleep() reads the existence of an 
interrupt, it generates an exception.

```java
thread.interrupt();

...

@Override
  public void run() {
      while (true) {
          System.out.print("a");
          try {
              sleep(1000);  //has chech isInterrupt
          } catch (InterruptedException e) {
              System.out.println("Exception");
              break;
          }
      }
  }
```

If sleep() is not used, interrupt is manually checked with the isInterrupted() method

```java
@Override
  public void run() {
      while (!this.isInterrupted()) {
          System.out.println("a");
      }
  }
```

