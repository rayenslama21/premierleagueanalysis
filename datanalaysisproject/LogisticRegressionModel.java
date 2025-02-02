package com.mycompany.dataanalysisproject;

import java.util.*;

public class LogisticRegressionModel {
    private double[] weights;
    
    public LogisticRegressionModel(int featureCount) {
        this.weights = new double[featureCount];
        Random random = new Random();
        for (int i = 0; i < featureCount; i++) {
            weights[i] = random.nextDouble();
        }
    }

    public void train(double[][] features, double[] labels, double learningRate, int epochs) {
        int dataSize = labels.length;
        for (int epoch = 0; epoch < epochs; epoch++) {
            double[] gradients = new double[weights.length];
            
            
            for (int i = 0; i < dataSize; i++) {
                double predicted = predict(features[i]);
                double error = predicted - labels[i];
                for (int j = 0; j < weights.length; j++) {
                    gradients[j] += error * features[i][j];
                }
            }
            
            
            for (int j = 0; j < weights.length; j++) {
                weights[j] -= learningRate * gradients[j] / dataSize;
            }
        }
    }

    public double predict(double[] features) {
        double result = 0.0;
        for (int i = 0; i < weights.length; i++) {
            result += weights[i] * features[i];
        }
        return sigmoid(result);
    }

    
    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}