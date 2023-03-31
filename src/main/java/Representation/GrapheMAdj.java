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
        for (int ligne = 0; ligne < nbrSommet; ligne++) {
            for (int colonne = 0; colonne < nbrSommet; colonne++) {
                matrice[ligne][colonne] = 0;
            }
        }
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!indice.containsKey(noeud))
            indice.put(noeud, 0);
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) throws IllegalArgumentException {
        /*if(!= 0) {
            throw new IllegalArgumentException("L'arc est déjà présent.");
        }*/

    }


    @Override
    public void oterSommet(String noeud) {
        for (Map.Entry<String, Integer> entry : indice.entrySet()) {

        }
        indice.remove(noeud);
    }

    @Override
    public void oterArc(String source, String destination) {
        /*for (Map.Entry<String, Integer> entry : indice.entrySet()) {
            if (entry.g)
        }*/
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
        for (int ligne = 0; ligne < matrice.length; ligne++) {
            for (int colonne = 0; colonne < matrice.length; colonne++) {
                ;
            }
        }
    }*/
}
