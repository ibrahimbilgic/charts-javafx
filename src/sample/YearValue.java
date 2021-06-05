package sample;

import java.time.LocalDate;

public class YearValue {

    LocalDate year;
    int value;

    public YearValue(int value, LocalDate year) {
        this.value = value;
        this.year = year;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "[ "+"value: " + value + ", year:" + year + "]";
    }
}