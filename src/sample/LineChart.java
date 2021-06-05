package sample;


import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Shadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class LineChart extends Chart{

    static StackPane stackPane = new StackPane();
    static FlowPane flowPane=new FlowPane();
    Label label;
    boolean controller=false;
    XYChart.Series<String, Number> series;
    javafx.scene.chart.LineChart<String, Number> lineChart;

    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();

    int yearMax;


    public LineChart(String title, String xAxisLabel) {
        super(title, xAxisLabel);
    }

    public javafx.scene.chart.LineChart<String, Number> cizdir(Stage stage, ArrayList<Line> list){

        xAxis.setAnimated(false);
        yAxis.setAnimated(false);

        lineChart = new javafx.scene.chart.LineChart<>(xAxis, yAxis);
        lineChart.setTitle(getTitle());
        lineChart.setAnimated(false);
        lineChart.setLegendVisible(false);
        lineChart.setLegendSide(Side.BOTTOM);
        stackPane.setAlignment(Pos.BOTTOM_RIGHT);
        stackPane.setMinWidth(970);
        stackPane.setMinHeight(530);
        stackPane.getChildren().add(lineChart);
        flowPane.setHgap(20);
        flowPane.setVgap(20);
        flowPane.setPadding(new Insets(5,0,0,40));


        for(int z=0;z<10;z++){
            series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data(list.get(z).nextYear(0).toString(),list.get(z).nextValue(0)));
            series.setName(list.get(z).getName()+"   ");
            lineChart.getData().add(series);

        }

        return lineChart;
    }


    public void plotAgain(ArrayList<Line> list,int a,String tur) {
        int i=0;
        for(Series<String, Number> series : lineChart.getData()){

            if(tur.equals("XML")){
                if(a<list.get(i).getValuesSize()){
                    series.getData().add(new XYChart.Data(list.get(i).nextYear(a).toString(),list.get(i).nextValue(a)));
                }
            }
            else{
                for(int p=0;p<list.get(i).getValuesSize();p++){
                    if(list.get(i).nextYear(p).toString().equals(ParseText.yearArrayList.get(a).toString())){
                        series.getData().add(new XYChart.Data(list.get(i).nextYear(p).toString(),list.get(i).nextValue(p)));
                        break;
                    }

                }
            }
            i++;
        }

        showName(list,tur);
        addGlowEffect(lineChart,list);
    }

    public void showName(ArrayList<Line> list, String tur){  /// Her bir line için isim bilgisini göstermek için.
        int i=0;

        for (Series<String, Number> series : lineChart.getData()) {

            for (javafx.scene.chart.XYChart.Data<String, Number> data : series.getData()) {

                // Her bir noktada isim, value ve yıl bilgilerinin gösterilmesi...
                Tooltip.install(data.getNode(), new Tooltip(
                        list.get(i).getName() + "\n" +
                                "Year : " + data.getXValue()
                                + " \n" + "Value : " + data.getYValue()));


                data.getNode().setOnMouseEntered(event -> data.getNode().getStyleClass().add("onHover"));


                data.getNode().setOnMouseExited(event -> data.getNode().getStyleClass().remove("onHover"));

                String[] dizi = data.getNode().getAccessibleText().split(" "+ " ");  // çift boşluğa göre böl

                for(int j=0;j<list.size();j++){ 

                    if(list.get(j).getName().equals(dizi[0])){
                        if(tur.equals("TEXT")){
                            series.getNode().setStyle("-fx-stroke: "+ sample.LcAnimation.colorCategory.get(list.get(j).getCategory())+";");
                            data.getNode().setStyle("-fx-background-color: "+ sample.LcAnimation.colorCategory.get(list.get(j).getCategory())+";");
                        }
                        else{
                            series.getNode().setStyle("-fx-stroke: "+ sample.LcAnimationXML.colorCategory.get(list.get(j).getCategory())+";");
                            data.getNode().setStyle("-fx-background-color: "+ sample.LcAnimationXML.colorCategory.get(list.get(j).getCategory())+";");
                        }

                    }
                }

            }

            i++;

        }

    }

    public void categoryl(ArrayList<Line> list,HashMap<String, String> colorCategory){
        for (int j = 0; j < list.size(); j++) {
            boolean controller2 = false;

                for (int p = 0; p < Main.lcontrol.size(); p++) {
                    if (Main.lcontrol.get(p).equals(list.get(j).getCategory())) {
                        controller2 = true;
                        break;
                    }
                }

                if (controller2 == false) {
                    label = new Label();
                    Main.lcontrol.add(list.get(j).getCategory());
                    label.setText("■ "+list.get(j).getCategory());
                    Main.lcontrol.add(list.get(j).getCategory());
                    
                    label.setStyle("-fx-text-fill: " + colorCategory.get(list.get(j).getCategory()) + ";");
                    label.setFont(Font.font("Ariel", FontWeight.SEMI_BOLD, 13));
                    flowPane.getChildren().add(label);
                }
        }
    }
    private void addGlowEffect(javafx.scene.chart.LineChart<String, Number> lineChart,ArrayList<Line> list) {
        for (Series<String, Number> series : lineChart.getData()) {

            if (series.getNode() != null && series.getNode() instanceof Path) {
                final Path path = (Path) series.getNode();

                Shadow sh = new Shadow();
                sh.setColor(Color.CADETBLUE);

                sh.setHeight(10);
                sh.setWidth(10);

                path.setEffect(null);
                path.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        path.setEffect(sh); // kullanıcı line üzerine mouse ile geldiğinde
                    }
                });
                path.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        path.setEffect(null);
                    }
                });


            }

        }

    }
}
