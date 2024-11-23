package it.gend;

import it.gend.controller.CompareController;
import it.gend.controller.InputUserController;
import it.gend.domain.EarDifferences;
import it.gend.domain.InputUser;
import it.gend.utils.PrintUtils;

import java.io.IOException;

/**
 * @author Daniele Asteggiante
 */
public class Main {
    public static void main(String[] args) {
        try {
            InputUserController inputUserController = new InputUserController();
            InputUser inputUser = inputUserController.readInputUser();
            EarDifferences earDifferences = CompareController.compareEar(inputUser);
            PrintUtils.printDifferences(earDifferences);
            PrintUtils.printSpecificDifferences(earDifferences);
        } catch (IOException e) {
            System.err.println("Error during comparison " + e.getMessage());
            throw new RuntimeException(e);
        }

    }
}