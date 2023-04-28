# Throughput

The application makes a http request to search for a word in a text document using a different number of threads. 
It also uses JMetter to measure the throughput of the application.

The table shows a pretty good improvement in throughput. As more threads are added, there is no more significant improvement.

| Threads | Latency |
|:-------:|:-------:|
| 1       | 946     |
| 2       | 1580    |
| 4       | 1846    |
| `6`     | `2670`  |
| 8       | 2500    |
| 10      | 2860    |
| 12      | 2840    |
| 14      | 2450    |
| 16      | 2970    |
