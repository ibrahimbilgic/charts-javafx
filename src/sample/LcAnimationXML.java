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

public class LcAnimationXML extends Application{

    int i=0;

    String tur = "XML";
    sample.LineChart chart;
    ParseXml Xml ;
    Timeline animation = new Timeline();

    static ArrayList<String> colorList=new ArrayList<>();
    static HashMap<String, String> colorCategory = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {

        chart= new sample.LineChart(Xml.title, "xAxisLabel");
        
        if((!ParseXml.fcontroller) && (!ParseText.tcontroller)){
            
            chart.cizdir(stage, ParseXml.listRecordsLine);


            animation = new Timeline();
            animation.getKeyFrames()
                    .add(new KeyFrame(Duration.millis(150),
                            (ActionEvent actionEvent) -> {

                                if(i<=ParseXml.yearArrayList.size()-1){ // Son değer sonrası ekrana gelen hataları ortadan kaldırmak için
                                    chart.plotAgain(ParseXml.listRecordsLine,i,tur);
                                    i++;

                                }
                                else
                                {
                                    animation.stop();
                                    Xml.clearAll();
                                    chartbitti();
                                    Main.silButon.setDisable(false);
                                }


                            }));
            animation.setCycleCount(Animation.INDEFINITE);
            animation.play();
            chart.categoryl(ParseXml.listRecordsLine,colorCategory);
        }


    }

    public static void createColor(){
        Random random = new Random();

        for(int i=0;i<ParseXml.categoryArrayList.size();i++){
            int nextInt = random.nextInt(0xffffff + 1);
            String colorCode = String.format("#%06x", nextInt);
            colorList.add(colorCode);
        }
    }
    public static void makeHashMapColor(){
        createColor();
        for(int i=0;i<ParseXml.categoryArrayList.size();i++){
            colorCategory.put(ParseXml.categoryArrayList.get(i),colorList.get(i));
        }
    }

    public static void main(String[] args) {
        launch(args);

    }

    public static void chartbitti()
    {
        Main.durdurbutton.setDisable(true);
        Main.devametbutton.setDisable(true);
        Main.silButon.setDisable(false);
    }
}