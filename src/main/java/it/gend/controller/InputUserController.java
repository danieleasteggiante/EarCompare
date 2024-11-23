package it.gend.controller;

import it.gend.domain.InputUser;
import it.gend.utils.PrintUtils;

import java.util.Scanner;

/**
 * @author Daniele Asteggiante
 */
public class InputUserController {

    public InputUserController() {
    }

    public InputUser readInputUser() {
        PrintUtils.printWelcome();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert path of ear1: ");
        String pathEar1 = scanner.nextLine();

        System.out.println("Insert path of ear2: ");
        String pathEar2 = scanner.nextLine();

        return new InputUser(pathEar1, pathEar2);
    }

}
