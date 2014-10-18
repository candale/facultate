package lab4;


import java.io.*;
import java.util.*;

public class LR0 {
    private Stack<String> inputSequence = new Stack<String>();
    private List<State> states;
    private LRTable LRTable = new LRTable();
    private Grammar grammar;
    private Stack<Integer> result = new Stack<Integer>();


    public LR0(Grammar grammar){
        this.grammar=grammar;
        this.states=new LinkedList<State>();
    }


    public Stack<String> getInputSequence() {
        return inputSequence;
    }

    public void getInputSequenceFromFile() throws IOException {
        FileInputStream fStream = new FileInputStream("D:\\IdeaProjects\\Compilatoare\\src\\Files\\4.sequence.2.txt");
        DataInputStream in = new DataInputStream(fStream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        List<String> list = new ArrayList<String>();

        while ((strLine = br.readLine()) != null) {
            strLine.trim();
            StringTokenizer stringTokenizer = new StringTokenizer(strLine," ");
            while (stringTokenizer.hasMoreElements()) {
                String stringToken = stringTokenizer.nextElement().toString();
                if (stringToken != null) {
                    list.add(stringToken);
                }
            }
        }
        Integer listSize = list.size();
        for(int i = listSize-1; i >= 0; i--) {
            inputSequence.push(list.get(i));
        }
    }

    public void startInitialPhase(){
        int indexOfSet=0;
        Set<Production> firstSet = new HashSet<Production>();
        Vector<String> res = new Vector<String>();
        State state = new State();

        res.add(grammar.getStartingSymbol());
        Production production = new Production("Y", res, 0,0);
        firstSet.add(production);

        firstSet = closure(firstSet);
        state.setSetProductions(firstSet);
        state.setNumberOfState(indexOfSet++);
        makeActionForState(state);
        states.add(state);

        LRTableRecord vertex=new LRTableRecord(state.getNumberOfState());
        LRTable.addVertex(vertex);


        Queue<State> stateQueue = new LinkedList<State>();
        stateQueue.add(state);

        while(!stateQueue.isEmpty()){
            State currentState = stateQueue.poll();
            for(String symbol:grammar.getAllSymbols()){
                State newState=goTo(currentState,symbol);
                makeActionForState(newState);
                if(newState!=null && !newState.getSetProductions().isEmpty() && !states.contains(newState)){
                    stateQueue.add(newState);
                    newState.setNumberOfState(indexOfSet++);
                    states.add(newState);
                    this.addStateToTable(newState,currentState,symbol);

                } else{
                    if(newState!=null && !newState.getSetProductions().isEmpty() ){
                        State newState2=this.getStateAtIndex(newState);
                        this.addStateToTableOldVertex(newState2, currentState, symbol);
                    }
                }
            }
        }
    }

    private void addStateToTable(State newState,State currentState,String symbol) {
        LRTableRecord vertex=new LRTableRecord(newState.getNumberOfState());
        LRTable.addVertex(vertex);

        if(LRTable.getTargetStateForSymbol(currentState.getNumberOfState(), symbol)!=-1){
            System.out.println("\n Two entries in the LR(0) table for state "+ currentState.getNumberOfState() +"and symbol " +symbol );
        }
        else{
            LRTable.addEdge(currentState,newState,symbol);
        }
    }

    private void addStateToTableOldVertex(State newState,State currentState,String symbol) {
        LRTableRecord vertex=LRTable.getVertexIndexWithIndex(newState.getNumberOfState());
        LRTable.addVertex(vertex);
        if(LRTable.getTargetStateForSymbol(currentState.getNumberOfState(), symbol)!=-1){
            System.out.println("\n Two entries in the LR(0) table for state "+ currentState.getNumberOfState() +"and symbol " +symbol );
        }
        else{
            LRTable.addEdge(currentState,newState,symbol);
        }
    }

    public Set<Production> closure(Set<Production> productions){
        Set<Production> newStateProductions=new HashSet<Production>();

        for(Production production:productions){
            String symbol=production.getSymbolAfterPoint();
            if(symbol!=null && grammar.isNonTerminal(symbol)){
                Set<Production> newProductions =grammar.getProductionsWithAGivenNonTerminal(symbol);
                for(Production productionInner:newProductions) {
                    if(!productions.contains(productionInner)){
                        newStateProductions.add(productionInner);
                    }
                }
                newStateProductions=closure(newStateProductions);
            }
        }
        newStateProductions.addAll(productions);
        return newStateProductions;
    }

    public State goTo(State state,String symbol){
        Set<Production> listForClosure=new HashSet<Production>();
        for(Production production:state.getSetProductions()){
            if(production.getSymbolAfterPoint()!=null && production.getSymbolAfterPoint().equals(symbol)){
                Production newProd=new Production();
                newProd.setNonTerminal(production.getNonTerminal());
                newProd.setPosition(production.getPosition() + 1);
                newProd.setResults(production.getResults());
                newProd.setProductionNumber(production.getProductionNumber());
                listForClosure.add(newProd);
            }
        }

        State newState=new State();
        newState.setSetProductions(closure(listForClosure));

        return newState;
    }

    public State getStateAtIndex(State state){
        Iterator<State> iterator=states.iterator();
        while(iterator.hasNext()){
            State stateInner=iterator.next();
            if (stateInner.equals(state)){
                return stateInner;
            }
        }
        return null;
    }

    public void makeActionForState(State newState){
        if(newState.stateIsAccept(grammar.getStartingSymbol())){
            newState.setAction("accept");
        }
        else{
            int stateIsReduce=newState.stateIsReduce(grammar.getProductions());
            if(stateIsReduce!=-1){
                newState.setAction(Integer.toString(stateIsReduce));
            }
            else{
                if(newState.stateIsShiftReduce()){
                    System.out.println(" A State " +"is shift reduce state. Table can`t be generated for LR(0) "+newState.toString());
                    System.exit(0);
                } else{
                    if(newState.getNumberProductions()!=0&&newState.stateIsReduceReduce()){
                        System.out.println(" A State " +"is reduce reduce state. Table can`t be generated for LR(0) "+newState.toString());
                        System.exit(0);
                    }
                    else{
                        newState.setAction("shift");
                    }

                }


            }
        }
    }

    public List<State> getStates() {
        return states;
    }

    public void printAllStates(){
        for(State state:this.states){
            System.out.println(state.toString());
        }
    }

    public void printGraph(LR0 lr0){
        LRTable.printTable(lr0);
    }

    private State getNextStateFromTable(State state, String symbol) {
        int numberOfState=LRTable.getTargetStateForSymbol(state.getNumberOfState(), symbol);
        if(numberOfState==-1){
            return null;
        }
        else{
            return states.get(numberOfState);
        }
    }

    public boolean parseSequence() throws IOException {
        Stack<StateSymbol> stateSymbols = new Stack<StateSymbol>();
        inputSequence.push("$");
        getInputSequenceFromFile();


        StateSymbol initial = new StateSymbol();
        initial.setState(states.get(0));
        initial.setSymbol("");
        stateSymbols.push(initial);

        while (!inputSequence.isEmpty()) {
            if (stateSymbols.peek().getState().getAction().equals("shift")) {
                StateSymbol next = new StateSymbol();
                next.setSymbol(inputSequence.pop());
                if (!next.getSymbol().equals("$")) {

                    if (getNextStateFromTable(stateSymbols.peek().getState(), next.getSymbol()) != null) {
                        next.setState(getNextStateFromTable(stateSymbols.peek().getState(), next.getSymbol()));
                        stateSymbols.push(next);
                    }
                }
            } else {

                if (stateSymbols.peek().getState().getAction().equals("accept") && inputSequence.peek().equals("$")) {
                    System.out.println("\nSequence accepted!\n");
                    System.out.println("Productions string: ");
                    for (int i = result.size() - 1; i >= 0; i--) {
                        System.out.print(result.get(i));
                    }
                    return true;

                } else {
                    Integer reduceIndex = Integer.parseInt(stateSymbols.peek().getState().getAction());
                    Production reduceProduction = grammar.getListProductions().get(reduceIndex-1);

                    result.push(reduceIndex);

                    for (int i = 0; i < reduceProduction.getResults().size(); i++) {
                        stateSymbols.pop();
                    }

                    StateSymbol next = new StateSymbol();
                    next.setSymbol(reduceProduction.getNonTerminal());
                    if(getNextStateFromTable(stateSymbols.peek().getState(), reduceProduction.getNonTerminal())!=null)  {
                        next.setState(getNextStateFromTable(stateSymbols.peek().getState(), reduceProduction.getNonTerminal()));
                    } else {
                        System.out.println("Sequence rejected!");
                        return false;
                    }
                    stateSymbols.push(next);
                }
            }

            System.out.print("$");
            for (StateSymbol s: stateSymbols) {
                System.out.print(s.getSymbol() + " " + "s" + s.getState().getNumberOfState() + " ");
            }
            System.out.print("                        " );
            for (int i = inputSequence.size() - 1; i >= 0; i--) {
                System.out.print(inputSequence.get(i));
            }
            System.out.println("");
        }
        System.out.println("Sequence rejected!");
        return false;
    }

}
