package sample;
public abstract class Chart {
    
    String title;
    String xAxisLabel;

    public Chart(String title, String xAxisLabel){
        this.title = title;
        this.xAxisLabel = xAxisLabel;
    }

    public void setTitle(String title) {
        this.title = title;
    }
   
    public void reset(){
        this.title = "";
        this.xAxisLabel = "";
    }
    public String getTitle(){
        return this.title;
    }

}

