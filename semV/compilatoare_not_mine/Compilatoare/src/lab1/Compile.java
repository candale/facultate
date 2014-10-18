package lab1;


import com.sun.deploy.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Compile {

    public Codification codification = new Codification();
    public Map<Integer, Pair> internalPIF = new TreeMap<Integer, Pair>();
    public SortedTable symbolTable = new SortedTable();
    public List<String> errorList = new ArrayList<String>();
    final static private Pattern pattern = Pattern.compile("^[a-zA-z]+[a-zA-Z0-9]*");
    final static private Pattern p = Pattern.compile("[^a-zA-Z0-9\"\']");

    public List<String> getErrorList() {
        return errorList;
    }

    public static List<String> getTokens() {
        List<String> lines = ReadFile.readFile("D:\\IdeaProjects\\Compilatoare\\src\\Files\\1.input.txt");
        String[] tokens1 = new String[0];
        List<String> tokens = new ArrayList<String>();
        for (int i = 0; i < lines.size(); i++) {
            tokens1 = lines.get(i).split("[\\s]");

            for (int j = 0; j < tokens1.length; j++) {
                if (!tokens1[j].equals(" ") && !tokens1[j].equals("")) {
                    if (p.matcher(tokens1[j]).find() && tokens1[j].length() > 1) {
                        String str = "";
                        String sep = "";
                        for (Character c : tokens1[j].toCharArray()) {
                            if (c != '=' && c != '!' && c != '<' && c != '>' && c != '+' && c != '-' && c != '*' && c != '/' && c != '(' && c != ')' && c != ';') {
                                str = str + c;
                                if (!sep.equals(" ") && !sep.equals("")) {
                                    tokens.add(sep);
                                }
                                sep = "";
                            } else {
                                if (c.equals('-') && str.equals("") && sep.equals("")) {
                                    str = str + c;
                                    if (!sep.equals(" ") && !sep.equals("")) {
                                        tokens.add(sep);
                                    }
                                    sep = "";
                                } else{
                                    sep = sep + c;
                                    if (!str.equals(" ") && !str.equals("")) {
                                        tokens.add(str);
                                    }
                                    str = "";
                                }


                            }

                        }
                        if (!str.equals(" ") && !str.equals("")) {
                            tokens.add(str);
                        }
                        if (!sep.equals(" ") && !sep.equals("")) {
                            tokens.add(sep);
                        }
                    } else tokens.add(tokens1[j]);
                }
            }
        }
        return tokens;
    }


    public boolean verifyIfConstant(String token) {
        Boolean verifyNr = true;
        Boolean verifyChar = true;
        Boolean verifyString = true;
        if (token.startsWith("-")) {
            try {
                Integer.parseInt(token);
            } catch (Exception e) {
                verifyNr = false;
            }
        } else {
            try {
                Integer.parseInt(token);
            } catch (Exception e) {
                verifyNr = false;
            }
        }
        if (token.charAt(0) != '\'' || token.charAt(token.length() - 1) != '\'' || token.length() != 3) {
            verifyChar = false;
        }

        if (token.charAt(0) != '\"' || token.charAt(token.length() - 1) != '\"') {
            verifyString = false;
        }

        return verifyNr || verifyChar || verifyString;
    }

    public Boolean verifyIfIdentifier(String token) {
        Matcher matcher = pattern.matcher(token);
        if (matcher.matches())
            return true;
        return false;
    }


    public void completeTables() {
        List<String> tokens = getTokens();
        System.out.println(tokens);
        for (String token : tokens) {
            if (codification.getCodificationList().containsKey(token)) {
            } else if (verifyIfConstant(token)) {
                symbolTable.add(token);
            } else if (verifyIfIdentifier(token)) {
                symbolTable.add(token);
            }
        }
        Integer key = 1;
        for (String token : tokens) {
            if (codification.getCodificationList().containsKey(token)) {
                internalPIF.put(key, new Pair(codification.getCodificationList().get(token), 0));
            } else if (verifyIfConstant(token)) {
                internalPIF.put(key, new Pair(2, symbolTable.getPosition(token)));
            } else if (verifyIfIdentifier(token) && token.length() <= 8) {
                internalPIF.put(key, new Pair(1, symbolTable.getPosition(token)));
            } else {
                int errorPosition = 0;
                for (int i = 0; i < tokens.size(); i++) {
                    if (tokens.get(i).contains(token)) {
                        errorPosition = i + 1;
                    }
                }
                if (verifyIfIdentifier(token) && token.length() > 8) {
                    String error = "Lexical error at line " + errorPosition + ". The identifier: \"" + token + "\" should have at most 8 characters";
                    errorList.add(error);
                } else {
                    String error = "Lexical error at line " + errorPosition + ". Unindentified token: \"" + token + "\"";
                    errorList.add(error);
                }
                System.out.println("Error at line " + errorPosition + ": " + token);
            }
            key++;
        }
    }

    public Map<Integer, Pair> getInternalPIF() {
        return internalPIF;
    }

    public SortedTable getSymbolTable() {
        return symbolTable;
    }

    public static void main(String[] args) {
        Compile compile = new Compile();
        compile.completeTables();

        WriteFile.outputST("D:\\IdeaProjects\\Compilatoare\\src\\Files\\1.outputST.txt", compile.symbolTable);
        WriteFile.outputPIF("D:\\IdeaProjects\\Compilatoare\\src\\Files\\1.outputPIF.txt", compile.getInternalPIF());
        WriteFile.outputError("D:\\IdeaProjects\\Compilatoare\\src\\Files\\1.outputError.txt", compile.getErrorList());
    }
}
