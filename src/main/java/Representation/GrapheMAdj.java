package Representation;

import Interface.IGraphe;

import java.util.List;
import java.util.Map;

public class GrapheMAdj implements IGraphe {
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

    @Override
    public void ajouterSommet(String noeud) {
        indice.put(noeud, 0);
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {

    }

    @Override
    public void oterSommet(String noeud) {

    }

    @Override
    public void oterArc(String source, String destination) {

    }

    @Override
    public List<String> getSommets() {
        return null;
    }

    @Override
    public List<String> getSucc(String sommet) {
        return null;
    }

    @Override
    public int getValuation(String src, String dest) {
        return 0;
    }

    @Override
    public boolean contientSommet(String sommet) {
        return false;
    }

    @Override
    public boolean contientArc(String src, String dest) {
        return false;
    }

    /*public String toString() {

    }*/
}
