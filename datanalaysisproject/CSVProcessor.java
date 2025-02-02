package com.mycompany.dataanalysisproject;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface CSVProcessor {
    void loadData(String filePath) throws IOException;
    void cleanData();
    void mergeData(List<Map<String, String>> otherData);
    void saveData(String outputPath) throws IOException;
}
