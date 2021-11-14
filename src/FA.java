import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FA {
    private List<String> alphabet;
    private List<String> states;
    private List<String> finalStates;
    private String initialState;
    private Map<TableEnityStructure<String, String>, List<String>> transitions;

    public FA() {
        this.alphabet = new ArrayList<>();
        this.states = new ArrayList<>();
        this.finalStates = new ArrayList<>();
        this.initialState = "";
        this.transitions = new HashMap<>();
    }


    public FA readFile(String file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            states = Arrays.stream(reader.readLine().split("\\s+")).collect(Collectors.toList());
            alphabet = Arrays.stream(reader.readLine().split("\\s+")).collect(Collectors.toList());
            initialState = reader.readLine();
            finalStates = Arrays.stream(reader.readLine().split("\\s+")).collect(Collectors.toList());

            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.replaceAll("\\s+", "").split(",");
                TableEnityStructure<String, String> SAndR = new TableEnityStructure<>(tokens[0], tokens[1]);
                String destination = tokens[2];

                if (transitions.containsKey(SAndR)) {
                    transitions.get(SAndR).add(destination);
                } else {
                    transitions.put(SAndR, new ArrayList<>() {{
                        add(destination);
                    }});
                }
            }

            return this;
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Failed to read  file");
    }

    public boolean accepted(final String sequence) {
        if (!isDFA()) {
            throw new RuntimeException("not deterministic");
        }

        String currentState = initialState;

        for (char ch : sequence.toCharArray()) {
            TableEnityStructure<String, String> SAndR = new TableEnityStructure<>(currentState, String.valueOf(ch));

            if (transitions.containsKey(SAndR)) {
                currentState = transitions.get(SAndR).get(0);
            } else {
                return false;
            }
        }

        return finalStates.contains(currentState);
    }

    private boolean isDFA() {
        for (final List<String> dest : transitions.values()) {
            if (dest.size() > 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "FA{" +
                "alphabet=" + alphabet +
                ", states=" + states +
                ", finalStates=" + finalStates +
                ", initialState='" + initialState + '\'' +
                ", transitions=" + transitions +
                '}';
    }

    public List<String> getstates() {
        return states;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public Map<TableEnityStructure<String, String>, List<String>> getTransitions() {
        return transitions;
    }

    public String getInitialState() {
        return initialState;
    }
}
