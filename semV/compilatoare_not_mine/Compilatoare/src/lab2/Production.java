package lab2;

import java.util.Vector;

public class Production {

    private String nonTerminal;
    private String result;

    public Production(String nonTerminal, String result) {
        this.nonTerminal = nonTerminal;
        this.result = result;
    }

    public String getNonTerminal() {
        return nonTerminal;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "(" + nonTerminal +
                " " + result +
                ')';
    }
}