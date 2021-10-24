public class Main {
    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable(5);

        System.out.println(symbolTable.add("a"));
        System.out.println(symbolTable.add("2"));
        System.out.println(symbolTable.add("a"));


        System.out.println("Symbol Table:");
        System.out.println(symbolTable);
    }
}
