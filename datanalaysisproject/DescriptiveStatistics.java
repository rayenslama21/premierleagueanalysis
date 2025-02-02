package com.mycompany.dataanalysisproject;

import java.util.List;

public interface DescriptiveStatistics {
    List<Double> readColumn(String columnName) throws Exception;

    double calculateMedian(List<Double> data);

    double calculateMode(List<Double> data);

    public double calculateMean(List<Double> dataColumn);

    public double calculateStandardDeviation(List<Double> dataColumn, double mean);

    public List<String> readColumnAsStrings(String awayTeam);
}