package it.gend.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Daniele Asteggiante
 */
public class EarDifferences {
    EarProperties ear1;
    EarProperties ear2;
    List<String> commonJars = new ArrayList<>();
    List<String> differentJars = new ArrayList<>();
    List<CustomFileTmp> commonFiles = new ArrayList<>();
    List<String> differentFiles = new ArrayList<>();
    List<String> commonFilesWithDifference = new ArrayList<>();
    List<String> differencesForFiles = new ArrayList<>();

    public EarDifferences(EarProperties ear1, EarProperties ear2) {
        this.ear1 = ear1;
        this.ear2 = ear2;
        this.checkCommonJars(ear1, ear2);
        this.checkDifferntJars(ear1, ear2, ear1.getName());
        this.checkDifferntJars(ear2, ear1, ear2.getName());
        this.checkCommonFiles(ear1, ear2);
        this.checkDifferentFiles(ear1, ear2, ear1.getName());
        this.checkDifferentFiles(ear2, ear1, ear2.getName());
        this.getFilesWithDifference();
    }

    private void checkDifferntJars(EarProperties ear1, EarProperties ear2, String name) {
        for (String jar : ear1.getJarsNameList()) {
            boolean found = false;
            for (String jar2 : ear2.getJarsNameList()) {
                if (jar.equals(jar2)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                differentJars.add(name + "-" + jar);
        }
    }

    public List<String> getCommonJars() {
        return commonJars;
    }

    public List<String> getDifferentJars() {
        return differentJars;
    }

    public List<String> getDifferentFiles() {
        return differentFiles;
    }

    private void checkCommonJars(EarProperties ear1, EarProperties ear2) {
        for (String jar : ear1.getJarsNameList()) {
            boolean found = false;
            for (String jar2 : ear2.getJarsNameList()) {
                if (jar.equals(jar2)) {
                    found = true;
                    break;
                }
            }
            if (found)
                commonJars.add(jar);
        }
    }

    private void checkDifferentFiles(EarProperties ear1, EarProperties ear2, String ear1Name) {
        for (CustomFileTmp file : ear1.getFiles()) {
            boolean found = false;
            for (CustomFileTmp file2 : ear2.getFiles()) {
                if (file.getName().equals(file2.getName())) {
                    found = true;
                    break;
                }
            }
            if (!found)
                differentFiles.add(createFileInfo(ear1Name, file));
        }
    }

    private String createFileInfo(String ear1Name, CustomFileTmp file) {
        Date date = new Date(file.getLastModified());
        return ear1Name + "-" + file.getName() + "-" + file.getSize() + "-" + date;
    }


    public void checkCommonFiles(EarProperties ear1, EarProperties ear2) {
        for (CustomFileTmp file : ear2.getFiles()) {
            boolean found = false;
            for (CustomFileTmp file2 : ear1.getFiles()) {
                if (file.getName().equals(file2.getName())) {
                    found = true;
                    break;
                }
            }
            if (found)
                commonFiles.add(file);
        }
    }

    public void getFilesWithDifference() {
        for (CustomFileTmp file : commonFiles) {
            CustomFileTmp fileEar1 = findFileInEar(ear1, file);
            CustomFileTmp fileEar2 = findFileInEar(ear2, file);
            checkDifference(fileEar1, fileEar2);
        }
    }

    private CustomFileTmp findFileInEar(EarProperties ear1, CustomFileTmp file) {
        for (CustomFileTmp fileEar : ear1.getFiles()) {
            if (fileEar.getName().equals(file.getName()))
                return fileEar;
        }
        throw new RuntimeException("File common not found in ear");
    }

    public static File writeByteArrayToFile(byte[] byteArray, String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.write(path, byteArray);
            return path.toFile();
        } catch (IOException e) {
            System.err.println("Error while writing file " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void compareFiles(CustomFileTmp customFile1, CustomFileTmp customFile2) {
        StringBuilder sb = new StringBuilder();
        File file1 = writeByteArrayToFile(customFile1.getContent(), "tempFile1");
        File file2 = writeByteArrayToFile(customFile2.getContent(), "tempFile2");
        try (BufferedReader reader1 = new BufferedReader(new FileReader(file1));
             BufferedReader reader2 = new BufferedReader(new FileReader(file2))) {

            String line1 = reader1.readLine();
            String line2 = reader2.readLine();
            int lineNumber = 1;
            sb.append("\n\nNel file ").append(customFile1.getName()).append(" ci sono le seguenti differenze:");
            while (line1 != null || line2 != null) {
                if (line1 == null || !line1.equals(line2)) {
                    sb.append("\nDifference at line ").append(lineNumber)
                            .append("\nFile1: ").append(line1 != null ? line1 : "EOF")
                            .append("\nFile2: ").append(line2 != null ? line2 : "EOF");
                    commonFilesWithDifference.add(customFile1.getName());;
                }
                line1 = reader1.readLine();
                line2 = reader2.readLine();
                lineNumber++;
            }
            differencesForFiles.add(sb.toString());
        } catch (IOException e) {
            System.err.println("Error while reading file " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            file1.delete();
            file2.delete();
        }
    }

    public List<String> getCommonFilesWithDifference() {
        return commonFilesWithDifference;
    }

    private void checkDifference(CustomFileTmp file, CustomFileTmp file2) {
        if (!file.getName().equals(file2.getName()))
            return;
        if (Objects.equals(file.getSize(), file2.getSize())
                && Objects.equals(file.getLastModified(), file2.getLastModified()))
            return;
       //compareFiles(file, file2); TODO da migliorare con SET e most common substring
        commonFilesWithDifference.add(createInfoDifference(file, file2));
    }

    private String createInfoDifference(CustomFileTmp file, CustomFileTmp file2) {
        String date1 = new Date(file.getLastModified()).toString();
        String date2 = new Date(file2.getLastModified()).toString();
        String header = "File " + file.getName() + " ha differenze con il file " + file2.getName() + "\n";
        String descriptionFile1 = file.getName() + "-" + file.getSize() + "-" + date1 + "\n";
        String descriptionFile2 = file2.getName() + "-" + file2.getSize() + "-" + date2;
        return header + descriptionFile1 + descriptionFile2;
    }

    public EarProperties getEar1() {
        return ear1;
    }

    public EarProperties getEar2() {
        return ear2;
    }

    public boolean isEquals() {
        boolean differentFiles = this.differentFiles.isEmpty();
        boolean differentJars = this.differentJars.isEmpty();
        boolean commonFilesWithDifference = this.commonFilesWithDifference.isEmpty();
        return differentFiles && differentJars && commonFilesWithDifference;
    }

    public List<String> getDifferencesForFiles() {
        return differencesForFiles;
    }

}
