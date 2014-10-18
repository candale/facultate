package lab4;

import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException{
        Grammar grammar=new Grammar();
        grammar.readGrammarFromFile();
        System.out.println("Start program \n");
        grammar.printGrammar();
        LR0 lr0=new LR0(grammar);
        lr0.startInitialPhase();
        System.out.println("\nStep 1: States \n");
        lr0.printAllStates();
        System.out.println("\nStep 2: Table \n");
        lr0.printGraph(lr0);
        System.out.println("Step 3: Parsing \n");
        lr0.parseSequence();

        System.out.print("\n\nEnd");
    }
}
