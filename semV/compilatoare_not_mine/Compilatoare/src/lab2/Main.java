package lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    private static Set<String> nonTerminals = new HashSet<String>();
    private static Set<String> terminals = new HashSet<String>();
    private static List<Production> productions = new ArrayList<Production>();
    private static StringBuilder startSymbol = new StringBuilder();

    private static Set<String> states = new HashSet<String>();
    private static Set<String> alphabet = new HashSet<String>();
    private static List<Element> transitionFunction = new ArrayList<Element>();
    private static StringBuilder startState = new StringBuilder();
    private static Set<String> finalStates = new HashSet<String>();

    static  Operations operations = new Operations();

    public static void fromRegularGrammarToFiniteAutomata() {
        String k = "k";
        states.addAll(nonTerminals);
        states.add(k);
        alphabet.addAll(terminals);
        startState = startSymbol;
        finalStates = operations.getFinalStatesFromGrammar(productions);
        finalStates.add(k);
        for (int i = 0; i < productions.size(); i++) {
            if (operations.makeTransitions(productions.get(i).getResult(), terminals).get(1).equals("")) {
                Element tri = new Element(productions.get(i).getNonTerminal(), k, operations.makeTransitions(productions.get(i).getResult(), terminals).get(0));
                transitionFunction.add(tri);
            } else {
                Element tri = new Element(productions.get(i).getNonTerminal(), operations.makeTransitions(productions.get(i).getResult(), terminals).get(1), operations.makeTransitions(productions.get(i).getResult(), terminals).get(0));
                transitionFunction.add(tri);
            }
        }

    }

    public static void fromFiniteAutomataToRegularGrammar() {
        nonTerminals = states;
        terminals = alphabet;
        startSymbol = startState;
        for (int i = 0; i < transitionFunction.size(); i++) {
            Production production;
            Production production2 = null;
            if (finalStates.contains(transitionFunction.get(i).getTo())) {
                production = new Production(transitionFunction.get(i).getFrom(), transitionFunction.get(i).getCost());
                production2 = new Production(transitionFunction.get(i).getFrom(), transitionFunction.get(i).getCost() + transitionFunction.get(i).getTo());

            } else {
                production = new Production(transitionFunction.get(i).getFrom(), transitionFunction.get(i).getCost() + transitionFunction.get(i).getTo());

            }
            productions.add(production);
            if(production2 != null){
                productions.add(production2);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Give your choice: ");
        System.out.println("1. REGULAR GRAMMAR");
        System.out.println("2. FINITE AUTOMATA");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String opt1 = null;
        try {
            opt1 = br.readLine();
        } catch (IOException e) {
            System.out.println("Couldn't read the option !");
        }
        try {
            Integer opt1n = Integer.parseInt(opt1);
            switch (opt1n) {
                case 1: {
                    System.out.println("Give your choice: ");
                    System.out.println("1.Read grammar from file(Files/regularGrammar.txt)");
                    System.out.println("2.Read grammar from keyboard");
                    br = new BufferedReader(new InputStreamReader(System.in));
                    String opt21 = null;
                    try {
                        opt21 = br.readLine();
                    } catch (IOException e) {
                        System.out.println("Couldn't read the option !");
                    }
                    try {
                        Integer opt21n = Integer.parseInt(opt21);
                        switch (opt21n) {
                            case 1: {
                                ReadRegularGrammar.readRegularGrammarFromFile(nonTerminals, terminals, productions, startSymbol);
                                System.out.println("Give your choice: ");
                                System.out.println("1.Display set of non-terminals");
                                System.out.println("2.Display set of terminals");
                                System.out.println("3.Display set of productions");
                                System.out.println("4.Display the productions of a non-terminal symbol");
                                System.out.println("5.Verify if the grammar is regular");
                                System.out.println("6.Construct the corresponding finite automaton");
                                System.out.println("To exit press 0");
                                br = new BufferedReader(new InputStreamReader(System.in));
                                String opt3 = null;
                                Integer opt3n = -1;
                                while(opt3n != 0){
                                    try {
                                        opt3 = br.readLine();
                                    } catch (IOException e) {
                                        System.out.println("Couldn't read the option !");
                                    }
                                    try{
                                        opt3n = Integer.parseInt(opt3);
                                        switch (opt3n){
                                            case 1:{
                                                System.out.println("Set of non-terminals: " + nonTerminals);
                                                break;
                                            }
                                            case 2:{
                                                System.out.println("Set of terminals: " + terminals.toString());
                                                break;
                                            }
                                            case 3:{
                                                System.out.println("Set of productions: " + productions.toString());
                                                break;
                                            }
                                            case 4:{
                                                System.out.println("Give the non-terminal symbol:");
                                                BufferedReader brn = new BufferedReader(new InputStreamReader(System.in));
                                                String symbol = null;
                                                try {
                                                    symbol = brn.readLine();
                                                } catch (IOException e) {
                                                    System.out.println("Couldn't read the option!!!");
                                                }
                                                System.out.println("The set of productions is: "+operations.getProductionsOfGivenSymbol(symbol,productions));
                                                break;
                                            }
                                            case 5:{
                                                if(operations.isRegularGrammar(productions,nonTerminals,terminals)){
                                                    System.out.println("Regular Grammar !");
                                                }
                                                else {
                                                    System.out.println("Not a regular grammar !");
                                                }
                                                break;
                                            }
                                            case 6:{
                                                if(operations.isRegularGrammar(productions,nonTerminals,terminals)){
                                                    fromRegularGrammarToFiniteAutomata();
                                                    System.out.println("Corresponding finite automaton: ");
                                                    System.out.println("States: " + states);
                                                    System.out.println("Alphabet: "+alphabet);
                                                    System.out.println("Transition Function: " + transitionFunction);
                                                    System.out.println("Initial State: "+startState);
                                                    System.out.println("Final States: "+finalStates);
                                                }
                                                else {
                                                    System.out.println("Sorry, not a regular grammar !");
                                                }
                                                break;
                                            }
                                            default: {
                                                System.out.println("Option not available!");
                                            }
                                        }
                                    } catch (Exception e){
                                        System.out.println("You must introduce a number !");
                                    }
                                }
                                break;
                            }
                            case 2: {
                                ReadRegularGrammar.readRegularGrammarFromKeyboard(nonTerminals, terminals, productions, startSymbol);
                                System.out.println("Give your choice: ");
                                System.out.println("1.Display set of non-terminals");
                                System.out.println("2.Display set of terminals");
                                System.out.println("3.Display set of productions");
                                System.out.println("4.Display the productions of a non-terminal symbol");
                                System.out.println("5.Verify if the grammar is regular");
                                System.out.println("6.Construct the corresponding finite automaton");
                                System.out.println("To exit press 0");
                                br = new BufferedReader(new InputStreamReader(System.in));
                                String opt3 = null;
                                Integer opt3n = -1;
                                while(opt3n != 0){
                                    try {
                                        opt3 = br.readLine();
                                    } catch (IOException e) {
                                        System.out.println("Couldn't read the option !");
                                    }
                                    try{
                                        opt3n = Integer.parseInt(opt3);
                                        switch (opt3n){
                                            case 1:{
                                                System.out.println("Set of non-terminals: "+nonTerminals);
                                                break;
                                            }
                                            case 2:{
                                                System.out.println("Set of terminals: "+terminals);
                                                break;
                                            }
                                            case 3:{
                                                System.out.println("Set of productions: "+productions);
                                                break;
                                            }
                                            case 4:{
                                                System.out.println("Give the non-terminal symbol:");
                                                BufferedReader brn = new BufferedReader(new InputStreamReader(System.in));
                                                String symbol = null;
                                                try {
                                                    symbol = brn.readLine();
                                                } catch (IOException e) {
                                                    System.out.println("Couldn't read the option!!!");
                                                }
                                                System.out.println("The set of productions is: "+operations.getProductionsOfGivenSymbol(symbol,productions));
                                                break;
                                            }
                                            case 5:{
                                                if(operations.isRegularGrammar(productions,nonTerminals,terminals)){
                                                    System.out.println("Regular Grammar !");
                                                }
                                                else {
                                                    System.out.println("Not a regular grammar !");
                                                }
                                                break;
                                            }
                                            case 6:{
                                                if(operations.isRegularGrammar(productions,nonTerminals,terminals)){
                                                    fromRegularGrammarToFiniteAutomata();
                                                    System.out.println("Corresponding finite automaton: ");
                                                    System.out.println("States: "+states);
                                                    System.out.println("Alphabet: "+alphabet);
                                                    System.out.println("Transition Function: "+transitionFunction);
                                                    System.out.println("Initial State: "+startState);
                                                    System.out.println("Final States: "+finalStates);
                                                }
                                                else {
                                                    System.out.println("Sorry, not a regular grammar !");
                                                }
                                                break;
                                            }
                                            default: {
                                                System.out.println("Option not available!");
                                            }
                                        }
                                    } catch (Exception e){
                                        System.out.println("You must introduce a number !");
                                    }
                                }
                                break;
                            }
                            default: {
                                System.out.println("Option not available!");
                            }

                        }
                    } catch (Exception e) {
                        System.out.println("You must introduce a number !");
                    }
                    break;
                }

                case 2: {
                    System.out.println("Give your choice: ");
                    System.out.println("1.Read finite automata from file(Files/finiteAutomata.txt)");
                    System.out.println("2.Read finite automata from keyboard");
                    br = new BufferedReader(new InputStreamReader(System.in));
                    String opt22 = null;
                    try {
                        opt22 = br.readLine();
                    } catch (IOException e) {
                        System.out.println("Couldn't read the option !");
                    }
                    try {
                        Integer opt22n = Integer.parseInt(opt22);
                        switch (opt22n) {
                            case 1: {
                                ReadFiniteAutomata.readFiniteAutomataFromFile(states,alphabet,transitionFunction,startState,finalStates);
                                System.out.println("Give your choice: ");
                                System.out.println("1.Display set of states");
                                System.out.println("2.Display the alphabet");
                                System.out.println("3.Display all the transitions");
                                System.out.println("4.Display the set of final states");
                                System.out.println("5.Construct the corresponding regular grammar");
                                System.out.println("To exit press 0");
                                br = new BufferedReader(new InputStreamReader(System.in));
                                String opt3 = null;
                                Integer opt3n = -1;
                                while(opt3n != 0){
                                    try {
                                        opt3 = br.readLine();
                                    } catch (IOException e) {
                                        System.out.println("Couldn't read the option !");
                                    }
                                    try{
                                        opt3n = Integer.parseInt(opt3);
                                        switch (opt3n){
                                            case 1:{
                                                System.out.println("Set of states: "+states);
                                                break;
                                            }
                                            case 2:{
                                                System.out.println("Alphabet: "+alphabet);
                                                break;
                                            }
                                            case 3:{
                                                System.out.println("Transitions: "+transitionFunction);
                                                break;
                                            }
                                            case 4:{
                                                System.out.println("Set of final states: "+finalStates);
                                                break;
                                            }
                                            case 5:{
                                                System.out.println("Corresponding regular grammar: ");
                                                fromFiniteAutomataToRegularGrammar();
                                                System.out.println("Non-terminals: "+nonTerminals);
                                                System.out.println("Terminals: "+terminals);
                                                System.out.println("Productions: "+productions);
                                                System.out.println("Starting Symbol: "+startSymbol);
                                                break;
                                            }
                                            default: {
                                                System.out.println("Option not available!");
                                            }
                                        }
                                    } catch (Exception e){
                                        System.out.println("You must introduce a number !");
                                    }
                                }
                                break;
                            }
                            case 2: {
                                ReadFiniteAutomata.readFiniteAutomataFromKeyboard(states, alphabet, transitionFunction, startState, finalStates);
                                System.out.println("Give your choice: ");
                                System.out.println("1.Display set of states");
                                System.out.println("2.Display the alphabet");
                                System.out.println("3.Display all the transitions");
                                System.out.println("4.Display the set of final states");
                                System.out.println("5.Construct the corresponding regular grammar");
                                System.out.println("To exit press 0");
                                br = new BufferedReader(new InputStreamReader(System.in));
                                String opt3 = null;
                                Integer opt3n = -1;
                                while(opt3n != 0){
                                    try {
                                        opt3 = br.readLine();
                                    } catch (IOException e) {
                                        System.out.println("Couldn't read the option !");
                                    }
                                    try{
                                        opt3n = Integer.parseInt(opt3);
                                        switch (opt3n){
                                            case 1:{
                                                System.out.println("Set of states: "+states);
                                                break;
                                            }
                                            case 2:{
                                                System.out.println("Alphabet: "+alphabet);
                                                break;
                                            }
                                            case 3:{
                                                System.out.println("Transitions: "+transitionFunction);
                                                break;
                                            }
                                            case 4:{
                                                System.out.println("Final States: "+finalStates);
                                                break;
                                            }
                                            case 5:{
                                                System.out.println("Corresponding regular grammar: ");
                                                fromFiniteAutomataToRegularGrammar();
                                                System.out.println("Non-terminals: "+nonTerminals);
                                                System.out.println("Terminals: "+terminals);
                                                System.out.println("Productions: "+productions);
                                                System.out.println("Starting Symbol: "+startSymbol);
                                                break;
                                            }
                                            default: {
                                                System.out.println("Option not available!");
                                            }
                                        }
                                    } catch (Exception e){
                                        System.out.println("You must introduce a number !");
                                    }
                                }
                                break;
                            }

                            default: {
                                System.out.println("Option not available!");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("You must introduce a number !");
                    }
                    break;
                }
                default: {
                    System.out.println("Option not available!");
                }
            }

        } catch (Exception e) {
            System.out.println("You must introduce a number !");
        }
    }
}
