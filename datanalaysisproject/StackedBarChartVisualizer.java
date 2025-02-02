package com.mycompany.dataanalysisproject;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


class DataProcessors {
    private final String fileName;

    public DataProcessors(String fileName) {
        this.fileName = fileName;
    }

    public CardData calculateCardData() throws IOException {
        int totalHomeYellowCards = 0;
        int totalAwayYellowCards = 0;
        int totalHomeRedCards = 0;
        int totalAwayRedCards = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); // Read the header

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");


                try {
                    totalHomeYellowCards += Integer.parseInt(values[20]);
                    totalAwayYellowCards += Integer.parseInt(values[21]);
                    totalHomeRedCards += Integer.parseInt(values[22]);
                    totalAwayRedCards += Integer.parseInt(values[23]);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {

                }
            }
        }

        return new CardData(totalHomeYellowCards, totalAwayYellowCards, totalHomeRedCards, totalAwayRedCards);
    }
}


class CardData {
    private final int homeYellowCards;
    private final int awayYellowCards;
    private final int homeRedCards;
    private final int awayRedCards;

    public CardData(int homeYellowCards, int awayYellowCards, int homeRedCards, int awayRedCards) {
        this.homeYellowCards = homeYellowCards;
        this.awayYellowCards = awayYellowCards;
        this.homeRedCards = homeRedCards;
        this.awayRedCards = awayRedCards;
    }

    public int getHomeYellowCards() {
        return homeYellowCards;
    }

    public int getAwayYellowCards() {
        return awayYellowCards;
    }

    public int getHomeRedCards() {
        return homeRedCards;
    }

    public int getAwayRedCards() {
        return awayRedCards;
    }
}


class ChartVisualizer {
    public void createChart(CardData cardData) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        
        dataset.addValue(cardData.getHomeYellowCards(), "Yellow Cards", "Home");
        dataset.addValue(cardData.getAwayYellowCards(), "Yellow Cards", "Away");
        dataset.addValue(cardData.getHomeRedCards(), "Red Cards", "Home");
        dataset.addValue(cardData.getAwayRedCards(), "Red Cards", "Away");

        
        JFreeChart stackedBarChart = ChartFactory.createStackedBarChart(
                "Home vs Away Cards",
                "Team Type",
                "Number of Cards",
                dataset
        );

        
        JFrame frame = new JFrame("Stacked Bar Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(stackedBarChart));
        frame.pack();
        frame.setVisible(true);
    }
}


public class StackedBarChartVisualizer {
    public static void visualizeData(String fileName) {
        DataProcessors dataProcessor = new DataProcessors(fileName);
        ChartVisualizer chartVisualizer = new ChartVisualizer();

        try {
            CardData cardData = dataProcessor.calculateCardData();
            chartVisualizer.createChart(cardData);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
    