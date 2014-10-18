package lab2;

import java.io.*;
import java.util.List;
import java.util.Set;

public class ReadFiniteAutomata {
    public static void readFiniteAutomataFromFile(Set<String> states, Set<String> alphabet, List<Element> transitionFunction, StringBuilder startState, Set<String> finalStates) {
        try {
            FileInputStream fstream = new FileInputStream("D:\\IdeaProjects\\Compilatoare\\src\\Files\\2.finiteAutomata.txt");

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
                states.add(temp[i]);
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
                alphabet.add(temp2[i]);
            }

            strLine = br.readLine();
            Integer nt = 0;
            try {
                nt = Integer.parseInt(strLine);
            } catch (Exception e) {
                System.out.println("Not a number!");
            }
            for (int i = 0; i < nt; i++) {
                strLine = br.readLine();
                String temp3[] = strLine.split(" ");
                Element element = new Element(temp3[0], temp3[1], temp3[2]);
                transitionFunction.add(element);
            }
            strLine = br.readLine();
            startState.append(strLine);

            strLine = br.readLine();
            Integer n3 = 0;
            try {
                n3 = Integer.parseInt(strLine);
            } catch (Exception e) {
                System.out.println("Not a number!");
            }
            strLine = br.readLine();
            String temp3[] = strLine.split(" ");
            for (int i = 0; i < n3; i++) {
                finalStates.add(temp3[i]);
            }
            in.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
    
    public static void readFiniteAutomataFromKeyboard(Set<String> states, Set<String> alphabet, List<Element> transitionFunction, StringBuilder startState, Set<String> finalStates){
        System.out.println("Give the number of states");
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
                System.out.println("Give a state: ");
                BufferedReader br211 = new BufferedReader(new InputStreamReader(System.in));
                String sample211 = null;
                try {
                    sample211 = br211.readLine();
                } catch (IOException e) {
                    System.out.println("Couldn't read!!!");
                }
                states.add(sample211);
            }
        }
        System.out.println("Give the number of elements of the alphabet");
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
                System.out.println("Give a symbol: ");
                BufferedReader br221 = new BufferedReader(new InputStreamReader(System.in));
                String sample221 = null;
                try {
                    sample221 = br221.readLine();
                } catch (IOException e) {
                    System.out.println("Couldn't read!!!");
                }
                alphabet.add(sample221);
            }
        }

        System.out.println("Give the number of transitions");
        BufferedReader br21t = new BufferedReader(new InputStreamReader(System.in));
        String sample21t = null;
        try {
            sample21t = br21t.readLine();
        } catch (IOException e) {
            System.out.println("Couldn't read!!!");
        }
        Integer nt = 0;
        Boolean okt = true;
        try {
            nt = Integer.parseInt(sample21t);
        } catch (Exception e) {
            okt = false;
            System.out.println("Not a number!");
        }
        if (okt == true) {

            for (int i = 0; i < nt; i++) {
                System.out.println("Transition function: ");
                System.out.println("From");
                BufferedReader br231 = new BufferedReader(new InputStreamReader(System.in));
                String sample231 = null;
                try {
                    sample231 = br231.readLine();
                } catch (IOException e) {
                    System.out.println("Couldn't read!!!");
                }
                System.out.println("To: ");
                BufferedReader br232 = new BufferedReader(new InputStreamReader(System.in));
                String sample232 = null;
                try {
                    sample232 = br232.readLine();
                } catch (IOException e) {
                    System.out.println("Couldn't read!!!");
                }
                System.out.println("Cost:");
                BufferedReader br233 = new BufferedReader(new InputStreamReader(System.in));
                String sample233 = null;
                try {
                    sample233 = br233.readLine();
                } catch (IOException e) {
                    System.out.println("Couldn't read!!!");
                }
                Element element = new Element(sample231, sample232, sample233);
                transitionFunction.add(element);
            }
        }
        System.out.println("Give the initial state: ");
        BufferedReader br24 = new BufferedReader(new InputStreamReader(System.in));
        String sample24 = null;
        try {
            sample24 = br24.readLine();
        } catch (IOException e) {
            System.out.println("Couldn't read!!!");
        }
        startState.append(sample24);

        System.out.println("Give the number of final states");
        BufferedReader br21f = new BufferedReader(new InputStreamReader(System.in));
        String sample21f = null;
        try {
            sample21f = br21f.readLine();
        } catch (IOException e) {
            System.out.println("Couldn't read!!!");
        }
        Integer nf = 0;
        Boolean okf = true;
        try {
            nf = Integer.parseInt(sample21f);
        } catch (Exception e) {
            okf = false;
            System.out.println("Not a number!");
        }
        if (okf == true) {
            for (int i = 0; i < nf; i++) {
                System.out.println("Give a final state: ");
                BufferedReader br221 = new BufferedReader(new InputStreamReader(System.in));
                String sample221 = null;
                try {
                    sample221 = br221.readLine();
                } catch (IOException e) {
                    System.out.println("Couldn't read!!!");
                }
                finalStates.add(sample221);
            }
        }
    }
}
