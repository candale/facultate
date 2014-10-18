package lab4;


import java.util.*;

public class LRTable {
    private Map<Integer,LRTableRecord> records =new HashMap<Integer, LRTableRecord>();

    public void addVertex(LRTableRecord vertex) {
        records.put(vertex.getOrigin(), vertex);
    }

    public void addEdge(State currentState, State newState, String symbol) {
        LRTableRecord current= records.get(currentState.getNumberOfState());
        LRTableRecord  newR= records.get(newState.getNumberOfState());
        current.addOutbound(new Element(current.getOrigin(), newR.getOrigin(), symbol));
    }

    public void printTable(LR0 lr0){
        List<LRTableRecord> list = new ArrayList<LRTableRecord>(records.values());
        for(LRTableRecord record:list){
            boolean ok = false;
            try{
                int val = Integer.parseInt(lr0.getStates().get(record.getOrigin()).getAction());
            }
            catch (Exception e){
                ok = true;
            }
            if(ok){
                System.out.println("s" + record.getOrigin() + " action: " + lr0.getStates().get(record.getOrigin()).getAction());
            }
            else{
                System.out.println("s" + record.getOrigin() + " action: reduce(" + lr0.getStates().get(record.getOrigin()).getAction()+")");
            }
            for(Element element:record.getOutbounds()){
                System.out.println("   "+element);
            }
            System.out.println();

        }
    }

    public LRTableRecord getVertexIndexWithIndex(int numberOfState) {
        return this.records.get(numberOfState);
    }

    public int getTargetStateForSymbol(int numberOfState, String symbol) {
        Set<Element> list=this.records.get(numberOfState).getOutbounds();
        for(Element edge:list){
            if(edge.getSymbol().equals(symbol)){
                return edge.getFinalState();
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return "DirectedGraph{" +
                "records=" + records +
                '}';
    }

}
