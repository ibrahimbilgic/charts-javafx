package sample;

import java.time.LocalDate;
public class Records implements Comparable<Records>{

    LocalDate year;
    String name;
    String country;
    int value;
    String category;

    public Records(LocalDate year,String name, String country, int value, String category){
        this.year = year;
        this.name = name;
        this.country = country;
        this.value = value;
        this.category = category;
    }

    public LocalDate getYear() {
        return year;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }


    public int getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }

    public String toString(){
        return "Year: "+ year+ " name: " + name + " country: "+ country+ " value: " + value + " category: " + category;
    }

    
    @Override
    public int compareTo(Records other){
        return this.year.compareTo(other.year);
    }

}