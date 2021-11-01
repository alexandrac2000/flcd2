import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Scanner {
    private static final List<String> separators = Arrays.asList( "{", "}", "(", ")", "."," ",":",",");
    private static final List<String> Operators = Arrays.asList(
            "+", "-", "*", "/", "%", "=", "<", ">", "!","~","<=",">="
    );


    private static final List<String> reservedWords = Arrays.asList(
            "while", "do", "int", "char", "str", "vect","@","v","float", "to", "from", "verify", "other", "otherif", "ret", "print",
            "read","and","or"
    );

    private static final String identifierRegex = "^[a-zA-Z]([a-zA-Z]|[0-9])*$";
    private static final String constantRegex = "^(0|[+\\-]?[1-9][0-9]*)|('([a-zA-Z]|[0-9])')|(\"([a-zA-Z]|[0-9])*\")|T|F$";
    private static final String anyNumberRegex = "([-+]?[0-9]*)";
    private static Pattern pattern = Pattern.compile("([a-zA-Z]([a-zA-Z]|[0-9])*|(0|[+\\-]?[1-9][0-9]*)|('([a-zA-Z]|[0-9])')|(\"([a-zA-Z]|[0-9])*\")|true|false|[&]{1,2}|[|]{1,2}|<=|>=|<|>|!=|\\+|-|\\*|%|;|/|\\(|\\)|\\[|\\]|\\{|\\}|!|[=]{1,2}| +)");

    static {
        final StringBuilder tokenizerRegex = new StringBuilder();
        tokenizerRegex.append("(");

        for (final String operator : Operators) {
            tokenizerRegex.append(Pattern.quote(operator)).append("|");
        }

        for (final String separator : separators) {
            tokenizerRegex.append(Pattern.quote(separator)).append("|");
        }

        tokenizerRegex.append("\\s+");
        tokenizerRegex.append(anyNumberRegex).append("|");
        tokenizerRegex.append("\\b([0-9]|[a-zA-Z])*\\b").append("|");
        tokenizerRegex.append(identifierRegex).append("|");
        tokenizerRegex.append(constantRegex).append("|");

        tokenizerRegex.append(")");
        pattern = Pattern.compile(tokenizerRegex.toString());
    }

    private static boolean isIdentifier(final String token) {
        return token.matches(identifierRegex);
    }

    private static boolean isConstant(final String token) {
        return token.matches(constantRegex);
    }

    private static List<String> getTokens(String line) {
        List<String> result = new ArrayList<>();
        Matcher matcher = pattern.matcher(line);
        int pos = 0;

        while (matcher.find()) {
            if (pos != matcher.start()) {
                result.add(line.substring(pos, matcher.start()));
            }
            result.add(matcher.group());
            pos = matcher.end();
        }
        if (pos != line.length()) {
            result.add(line.substring(pos));
        }

        return result
                .stream()
                .map(String::trim)
                .filter(string -> string.length() > 0)
                .collect(Collectors.toList());
    }

    public static TableEnityStructure<SymbolTable, List<TableEnityStructure<String, TableEnityStructure<Integer, Integer>>>> scan(final String filename) {
        final SymbolTable symTbl = new SymbolTable();
        final List<TableEnityStructure<String, TableEnityStructure<Integer, Integer>>> pif = new ArrayList<>();

        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 1;

            while ((line = bufferedReader.readLine()) != null) {
                var tokens = getTokens(line);

                for (var token : tokens) {
                    if (token.length() > 0) {
                        System.out.print("Token '" + token + "' -> ");
                        if (separators.contains(token) || Operators.contains(token) || reservedWords.contains(token)) {
                            System.out.println("SEPARATOR / OPERATOR / RESERVED WORD");
                            pif.add(new TableEnityStructure<>(token, new TableEnityStructure<>(-1, -1)));
                        } else if (isIdentifier(token)) {
                            System.out.println("IDENTIFIER");
                            pif.add(new TableEnityStructure<>("IDENTIFIER", symTbl.add(token)));
                        } else if (isConstant(token)) {
                            System.out.println("CONSTANT");
                            pif.add(new TableEnityStructure<>("CONSTANT", symTbl.add(token)));
                        } else {
                            throw new RuntimeException(String.format("Unknown token %s at line %s%n", token, lineNumber));
                        }
                    }
                }
                ++lineNumber;
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

         return new TableEnityStructure<>(symTbl,pif);
    }
}
