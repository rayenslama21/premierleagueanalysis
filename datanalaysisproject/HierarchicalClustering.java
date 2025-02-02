package com.mycompany.dataanalysisproject;

import java.util.*;

public class HierarchicalClustering {

 
    static class DataPoint {
        String id;      
        double[] features;

        DataPoint(String id, double[] features) {
            this.id = id;
            this.features = features;
        }
    }

   
    static class Cluster {
        List<DataPoint> points; 
        String clusterName;     

        Cluster(String name, List<DataPoint> points) {
            this.clusterName = name;
            this.points = new ArrayList<>(points);
        }

        @Override
        public String toString() {
            return "Cluster: " + clusterName + ", Points: " + points.stream().map(p -> p.id).toList();
        }
    }

   
    private double calculateDistance(DataPoint p1, DataPoint p2) {
        double sum = 0.0;
        for (int i = 0; i < p1.features.length; i++) {
            sum += Math.pow(p1.features[i] - p2.features[i], 2);
        }
        return Math.sqrt(sum);
    }

   
    public List<Cluster> performClustering(List<DataPoint> dataPoints, int numClusters) {
       
        List<Cluster> clusters = new ArrayList<>();
        for (int i = 0; i < dataPoints.size(); i++) {
            clusters.add(new Cluster("C" + (i + 1), List.of(dataPoints.get(i))));
        }

        
        while (clusters.size() > numClusters) {
            double minDistance = Double.MAX_VALUE;
            int clusterA = -1, clusterB = -1;

            
            for (int i = 0; i < clusters.size(); i++) {
                for (int j = i + 1; j < clusters.size(); j++) {
                    double distance = calculateClusterDistance(clusters.get(i), clusters.get(j));
                    if (distance < minDistance) {
                        minDistance = distance;
                        clusterA = i;
                        clusterB = j;
                    }
                }
            }

            // Merge the two closest clusters
            Cluster mergedCluster = mergeClusters(clusters.get(clusterA), clusters.get(clusterB));
            clusters.remove(clusterB); 
            clusters.remove(clusterA); 
            clusters.add(mergedCluster); 
        }

        return clusters;
    }

 
    private double calculateClusterDistance(Cluster c1, Cluster c2) {
        double minDistance = Double.MAX_VALUE;
        for (DataPoint p1 : c1.points) {
            for (DataPoint p2 : c2.points) {
                double distance = calculateDistance(p1, p2);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }
        return minDistance;
    }

    private Cluster mergeClusters(Cluster c1, Cluster c2) {
        List<DataPoint> mergedPoints = new ArrayList<>();
        mergedPoints.addAll(c1.points);
        mergedPoints.addAll(c2.points);
        return new Cluster(c1.clusterName + "+" + c2.clusterName, mergedPoints);
    }
}