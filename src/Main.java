import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
public class Main {


    public static void printMenu() {

        System.out.println("1: Display the states");
        System.out.println("2: Display the alphabet");
        System.out.println("3: Display the transitions");
        System.out.println("4: Display the final state");
        System.out.println("5.Get the transitions");
        System.out.println("6.Check accepted sequence");
        System.out.println("7. Exit");
    }

        public static void main(final String[] args) {
           printMenu();
            FA fa = new FA();
            String file="C:\\Users\\alexa\\IdeaProjects\\lab2flcd\\src\\FA.in";
            fa.readFile(file);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Option: ");
                String option=scanner.nextLine();
                try {
                    switch (option) {
                        case "1": System.out.println(fa.getstates());
                        case "2":
                            System.out.println(fa.getAlphabet());
                            break;
                        case "3":
                            System.out.println(fa.getInitialState());
                            break;
                        case "4":
                            System.out.println(fa.getFinalStates());
                        case "5":
                            for (final var transition : fa.getTransitions().entrySet()) {
                                System.out.println(transition.getKey().getPosition() + ", " + transition.getKey().getValue() + " -> " + transition.getValue());
                            }
                            break;

                        case "6":
                            System.out.print("Sequence: ");
                            String sequence = scanner.nextLine();
                            if (fa.accepted(sequence)) {
                                System.out.println("Sequence is accepted");
                            } else {
                                System.out.println("Sequence is not accepted");
                            }
                            break;
                        default:
                            System.out.println("Invalid option");
        }}catch (Exception e){e.printStackTrace();}
}}}
