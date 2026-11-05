# Monte Carlo Simulation

Estimates the percolation threshold of an n x n grid (2025). Sites are opened
at random until the top row is connected to the bottom row, and the share of
open sites is averaged over many trials. For large grids the result is close to
the known value of about 0.5927.

Connectivity is tracked with weighted quick-union. In the code the sites are
called onions.

## Running it

From this folder:

```
javac -d out src/java/monteCarloSimulation/*.java
java -cp out src.java.monteCarloSimulation.App 200 100
```

The first argument is n (grid size), the second the number of trials.
