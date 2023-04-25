In first part this section were used two ways to create new Thread:

---

Created a new thread by simply extending my class from Thread and overriding its run() method.

```java
private static class NewThread extends Thread {
    @Override
    public void run() {
        System.out.println("Hello from " + this.getName());
    }
}
```

The run() method contains code that will be executed inside the new thread. After creating the thread, started it by calling the start() method.

---

And was used the Runnable interface with its single run() method, which had to be overridden. 

```java
Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("Long way Runnable " + Thread.currentThread().getName());
    }
});
```
