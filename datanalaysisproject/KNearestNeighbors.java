package com.mycompany.dataanalysisproject;
import java.util.*;

public class KNearestNeighbors {
    private double[][] trainingFeatures;
    private String[] trainingLabels;
    private int k;

    public KNearestNeighbors(int k) {
        this.k = k;
    }

    public void fit(double[][] features, String[] labels) {
        this.trainingFeatures = features;
        this.trainingLabels = labels;
    }

    public String predict(double[] features) {
        PriorityQueue<double[]> neighbors = new PriorityQueue<>(
            (a, b) -> Double.compare(b[0], a[0]) 
        );

        for (int i = 0; i < trainingFeatures.length; i++) {
            double distance = euclideanDistance(features, trainingFeatures[i]);
            neighbors.offer(new double[] {distance, i});

            if (neighbors.size() > k) {
                neighbors.poll(); 
            }
        }

        Map<String, Integer> labelCounts = new HashMap<>();
        while (!neighbors.isEmpty()) {
            double[] neighbor = neighbors.poll();
            String label = trainingLabels[(int) neighbor[1]];
            labelCounts.put(label, labelCounts.getOrDefault(label, 0) + 1);
        }

        return labelCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }

    private double euclideanDistance(double[] point1, double[] point2) {
        double sum = 0.0;
        for (int i = 0; i < point1.length; i++) {
            sum += Math.pow(point1[i] - point2[i], 2);
        }
        return Math.sqrt(sum);
    }
}