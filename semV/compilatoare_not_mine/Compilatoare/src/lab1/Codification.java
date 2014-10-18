package lab1;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


public class Codification {

    private SortedMap<String,Integer> codificationList;

    public Codification(){
        codificationList = new TreeMap<String,Integer>();
        codificationList.put("Constant",3);
        codificationList.put("var",4);
        codificationList.put("int",5);
        codificationList.put("char",6);
        codificationList.put("bool",7);
        codificationList.put("struct",8);
        codificationList.put("start",9);
        codificationList.put("stop",10);
        codificationList.put("input",11);
        codificationList.put("outout",12);
        codificationList.put("exec",13);
        codificationList.put("endexec",14);
        codificationList.put("if",15);
        codificationList.put("else",16);
        codificationList.put("for",17);
        codificationList.put("from",18);
        codificationList.put("to",19);
        codificationList.put("=",20);
        codificationList.put("==",21);
        codificationList.put("!=",22);
        codificationList.put("<",23);
        codificationList.put(">",24);
        codificationList.put("<=",25);
        codificationList.put(">=",26);
        codificationList.put("+",27);
        codificationList.put("-",28);
        codificationList.put("*",29);
        codificationList.put("/",30);
        codificationList.put(";",31);
        codificationList.put("(",32);
        codificationList.put(")",33);

    }

    public Map<String, Integer> getCodificationList() {
        return codificationList;
    }

//    public static void main(String[] args){
//        Codification codification1 = new Codification();
//        System.out.println(codification1.getCodificationList().get("if"));
//    }
}
