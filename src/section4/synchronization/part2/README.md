# Volatile

Volatile - tells the thread that the variable can change and informs the thread to refer to the latest version, 
not the hashed copy, and to propagate changes in time.

Atomicity is the execution of the entire operation, that is, at once, or in case of an error, not at all.

All reference assignments are atomic

```java
Object a = new Object();
Object b = new Object();
a = b; //Atomic
```

---

```java
public int[] getCar() {  //Atomic
  return this.car;
}

public void setName(String name) {  //Atomic
  this.name = name;
}

publoc void setPerson(Person person) {  //Atomic
  this.person = person;
}
```

---

Important:

All assignments to primitive types are safe **except long and double**

```java
byte	8 bit
char	16 bit
short	16 bit
int	32 bit
float	32 bit
double	64 bit
long	64 bit
```

The processor has a machine word size of 32/64 bits, which means that it can process 32/64 bits in a single action.

Type **double and long** have a dimension of 64 bits, which means that in the 32-bit architecture of the processor, operations 
with these values will occur in two steps. First, it will read/write the upper 32 bits, then it will read the lower 32 bits.

For example, one thread has performed a write operation on the upper 32 bits but hasn't finished the second operation and the second 
thread has started reading the variable. The second thread will generate a read error since the data is in an intermediate state, i.e. half-altered.
