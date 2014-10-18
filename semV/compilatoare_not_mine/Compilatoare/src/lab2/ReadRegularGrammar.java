package lab2;

import java.io.*;
import java.util.List;
import java.util.Set;

public class ReadRegularGrammar {

    public static void readRegularGrammarFromKeyboard(Set<String> nonTerminals,Set<String> terminals,List productions,StringBuilder startSymbol){
        System.out.println("Give the number of non-terminals");
        BufferedReader br21 = new BufferedReader(new InputStreamReader(System.in));
        String sample21 = null;
        try {
            sample21 = br21.readLine();
        } catch (IOException e) {
            System.out.println("Couldn't read!!!");
        }
        Integer n = 0;
        Boolean ok = true;
        try {
            n = Integer.parseInt(sample21);
        } catch (Exception e) {
            ok = false;
            System.out.println("Not a number!");
        }
        if (ok == true) {
            for (int i = 0; i < n; i++) {
                System.out.println("Give a non-terminal: ");
                BufferedReader br211 = new BufferedReader(new InputStreamReader(System.in));
                String sample211 = null;
                try {
                    sample211 = br211.readLine();
                } catch (IOException e) {
                    System.out.println("Couldn't read!!!");
                }
                nonTerminals.add(sample211);
            }
        }
        System.out.println("Give the number of terminals");
        BufferedReader br22 = new BufferedReader(new InputStreamReader(System.in));
        String sample22 = null;
        try {
            sample22 = br22.readLine();
        } catch (IOException e) {
            System.out.println("Couldn't read!!!");
        }
        Integer n2 = 0;
        Boolean ok2 = true;
        try {
            n2 = Integer.parseInt(sample22);
        } catch (Exception e) {
            ok2 = false;
            System.out.println("Not a number!");
        }
        if (ok2 == true) {
            for (int i = 0; i < n2; i++) {
                System.out.println("Give a terminal: ");
                BufferedReader br221 = new BufferedReader(new InputStreamReader(System.in));
                String sample221 = null;
                try {
                    sample221 = br221.readLine();
                } catch (IOException e) {
                    System.out.println("Couldn't read!!!");
                }
                terminals.add(sample221);
            }
        }
        System.out.println("Give the number of productions");
        BufferedReader br23 = new BufferedReader(new InputStreamReader(System.in));
        String sample23 = null;
        try {
            sample23 = br23.readLine();
        } catch (IOException e) {
            System.out.println("Couldn't read!!!");
        }
        Integer n3 = 0;
        Boolean ok3 = true;
        try {
            n3 = Integer.parseInt(sample23);
        } catch (Exception e) {
            ok3 = false;
            System.out.println("Not a number!");
        }
        if (ok3 == true) {
            for (int i = 0; i < n3; i++) {
                System.out.println("Give the non-terminal: ");
                BufferedReader br231 = new BufferedReader(new InputStreamReader(System.in));
                String sample231 = null;
                try {
                    sample231 = br231.readLine();
                } catch (IOException e) {
                    System.out.println("Couldn't read!!!");
                }
                System.out.println("Give the result: ");
                BufferedReader br232 = new BufferedReader(new InputStreamReader(System.in));
                String sample232 = null;
                try {
                    sample232 = br232.readLine();
                } catch (IOException e) {
                    System.out.println("Couldn't read!!!");
                }
                Production pair = new Production(sample231, sample232);
                productions.add(pair);
            }
        }
        System.out.println("Give the starting symbol: ");
        BufferedReader br24 = new BufferedReader(new InputStreamReader(System.in));
        String sample24 = null;
        try {
            sample24 = br24.readLine();
        } catch (IOException e) {
            System.out.println("Couldn't read!!!");
        }
        startSymbol.append(sample24);
    }

    public static void readRegularGrammarFromFile(Set<String> nonTerminals,Set<String> terminals,List productions,StringBuilder startSymbol) {
        try {
            FileInputStream fstream = new FileInputStream("C:\\Users\\fgheorghe\\Dropbox\\Compilatoare\\src\\Files\\2.regularGrammar.txt");

            DataInputStream in;
            in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            strLine = br.readLine();
            Integer n = 0;
            try {
                n = Integer.parseInt(strLine);
            } catch (Exception e) {
                System.out.println("Not a number!");
            }
            strLine = br.readLine();
            String temp[] = strLine.split(" ");
            for (int i = 0; i < n; i++) {

                nonTerminals.add(temp[i]);
            }
            strLine = br.readLine();
            Integer n2 = 0;
            try {
                n2 = Integer.parseInt(strLine);
            } catch (Exception e) {
                System.out.println("Not a number!");
            }
            strLine = br.readLine();
            String temp2[] = strLine.split(" ");
            for (int i = 0; i < n2; i++) {
                terminals.add(temp2[i]);
            }
            strLine = br.readLine();
            Integer n3 = 0;
            try {
                n3 = Integer.parseInt(strLine);
            } catch (Exception e) {
                System.out.println("Not a number!");
            }

            for (int i = 0; i < n3; i++) {
                strLine = br.readLine();
                String temp3[] = strLine.split(" ");
                Production production = new Production(temp3[0], temp3[1]);
                productions.add(production);
            }
            strLine = br.readLine();
            startSymbol.append(strLine);

            in.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
