package com.mycompany.dataanalysisproject;

import org.jfree.chart.JFreeChart;

public interface Chartable {
    void loadData();
    JFreeChart createChart();
    void displayChart();
}