package lab1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

public class WriteFile {

    public static void outputST(String filename, SortedTable sortedTable){
        try{
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("Value  Key \n");
            for(int i=0;i<sortedTable.getSize();i++){
                out.write(i+"    "+sortedTable.getElement(i)+"\n");
            }
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void outputPIF(String filename, Map<Integer,Pair> pif){
        try{
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("Nr. crt |  Code   Location \n");
            for(Map.Entry entry: pif.entrySet()){
                if((Integer)entry.getKey()<10){
                    out.write(entry.getKey().toString() + "       |" );
                } else
                {
                    out.write(entry.getKey().toString() + "      |" );
                }
                Pair pair = (Pair)entry.getValue();
                out.write("   " + pair.getCode() + "        ");
                out.write(pair.getLocation() + "\n");
            }
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void outputError(String filename, List<String> errorList){
        try{
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("Error list: \n");
            for(String s: errorList){
                out.write(s + "\n");
            }
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
