package lab4;

public class Element implements Comparable<Element>{
	private int initialState;
	private int finalState;
	private String symbol;
	public Element(Integer origin, Integer finalState, String symbol) {
		this.initialState =origin;
		this.finalState = finalState;
		this.symbol = symbol;
	}

    public int getInitialState() {
        return initialState;
    }

    public int compareTo(Element e) {
		if(this.initialState <e.initialState)
			return -1;
		else if(this.initialState >e.initialState)
			return 1;
		return 0;
	}

	public int getFinalState() {
		return finalState;
	}

	public String getSymbol() {
		return symbol;
	}


    @Override
    public String toString() {
        return "s"+ initialState +" "+ symbol +" -> s"+ finalState;
    }
}
