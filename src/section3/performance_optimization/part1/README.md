# Performance & Optimization

The application allows you to change the image pixel by pixel in a single stream or many streams. 

If we keep increasing the number of threads, at some point the performance improvement and latency reduction will become 
negligible and even counterproductive.

| Threads | Latency |
|:-------:|:-------:|
| 1       | 867     |
| 2       | 706     |
| 4       | 530     |
| 6       | 528     |
| 8       | 491     |
| 10      | 466     |
| `12`    | `372`   |
| 14      | 387     |
| 16      | 411     |
