package com.mycompany.dataanalysisproject;

import java.io.BufferedReader;
import java.io.FileReader;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class DataProcessor {
    private final String fileName;

    public DataProcessor(String fileName) {
        this.fileName = fileName;
    }

    public List<double[]> getRegressionData(String xColumnName, String yColumnName) throws IOException {
        List<double[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new IOException("Empty file");
            }

            String[] headers = headerLine.split(",");
            int xIndex = findColumnIndex(headers, xColumnName);
            int yIndex = findColumnIndex(headers, yColumnName);

            if (xIndex == -1 || yIndex == -1) {
                throw new IllegalArgumentException("Column not found: " + xColumnName + " or " + yColumnName);
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                try {
                    double x = Double.parseDouble(values[xIndex]);
                    double y = Double.parseDouble(values[yIndex]);
                    data.add(new double[]{x, y});
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                    // Skip invalid or missing values
                }
            }
        }

        return data;
    }

    private int findColumnIndex(String[] headers, String columnName) {
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        return -1;
    }
}

class RegressionCalculator {
    public SimpleRegression calculateRegression(List<double[]> data) {
        SimpleRegression regression = new SimpleRegression();

        for (double[] point : data) {
            regression.addData(point[0], point[1]);
        }

        return regression;
    }
}

// Class to encapsulate regression analysis logic
class RegressionAnalysis {
    private final DataProcessor dataProcessor;
    private final RegressionCalculator regressionCalculator;

    public RegressionAnalysis(String fileName) {
        this.dataProcessor = new DataProcessor(fileName);
        this.regressionCalculator = new RegressionCalculator();
    }

    /**
     * Performs linear regression analysis on the specified columns.
     *
     * @param xColumn The name of the independent variable column.
     * @param yColumn The name of the dependent variable column.
     * @return A SimpleRegression object containing the results of the analysis.
     */
    public SimpleRegression performAnalysis(String xColumn, String yColumn) {
        try {
            List<double[]> regressionData = dataProcessor.getRegressionData(xColumn, yColumn);
            return regressionCalculator.calculateRegression(regressionData);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Returns the regression equation as a string.
     *
     * @param regression The SimpleRegression object containing the regression results.
     * @return A string representation of the regression equation.
     */
    public String getRegressionEquation(SimpleRegression regression) {
        if (regression == null) {
            return "Regression analysis failed.";
        }
        return String.format("Regression Equation: Y = %.2f * X + %.2f", regression.getSlope(), regression.getIntercept());
    }

    /**
     * Returns the R-squared value of the regression.
     *
     * @param regression The SimpleRegression object containing the regression results.
     * @return The R-squared value, or NaN if the regression failed.
     */
    public double getRSquared(SimpleRegression regression) {
        if (regression == null) {
            return Double.NaN;
        }
        return regression.getRSquare();
    }

    /**
     * Returns the standard error of the regression.
     *
     * @param regression The SimpleRegression object containing the regression results.
     * @return The standard error, or NaN if the regression failed.
     */
    public double getStandardError(SimpleRegression regression) {
        if (regression == null) {
            return Double.NaN;
        }
        return regression.getMeanSquareError();
    }
}