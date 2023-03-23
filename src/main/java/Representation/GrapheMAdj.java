package Representation;

import java.util.List;
import java.util.Map;

public class GrapheMAdj {
    private int[][] matrice;
    private Map<String, Integer> indice;

    public GrapheMAdj(int nbrSommet) {
        matrice = new int[nbrSommet][nbrSommet];
        initMatrice(nbrSommet);
    }

    private void initMatrice(int nbrSommet) {
        assert (nbrSommet == 0);
        for (int i = 0; i < nbrSommet; i++) {
            for (int j = 0; j < nbrSommet; j++) {
                matrice[i][j] = 0;
            }
        }
    }
    
}
