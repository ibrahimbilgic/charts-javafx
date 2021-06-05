package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class Main extends Application {

     Button grafikbutton = new Button ("Grafik Oluştur");
     static Button silButon = new Button ("Chart temizle");
     static Button durdurbutton = new Button ("Durdur");
     static Button devametbutton = new Button ("Devam Et");
     Button dosyaeklebutton=new Button("Dosya Ekle");
     static TextField urltext=new TextField();
     static ArrayList<String> bcontrol=new ArrayList<>();
     static ArrayList<String> lcontrol = new ArrayList<>();
     static boolean controller=false;


    ComboBox dosyaComboBox = new ComboBox();
    ComboBox grafikComboBox = new ComboBox();
    ToggleGroup secimgroup = new ToggleGroup();
    RadioButton urlrb = new RadioButton("Url Ekle");
    RadioButton dosyarb = new RadioButton("Dosya Ekle");

    GridPane grid = new GridPane();

    static Label dosyaadi=new Label();
    Line line = new Line();
    FileChooser fileChooser;
    File file;
    URL url;
    String urlcontrol;

    BcAnimation barchartAnimation;
    BcAnimationXML barchartAnimationXml;

    LcAnimationXML lineAnimationXml;
    LcAnimation lineAnimationText;

    VBox vBox=new VBox();

    static Boolean isActive = false;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        HBox HBox= new HBox(grid,line,vBox);


        Scene scene = new Scene(HBox, 1300, 655);

        dosyarb.setToggleGroup(secimgroup);
        urlrb.setToggleGroup(secimgroup);
        dosyarb.setSelected(true);

        dosyaeklebutton.setMaxWidth(Double.MAX_VALUE);
        grafikbutton.setMaxWidth(Double.MAX_VALUE);
        silButon.setMaxWidth(Double.MAX_VALUE);
        devametbutton.setMaxWidth(Double.MAX_VALUE);
        durdurbutton.setMaxWidth(Double.MAX_VALUE);
        devametbutton.setMaxWidth(Double.MAX_VALUE);
        durdurbutton.setMaxWidth(Double.MAX_VALUE);
        dosyaComboBox.setMaxWidth(Double.MAX_VALUE);
        grafikComboBox.setMaxWidth(Double.MAX_VALUE);
        dosyarb.setMaxWidth(Double.MAX_VALUE);
        urlrb.setMaxWidth(Double.MAX_VALUE);
        urltext.setMaxWidth(Double.MAX_VALUE);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: #c1cdcd;");
        grid.setPadding(new Insets(10, 25, 10, 25));
        line.setStartY(0.0);
        line.setEndY(1000);
        line.setStroke(Color.CADETBLUE);
        line.setStrokeWidth(3);
        silButon.setDisable(true);

        dosyaComboBox.getItems().addAll(
                "TEXT",
                "XML"
        );
        dosyaComboBox.setValue("TEXT");
        grafikComboBox.getItems().addAll(
                "Linechart",
                "Barchart"
        );

        grafikComboBox.setValue("Linechart");

        urltext.setDisable(true);
        dosyarb.setDisable(true);
        urlrb.setDisable(true);
        durdurbutton.setDisable(true);
        devametbutton.setDisable(true);


        grid.add(new Label("Dosya Türü Seçiniz: "), 0, 0);
        grid.add(dosyaComboBox, 1, 0);

        grid.add(new Label("Grafik Türü Seçiniz: "), 0, 1);
        grid.add(grafikComboBox, 1, 1);

        grid.add(dosyarb,0,2);
        grid.add(urlrb,1,2);
        grid.add(urltext,0,4,2,1);
        grid.add(dosyaeklebutton,0,3,2,1);
        grid.add(dosyaadi,0,5,2,1);
        dosyaadi.setFont(Font.font("Ariel", FontWeight.SEMI_BOLD,13));

        grid.add(grafikbutton, 0, 17,2,1);
        grid.add(silButon, 0, 20,2,1); 

        grid.add(durdurbutton, 0,18);
        grid.add(devametbutton, 1, 18);



        stage.setScene(scene);
        stage.setTitle("GRAFİK OLUŞTURMA");
        stage.show();

        //eventlar
        //grafik oluşturmaya başlama butonu
        dosyaComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(dosyaComboBox.getValue().toString()=="XML")
                {
                    dosyaeklebutton.setDisable(false);
                    urltext.setDisable(true);
                    dosyarb.setDisable(false);
                    urlrb.setDisable(false);

                }
                else {
                    dosyaeklebutton.setDisable(false);
                    urltext.setDisable(true);
                    dosyarb.setDisable(true);
                    urlrb.setDisable(true);
                    dosyarb.setSelected(true);
                }
                urltext.clear();
                file=null;
                urlcontrol=null;
            }
        });

        urlrb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                dosyaeklebutton.setDisable(true);
                urltext.setDisable(false);
                urltext.clear();
                urlcontrol=null;
            }
        });

        dosyarb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dosyaeklebutton.setDisable(false);
                urltext.setDisable(true);
                urltext.clear();
                urlcontrol=null;
            }
        });

        urltext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                vBox.getChildren().clear();
                ParseXml.fcontroller = false;

                if(urltext==null)
                {
                    dosyaadi.setText("Url yazmadınız");
                }
                else
                {
                    if(urltext.getText().substring(urltext.getText().lastIndexOf(".")+1).equals("xml"))
                    {
                        try {
                            url=new URL(urltext.getText());
                            dosyaadi.setText(url.getFile());
                            urlcontrol=url.getFile();

                        } catch (MalformedURLException e) {
                            dosyaadi.setText("Bu bir Url değil.");
                        }

                    }
                    else
                    {
                        dosyaadi.setText("Hatalı giriş\nLütfen xml formatında bir Url girin.");
                    }

                }
      
            }
        });

        dosyaeklebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                vBox.getChildren().clear();
                ParseXml.fcontroller = false;
                ParseText.tcontroller = false;

                fileChooser = new FileChooser();
                if (dosyaComboBox.getValue().toString() == "TEXT") {
                   
                    file = fileChooser.showOpenDialog(stage);
                    if (file != null) {

                        if(file.toString().substring(file.toString().lastIndexOf(".")+1).equals("txt"))
                        {
                            dosyaadi.setText(file.getName());

                        }
                        else
                        {
                            dosyaadi.setText("Lütfen Text Dosya Seçiniz.");
                            file=null;

                        }
                    }else{
                        dosyaadi.setText("Lütfen Dosya Seçiniz.");
                    }
                } else {
                  
                    file = fileChooser.showOpenDialog(stage);
                    if (file != null) {

                        if(file.getName().substring(file.getName().lastIndexOf(".")+1).equals("xml"))
                        {
                            dosyaadi.setText(file.getName());
 
                        }
                        else
                        {
                            dosyaadi.setText("Lütfen XML Dosya Seçiniz.");
                            file=null;
                        }
                    }
                    else
                        dosyaadi.setText("Lütfen Dosya Seçiniz.");
                }

            }
        });

        grafikComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                file=null;
            }
        });

        grafikbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(dosyarb.isSelected()) {
                    if (file != null) {
                        if (grafikComboBox.getValue().equals("Linechart") && dosyaComboBox.getValue().equals("TEXT")) {

                            vBox.getChildren().addAll(LineChart.stackPane,LineChart.flowPane);

                            lineAnimationText = new LcAnimation();

                            if(!ParseText.tcontroller){

                                devametbutton.setDisable(true);
                                durdurbutton.setDisable(false);

                                lineAnimationText.Text = new ParseText(file.toPath().toString(),grafikComboBox.getValue().toString());

                                try {
    
                                    lineAnimationText.makeHashMapColor();
                                    lineAnimationText.start(stage);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } 

                            }

                            deneme();
                            

                       

                        } else if (grafikComboBox.getValue().equals("Linechart") && dosyaComboBox.getValue().equals("XML")) {
                            vBox.getChildren().addAll(LineChart.stackPane,LineChart.flowPane);
                            
                            lineAnimationXml = new LcAnimationXML();

                            if(!ParseXml.fcontroller){
                                  
                                devametbutton.setDisable(true);
                                durdurbutton.setDisable(false);

                                lineAnimationXml.Xml = new ParseXml(file.toPath().toString(),grafikComboBox.getValue().toString());
                                try {
                                    lineAnimationXml.makeHashMapColor();
                                    lineAnimationXml.start(stage);
    
                                } catch (FileNotFoundException e) {
    
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }


                            deneme();
                            


                        } else if (grafikComboBox.getValue().equals("Barchart") && dosyaComboBox.getValue().equals("TEXT")) {
                           
                            vBox.getChildren().addAll(BarChart.stackPane,BarChart.pane1);
                            
                            barchartAnimation = new BcAnimation();

                            if(!ParseText.tcontroller){

                                devametbutton.setDisable(true);
                                durdurbutton.setDisable(false);

                                barchartAnimation.Test=new ParseText(file.toPath().toString(),grafikComboBox.getValue().toString());
                                try {
                                    barchartAnimation.makeHashMapColor();
                                    barchartAnimation.start(stage);
                                } catch (FileNotFoundException e) {
    
                                    e.printStackTrace();
                                } catch (Exception e) {
                                e.printStackTrace();
                                }


                            }


                            deneme();
                            



                        } else {

                            vBox.getChildren().addAll(BarChart.stackPane,BarChart.pane1);
                            barchartAnimationXml = new BcAnimationXML();

                            if(!ParseXml.fcontroller){

                                devametbutton.setDisable(true);
                                durdurbutton.setDisable(false);
                                
                                barchartAnimationXml.xml = new ParseXml(file.toPath().toString(),grafikComboBox.getValue().toString());
                                try {
                                    barchartAnimationXml.makeHashMapColor();
                                    barchartAnimationXml.start(stage);
                                    dosyaeklebutton.setDisable(true);
    
                                } catch (FileNotFoundException e) {
    
                                    e.printStackTrace();
                                }

                            }

                            deneme();

                        }

                    } else {
                        dosyaadi.setText("Dosya Seçmeden Devam Edemezsiniz");
                    }
                }
                else
                {
                    if(urlcontrol==null)
                    {
                        dosyaadi.setText("Grafik oluşturulabilecek bir Url yok\nveya Enter'a tıklamamış olabilirsiniz.");
                    }
                    else
                    {
                        if(grafikComboBox.getValue().toString().equals("Linechart")){
                            vBox.getChildren().addAll(LineChart.stackPane,LineChart.flowPane);
                            lineAnimationXml = new LcAnimationXML();
                           
                            if(ParseXml.fcontroller == false){

                                devametbutton.setDisable(true);
                                durdurbutton.setDisable(false);

                                lineAnimationXml.Xml = new ParseXml(urltext.getText(),"URL"+grafikComboBox.getValue().toString());
                                try {
                                    
                                    lineAnimationXml.makeHashMapColor();
                                    lineAnimationXml.start(stage);

                                } catch (FileNotFoundException e) {

                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                
                            }

                            deneme();
                            

                            urlcontrol=null;
                        }
                        else{
                            vBox.getChildren().addAll(BarChart.stackPane,BarChart.pane1);

                            barchartAnimationXml = new BcAnimationXML();

                            if(ParseXml.fcontroller == false){
                                
                                devametbutton.setDisable(true);
                                durdurbutton.setDisable(false);

                                barchartAnimationXml.xml = new ParseXml(urltext.getText(),"URL"+grafikComboBox.getValue().toString());
                                try {
                                    barchartAnimationXml.makeHashMapColor();
                                    barchartAnimationXml.start(stage);

                                } catch (FileNotFoundException e) {

                                    e.printStackTrace();
                                }


                            }
                            urlcontrol=null;
                            deneme();
                        }
                        
                    }
                }

                file=null;
                urltext.clear();
                urlcontrol=null;
            }});

            silButon.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent arg0) {

                    silButon.setDisable(true);
                    BarChart.stackPane.getChildren().clear();
                    BarChart.pane1.getChildren().clear();
                    LineChart.stackPane.getChildren().clear();
                    LineChart.flowPane.getChildren().clear();
                    controller=false;
                    dosyaComboBox.setDisable(false);
                    grafikComboBox.setDisable(false);
                    grafikbutton.setDisable(false);
                    dosyaadi.setText(null);
                    bcontrol.clear();
                    lcontrol.clear();
                    
                    if(dosyaComboBox.getValue().equals("XML"))
                    {

                        urlrb.setDisable(false);
                        dosyarb.setDisable(false);
                        if(dosyarb.isSelected())
                        {
                            dosyaeklebutton.setDisable(false);

                        }
                        else
                        {
                            urltext.setDisable(false);
                        }
                    }
                    else
                    {
                        dosyaeklebutton.setDisable(false);
                    }
                }
                 
            });

        //grafiği durdurma butonu
        durdurbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                devametbutton.setDisable(false);
                durdurbutton.setDisable(true);
                
                // Pause chart
                if (grafikComboBox.getValue().equals("Barchart") && dosyaComboBox.getValue().equals("TEXT"))
                {
                    barchartAnimation.timeline.pause();
                }
                else if (grafikComboBox.getValue().equals("Barchart") && dosyaComboBox.getValue().equals("XML")){
                    barchartAnimationXml.timeline.pause();
                }
                else if (grafikComboBox.getValue().equals("Linechart") && dosyaComboBox.getValue().equals("TEXT")){
                    lineAnimationText.animation.pause();
                }
                else if (grafikComboBox.getValue().equals("Linechart") && dosyaComboBox.getValue().equals("XML"))
                {
                    lineAnimationXml.animation.pause();
                }

            }
        });

        //grafiği devam ettirme butonu
        devametbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                devametbutton.setDisable(true);
                durdurbutton.setDisable(false);

                
                // Continue chart
                if (grafikComboBox.getValue().equals("Barchart") && dosyaComboBox.getValue().equals("TEXT"))
                {
                    barchartAnimation.timeline.play();
                }
                else if (grafikComboBox.getValue().equals("Barchart") && dosyaComboBox.getValue().equals("XML")){
                    barchartAnimationXml.timeline.play();
                }
                else if (grafikComboBox.getValue().equals("Linechart") && dosyaComboBox.getValue().equals("TEXT")){
                    lineAnimationText.animation.play();
                }
                else if (grafikComboBox.getValue().equals("Linechart") && dosyaComboBox.getValue().equals("XML"))
                {
                    lineAnimationXml.animation.play();
                }

            }
        });
    }
    public void deneme()
    {
        dosyaComboBox.setDisable(true);
        grafikComboBox.setDisable(true);
        urlrb.setDisable(true);
        dosyarb.setDisable(true);
        urltext.setDisable(true);
        grafikbutton.setDisable(true);
        dosyaeklebutton.setDisable(true);

    }
}