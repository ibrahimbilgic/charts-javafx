package sample;

public class Bar implements Comparable<Bar> {

    private String name,category;
    private int value;

    public Bar(String name, String category, int value){
        this.name = name;
        this.category = category;
        this.value = value;
    }

    public String getName(){
        return this.name;
    }

    public String getCategory(){
        return this.category;
    }

    public int getValue(){
        return this.value;
    }

    public int compareTo(Bar other){
        return Integer.compare(this.value, other.value);
    }

    public String toString(){
        return " name: " + name +" value: " + value + " category: " + category;
    }

}