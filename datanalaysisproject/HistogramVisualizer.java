package com.mycompany.dataanalysisproject;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.HistogramDataset;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HistogramVisualizer {

    public static void visualizeData(String fileName) {
        HistogramDataset dataset = new HistogramDataset();

        // Parse the CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            double[] homeGoals = new double[0];
            int count = 0;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (homeGoals.length == count) {
                    double[] newArray = new double[homeGoals.length + 5];
                    System.arraycopy(homeGoals, 0, newArray, 0, homeGoals.length);
                    homeGoals = newArray;
                }

                try {
                    homeGoals[count] = Double.parseDouble(values[6]);
                    count++;
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
          
                }
            }

            dataset.addSeries("Home Team Goals", homeGoals, 10);

        } catch (IOException e) {
            return;
        }

        JFreeChart histogram = ChartFactory.createHistogram(
                "Distribution of Home Team Goals",
                "Goals",
                "Frequency",
                dataset
        );


        JFrame frame = new JFrame("Histogram Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(histogram));
        frame.pack();
        frame.setVisible(true);
    }
}
