package src.java.monteCarloSimulation;

import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.Random;


public class MonteCarloSimulation {

    private int[] pointer;
    private boolean[] open;
    private Random random = new Random();
    private int[] weight;
    private int n;


    /**
     * initialize values based on n before each simulation to reset values
     * @param n as int
     */

    private void initialize(int n) {

        // the total sum of fields is (n*n) + 2 (two virtual onions)
        int totalFieldSize = (n * n) + 2;

        // all required list are equals in size
        this.pointer = new int[totalFieldSize];
        this.open = new boolean[totalFieldSize];
        this.weight = new int[totalFieldSize];

        this.open[0] = true;
        this.open[totalFieldSize - 1] = true;


        // point each onion to itself
        for (int i = 0; i < pointer.length; i++) {
            pointer[i] = i;
        }

        // connect the first and last row with both virtual onions
        for (int i = 1; i <= n; i++) {
            connect(0, i);
            connect(totalFieldSize - 1, (totalFieldSize - 1) - i);
        }
    }

    public void start(int n, int trials) {

        this.n = n;

        // n under 3 doesn't make sense, trials have to be at least 1
        if (n < 3 || trials < 1) {
            throw new IllegalArgumentException("n has to be at least 3 and trials at least 1");
        }

        // store the result of each simulation
        int[] results = new int[trials];

        for (int i = 0; i < trials; i++) {
            initialize(n);

            // open a new random onion until both virtual onions are connected
            while (!connected(0,  pointer.length - 1)) {
                openRandomOnion();
            }
            results[i] = getAmountOfOpenOnions();
        }

        // get the average of all results
        OptionalDouble average = Arrays.stream(results)
                .average();

        System.out.println(average.getAsDouble()/ (n * n));

    }

    /**
     * if both onions have the same root they are connected
     */

    private boolean connected(int onionOne, int onionTwo) {
        return findRoot(onionOne) == findRoot(onionTwo);
    }

    /**
     * get the root of the given onion
     * the root is the onion that points to itself
     * @param indexOfOnion index of the given onion as int
     * @return index of the root onion
     */

    private int findRoot(int indexOfOnion) {
        int pointer = this.pointer[indexOfOnion];

        while (pointer != indexOfOnion) {
            indexOfOnion = pointer;
            pointer = this.pointer[pointer];
        }
        return pointer;
    }

    /**
     * generates a new random onion and connect it to open neighbours
     * it's not possible to run out of onions, because we percolate before
     */

    private void openRandomOnion() {
        int newOpenOnionIndex = getRandomOnion();

        while (open[newOpenOnionIndex]) {
            newOpenOnionIndex = getRandomOnion();
        }
        open[newOpenOnionIndex] = true;

        connectToNeighbours(newOpenOnionIndex);
    }

    private int getRandomOnion() {
        return random.nextInt(n * n) + 1;
    }

    /**
     * if we open a new onion we have to connect it to all open onions next to it
     * there are only 4 possible directions
     * @param onionIndex index of the newly opened onion as int
     */

    private void connectToNeighbours(int onionIndex) {

        int onionIndexModuloRows = onionIndex % n;

        // a left onion does exist
        if (onionIndexModuloRows != 1 && this.open[onionIndex - 1]) {
            connect(onionIndex, onionIndex - 1);
        }
        // right onion does exist
        if (onionIndexModuloRows != 0 && this.open[onionIndex + 1]) {
            connect(onionIndex, onionIndex + 1);
        }
        // top onion does exist
        if (onionIndex - n > 0 && this.open[onionIndex - n]) {
            connect(onionIndex, onionIndex - n);
        }
        // bot onion does exists
        if (onionIndex + n < pointer.length - 1 && this.open[onionIndex + n]) {
            connect(onionIndex, onionIndex + n);
        }

    }

    /**
     * connect to onions by using the weighted onion algorithm
     * we first search for the root of both onions and connect the smaller to the bigger one
     * @param onion1 index to onion one as int
     * @param onion2 index to onion two as int
     */

    private void connect(int onion1, int onion2) {

        int root1 = findRoot(onion1);
        int root2 = findRoot(onion2);

        if (root1 == root2) {
            return;
        }

        int weight = calculateWeight(root1, root2);

        if (this.weight[root1] < this.weight[root2]) {
            pointer[root1] = root2;
            this.weight[root2] = weight;
        } else {
            pointer[root2] = root1;
            this.weight[root1] = weight;
        }

    }

    /**
     * as we only look at the root onion add the weight of both onions together
     * we start with 0 so, we have to add by one if it's a single onion
     * @param onion1 index of root onion one as int
     * @param onion2 index of root onion two as int
     * @return sum of both weights
     */

    private int calculateWeight(int onion1, int onion2) {
        int weightOne = Math.max(this.weight[onion1], 1);
        int weightTwo = Math.max(this.weight[onion2], 1);

        return weightOne + weightTwo;
    }

    /**
     * get the amount of open onions to calculate the threshold
     * @return amount of all open onions
     */

    public int getAmountOfOpenOnions() {
        int amountOfOpenOnions = 0;

        for (int i = 1; i < open.length - 1; i++) {
            if (open[i]) {
                amountOfOpenOnions++;
            }
        }
        return amountOfOpenOnions;
    }
}
