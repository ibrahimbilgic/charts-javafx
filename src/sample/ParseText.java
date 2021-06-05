package sample;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseText {

    static ArrayList<Bar> listRecordsBar = new ArrayList<>();
    static ArrayList<Line> listRecordsLine = new ArrayList<>();
    static ArrayList<String> listTemp = new ArrayList<>();
    static ArrayList<Integer> tempRecordGroup = new ArrayList<>();
    static ArrayList<LocalDate> recordYear = new ArrayList<>();
    static ArrayList<LocalDate> yearArrayList = new ArrayList<>();
    static ArrayList<Records> listRecords = new ArrayList<>();
    static ArrayList<String> nameLineArrayList = new ArrayList<>();
    static ArrayList<String> categoryLineArrayList = new ArrayList<>();
    static ArrayList<String> categoryArrayList = new ArrayList<>();

    LocalDate tempYear;
    static int i = 2;
    static String title;
    static String xAxis;
    static boolean tcontroller = false;

    public ParseText(String path,String tur){
        readTxt(path,tur);
        
        if(!tcontroller){
            findCategories();
            findYear();
    
            if(tur.equals("Barchart")){
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
        tempRecordGroup.clear();
        recordYear.clear();
        listRecords.clear();
        nameLineArrayList.clear();
        categoryLineArrayList.clear();
        categoryArrayList.clear();
        yearArrayList.clear();
    }
    public static int findMaxValue(){
        Collections.sort(listRecords);
        Collections.reverse(listRecords);
        return listRecords.get(0).getValue();
    }
    public void makeBar(){
        for(int i=0;i<listRecords.size();i++){
            Bar bar = new Bar(listRecords.get(i).getName(), listRecords.get(i).getCategory(), listRecords.get(i).getValue());
            listRecordsBar.add(bar);
        }
    }

    public void makeLine(){
        findName();

        for(int i=0;i<nameLineArrayList.size();i++){
            boolean findCategory=false;
            ArrayList<YearValue> valuesRecord = new ArrayList<>();
            for(int j=0;j<listRecords.size();j++){
                if(nameLineArrayList.get(i).equals(listRecords.get(j).getName())){
                    YearValue yearValue = new YearValue(listRecords.get(j).getValue(),listRecords.get(j).getYear());
                    valuesRecord.add(yearValue);
                    if(findCategory == false){
                        categoryLineArrayList.add(listRecords.get(j).getCategory());
                        findCategory = true;
                    }
                }

            }
            Line line = new Line(nameLineArrayList.get(i), valuesRecord, categoryLineArrayList.get(i));
            listRecordsLine.add(line);
        }

    }
    public void findName(){

        nameLineArrayList.add(listRecords.get(0).getName());

        for(int i=0;i<listRecords.size();i++){
            boolean isFind=false;
            if(!(nameLineArrayList.get(0).equals(listRecords.get(i).getName()))){
                for(int j=0;j<nameLineArrayList.size();j++){
                    if(listRecords.get(i).getName().equals(nameLineArrayList.get(j))){
                        isFind = true;
                        break;
                    }

                }
                if(isFind==false){
                    nameLineArrayList.add(listRecords.get(i).getName());
                }
            }
        }
    }

    public void findYear(){
        yearArrayList.add(recordYear.get(0));

        for(int i=0;i<recordYear.size();i++){
            boolean isFind=false;
            if(!(yearArrayList.get(0).equals(recordYear.get(i)))){
                for(int j=0;j<yearArrayList.size();j++){
                    if(recordYear.get(i).equals(yearArrayList.get(j))){
                        isFind = true;
                        break;
                    }

                }
                if(isFind==false){
                    yearArrayList.add(recordYear.get(i));
                }
            }
        }
    }

    public void findCategories(){
        categoryArrayList.add(listRecords.get(0).getCategory());

        for(int i=0;i<listRecords.size();i++){
            boolean isFind=false;
            if(!(categoryArrayList.get(0).equals(listRecords.get(i).getCategory()))){
                for(int j=0;j<categoryArrayList.size();j++){
                    if(listRecords.get(i).getCategory().equals(categoryArrayList.get(j))){
                        isFind = true;
                        break;
                    }
                }
                if(isFind==false){
                    categoryArrayList.add(listRecords.get(i).getCategory());
                }
            }
        }
    }

    public void readTxt(String path,String tur){
        File file = new File(path);
        Scanner scan;
        try {
            scan = new Scanner(file);

            String line;
            title = scan.nextLine();
            xAxis = scan.nextLine();

            while(scan.hasNext()){
                line = scan.nextLine();
                if(line.length()>0){
                    if(!line.contains(",")){
                        tempRecordGroup.add(Integer.parseInt(line));
                        continue;
                    }

                    listTemp.add(line);
                    String dizi[] = listTemp.get(i-2).split(",");

                    String str = "^\\d{4}-\\d{2}-\\d{2}$";
                    Pattern pattern = Pattern.compile(str);
                    Matcher matcher = pattern.matcher(dizi[0]);

                    if(matcher.matches()){
                        tempYear= LocalDate.parse(dizi[0]);
                    }
                    else{
                        tempYear = LocalDate.of(Integer.parseInt(dizi[0]),1,1);
                    }

                    recordYear.add(tempYear);

                    String tempname = dizi[1];
                    String tempCountry = dizi[2];
                    int tempValue = Integer.parseInt(dizi[3]);
                    String tempCategory = dizi[4];

                    Records rd = new Records(tempYear, tempname, tempCountry, tempValue, tempCategory);
                    listRecords.add(rd);

                    i++;
                }
                else{
                    continue;
                }

            }
            scan.close();

        } catch (Exception e) {
            Main.dosyaadi.setText("Hatalı Giriş!");            
            LcAnimationXML.chartbitti();
            tcontroller = true;
        }
    }

}