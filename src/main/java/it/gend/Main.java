package it.gend;

import it.gend.controller.CompareController;
import it.gend.controller.FindController;
import it.gend.domain.EarDifferences;
import it.gend.domain.InputUser;
import it.gend.utils.PrintUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Daniele Asteggiante
 */
public class Main {
    public static void main(String[] args) {
        try {
            if (args[0].equals("find")) {
                Map<String,String> pathAndClass = PrintUtils.pathToSearch();
                List<String> classesPathFinded = FindController.findPath(pathAndClass.get("path"), pathAndClass.get("name"));
                PrintUtils.printClassesPath(classesPathFinded);
                return;
            }
            InputUser inputUser = PrintUtils.readInputUser();
            EarDifferences earDifferences = CompareController.compareEar(inputUser);
            PrintUtils.printDifferences(earDifferences);
            PrintUtils.printSpecificDifferences(earDifferences);
        } catch (IOException e) {
            System.err.println("Error during comparison " + e.getMessage());
            throw new RuntimeException(e);
        }

    }
}