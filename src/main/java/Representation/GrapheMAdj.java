package Representation;

import java.util.List;
import java.util.Map;

public class GrapheMAdj {
    private int[][] matrice;
    private Map<String, Integer> indice;

    public GrapheMAdj(int sommet) {
        matrice = new int[sommet][sommet];
    }
}
