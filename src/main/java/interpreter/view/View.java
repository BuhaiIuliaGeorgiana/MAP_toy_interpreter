package interpreter.view;

import interpreter.PrgState;
import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.adt.*;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;

import java.util.Scanner;

public class View {
    private Scanner scanner = new Scanner(System.in);
    private IStmt selectedProgram;

    public void start() {
        while (true) {
            System.out.println("\n1. Select program");
            System.out.println("2. Run selected program");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1" -> selectProgram();
                case "2" -> runProgram();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void selectProgram() {
        System.out.println("\n1. Example 1");
        System.out.println("2. Example 2");
        System.out.println("3. Example 3");
        System.out.print("Choose: ");

        String option = scanner.nextLine();

        switch (option) {
            case "1" -> selectedProgram = Examples.ex1();
            case "2" -> selectedProgram = Examples.ex2();
            case "3" -> selectedProgram = Examples.ex3();
            default -> {
                System.out.println("Invalid program");
                return;
            }
        }

        System.out.println("Selected: " + selectedProgram);
    }

    private void runProgram() {
        if (selectedProgram == null) {
            System.out.println("Select a program first!");
            return;
        }

        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                selectedProgram
        );

        IRepository repo = new Repository(state);
        Controller controller = new Controller(repo, true);

        try {
            controller.allStep();
            System.out.println("Final output: " + state.getOut());
        } catch (MyException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}