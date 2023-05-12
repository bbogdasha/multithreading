# Atomic References

`AtomicReference` provides operations on the underlying object reference that can be read and written atomically, and also contains extended atomic operations. AtomicReference supports atomic operations on the underlying object variable. It has get and set methods that work as reads and writes on variable variables.

Operations: 

* `public boolean compareAndSet( expectation, update V)`

Atomically sets the value for a given updated value if the current value == expected value.

* `public boolean get()`

Returns the current value.

* `public boolean getAndSet(V newValue)`

Atomically sets to the given value and returns the previous value.