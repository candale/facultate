package lab4;
import java.util.Vector;

public class Production {

    private String nonTerminal;
    private Vector<String> results;
    private int productionNumber;
    private int position;

    public Production(String nonTerminal, Vector<String> results, int  position, int prodNo) {
        this.nonTerminal = nonTerminal;
        this.results = results;
        this.position = position;
        this.productionNumber=prodNo;
    }

    public Production()
    {
        this.results=new Vector<String>();
    }

    public String getNonTerminal() {
        return nonTerminal;
    }

    public void setNonTerminal(String nonTerminal) {
        this.nonTerminal = nonTerminal;
    }

    public Vector<String> getResults() {
        return results;
    }

    public void setResults(Vector<String> results) {
        this.results = results;
    }

    public int getProductionNumber() {
        return productionNumber;
    }

    public void setProductionNumber(int productionNumber) {
        this.productionNumber = productionNumber;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void addResult(String result) {
        this.results.add(result);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int index=0;
        boolean pointAdded=false;
        for (String result: results) {
            if(index==position){
                stringBuilder.append(".");
                pointAdded=true;
            }
            index++;
            stringBuilder.append(result);
        }
        if(pointAdded==false){
            stringBuilder.append(".");
        }

        return this.nonTerminal +"->"+stringBuilder;
    }

    public boolean hasResult(String result) {
        for (String resultFromList: results) {
            if (resultFromList.equals(result)) {
                return true;
            }
        }
        return false;
    }

    public String getSymbolAfterPoint() {
        if(position<results.size()){
            return this.getResults().get(position);
        }
        else{
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Production)) return false;

        Production that = (Production) o;

        if (position != that.position) return false;
        if (!nonTerminal.equals(that.nonTerminal)) return false;
        if (!results.equals(that.results)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nonTerminal.hashCode();
        result = 31 * result + results.hashCode();
        result = 31 * result + position;
        return result;
    }
}

