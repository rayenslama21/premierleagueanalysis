package com.mycompany.dataanalysisproject;

import org.jfree.chart.JFreeChart;

import javax.swing.*;

public abstract class BaseChart implements Chartable {
    protected String filePath;

    public BaseChart(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void displayChart() {
        JFreeChart chart = (JFreeChart) createChart();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new org.jfree.chart.ChartPanel(chart));
        frame.setVisible(true);
    }
}

