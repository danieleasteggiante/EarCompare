package it.gend.utils;

import com.github.lalyos.jfiglet.FigletFont;
import it.gend.controller.ReportGeneratorController;
import it.gend.domain.EarDifferences;
import it.gend.domain.InputUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static InputUser readInputUser() {
        PrintUtils.printWelcome();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert path of ear1: ");
        String pathEar1 = scanner.nextLine();

        System.out.println("Insert path of ear2: ");
        String pathEar2 = scanner.nextLine();

        return new InputUser(pathEar1, pathEar2);
    }

    public static void printDifferences(EarDifferences differences) {
        System.out.println("[+] Printing differences between" + differences.getEar1().getName() + " and " + differences.getEar2().getName());
        if (differences.isEquals()) {
            System.out.println("[+] The two ears are equals");
            return;
        }
        System.out.println("[+] Jars not common nr.: " + differences.getDifferentJars().size());
        System.out.println("[+] File not common nr.: " + differences.getDifferentFiles().size());
        System.out.println("[+] Files has differences: " + differences.getCommonFilesWithDifference().size());

    }

    public static void printSpecificDifferences(EarDifferences earDifferences) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printSpecificDifferencesMenu();
            int choice = scanner.nextInt();
            printChoice(earDifferences, choice, scanner);
        }

    }

    private static void printChoice(EarDifferences earDifferences, int choice, Scanner scanner) {
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
                System.out.println("[+] Printing common files with differences");
                earDifferences.getCommonFilesWithDifference().forEach(System.out::println);
                break;
            case 4:
                System.out.println("[+] Printing record in txt");
                createTxt(earDifferences);
            case 5:
                System.out.println("[+] Exiting");
                scanner.close();
                System.exit(0);
            default:
                System.out.println("[-] Invalid choice");
                break;
        }
    }

    private static void createTxt(EarDifferences earDifferences) {
        System.out.println("[+] Insert path and name of txt file (ex. /home/user/report.txt)");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        System.out.println("[+] Creating txt file");
        ReportGeneratorController reportGeneratorController = new ReportGeneratorController(earDifferences, path);
        reportGeneratorController.printReport();
    }

    private static void printSpecificDifferencesMenu() {
        System.out.println("[+] Printing specific differences");
        System.out.println(" - Select 1 for print different jars");
        System.out.println(" - Select 2 for print different files");
        System.out.println(" - Select 3 for print files with differences");
        System.out.println(" - Select 4 for create txt report");
        System.out.println(" - Select 5 for exit");
    }

    public static void printClassesPath(List<String> classesPathFinded) {
        System.out.println("[+] Classes path finded:");
        if(classesPathFinded.isEmpty()){
            System.out.println("[-] No classes finded");
            return;
        }
        for (String path : classesPathFinded) {
            System.out.println("[+] " + path);
        }
    }

    public static Map<String,String> pathToSearch() {
        Scanner scanner = new Scanner(System.in);
        Map<String,String> map = new HashMap<>();
        System.out.println("[+] Classes ear to find in: ");
        String path = scanner.nextLine();
        map.put("path", path);
        System.out.println("[+] Classes name to Find: ");
        String name = scanner.nextLine();
        map.put("name", name);
        return map;

    }
}
