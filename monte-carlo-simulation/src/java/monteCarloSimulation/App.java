package src.java.monteCarloSimulation;

import java.text.NumberFormat;
import java.text.ParseException;

public class App {
    public static void main(String[] args) {
        // Check if there are enough command-line arguments
        if (args.length < 2) {
            System.out.println("Please provide both n (steps) and trials (iterations) as arguments.");
            return;
        }

        MonteCarloSimulation monteCarloSimulation = new MonteCarloSimulation();
        int n;
        int trials;
        // Parse the command-line arguments to integers
        try {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("n and trials have to be whole numbers up to " + Integer.MAX_VALUE);
        }

        // Call the function with n and trials
        monteCarloSimulation.start(n, trials);

    }

}
