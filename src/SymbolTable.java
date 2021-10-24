import java.util.ArrayList;
import java.util.List;

public class SymbolTable {
    private List<List<String>> symTbl;

    public SymbolTable(int capacity){
        this.symTbl=new ArrayList<>();
        for(int i=0;i<capacity;i++){
            symTbl.add(new ArrayList<>());
        }

    }

    public int hashFunc(String value) {
        return Math.abs(value.hashCode()) % symTbl.size();
    }

    public TableEnityStructure<Integer, Integer> add(String value) {
        TableEnityStructure<Integer, Integer> inst = getEntity(value);

        if (inst != null) {
            return inst;
        }

        int h = hashFunc(value);
        int position = symTbl.get(h).size();
        symTbl.get(h).add(value);

        return new TableEnityStructure<>(position, h);
    }


    public TableEnityStructure<Integer, Integer> getEntity(String value) {
        int h = hashFunc(value);

        if (h >= symTbl.size()) {
            return null;
        }

        int position = symTbl.get(h).indexOf(value);

        if (position == -1) {
            return null;
        }

        return new TableEnityStructure<>(position, h);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        for (var l : symTbl) {
            string.append(l);
            string.append("\n");
        }

        return string.toString();
    }

}
