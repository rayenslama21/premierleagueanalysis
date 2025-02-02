package com.mycompany.dataanalysisproject;

import java.util.List;

public abstract class AbstractStatistics {
    public double calculateMean(List<Double> data) {
        return data.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public double calculateStandardDeviation(List<Double> data, double mean) {
        return Math.sqrt(data.stream()
                .mapToDouble(value -> Math.pow(value - mean, 2))
                .average()
                .orElse(0.0));
    }
}
