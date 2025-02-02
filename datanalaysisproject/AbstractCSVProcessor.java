/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dataanalysisproject;
import java.io.*;
import java.util.*;

public abstract class AbstractCSVProcessor implements CSVProcessor {
    protected List<Map<String, String>> data = new ArrayList<>();

    @Override
    public void loadData(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerLine = reader.readLine();
            if (headerLine == null) throw new IOException("File is empty: " + filePath);

            String[] headers = headerLine.split(",");
            String line;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == headers.length) {
                    Map<String, String> row = new HashMap<>();
                    for (int i = 0; i < headers.length; i++) {
                        row.put(headers[i], values[i]);
                    }
                    data.add(row);
                }
            }
        }
        System.out.println("Data loaded from: " + filePath);
    }

    @Override
    public void saveData(String outputPath) throws IOException {
        if (data.isEmpty()) {
            System.out.println("No data to save.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            
            Set<String> headers = data.get(0).keySet();
            writer.write(String.join(",", headers));
            writer.newLine();
            
            for (Map<String, String> row : data) {
                List<String> values = new ArrayList<>();
                for (String header : headers) {
                    values.add(row.get(header));
                }
                writer.write(String.join(",", values));
                writer.newLine();
            }
        }
        System.out.println("Data written to: " + outputPath);
    }

    public List<Map<String, String>> getData() {
        return data;
    }
   
    public abstract void cleanData();
}
