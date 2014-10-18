package lab2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Operations {
    public static Set<String> getProductionsOfGivenSymbol(String nonterm, List<Production> productions) {
        Set<String> result = new HashSet<String>();
        for (int i = 0; i < productions.size(); i++) {
            if (productions.get(i).getNonTerminal().equals(nonterm)) {
                result.add(productions.get(i).getResult());
            }
        }
        return result;
    }

    public static boolean firstVerify(String s, Set<String> nonTerminals, Set<String> terminals) {
        String temp[] = s.split("");
        Boolean ok = false;
        int i = 0;
        for (i = 1; i < temp.length && ok == false; i++) {
            if (terminals.contains(temp[i])) {
                ok = false;
            } else {
                ok = true;
            }
        }
        for (int j = i; j < temp.length && ok == true; j++) {
            if (nonTerminals.contains(temp[j])) {
                ok = true;
            } else {
                ok = false;
            }

        }
        return ok;
    }

    public static boolean secondVerify(String s, Set<String> nonTerminals, Set<String> terminals) {
        String temp[] = s.split("");
        Boolean ok = false;
        int i = 0;
        for (i = 1; i < temp.length && ok == false; i++) {
            if (nonTerminals.contains(temp[i])) {
                ok = false;
            } else {
                ok = true;
            }
        }
        for (int j = i; j < temp.length && ok == true; j++) {
            if (terminals.contains(temp[j])) {
                ok = true;
            } else {
                ok = false;
            }
        }
        return ok;
    }

    public static boolean isRegularGrammar(List<Production> productions, Set<String> nonTerminals, Set<String> terminals) {
        for (int i = 0; i < productions.size(); i++) {
            Production p = productions.get(i);
            if (nonTerminals.contains(p.getNonTerminal())) {
                String temp[] = p.getResult().split("");
                if (temp.length == 2 && temp[1].equals("")) {

                } else if (temp.length == 2 && terminals.contains(temp[1])) {

                } else if (firstVerify(p.getResult(), nonTerminals, terminals) == true || secondVerify(p.getResult(), nonTerminals, terminals)) {

                } else {
                    return false;
                }

            } else {
                return false;
            }
        }
        return true;
    }

    public static Set<String> getFinalStatesFromGrammar(List<Production> productions) {
        Set<String> result = new HashSet<String>();
        for (int i = 0; i < productions.size(); i++) {
            Production p = productions.get(i);
            if (p.getResult().equals("")) {
                result.add(p.getNonTerminal());
            }
        }
        return result;
    }

    public static List<String> makeTransitions(String s,Set<String> terminals) {
        String temp[] = s.split("");
        List<String> toSend = new ArrayList<String>();
        String result = "";
        String result2 = "";
        for (int i = 1; i < temp.length; i++) {
            if (terminals.contains(temp[i])) {
                result = result + temp[i];
            } else {
                result2 = result2 + temp[i];
            }
        }
        toSend.add(result);
        toSend.add(result2);
        return toSend;
    }

}
