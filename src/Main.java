import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

        private static final String INPUT_FILENAME = "C:\\Users\\alexa\\IdeaProjects\\lab2flcd\\src\\problems.in";

        public static void main(final String[] args) {
            final TableEnityStructure<SymbolTable, List<TableEnityStructure<String, TableEnityStructure<Integer, Integer>>>> result = Scanner.scan(INPUT_FILENAME);

            final SymbolTable symbolTable = result.getPosition();
            final List<TableEnityStructure<String, TableEnityStructure<Integer, Integer>>> pif = result.getValue();

            System.out.println();
            System.out.println("Lexically correct.");

            try (final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\alexa\\IdeaProjects\\lab2flcd\\src\\ST.out"))) {
                for (int i = 0; i < symbolTable.getSymbolTable().size(); ++i) {
                    bufferedWriter.write(String.format("%d -> %s%n", i, symbolTable.getSymbolTable().get(i)));
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }

            try (final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\alexa\\IdeaProjects\\lab2flcd\\src\\PIF.out"))) {
                for (final TableEnityStructure<String, TableEnityStructure<Integer, Integer>> pair : pif) {
                    bufferedWriter.write("\"" + pair.getPosition() + "\"" + " -> " + pair.getValue() + "\n");
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
}
