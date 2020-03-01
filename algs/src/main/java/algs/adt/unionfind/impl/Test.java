package algs.adt.unionfind.impl;

import algs.adt.unionfind.UF;

import java.io.Console;

public class Test {
    
    private static final String EXIT_CMD = "exit";

    public static void main(String... args) {
		new Test().start();
    }

    public void start() {
        Console console = System.console();

        console.format("Type '%s' to exit program\n", EXIT_CMD);

        console.format("Choose algorithm:\n");
        console.format("    1: Quick find\n" +
                       "    2: Quick union\n" +
                       "    3: Weighted Quick union\n");        
        int algorithmCd = Integer.parseInt(console.readLine().trim());        

        console.format("Inter a number of objects\n");
        int objectsCount = Integer.parseInt(console.readLine().trim());

        UF ufAlgorithm = useAlgorithm(algorithmCd, objectsCount);

        console.format("Inter comma separated IDs of objects p and q to connect\n");
        while (true) {
            String cmd = console.readLine();
            if (EXIT_CMD.equals(cmd)) {
                break;
            }

            String[] objectsToConnect = cmd.split(",");
            int p = Integer.parseInt(objectsToConnect[0].trim());
            int q = Integer.parseInt(objectsToConnect[1].trim());
            
            if (p < 0 || p >= objectsCount || q < 0 || q >= objectsCount) {
                console.format("Incorrect values %d or %d. Both of them must be positive and less than %d. Try again\n", p, q, objectsCount);    
                continue;
            }

            if (ufAlgorithm.connected(p, q)) {
                console.format("Objects %d and %d has been already connected\n", p, q);
            } else {
                ufAlgorithm.union(p, q);
            }
        }
    }

    private UF useAlgorithm(int algorithmCd, int objectsCount) {
        switch (algorithmCd) {
            case 1:
                return UFProvider.quickFind(objectsCount);
            case 2:
                return UFProvider.quickUnion(objectsCount);  
            case 3: 
                return UFProvider.weightedQuickUnion(objectsCount);   
            default:
                throw new IllegalArgumentException("Unknown algorithm");    
        }    
    }

}
