public class Line{
    //Feel free to add other necessary variables
    String name, category;
    int value;
    // Creates a new line.
    public Line(String name, int value, String category){
        this.name = name;
        this.value = value;
        this.category = category;
    }
    
    // Returns the name of this line.
    public String getName(){
        return this.name;
    }
    
    // Returns the category of this line.
    public String getCategory(){
        return this.category;
    }
    
    //Returns the next value of this line.
    public int nexValue(){
        // next line
    }
    
    //Feel free to add other necessary method
    }