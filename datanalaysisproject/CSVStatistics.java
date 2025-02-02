/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dataanalysisproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class CSVStatistics extends AbstractStatistics implements DescriptiveStatistics {
    private final String filePath;

    public CSVStatistics(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Double> readColumn(String columnName) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String[] headers = reader.readLine().split(",");
        int columnIndex = Arrays.asList(headers).indexOf(columnName);

        if (columnIndex == -1) {
            throw new IllegalArgumentException("Column not found: " + columnName);
        }

        List<Double> columnData = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            try {
                columnData.add(Double.parseDouble(values[columnIndex]));
            } catch (NumberFormatException e) {
                // Ignore non-numeric values
            }
        }
        reader.close();
        return columnData;
    }

    @Override
    public double calculateMedian(List<Double> data) {
        List<Double> sortedData = data.stream().sorted().collect(Collectors.toList());
        int size = sortedData.size();
        if (size % 2 == 0) {
            return (sortedData.get(size / 2 - 1) + sortedData.get(size / 2)) / 2.0;
        } else {
            return sortedData.get(size / 2);
        }
    }

    @Override
    public double calculateMode(List<Double> data) {
        Map<Double, Long> frequencyMap = data.stream()
                .collect(Collectors.groupingBy(value -> value, Collectors.counting()));

        return frequencyMap.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElseThrow(() -> new IllegalArgumentException("No mode found"))
                .getKey();
    }

    @Override
    public List<String> readColumnAsStrings(String awayTeam) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
