package main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter dividend integer: ");
            int dividend = scanner.nextInt();
            System.out.print("Enter divider integer: ");
            int divisor = scanner.nextInt();
            System.out.print(Divider.doDivision(dividend, divisor));
            scanner.close();
        } catch (InputMismatchException e) {
            System.out.println("Error: Incorrect integer value!");
        }
    }

}
