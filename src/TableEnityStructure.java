import java.util.Objects;

public class TableEnityStructure<K,V>{

    private K position;
    private V value;

    public TableEnityStructure(K p,V v){
        position=p;
        value=v;
    }
    public K getPosition(){
        return position;
    }
    public V getValue(){
        return value;
    }
    @Override
    public String toString() {
        return "Entity{" +
                "position=" + position +
                ", hashValue=" + value +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TableEnityStructure)) return false;

        TableEnityStructure<?, ?> inst = (TableEnityStructure<?, ?>) o;

        if (position != null ? !position.equals(inst.position) : inst.position != null) return false;
        return value != null ? value.equals(inst.value) : inst.value == null;
    }
}
