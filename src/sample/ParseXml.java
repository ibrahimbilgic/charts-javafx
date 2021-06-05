package sample;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParseXml {

    static ArrayList<Bar> listRecordsBar = new ArrayList<>();
    static ArrayList<Line> listRecordsLine = new ArrayList<>();
    static ArrayList<LocalDate> yearArrayList = new ArrayList<>();
    static ArrayList<String> categoryArrayList = new ArrayList<>();
    ArrayList<Records> comparedRd = new ArrayList<>();
    ArrayList<Records> rdList = new ArrayList<>();

    LocalDate tempYear;
    static String title;
    static String xAxis;
    String name;
    int value;
    String category;
    Line line;
    static int counter=0;
    static int yearCounter;
    static boolean fcontroller = false;
    String country;
    int year;
    String tempName;
    

    public ParseXml(String path,String tur){
        readXml(path,tur);
        if(!fcontroller){
            
            findCategory();
            findYear();
            if(tur.equals("Barchart") || tur.equals("URLBarchart")){
                makeBar();
            }
            else{
                makeLine();
            }
        }

    }
    public void clearAll(){
        listRecordsBar.clear();
        listRecordsLine.clear();
        comparedRd.clear();
        yearArrayList.clear();
        rdList.clear();
        categoryArrayList.clear();
        counter = 0;
        yearCounter = 0;
    }

    public void makeBar(){
        for(int i=0;i<comparedRd.size();i++){
            Bar bar = new Bar(comparedRd.get(i).getName(), comparedRd.get(i).getCategory(), comparedRd.get(i).getValue());
            listRecordsBar.add(bar);
        }
    }

    public void makeLine(){

        for(int i=0;i<rdList.size();i+=yearCounter){
            ArrayList<YearValue> valuesRecord = new ArrayList<>();

            for(int j=i;j<i+yearCounter;j++){
                YearValue yearValue = new YearValue(rdList.get(j).getValue(),rdList.get(j).getYear());
                valuesRecord.add(yearValue);
            }
            Line line = new Line(rdList.get(i).getName(), valuesRecord, rdList.get(i).getCategory());
            listRecordsLine.add(line);
            valuesRecord.clear();
        }


    }

    public void findCategory(){
        categoryArrayList.add(comparedRd.get(0).getCategory());

        for(int i=0;i<comparedRd.size();i++){
            boolean isFind=false;
            if(!(categoryArrayList.get(0).equals(comparedRd.get(i).getCategory()))){
                for(int j=0;j<categoryArrayList.size();j++){
                    if(comparedRd.get(i).getCategory().equals(categoryArrayList.get(j))){
                        isFind = true;
                        break;
                    }

                }
                if(isFind==false){
                    categoryArrayList.add(comparedRd.get(i).getCategory());
                }
            }
        }
    }
    public void findYear(){
        yearArrayList.add(comparedRd.get(0).getYear());

        for(int i=0;i<comparedRd.size();i++){
            boolean isFind=false;
            if(!(yearArrayList.get(0).equals(comparedRd.get(i).getYear()))){
                for(int j=0;j<yearArrayList.size();j++){
                    if(comparedRd.get(i).getYear().equals(yearArrayList.get(j))){
                        isFind = true;
                        break;
                    }

                }
                if(isFind==false){
                    yearArrayList.add(comparedRd.get(i).getYear());
                }
            }
        }
    }

    public void readXml(String path,String tur){

        DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document;
        try {
            builder = factory.newDocumentBuilder();

            if(tur.equals("Linechart") || tur.equals("Barchart")){
                document = builder.parse(new File(path));
            }
            else{
                document = builder.parse(new URL(path).openStream());
            }

            document.getDocumentElement().normalize();

            Element root = document.getDocumentElement();

            title = document.getElementsByTagName("title").item(0).getTextContent(); // title

            xAxis = document.getElementsByTagName("xlabel").item(0).getTextContent(); // xAxis

            tempName = document.getElementsByTagName("field").item(0).getTextContent();
            LocalDate tempFirstYear = LocalDate.of(Integer.parseInt(document.getElementsByTagName("field").item(2).getTextContent()),1,1);

            NodeList nList = document.getElementsByTagName("record");

            for(int i=0;i<nList.getLength();i++){
                Node node = nList.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;

                    name = element.getElementsByTagName("field").item(0).getTextContent();
                    value = Integer.parseInt(element.getElementsByTagName("field").item(3).getTextContent());
                    category = element.getElementsByTagName("field").item(4).getTextContent();
                    country = element.getElementsByTagName("field").item(1).getTextContent();

                    String str = "^\\d{4}-\\d{2}-\\d{2}$";
                    Pattern pattern = Pattern.compile(str);
                    Matcher matcher = pattern.matcher(element.getElementsByTagName("field").item(2).getTextContent());

                    if(matcher.matches()){
                        tempYear= LocalDate.parse(element.getElementsByTagName("field").item(2).getTextContent());
                    }
                    else{
                        tempYear = LocalDate.of(Integer.parseInt(element.getElementsByTagName("field").item(2).getTextContent()),1,1);
                    }


                    Records rd = new Records(tempYear,name,country,value,category);
                    rdList.add(rd);
                }
            }

            for(int x = 0;rdList.get(x).getName().equals(tempName); x++){
                yearCounter++;
                for(int y =0; y<rdList.size();y++){
                    if ((rdList.get(x).getYear().equals(rdList.get(y).getYear()))){
                        comparedRd.add(rdList.get(y));
                    }
                }
            }

            for(int a=0;comparedRd.get(a).getYear().equals(tempFirstYear);a++){
                counter++;
            }
        }
        catch (Exception e) {
            Main.dosyaadi.setText("Hatalı Giriş!");
            Main.urltext.clear();
            LcAnimationXML.chartbitti();
            fcontroller = true;
        }

    }

}