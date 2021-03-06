package lab4;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class State {

    private Set<Production> setProductions;
    private int numberOfState;
    private String action=null;

    public State() {
    }

    public State(State currentState) {
        setProductions=new HashSet<Production>();
    }

    public int getNumberOfState() {
        return numberOfState;
    }

    public void setNumberOfState(int numberOfState) {
        this.numberOfState = numberOfState;
    }

    public void addProduction(Production production) {
        setProductions.add(production);
    }

    public int getNumberProductions() {
        return setProductions.size();
    }

    public Set<Production> getSetProductions() {
        return setProductions;
    }

    public void setSetProductions(Set<Production> setProductions) {
        this.setProductions = setProductions;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state = (State) o;

        if (setProductions != null ? !setProductions.equals(state.setProductions) : state.setProductions != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return setProductions != null ? setProductions.hashCode() : 0;
    }

    public Object clone(){
        try {
           return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public boolean stateIsAccept(String startingSymbol){
        if(this.hasOneProduction()){
            Production firstProduction=getFirstProduction();
            if(firstProduction.getNonTerminal().equals("Y") && firstProduction.getResults().get(0).equals(startingSymbol) && firstProduction.getPosition()==1){
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public int stateIsReduce(Set<Production> grammarProductions){
           int productionNumber=-1;
           if(this.hasOneProduction() && this.allProductionsHavePointAtLastPosition()){
               productionNumber=this.getFirstProduction().getProductionNumber();
           }
        return productionNumber;
    }

    private boolean allProductionsHavePointAtLastPosition() {
        boolean flag=true;
        for(Production production:setProductions){
            if(production.getSymbolAfterPoint()!=null){
                flag=false;
            }
        }
        return flag;
    }

    public boolean stateIsShiftReduce(){
        if(getNumberProductions()>1 && hasOneFinalStateProduction())
        {
          return true;
        }
          return false;


    }

    private boolean hasOneFinalStateProduction() {
        for(Production production:setProductions){
            if(production.getSymbolAfterPoint()==null){
                return true;
            }
        }
        return false;
    }

    public boolean hasOneProduction(){
        return (this.getNumberProductions()==1);
    }

    public Production getFirstProduction(){
        Iterator iterator=setProductions.iterator();
        return (Production) iterator.next();
    }

    @Override
    public String toString() {
        return "s" +  numberOfState +
                " = { " + setProductions+" } ";
    }

    public boolean stateIsReduceReduce() {
        if(allProductionsHavePointAtLastPosition()){
            return true;  //To change body of created methods use File | Settings | File Templates.
        }
        return false;
    }
}
