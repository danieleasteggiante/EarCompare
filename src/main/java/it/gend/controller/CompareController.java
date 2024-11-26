package it.gend.controller;

import it.gend.domain.CustomFileTmp;
import it.gend.domain.EarDifferences;
import it.gend.domain.EarProperties;
import it.gend.domain.InputUser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Daniele Asteggiante
 */
public class CompareController {
    public static EarDifferences compareEar(InputUser inputUser) throws IOException {
        System.out.println("Comparing " + inputUser.getPathEar1() + " with " + inputUser.getPathEar2());
        System.out.println("Creating file from path1");
        File fileEar1 = new File(inputUser.getPathEar1());
        System.out.println("Creating file from path2");
        File fileEar2 = new File(inputUser.getPathEar2());
        EarProperties earProperties1 = rapidChecks(fileEar1);
        EarProperties earProperties2 = rapidChecks(fileEar2);
        if (earProperties2.equals(earProperties1))
            return generateEarDifferences(earProperties1, earProperties2);
        deepCheck(createRootFile(fileEar1), earProperties1);
        deepCheck(createRootFile(fileEar2), earProperties2);
        return generateEarDifferences(earProperties1, earProperties2);
    }

    private static CustomFileTmp createRootFile(File fileEar1) {
        try {
            return new CustomFileTmp(fileEar1.getPath(), fileEar1.getName(), Files.readAllBytes(fileEar1.toPath()), fileEar1.length(), fileEar1.lastModified());
        } catch (IOException e) {
            System.err.println("Error while creating root file " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static EarDifferences generateEarDifferences(EarProperties earProperties1, EarProperties earProperties2) {
        return new EarDifferences(earProperties1, earProperties2);
    }


    private static void deepCheck(CustomFileTmp fileEar, EarProperties earProperties) {
        JarFile jarFile = createJarFile(fileEar);
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            System.out.println(entry.getName());
            if (isJavaArchive(entry)) {
                CustomFileTmp extractedJar = getFileFromEntry(jarFile, entry);
                deepCheck(extractedJar, earProperties);
                earProperties.getJarsNameList().add(entry.getName());
                continue;
            }
            if (entry.isDirectory()) continue;
            earProperties.getFiles().add(getFileFromEntry(jarFile, entry));
        }

    }

    private static JarFile createJarFile(CustomFileTmp fileEar) {
        try {
            String prefix = "tempJar";
            String suffix = fileEar.getName().substring(fileEar.getName().lastIndexOf("."));
            File tmpFile = File.createTempFile(prefix, suffix);
            Files.write(tmpFile.toPath(), fileEar.getContent());
            if (tmpFile.length() == 0)
                throw new IOException("The JAR file is empty.");

            return new JarFile(tmpFile);
        } catch (IOException e) {
            System.err.println("Error while creating jar file " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static CustomFileTmp getFileFromEntry(JarFile jarFile, JarEntry entry) {
        try {
            Path tempJar = Files.createTempFile("tempJar", ".jar");
            Files.copy(jarFile.getInputStream(entry), tempJar, StandardCopyOption.REPLACE_EXISTING);
            return new CustomFileTmp(tempJar.toFile().getPath(), entry.getName(), Files.readAllBytes(tempJar), entry.getSize(), entry.getTime());
        } catch (IOException e) {
            System.err.println("Error while creating temp file " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static boolean isJavaArchive(JarEntry entry) {
        return entry.getName().endsWith(".jar") || entry.getName().endsWith(".war") || entry.getName().endsWith(".ear");
    }

    private static EarProperties rapidChecks(File fileEar) {
        EarProperties earProperties = new EarProperties();
        earProperties.setPath(fileEar.getAbsolutePath());
        earProperties.setName(fileEar.getName());
        earProperties.setSize(String.valueOf(fileEar.length()));
        Date lastModified = new Date(fileEar.lastModified());
        earProperties.setLastModified(lastModified.toString());
        return earProperties;
    }
}
