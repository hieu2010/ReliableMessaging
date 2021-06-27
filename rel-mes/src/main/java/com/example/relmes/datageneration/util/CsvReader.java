package com.example.relmes.datageneration.util;

import org.springframework.lang.NonNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    private static final String RES_FOLDER_PATH = "src/main/resources/";
    private static final String CSV_ID = ".csv";

    private static final short TEMP_COL = 2;
    private static final short HUM_COL = 4;

    private List<String[]> entries = new ArrayList<>();

    public CsvReader(@NonNull String fileName, String sep) {
        StringBuilder data = new StringBuilder();
        String fullPathToFile = RES_FOLDER_PATH + fileName + CSV_ID;
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

    public String getTemp(int rowIndex) {
        return entries.get(rowIndex)[TEMP_COL];
    }

    public String getHum(int rowIndex) {
        return entries.get(rowIndex)[HUM_COL];
    }

    public int getNumberOfDataEntries() {
        return entries.size();
    }
}
