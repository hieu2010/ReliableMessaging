package com.example.commons.util;


import org.springframework.lang.NonNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    private static final String RES_FOLDER_PATH = "src/test/resources/";
    private static final String CSV_ID = ".csv";

    private List<String[]> entries = new ArrayList<>();

    public CsvReader(@NonNull String fileName, String sep) {
        StringBuilder data = new StringBuilder();
        String fullPathToFile = RES_FOLDER_PATH+fileName+CSV_ID;
        try (BufferedReader reader = new BufferedReader(new FileReader(fullPathToFile, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    entries.add(line.split(sep));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getRow(int rowIndex) {
        return entries.get(rowIndex);
    }

    public String getEntry(int rowIndex, int colIndex) {
        return entries.get(rowIndex)[colIndex];
    }

    public int getNumberOfDataEntries() {
        return entries.size();
    }
}
