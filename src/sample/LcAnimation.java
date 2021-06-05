package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LcAnimation extends Application{

    int i=0;
    String tur = "TEXT";

    sample.LineChart chart;
    ParseText Text;

    Timeline animation = new Timeline();

    static ArrayList<String> colorList=new ArrayList<>();
    static HashMap<String, String> colorCategory = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {

        chart = new sample.LineChart(Text.title, "xAxisLabel");


        if((!ParseXml.fcontroller) && (!ParseText.tcontroller)){
            chart.cizdir(stage, ParseText.listRecordsLine);

            animation = new Timeline();
            animation.getKeyFrames()
                    .add(new KeyFrame(Duration.millis(150),
                            (ActionEvent actionEvent) -> {
    
                                if(i<=ParseText.yearArrayList.size()-1){
                                    chart.plotAgain(ParseText.listRecordsLine,i,tur);
                                    i++;
                                }
                                else
                                {
                                   
                                    animation.stop();
                                    Text.clearAll();
                                    LcAnimationXML.chartbitti();
                                    Main.silButon.setDisable(false);
                                    
                                }
    
    
                            }));
            animation.setCycleCount(Animation.INDEFINITE);
            animation.play();
            chart.categoryl(ParseText.listRecordsLine,colorCategory);
        }
    }

    public static void createColor(){
        Random random = new Random();

        for(int i=0;i<ParseText.categoryArrayList.size();i++){
            int nextInt = random.nextInt(0xffffff + 1);
            String colorCode = String.format("#%06x", nextInt);
            colorList.add(colorCode);
        }
    }
    public static void makeHashMapColor(){
        createColor();
        for(int i=0;i<ParseText.categoryArrayList.size();i++){
            colorCategory.put(ParseText.categoryArrayList.get(i),colorList.get(i));
        }
    }

    public static void main(String[] args) {
        launch(args);
  

    }

}