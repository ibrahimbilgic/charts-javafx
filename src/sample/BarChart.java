package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BarChart extends Chart{

    NumberAxis xAxis = new NumberAxis();
    CategoryAxis yAxis = new CategoryAxis();
    Label label2;


    javafx.scene.chart.BarChart<Number, String> bc = new javafx.scene.chart.BarChart<Number, String>(xAxis, yAxis);
     static FlowPane pane1=new FlowPane();

     Label label=new Label();
     static StackPane stackPane = new StackPane();
     String tur;

    public BarChart(String title, String xAxisLabel,ArrayList<Bar> list,String tur) {
        super(title, xAxisLabel);
        sort(list);
        this.tur = tur;
    }

    private void sort(ArrayList<Bar> list) {
        Collections.sort(list);
        Collections.reverse(list);
    }
    public void cizdir(Stage stage, ArrayList<Bar> list, int labelCount){


        xAxis.setAutoRanging(false);
        xAxis.setUpperBound(list.get(0).getValue()+list.get(9).getValue());
        xAxis.setLowerBound(0);
        xAxis.setTickUnit(xAxis.getUpperBound()/8);

        bc.setAnimated(false);


        yAxis.setTickLabelsVisible(false);
        xAxis.setTickLabelsVisible(false);

        XYChart.Series series1 = new XYChart.Series();
        series1.getData().add(new XYChart.Data(list.get(9).getValue(),  list.get(9).getName()));

        XYChart.Series series2 = new XYChart.Series();
        series2.getData().add(new XYChart.Data(list.get(8).getValue(),  list.get(8).getName()));

        XYChart.Series series3 = new XYChart.Series();
        series3.getData().add(new XYChart.Data(list.get(7).getValue(),  list.get(7).getName()));

        XYChart.Series series4 = new XYChart.Series();
        series4.getData().add(new XYChart.Data(list.get(6).getValue(),  list.get(6).getName()));

        XYChart.Series series5 = new XYChart.Series();
        series5.getData().add(new XYChart.Data(list.get(5).getValue(),  list.get(5).getName()));

        XYChart.Series series6 = new XYChart.Series();
        series6.getData().add(new XYChart.Data(list.get(4).getValue(),  list.get(4).getName()));

        XYChart.Series series7 = new XYChart.Series();
        series7.getData().add(new XYChart.Data(list.get(3).getValue(),  list.get(3).getName()));

        XYChart.Series series8 = new XYChart.Series();
        series8.getData().add(new XYChart.Data(list.get(2).getValue(),  list.get(2).getName()));

        XYChart.Series series9 = new XYChart.Series();
        series9.getData().add(new XYChart.Data(list.get(1).getValue(),  list.get(1).getName()));

        XYChart.Series series10 = new XYChart.Series();
        series10.getData().add(new XYChart.Data(list.get(0).getValue(),  list.get(0).getName()));

        if(tur.equals("TEXT")){
            label.setText(ParseText.yearArrayList.get(labelCount).toString());
        }
        else{
            label.setText(ParseXml.yearArrayList.get(labelCount).toString());
        }

        bc.getData().addAll(series1,series2,series3,series4,series5,series6,series7,series8,series9,series10);

        stackPane.setAlignment(Pos.BOTTOM_RIGHT);
       
        stackPane.setMinWidth(970);
        stackPane.setMinHeight(530);
        label.setStyle("-fx-font-size: 36px;-fx-text-fill: gray;");
        label.setPadding(new Insets(10,30,50,10));
        stackPane.getChildren().addAll(bc,label);

        pane1.setHgap(20);
        pane1.setVgap(20);
        pane1.setPadding(new Insets(5,0,0,40));

        bc.setBarGap(-30); 
        bc.setLegendVisible(false); 


        for (Series<Number, String> series : bc.getData()) {
            for (javafx.scene.chart.XYChart.Data<Number, String> data : series.getData()) {
                StackPane bar = (StackPane) data.getNode();
                Text dataText = new Text(data.getYValue()+"\n"+data.getXValue() + "");
                dataText.setFont(Font.font("Verdana", 14));
                dataText.setStyle("-fx-fill :white;");
                bar.getChildren().add(dataText);

                for(int j=0;j<list.size();j++) {

                    if(list.get(j).getName().equals(data.getYValue())){
                        if(tur.equals("TEXT")){
                            data.getNode().setStyle("-fx-bar-fill: "+ sample.BcAnimation.colorCategory.get(list.get(j).getCategory())+";");
                        }
                        else{
                            data.getNode().setStyle("-fx-bar-fill: "+ sample.BcAnimationXML.colorCategory.get(list.get(j).getCategory())+";");
                        }


                    }
                }
            }
        }
    }
    public void makeTitleVisible(String title){
        bc.setTitle(title);
    }

    public void categoryb(ArrayList<Bar> list,HashMap<String, String> colorCategory) {
        for (int j = 0; j < list.size(); j++) {
            boolean controller2 = false;

            for (int p = 0; p < Main.bcontrol.size(); p++) {
                if (Main.bcontrol.get(p).equals(list.get(j).getCategory())) {
                    controller2 = true;
                    break;
                }
            }
            if (controller2 == false) {
                label2 = new Label();
                label2.setText("■ "+list.get(j).getCategory());
                Main.bcontrol.add(list.get(j).getCategory());
                label2.setStyle("-fx-text-fill: " +colorCategory.get(list.get(j).getCategory()) + ";");
                label2.setFont(Font.font("Ariel", FontWeight.SEMI_BOLD, 13));
                pane1.getChildren().add(label2);
            }
        }
    }
}