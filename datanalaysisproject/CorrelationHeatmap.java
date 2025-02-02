package com.mycompany.dataanalysisproject;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CorrelationHeatmap {

    public static void visualizeData(String fileName) {
        
        String[] columns = {"FTHG", "FTAG", "HS", "AS", "HY", "AY", "HR", "AR"};
        Map<String, List<Double>> data = new HashMap<>();

        
        for (String column : columns) {
            data.put(column, new ArrayList<>());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            String[] headers = line.split(",");

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int i = 0; i < columns.length; i++) {
                    try {
                        int colIndex = findColumnIndex(headers, columns[i]);
                        if (colIndex != -1) {
                            data.get(columns[i]).add(Double.parseDouble(values[colIndex]));
                        }
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                        
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        double[][] correlationMatrix = new double[columns.length][columns.length];
        for (int i = 0; i < columns.length; i++) {
            for (int j = 0; j < columns.length; j++) {
                correlationMatrix[i][j] = calculateCorrelation(data.get(columns[i]), data.get(columns[j]));
            }
        }

     
        JFrame frame = new JFrame("Correlation Heatmap");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        JPanel panel = new JPanel(new GridLayout(columns.length + 1, columns.length + 1));

        
        panel.add(new JLabel(""));
        for (String column : columns) {
            panel.add(new JLabel(column, SwingConstants.CENTER));
        }

        for (int i = 0; i < columns.length; i++) {
            panel.add(new JLabel(columns[i], SwingConstants.CENTER)); 
            for (int j = 0; j < columns.length; j++) {
                double value = correlationMatrix[i][j];
                JLabel label = new JLabel(String.format("%.2f", value), SwingConstants.CENTER);
                label.setOpaque(true);
                label.setBackground(getColor(value));
                panel.add(label);
            }
        }

        frame.add(panel);
        frame.setVisible(true);
    }

    private static int findColumnIndex(String[] headers, String columnName) {
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        return -1;
    }

    private static double calculateCorrelation(List<Double> x, List<Double> y) {
        if (x.size() != y.size() || x.isEmpty()) {
            return 0.0;
        }

        int n = x.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0, sumY2 = 0;

        for (int i = 0; i < n; i++) {
            double xi = x.get(i);
            double yi = y.get(i);

            sumX += xi;
            sumY += yi;
            sumXY += xi * yi;
            sumX2 += xi * xi;
            sumY2 += yi * yi;
        }

        double numerator = n * sumXY - sumX * sumY;
        double denominator = Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY));

        return (denominator == 0) ? 0 : numerator / denominator;
    }

    private static Color getColor(double value) {
        int yellow = (int) ((value + 1) / 2 * 255);
        int red = 255 - yellow;
        return new Color(yellow, 0, red);
    }
}

