package it.gend.controller;

import it.gend.domain.EarDifferences;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * @author Daniele Asteggiante
 */
public class ReportGeneratorController {
    private EarDifferences earDifferences;
    private String path;
    private FileWriter fileWriter;

    public ReportGeneratorController(EarDifferences earDifferences, String path) {
        this.earDifferences = earDifferences;
        this.path = path;
        try {
            this.fileWriter = new FileWriter(path);
        } catch (IOException e) {
            System.err.println("Error during File Writer Creation " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void printHeader() throws IOException {
        Date date = new Date();
        fileWriter.write("Report printed on: " + date + "\n");
        fileWriter.write("Results of comparison between " + earDifferences.getEar1().getName() + " and " + earDifferences.getEar2().getName() + "\n");
        fileWriter.write("-------------------------------------------------------------\n");
    }

    private void printDifferentJars() throws IOException {
        fileWriter.write("Different Jars:\n");
        for (String jar : earDifferences.getDifferentJars()) {
            fileWriter.write(jar + "\n");
        }
        fileWriter.write("-------------------------------------------------------------\n");
    }

    private void printDifferentFiles() throws IOException {
        fileWriter.write("Different Files:\n");
        for (String file : earDifferences.getDifferentFiles()) {
            fileWriter.write(file + "\n");
        }
        fileWriter.write("-------------------------------------------------------------\n");
    }

    private void printCommonFilesWithDifference() throws IOException {
        fileWriter.write("Common Files with differences:\n");
        for (String file : earDifferences.getCommonFilesWithDifference()) {
            fileWriter.write(file + "\n");
        }
        fileWriter.write("-------------------------------------------------------------\n");
    }

    private void printDifferenceForFiles() throws IOException {
        fileWriter.write("Differences for files:\n");
        for (String file : earDifferences.getDifferencesForFiles()) {
            fileWriter.write(file + "\n");
        }
        fileWriter.write("-------------------------------------------------------------\n");
    }

    public void printReport() {
        try {
            printHeader();
            printDifferentJars();
            printDifferentFiles();
            printCommonFilesWithDifference();
            printDifferenceForFiles();
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("Error during report generation " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
