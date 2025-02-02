package com.mycompany.dataanalysisproject;

import java.io.IOException;
import java.util.*;
import java.util.Arrays;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class Dataanalysisproject extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        
        Label lblTitle = new Label("Premier League Data Analysis App");
        lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblTitle.setTextFill(Color.WHITE);
        lblTitle.setStyle("-fx-padding: 20px;");
        
        
        TextField txtColumnName = new TextField();
        txtColumnName.setPromptText("Enter Column Name for Analysis");
        txtColumnName.setStyle(
                "-fx-font-size: 16px; " +
                "-fx-border-radius: 8px; " +
                "-fx-background-radius: 8px; " +
                "-fx-padding: 10px;"
        );
        
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setStyle(
                "-fx-font-family: 'Courier New'; " +
                "-fx-font-size: 14px; " +
                "-fx-control-inner-background: #f4f4f4;" +
                "-fx-border-radius: 8px;" +
                "-fx-border-color: #bdc3c7;" +
                "-fx-padding: 10px;"
        );

        
        Button btnAnalyzeData = createStyledButton("Run Descriptive Analysis", "#3498db");
        Button btnVisualizeCharts = createStyledButton("Generate Visualizations", "#2ecc71");
        Button btnRegressionAnalysis = createStyledButton("Perform Linear Regression", "#e74c3c");
        Button btnLogisticRegression = createStyledButton("Run Logistic Regression", "#f39c12");
        Button btnKNN = createStyledButton("Run KNN", "#8e44ad");
        Button btnHierarchicalClustering = createStyledButton("Hierarchical Clustering", "#34495e");

       
        btnAnalyzeData.setOnAction(event -> {
            String columnName = txtColumnName.getText();
            if (columnName.isEmpty()) {
                showError("Please enter a valid column name.");
            } else {
                try {
                    runDataAnalysis(columnName, outputArea);
                } catch (Exception e) {
                    showError("Error: " + e.getMessage());
                }
            }
        });

        btnVisualizeCharts.setOnAction(event -> {
            try {
                visualizeData(outputArea);
            } catch (Exception e) {
                showError("Error: " + e.getMessage());
            }
        });

        btnRegressionAnalysis.setOnAction(event -> {
            try {
                performRegressionAnalysis(outputArea);
            } catch (Exception e) {
                showError("Error: " + e.getMessage());
            }
        });

        btnLogisticRegression.setOnAction(event -> {
            try {
                runLogisticRegression(outputArea);
            } catch (Exception e) {
                showError("Error: " + e.getMessage());
            }
        });

        btnKNN.setOnAction(event -> {
            try {
                runKNN(outputArea);
            } catch (Exception e) {
                showError("Error: " + e.getMessage());
            }
        });

        btnHierarchicalClustering.setOnAction(event -> {
            try {
                performHierarchicalClustering(outputArea);
            } catch (Exception e) {
                showError("Error: " + e.getMessage());
            }
        });

        // Root layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2c3e50, #34495e);" +
                "-fx-border-radius: 15px;"
        );

        // Add all elements to the root layout
        root.getChildren().addAll(
                lblTitle,
                btnAnalyzeData,
                btnVisualizeCharts,
                btnRegressionAnalysis,
                btnLogisticRegression,
                btnKNN,
                btnHierarchicalClustering,
                txtColumnName,
                outputArea
        );

        
        Scene scene = new Scene(root, 600, 750);
        primaryStage.setTitle("Premier League Data Analysis App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createStyledButton(String label, String backgroundColor) {
        Button button = new Button(label);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        button.setTextFill(Color.WHITE);
        button.setStyle(
                "-fx-background-color: " + backgroundColor + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-padding: 10px 20px;" +
                "-fx-cursor: hand;"
        );

        
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #16a085;" +
                "-fx-background-radius: 8px;" +
                "-fx-padding: 10px 20px;" +
                "-fx-cursor: hand;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: " + backgroundColor + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-padding: 10px 20px;" +
                "-fx-cursor: hand;"
        ));
        return button;
    }
    private void runDataAnalysis(String columnName, TextArea outputArea) throws IOException {
        String file1 = "season1 - 2019-20.csv";
        String file2 = "Season2 - 2018-19.csv";
        String file3 = "Season3 - 2019-20.csv";
        String outputFile = "merged_seasons.csv";

        try {
            FootballCSVProcessor processor1 = new FootballCSVProcessor();
            FootballCSVProcessor processor2 = new FootballCSVProcessor();
            FootballCSVProcessor processor3 = new FootballCSVProcessor();

            processor1.loadData(file1);
            processor1.cleanData();

            processor2.loadData(file2);
            processor2.cleanData();

            processor3.loadData(file3);
            processor3.cleanData();

            processor1.mergeData(processor2.getData());
            processor1.mergeData(processor3.getData());

            processor1.saveData(outputFile);

            DescriptiveStatistics stats = new CSVStatistics(outputFile);
            List<Double> dataColumn = stats.readColumn(columnName);
            double mean = stats.calculateMean(dataColumn);
            double median = stats.calculateMedian(dataColumn);
            double stdDev = stats.calculateStandardDeviation(dataColumn, mean);
            double mode = stats.calculateMode(dataColumn);

            outputArea.appendText("Descriptive Statistics:\n");
            outputArea.appendText(String.format("Mean: %.2f\nMedian: %.2f\nStandard Deviation: %.2f\nMode: %.2f\n\n", mean, median, stdDev, mode));
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    private void visualizeData(TextArea outputArea) {
        String fileName = "Visualiaztion.csv";
        PieChartVisualizer.visualizeData(fileName);
        HistogramVisualizer.visualizeData(fileName);
        StackedBarChartVisualizer.visualizeData(fileName);
        CorrelationHeatmap.visualizeData(fileName);
        
        BarChart barChart = new BarChart(fileName);
        barChart.loadData();
        barChart.displayChart();
        
        PieChart pieChart = new PieChart(fileName);
        pieChart.loadData();
        pieChart.displayChart();
        
        outputArea.appendText("Data visualizations generated successfully.\n\n");
    }

  private void performRegressionAnalysis(TextArea outputArea) {
    
    TextInputDialog xColumnDialog = new TextInputDialog();
    xColumnDialog.setTitle("Input Column Name");
    xColumnDialog.setHeaderText("Enter the X Column Name (Independent Variable)");
    xColumnDialog.setContentText("Column Name:");

    Optional<String> xColumnResult = xColumnDialog.showAndWait();
    if (!xColumnResult.isPresent() || xColumnResult.get().trim().isEmpty()) {
        showError("X Column name cannot be empty.");
        return;
    }
    String xColumn = xColumnResult.get().trim();

    TextInputDialog yColumnDialog = new TextInputDialog();
    yColumnDialog.setTitle("Input Column Name");
    yColumnDialog.setHeaderText("Enter the Y Column Name (Dependent Variable)");
    yColumnDialog.setContentText("Column Name:");

    Optional<String> yColumnResult = yColumnDialog.showAndWait();
    if (!yColumnResult.isPresent() || yColumnResult.get().trim().isEmpty()) {
        showError("Y Column name cannot be empty.");
        return;
    }
    String yColumn = yColumnResult.get().trim();

    String regressionFile = "Visualiaztion.csv";
    RegressionAnalysis analysis = new RegressionAnalysis(regressionFile);
    SimpleRegression regression = analysis.performAnalysis(xColumn, yColumn);

    if (regression != null) {
        String equation = analysis.getRegressionEquation(regression);
        double rSquared = analysis.getRSquared(regression);
        double standardError = analysis.getStandardError(regression);

        outputArea.appendText("Linear Regression Analysis:\n");
        outputArea.appendText(equation + "\n");
        outputArea.appendText(String.format("R-squared: %.2f\n", rSquared));
        outputArea.appendText(String.format("Standard Error: %.2f\n\n", standardError));
    } else {
        outputArea.appendText("Linear regression analysis failed.\n\n");
    }
}

    private void runLogisticRegression(TextArea outputArea) {
    
    TextInputDialog dialog = new TextInputDialog("0,1,1");
    dialog.setTitle("Input Labels");
    dialog.setHeaderText("Enter labels for Logistic Regression");
    dialog.setContentText("Please enter labels as a comma-separated list (e.g., 0,1,1):");
    String input = dialog.showAndWait().orElse("");

    
    double[] labels;
    try {
        labels = Arrays.stream(input.split(","))
                       .mapToDouble(Double::parseDouble)
                       .toArray();
    } catch (NumberFormatException e) {
        outputArea.appendText("Invalid input. Please enter numbers separated by commas.\n\n");
        return;
    }

    double[][] features = {{7.0, 1.0, 8.0}, {4.0, 5.0, 2.0}, {2.0, 9.0, 5.0}};
    if (labels.length != features.length) {
        outputArea.appendText("Enter the Results for the last 3 games");
        return;
    }

    LogisticRegressionModel model = new LogisticRegressionModel(features[0].length);
    model.train(features, labels, 0.01, 1000);

    double[] newMatch = {1.5, 2.5, 3.5};
    double prediction = model.predict(newMatch);
    outputArea.appendText("Logistic Regression Prediction: " + prediction + "\n\n");
}
    
    
    private void runKNN(TextArea outputArea) {

    TextInputDialog dialog = new TextInputDialog("Loss,Win,Win,Loss");
    dialog.setTitle("Input Labels");
    dialog.setHeaderText("Enter labels for KNN");
    dialog.setContentText("Please enter labels for the last 4 games (e.g., Loss,Win,Win,Loss):");
    String input = dialog.showAndWait().orElse("");


    String[] labels = input.split(",");
    if (labels.length != 4) {
        outputArea.appendText("Invalid input. Please enter exactly 4 labels separated by commas.\n\n");
        return;
    }
    
    double[][] features = {
        {2.0, 7.0, 3.0}, 
        {2.0, 3.0, 4.0}, 
        {3.0, 5.0, 5.0}, 
        {2.0, 4.0, 7.0}
    };

    
    KNearestNeighbors knn = new KNearestNeighbors(3);
    knn.fit(features, labels);

 
    double[] newFeatures = {3.5, 4.5, 5.5};
    String prediction = knn.predict(newFeatures);

    outputArea.appendText("KNN Prediction: " + prediction + "\n\n");
}

private void performHierarchicalClustering(TextArea outputArea) {

    TextInputDialog numPointsDialog = new TextInputDialog();
    numPointsDialog.setTitle("Input Number of Data Points");
    numPointsDialog.setHeaderText("Enter the number of games to cluster");
    numPointsDialog.setContentText("Number of games:");

    Optional<String> numPointsResult = numPointsDialog.showAndWait();
    if (!numPointsResult.isPresent() || numPointsResult.get().trim().isEmpty()) {
        showError("Number of data points cannot be empty.");
        return;
    }

    int numPoints;
    try {
        numPoints = Integer.parseInt(numPointsResult.get().trim());
    } catch (NumberFormatException e) {
        showError("Invalid input. Please enter a valid integer.");
        return;
    }
    
    List<HierarchicalClustering.DataPoint> dataPoints = new ArrayList<>();
    for (int i = 0; i < numPoints; i++) {
        TextInputDialog labelDialog = new TextInputDialog();
        labelDialog.setTitle("Enter the number of games to cluster");
        labelDialog.setHeaderText("Enter the Name of the football " + (i + 1) + ":");
        labelDialog.setContentText("Name:");

        Optional<String> labelResult = labelDialog.showAndWait();
        if (!labelResult.isPresent() || labelResult.get().trim().isEmpty()) {
            showError("Result cannot be empty.");
            return;
        }
        String label = labelResult.get().trim();
        TextInputDialog featuresDialog = new TextInputDialog();
        featuresDialog.setTitle("Enter the result for the game");
        featuresDialog.setHeaderText("Enter the result for the game " + (i + 1) + " (comma-separated):");
        featuresDialog.setContentText("Result:");

        Optional<String> featuresResult = featuresDialog.showAndWait();
        if (!featuresResult.isPresent() || featuresResult.get().trim().isEmpty()) {
            showError("Result cannot be empty.");
            return;
        }

       
        double[] features;
        try {
            features = Arrays.stream(featuresResult.get().trim().split(","))
                             .mapToDouble(Double::parseDouble)
                             .toArray();
        } catch (NumberFormatException e) {
            showError("Invalid input. Please enter numbers separated by commas.");
            return;
        }

        dataPoints.add(new HierarchicalClustering.DataPoint(label, features));
    }

    HierarchicalClustering hc = new HierarchicalClustering();
    List<HierarchicalClustering.Cluster> clusters = hc.performClustering(dataPoints, 2);

    outputArea.appendText("Hierarchical Clustering Results:\n");
    clusters.forEach(cluster -> outputArea.appendText(cluster.toString() + "\n"));
    outputArea.appendText("\nHierarchical clustering completed successfully.\n\n");
}
  
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
