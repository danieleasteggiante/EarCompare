package it.gend.utils;

import com.github.lalyos.jfiglet.FigletFont;
import it.gend.domain.EarDifferences;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Daniele Asteggiante
 */
public class PrintUtils {
    public static void printWelcome() {
        try {
            String asciiArt = FigletFont.convertOneLine("EarCompare");
            System.out.println(asciiArt);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printDifferences(EarDifferences differences) {
        System.out.println("[+] Printing differences between" + differences.getEar1().getName() + " and " + differences.getEar2().getName());
        if (differences.isEquals()) {
            System.out.println("[+] The two ears are equals");
            return;
        }
        System.out.println("[+] Jars not common nr.: " + differences.getDifferentJars().size());
        System.out.println("[+] File not common nr.: " + differences.getDifferentJars().size());
        System.out.println("[+] Files has differences: " + differences.getCommonFilesWithDifference().size());

    }

    public static void printSpecificDifferences(EarDifferences earDifferences) {
        while (true) {
            printSpecificDifferencesMenu();
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            printChoice(earDifferences, choice);
        }
    }

    private static void printChoice(EarDifferences earDifferences, int choice) {
        switch (choice) {
            case 1:
                System.out.println("[+] Printing different jars");
                earDifferences.getDifferentJars().forEach(System.out::println);
                break;
            case 2:
                System.out.println("[+] Printing different files");
                earDifferences.getDifferentFiles().forEach(System.out::println);
                break;
            case 3:
                System.out.println("[+] Printing files with differences");
                earDifferences.getCommonFilesWithDifference().forEach(System.out::println);
                break;
            case 4:
                System.out.println("[+] Printing record in txt");
                createTxt(earDifferences);
            case 5:
                System.out.println("[+] Exiting");
                System.exit(0);
            default:
                System.out.println("[-] Invalid choice");
                break;
        }
    }

    private static void createTxt(EarDifferences earDifferences) {
        System.out.println("[+] Creating txt file");
        //TODO: Implement this method
    }

    private static void printSpecificDifferencesMenu() {
        System.out.println("[+] Printing specific differences");
        System.out.println(" - Select 1 for print different jars");
        System.out.println(" - Select 2 for print different files");
        System.out.println(" - Select 3 for print files with differences");
    }
}
