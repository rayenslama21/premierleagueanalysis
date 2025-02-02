package com.mycompany.dataanalysisproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart extends BaseChart {

    private int totalHomeGoals = 0;
    private int totalAwayGoals = 0;

    public PieChart(String filePath) {
        super(filePath);
    }

    @Override
    public void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                totalHomeGoals += Integer.parseInt(fields[5]); // FTHG column
                totalAwayGoals += Integer.parseInt(fields[6]); // FTAG column
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JFreeChart createChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Home Team Goals", totalHomeGoals);
        dataset.setValue("Away Team Goals", totalAwayGoals);

        return ChartFactory.createPieChart(
                "Goals Distribution (Home vs Away)",
                dataset,
                true,
                true,
                false
        );
    }
}
