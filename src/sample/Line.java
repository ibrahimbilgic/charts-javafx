package sample;
import java.time.LocalDate;
import java.util.ArrayList;

public class Line{

    String name, category;
    ArrayList<YearValue> values = new ArrayList<>();


    public Line(String name, ArrayList<YearValue> values, String category){
        this.name = name;
        this.values.addAll(values);
        this.category = category;
    }


    public String getName(){
        return this.name;
    }


    public String getCategory(){
        return this.category;
    }

    public int nextValue(int i){
        return values.get(i).getValue();
    }
    public LocalDate nextYear(int i){
        return values.get(i).getYear();
    }

    public String toString(){
        return " name: " + name +" value: " + values + " category: " + category;
    }

    public int getValuesSize() {
        return values.size();
    }


}