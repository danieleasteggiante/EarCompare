package it.gend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Daniele Asteggiante
 */
public class FindController {
    public static List<String> findPath(String pathEar, String className) {
        List<String> classesPathFinded = new java.util.ArrayList<>();
        try (JarFile jarFile = new JarFile(pathEar)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if(entry.getName().contains(className))
                    classesPathFinded.add(entry.getName());
                if (CompareController.isJavaArchive(entry)) {
                    Path tempJar = Files.createTempFile("tempJar", ".jar");
                    Files.copy(jarFile.getInputStream(entry), tempJar, StandardCopyOption.REPLACE_EXISTING);
                    findPath(tempJar.toAbsolutePath().toString(), className);
                }
            }
            return classesPathFinded;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
