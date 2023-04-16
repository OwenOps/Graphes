package Representation;

import graphe.IGraphe;

import java.util.*;

public class GrapheMAdj implements IGraphe {
    private int[][] matrice;
    private Map<String, Integer> indice;
    private static final int MAUVAISE_VALUATION = -1;

    public GrapheMAdj() {
        matrice = new int[0][0];
        indice = new HashMap<>();
    }

    public GrapheMAdj(String graphe) {
        this();
        peupler(graphe);
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (indice.containsKey(noeud))
            return;

        indice.put(noeud, matrice.length);
        int[][] matrice2 = new int[matrice.length + 1][matrice.length + 1];
        for (int i = 0; i < matrice2.length; i++) {
            for (int j = 0; j < matrice2.length; j++) {
                if (i == indice.get(noeud) || j == indice.get(noeud)) {
                    matrice2[i][j] = MAUVAISE_VALUATION;
                } else {
                    matrice2[i][j] = matrice[i][j];
                }
            }
        }
        matrice = matrice2;
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (contientArc(source, destination) || valeur < 0)
            throw new IllegalArgumentException();

        ajouterSommet(source);
        ajouterSommet(destination);

        matrice[indice.get(source)][indice.get(destination)] = valeur;
    }

    @Override
    public void oterSommet(String noeud) {
        if (!contientSommet(noeud))
            return;

        Map<String, Integer> indice2 = new HashMap<>();
        int indice_sommet = indice.get(noeud);
        Set<String> cles = indice.keySet();

        for (String cle : cles) {
            int ex_indice = indice.get(cle);
            if (ex_indice < indice_sommet) {
                indice2.put(cle, ex_indice);
            } else if (ex_indice > indice_sommet) {
                indice2.put(cle, ex_indice - 1);
            }
        }

        indice = indice2;
        int[][] matrice2 = new int[matrice.length - 1][matrice.length - 1];
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice.length; j++) {
                if (i < indice_sommet && j < indice_sommet) {
                    matrice2[i][j] = matrice[i][j];
                } else if (i < indice_sommet && j > indice_sommet) {
                    matrice2[i][j - 1] = matrice[i][j];
                } else if (i > indice_sommet && j < indice_sommet) {
                    matrice2[i - 1][j] = matrice[i][j];
                } else if (i > indice_sommet && j > indice_sommet) {
                    matrice2[i - 1][j - 1] = matrice[i][j];
                }
            }
        }
        matrice = matrice2;
    }

    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination))
            throw new IllegalArgumentException();

        matrice[indice.get(source)][indice.get(destination)] = MAUVAISE_VALUATION;
    }

    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<>(indice.keySet());
        Collections.sort(sommets);
        return sommets;
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> succ = new ArrayList<String>();
        if (contientSommet(sommet)) {
            int i_sommet = indice.get(sommet);
            for (int j = 0; j < matrice.length; j++) {
                if (matrice[i_sommet][j] >= 0) {
                    for (Map.Entry<String, Integer> successeur : indice.entrySet()) {
                        String key = successeur.getKey();
                        if (indice.get(key) == j)
                            succ.add(key);
                    }
                }
            }
        }
        return succ;
    }

    @Override
    public int getValuation(String src, String dest) {
        if (contientArc(src, dest)) {
            return matrice[indice.get(src)][indice.get(dest)];
        }
        return MAUVAISE_VALUATION;
    }

    @Override
    public boolean contientSommet(String sommet) {
        Set<String> cles = indice.keySet();
        for (String cle : cles) {
            if (cle.equals(sommet))
                return true;
        }
        return false;
    }

    @Override
    public boolean contientArc(String src, String dest) {
        if (indice.get(src) == null || indice.get(dest) == null)
            return false;

        return matrice[indice.get(src)][indice.get(dest)] != MAUVAISE_VALUATION;
    }

    @Override
    public String toString() {
        return toAString();
    }
}