package com.mycompany.dataanalysisproject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PieChartVisualizer {

    public static void visualizeData(String fileName) {
        DefaultPieDataset dataset = new DefaultPieDataset();


        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); 
          
            int homeWin = 0, draw = 0, awayWin = 0;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");


                String result = values[7];

                switch (result) {
                    case "H":
                        homeWin++;
                        break;
                    case "D":
                        draw++;
                        break;
                    case "A":
                        awayWin++;
                        break;
                }
            }

            dataset.setValue("Home Wins", homeWin);
            dataset.setValue("Draws", draw);
            dataset.setValue("Away Wins", awayWin);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        JFreeChart pieChart = ChartFactory.createPieChart(
                "Match Outcomes",
                dataset,
                true, 
                true, 
                false 
        );


        JFrame frame = new JFrame("Pie Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(pieChart));
        frame.pack();
        frame.setVisible(true);
    }
}
