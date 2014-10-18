package lab1;


import java.util.Arrays;

public class SortedTable {
    private Integer size;
    private String values[];
   // private Integer keys[];

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

//    public Integer[] getKeys() {
//        return keys;
//    }

    public SortedTable(){
        size = 0;
        values = new String[20];
        //keys = new Integer[5];
    }

    public Boolean contains(String elem){
        for(int i=0; i<size;i++){
            if(values[i].compareTo(elem)==0){
                return true;
            }
        }
        return false;
    }

    public Integer getPosition(String elem){
        for(int i=0;i<values.length;i++){
            if(values[i].compareTo(elem)==0)
                return i+1;
        }
        return -1;
    }

    public String getElement(Integer poz){
        for(int i=0;i<values.length;i++){
            if(i == poz){
                return values[i];
            }
        }
        return null;
    }

//    public Integer getKey(Integer poz){
//        for(int i=0;i<keys.length;i++){
//            if(i == poz){
//                return keys[i];
//            }
//        }
//        return null;
//    }

    public void setValue(Integer poz, String elem){
        values[poz] = elem;
        //keys[poz] = key;
    }

    public void add(String elem){
        int k=0;
        if(size!=0){
            if(!contains(elem)){
                int i = size;
                while(i>0){
                    if(values[i-1].compareTo(elem)>0){
                        setValue(i,values[i-1]);
                        i--;
                    } else {
                        k=i;
                        break;
                    }
                }
                setValue(k,elem);
                size++;
            }
        }
        else {
            values[size] = elem;
            //keys[size] = key;
            size++;
        }

    }

    @Override
    public String toString() {
        return "SortedTable{" +
                "size=" + size +
                ", values=" + (values == null ? null : Arrays.asList(values)) +
                '}';
    }
}
