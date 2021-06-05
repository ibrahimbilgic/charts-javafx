package sample;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BcAnimationXML extends Application {

    Timeline timeline = new Timeline();

    int grup_sira = 0;
    int labelcount = 0;
    ParseXml xml;
    sample.BarChart barchart;
    ArrayList<String> colorList=new ArrayList<>();
    static HashMap<String, String> colorCategory = new HashMap<>();

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        if((!ParseXml.fcontroller) && (!ParseText.tcontroller)){
            
            timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(150), (ActionEvent actionEvent) -> {
                    printBars(stage, grup_sira);
                    labelcount++;
                }));

            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setAutoReverse(true); 
            timeline.play();
        }

    }

    public void printBars(Stage stage,int bas){
        int satir_sira=0;
        ArrayList<Bar> list = new ArrayList<>();

        for (int j=0;j<bas;j++)
        {
            satir_sira=ParseXml.counter+satir_sira;
        }

        for (int i=satir_sira;i<satir_sira+ParseXml.counter;i++)
        {
            
            list.add(ParseXml.listRecordsBar.get(i));
    
            if(i+1==ParseXml.listRecordsBar.size())
            {
                timeline.stop();

            }
        }

        barchart = new sample.BarChart(ParseXml.title, "xAxisLabel",list,"XML");
        barchart.makeTitleVisible(barchart.getTitle());

        if(Main.controller==false)
        {
            barchart.categoryb(ParseXml.listRecordsBar,colorCategory);
            Main.controller=true;
        }
        barchart.cizdir(stage, list,labelcount);

        if(barchart.label.getText().equals(ParseXml.yearArrayList.get(ParseXml.yearArrayList.size()-1).toString()))
        {
            xml.clearAll();
            labelcount=0;
            grup_sira=0;
            barchart.reset();
            LcAnimationXML.chartbitti();
            Main.silButon.setDisable(false);

        }
        grup_sira=grup_sira+1;

    }

    public void createColor(){
        Random random = new Random();

        for(int i=0;i<ParseXml.categoryArrayList.size();i++){
            int nextInt = random.nextInt(0xffffff + 1);
            String colorCode = String.format("#%06x", nextInt);
            colorList.add(colorCode);
        }
    }
    public void makeHashMapColor(){
        createColor();
        for(int i=0;i<ParseXml.categoryArrayList.size();i++){
            colorCategory.put(ParseXml.categoryArrayList.get(i),colorList.get(i));
        }
    }

}