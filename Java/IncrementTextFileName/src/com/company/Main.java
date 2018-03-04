package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        File inputFile = new File("/home/jacob/VmShare/dhcp.xml");
        File outputFile = new File("/home/jacob/VmShare/dhcpProcessed.xml");

        replaceStringInFile(inputFile, outputFile);
    }

    static void replaceStringInFile(File inputFile, File outputFile) throws IOException {
        List<String> result = new LinkedList<>();

        // Load file content.
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        List<String> fileContent = new ArrayList<>();
        String inputLine = reader.readLine();
        while (inputLine != null) {
            fileContent.add(inputLine);
            inputLine = reader.readLine();
        }

        // Process each line.
        for (int i = 0; fileContent.size() > i; i++) {
            String line = fileContent.get(i);
            String lineTrimmed = line.trim();

            String lineResult = line;
            boolean replace = false;
            int currentValue = 0;

            if(lineTrimmed.contains("<opt")) {
                String optValueString = lineTrimmed.substring(4, lineTrimmed.length() - 1);
                currentValue = Integer.parseInt(optValueString);

                replace = true;
            }
            else if(lineTrimmed.contains("</opt")) {
                String optValueString = lineTrimmed.substring(5, lineTrimmed.length() - 1);
                currentValue = Integer.parseInt(optValueString);

                replace = true;
            }

            if(replace) {
                // This way it keeps the line indentation.
                lineResult = line.replace("opt" + currentValue, "opt" + (currentValue + 1));
            }

            result.add(i, lineResult);
        }

        // Saves changes to outputFile
        Files.deleteIfExists(outputFile.toPath());

        try(FileWriter writer = new FileWriter(outputFile)) {

            for(String s : result) {
                writer.write(s);
                writer.write(System.lineSeparator());
            }
            writer.close();
        }
    }
}
