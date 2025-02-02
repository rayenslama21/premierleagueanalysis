package com.mycompany.dataanalysisproject;

import java.util.*;
import java.util.stream.Collectors;

public class FootballCSVProcessor extends AbstractCSVProcessor {

    @Override
    public void cleanData() {
        data.removeIf(row -> row.containsValue("") || row.containsValue(null));
        System.out.println("Data cleaned. Total rows after cleaning: " + data.size());
    }

    @Override
    public void mergeData(List<Map<String, String>> otherData) {
        data.addAll(otherData);
        System.out.println("Data merged. Total rows after merging: " + data.size());
    }

    public double calculateMean(String columnName) {
        List<Double> columnData = extractNumericColumn(columnName);
        return columnData.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public double calculateMedian(String columnName) {
        List<Double> columnData = extractNumericColumn(columnName);
        Collections.sort(columnData);

        int size = columnData.size();
        if (size == 0) return 0.0; 
        if (size % 2 == 0) {
            return (columnData.get(size / 2 - 1) + columnData.get(size / 2)) / 2.0;
        } else {
            return columnData.get(size / 2);
        }
    }

    public double calculateMode(String columnName) {
        List<Double> columnData = extractNumericColumn(columnName);
        Map<Double, Long> frequencyMap = columnData.stream()
                .collect(Collectors.groupingBy(value -> value, Collectors.counting()));

        return frequencyMap.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElseThrow(() -> new IllegalArgumentException("No mode found"))
                .getKey();
    }

    public double calculateStandardDeviation(String columnName) {
        List<Double> columnData = extractNumericColumn(columnName);
        double mean = calculateMean(columnName);
        return Math.sqrt(columnData.stream()
                .mapToDouble(value -> Math.pow(value - mean, 2))
                .average()
                .orElse(0.0));
    }
    
    private List<Double> extractNumericColumn(String columnName) {
        List<Double> columnData = new ArrayList<>();
        for (Map<String, String> row : data) {
            try {
                columnData.add(Double.parseDouble(row.get(columnName)));
            } catch (NumberFormatException | NullPointerException e) {
                            }
        }
        return columnData;
    }
}
