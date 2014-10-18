package lab4;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Grammar {
    private List<String> terminals;
    private List<String> nonTerminals;
    private String startingSymbol;
    private Set<Production> productions;

    public  Grammar() {
        this.nonTerminals = new ArrayList<String>();
        this.terminals = new ArrayList<String>();
        this.productions = new HashSet<Production>();
    }

    public void readGrammarFromFile() {
        try {
            FileInputStream fStream = new FileInputStream("D:\\IdeaProjects\\Compilatoare\\src\\Files\\4.grammar.2.txt");
            DataInputStream in = new DataInputStream(fStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int index = 0;
            int productionNumber = 1;
            while ((strLine = br.readLine()) != null) {
                strLine.trim();
                StringTokenizer stringTokenizer = new StringTokenizer(strLine, " ");

                if (index == 0) {
                    while (stringTokenizer.hasMoreElements()) {
                        String stringToken = stringTokenizer.nextElement().toString();
                        if (stringToken != null)
                            terminals.add(stringToken);
                    }
                } else {
                    if (index == 1) {
                        while (stringTokenizer.hasMoreTokens()) {
                            nonTerminals.add(stringTokenizer.nextToken());
                        }
                    } else {
                        if (index == 2) {
                            startingSymbol = strLine;
                        } else {
                            String[] splited = strLine.split("->");
                            String nonTerm = splited[0].trim();
                            stringTokenizer = new StringTokenizer(splited[1], "|");
                            while (stringTokenizer.hasMoreTokens()) {
                                Production newProduction = new Production();
                                newProduction.setNonTerminal(nonTerm);
                                newProduction.setProductionNumber(productionNumber);
                                productionNumber++;
                                String resultSide = stringTokenizer.nextToken().trim();
                                StringTokenizer stringTokenizer2 = new StringTokenizer(resultSide, " ");
                                while (stringTokenizer2.hasMoreElements()) {
                                    newProduction.addResult(stringTokenizer2.nextToken());
                                }
                                this.productions.add(newProduction);
                            }

                        }
                    }
                }
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isNonTerminal(String symbol) {
        for (String nonTerminal: this.nonTerminals) {
            if (nonTerminal.equals(symbol)) {
                return true;
            }
        }
        return false;
    }

    public boolean isTerminal(String symbol) {
        for (String terminal: this.terminals) {
            if (terminal.equals(symbol)) {
                return true;
            }
        }
        return false;
    }

    public String getStartingSymbol() {
        return startingSymbol;
    }

    public Set<Production> getProductionsWithAGivenNonTerminal(String symbol){
        Set<Production> listNewProductions=new HashSet<Production>();
        for(Production production:this.productions){
            if(production.getNonTerminal().equals(symbol)){
                listNewProductions.add(new Production(production.getNonTerminal(),production.getResults(),0,production.getProductionNumber()));
            }
        }
        return listNewProductions;
    }

    public Set<String> getAllSymbols(){
        Set<String> setSymbols=new HashSet<String>();
        setSymbols.addAll(this.nonTerminals);
        setSymbols.addAll(this.terminals);
        return setSymbols;
    }

    public List<Production> getListProductions() {
        List<Production> list=new ArrayList<Production>();
        for(int index=0; index<productions.size(); index++){
            list.add(index,getProductionWithIndex(index+1));
        }
        return list;
    }

    public Production getProductionWithIndex(int index){
        Iterator<Production> iterator=productions.iterator();
        while(iterator.hasNext()){
            Production production=iterator.next();
            if(production.getProductionNumber()==index){
                return production;
            }
        }
        return null;
    }

    public void printGrammar() {
        System.out.print("\nSet of non-terminals: ");

        for (String nonTerm: nonTerminals) {
            System.out.print(nonTerm + " ");
        }

        System.out.println("\nStarting symbol: " + startingSymbol);
        System.out.print("Set of terminals: ");
        for (String term: terminals) {
            System.out.print(term+ " ");
        }
        System.out.println("\nSet of productions: ");
        for (Production production: productions) {
            System.out.println(production.toString() + " ");
        }
    }

    public Set<Production> getProductions() {
        return productions;
    }
}


