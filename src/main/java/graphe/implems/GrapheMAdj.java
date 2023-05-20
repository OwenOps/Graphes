package graphe.implems;

import graphe.core.IGraphe;

import java.util.*;

public class GrapheMAdj implements IGraphe {
    private int[][] matrice;
    private Map<String, Integer> indice;
    private static final int MAUVAISE_VALUATION = -1;

    /**
     * Constructeur par défaut de la classe GrapheMAdj.
     * Initialise la matrice d'adjacence vide et l'indice des sommets.
     */
    public GrapheMAdj() {
        matrice = new int[0][0];
        indice = new HashMap<>();
    }

    /**
     * Constructeur de la classe GrapheMAdj qui permet de peupler le graphe
     *
     * @param graphe La représentation textuelle du graphe.
     */
    public GrapheMAdj(String graphe) {
        this();
        peupler(graphe);
    }

    /**
     * Ajoute un sommet au graphe.
     *
     * @param noeud Le nom du sommet à ajouter.
     */
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

    /**
     * Ajoute un arc entre deux sommets avec une valuation donnée.
     *
     * @param source      Le sommet source de l'arc.
     * @param destination Le sommet destination de l'arc.
     * @param valeur      La valuation de l'arc.
     * @throws IllegalArgumentException si le graphe contient déjà l'arc ou si la valuation est négative.
     */
    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (contientArc(source, destination) || valeur < 0)
            throw new IllegalArgumentException();

        ajouterSommet(source);
        ajouterSommet(destination);

        matrice[indice.get(source)][indice.get(destination)] = valeur;
    }

    /**
     * Supprime un sommet du graphe ainsi que tous les arcs qui y sont associés.
     *
     * @param noeud Le nom du sommet à supprimer.
     */
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

    /**
     * Supprime l'arc entre deux sommets du graphe.
     *
     * @param source      Le sommet source de l'arc à supprimer.
     * @param destination Le sommet destination de l'arc à supprimer.
     * @throws IllegalArgumentException si le graphe ne contient pas l'arc.
     */
    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination))
            throw new IllegalArgumentException();

        matrice[indice.get(source)][indice.get(destination)] = MAUVAISE_VALUATION;
    }

    /**
     * Retourne la liste des sommets du graphe, triés par ordre alphabétique.
     *
     * @return La liste des sommets du graphe.
     */
    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<>(indice.keySet());
        Collections.sort(sommets);
        return sommets;
    }

    /**
     * Retourne la liste des successeurs d'un sommet donné, triés par ordre alphabétique.
     *
     * @param sommet Le sommet pour lequel on veut obtenir les successeurs.
     * @return La liste des successeurs du sommet.
     */
    @Override
    public List<String> getSucc(String sommet) {
        List<String> succ = new ArrayList<>();
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
        Collections.sort(succ);
        return succ;
    }

    /**
     * Retourne la valuation de l'arc entre deux sommets du graphe.
     *
     * @param src  Le sommet source de l'arc.
     * @param dest Le sommet destination de l'arc.
     * @return La valuation de l'arc, ou la constante MAUVAISE_VALUATION si l'arc n'existe pas.
     */
    @Override
    public int getValuation(String src, String dest) {
        if (contientArc(src, dest)) {
            return matrice[indice.get(src)][indice.get(dest)];
        }
        return MAUVAISE_VALUATION;
    }

    /**
     * Vérifie si le graphe contient un sommet donné.
     *
     * @param sommet Le sommet à vérifier.
     * @return true si le graphe contient le sommet, sinon false.
     */
    @Override
    public boolean contientSommet(String sommet) {
        Set<String> cles = indice.keySet();
        for (String cle : cles) {
            if (cle.equals(sommet))
                return true;
        }
        return false;
    }

    /**
     * Vérifie si le graphe contient un arc entre deux sommets donnés.
     *
     * @param src  Le sommet source de l'arc.
     * @param dest Le sommet destination de l'arc.
     * @return true si le graphe contient l'arc, sinon false.
     */
    @Override
    public boolean contientArc(String src, String dest) {
        if (indice.get(src) == null || indice.get(dest) == null)
            return false;

        return matrice[indice.get(src)][indice.get(dest)] != MAUVAISE_VALUATION;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du graphe.
     *
     * @return La représentation du graphe sous forme de chaîne de caractères.
     */
    @Override
    public String toString() {
        return toAString();
    }
}