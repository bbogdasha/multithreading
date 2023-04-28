# Joining threads

Java Thread Join is a method that can be used to pause the execution of the current thread until another thread finishes its execution.

```java         
  t1.start();

  //start the second thread only after the first thread waits for 2 seconds (or when it dies/finishes running)
  try {
      t1.join(2000);
  } catch (InterruptedException e) {
      e.printStackTrace();
  }

  t2.start();

  //start the 3rd thread only after the 1st thread finishes its execution
  try {
      t1.join();
  } catch (InterruptedException e) {
      e.printStackTrace();
  }

  t3.start();

  //allow all threads to finish execution before the program (main thread) finishes its execution
  try {
      t1.join();
      t2.join();
      t3.join();
  } catch (InterruptedException e) {
      e.printStackTrace();
  }

  System.out.println("All threads finished");


@Override
public void run() {
  System.out.println("Thread starts:" + Thread.currentThread().getName());
  try {
      Thread.sleep(4000);
  } catch (InterruptedException e) {
      e.printStackTrace();
  }
  System.out.println("Thread finished:" + Thread.currentThread().getName());
}  

Thread starts:t1
Thread starts:t2
Thread finished:t1
Thread starts:t3
Thread finished:t2
Thread finished:t3
```

