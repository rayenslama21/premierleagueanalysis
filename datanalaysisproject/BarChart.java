package com.mycompany.dataanalysisproject;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BarChart extends BaseChart {

    private int totalHomeGoals = 0;
    private int totalAwayGoals = 0;

    public BarChart(String filePath) {
        super(filePath);
    }

    @Override
    public void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); 

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                totalHomeGoals += Integer.parseInt(fields[5]); 
                totalAwayGoals += Integer.parseInt(fields[6]);
            }
        } catch (IOException | NumberFormatException e) {
        }
    }

    @Override
    public JFreeChart createChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(totalHomeGoals, "Goals", "Home Team");
        dataset.addValue(totalAwayGoals, "Goals", "Away Team");

        return ChartFactory.createBarChart(
                "Total Goals Scored",
                "Team",
                "Goals",
                dataset
        );
    }
}
