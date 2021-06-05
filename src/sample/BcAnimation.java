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


public class BcAnimation extends Application {

    int grup_sira = 1;
    int labelCount = 0;
    ParseText Test;
    Timeline timeline = new Timeline();
    BarChart brchart;
    static ArrayList<String> colorList=new ArrayList<>();
    static HashMap<String, String> colorCategory = new HashMap<>();
    boolean controller=false;


    @Override
    public void start(Stage stage) throws FileNotFoundException {
    
        if((!ParseXml.fcontroller) && (!ParseText.tcontroller)){
            timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(150), (ActionEvent actionEvent) -> {
                    printBars(stage, grup_sira);
                    labelCount++;

                }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
        }
    }

    public void printBars(Stage stage,int bas){
        int satir_sira=0;
        int a = 0;
        ArrayList<Bar> list = new ArrayList<>();

        for (int j=1;j<bas;j++)
        {
            satir_sira=ParseText.tempRecordGroup.get(j-1)+satir_sira;
            a=j;
        }

        for (int i=satir_sira;i<satir_sira+ParseText.tempRecordGroup.get(a);i++)
        {
            list.add(ParseText.listRecordsBar.get(i));
            if(ParseText.listRecordsBar.size()==i+1)
            {
                timeline.stop();

            }
        }
        brchart = new BarChart(Test.title, "xAxisLabel",list,"TEXT");
        brchart.makeTitleVisible(brchart.getTitle());

        if(Main.controller==false)
        {
            brchart.categoryb(ParseText.listRecordsBar,colorCategory);
            Main.controller=true;
        }
        brchart.cizdir(stage, list,labelCount);


        if(brchart.label.getText().equals(ParseText.yearArrayList.get(ParseText.yearArrayList.size()-1).toString()))
        {
            Test.clearAll();
            labelCount=0;
            grup_sira=1;
            brchart.reset();
            LcAnimationXML.chartbitti();
            Main.silButon.setDisable(false);
        }
        grup_sira=grup_sira+1;
    }

    public void createColor(){
        Random random = new Random();

        for(int i=0;i<ParseText.categoryArrayList.size();i++){
            int nextInt = random.nextInt(0xffffff + 1);
            String colorCode = String.format("#%06x", nextInt);
            colorList.add(colorCode);
        }
    }
    public void makeHashMapColor(){
        createColor();
        for(int i=0;i<ParseText.categoryArrayList.size();i++){
            colorCategory.put(ParseText.categoryArrayList.get(i),colorList.get(i));
        }
    }

}