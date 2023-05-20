package graphe.implems;

import graphe.core.IGraphe;

import java.util.*;

public class GrapheHHAdj implements IGraphe {
    private Map<String, Map<String, Integer>> hhadj;
    private static final int MAUVAISE_VALUATION = -1;

    /**
     * Constructeur par défaut qui initialise un graphe vide.
     */
    public GrapheHHAdj() {
        hhadj = new HashMap<>();
    }

    /**
     * Constructeur qui crée un graphe à partir d'une représentation de graphe donnée.
     *
     * @param graphe La représentation du graphe à utiliser pour peupler le graphe.
     */
    public GrapheHHAdj(String graphe) {
        this();
        peupler(graphe);
    }

    /**
     * Ajoute un sommet au graphe.
     *
     * @param noeud Le sommet à ajouter.
     */
    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud))
            hhadj.put(noeud, new HashMap<>());
    }

    /**
     * Ajoute un arc au graphe avec une valeur donnée.
     *
     * @param source      Le sommet source de l'arc.
     * @param destination Le sommet destination de l'arc.
     * @param valeur      La valeur associée à l'arc.
     * @throws IllegalArgumentException si l'arc existe déjà dans le graphe ou si la valeur est négative.
     */
    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (contientArc(source, destination) || valeur < 0)
            throw new IllegalArgumentException();

        ajouterSommet(source);
        ajouterSommet(destination);

        hhadj.get(source).put(destination, valeur);
    }

    /**
     * Supprime un sommet spécifié du graphe, ainsi que tous les arcs liés à ce sommet.
     * Si le graphe ne contient pas le sommet, la méthode se termine sans effectuer d'action.
     *
     * @param noeud Le nom du sommet à supprimer.
     */
    @Override
    public void oterSommet(String noeud) {
        if (!contientSommet(noeud))
            return;

        hhadj.remove(noeud);
        for (Map.Entry<String, Map<String, Integer>> entry : hhadj.entrySet()) {
            if (hhadj.get(entry.getKey()).get(noeud) != null)
                hhadj.get(entry.getKey()).remove(noeud);
        }
    }

    /**
     * Supprime l'arc spécifié du graphe.
     *
     * @param source      Le sommet source de l'arc à supprimer.
     * @param destination Le sommet destination de l'arc à supprimer.
     * @throws IllegalArgumentException si l'arc n'existe pas dans le graphe.
     */
    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination)) {
            throw new IllegalArgumentException();
        }
        hhadj.get(source).remove(destination);
    }

    /**
     * Renvoie la liste des sommets du graphe.
     *
     * @return La liste des sommets du graphe.
     */
    @Override
    public List<String> getSommets() {
        List<String> sommet = new ArrayList<>(hhadj.keySet());
        Collections.sort(sommet);
        return sommet;
    }

    /**
     * Renvoie la liste des successeurs d'un sommet spécifié.
     *
     * @param sommet Le sommet dont on souhaite obtenir les successeurs.
     * @return La liste des successeurs du sommet spécifié.
     */
    @Override
    public List<String> getSucc(String sommet) {
        if (hhadj.get(sommet) == null)
            return new ArrayList<>();
        return new ArrayList<>(hhadj.get(sommet).keySet());
    }

    /**
     * Renvoie la valuation de l'arc entre deux sommets spécifiés.
     *
     * @param src  Le sommet source de l'arc.
     * @param dest Le sommet destination de l'arc.
     * @return La valuation de l'arc entre les sommets spécifiés.
     *         Si l'arc n'existe pas, renvoie la constante MAUVAISE_VALUATION.
     */
    @Override
    public int getValuation(String src, String dest) {
        if (contientArc(src, dest))
            return hhadj.get(src).get(dest);
        return MAUVAISE_VALUATION;
    }

    /**
     * Vérifie si le graphe contient un sommet spécifié.
     *
     * @param sommet Le sommet à vérifier.
     * @return true si le graphe contient le sommet, false sinon.
     */
    @Override
    public boolean contientSommet(String sommet) {
        return hhadj.containsKey(sommet);
    }

    /**
     * Vérifie si le graphe contient un arc spécifié.
     *
     * @param src  Le sommet source de l'arc.
     * @param dest Le sommet destination de l'arc.
     * @return true si le graphe contient l'arc, false sinon.
     */
    @Override
    public boolean contientArc(String src, String dest) {
        return hhadj.containsKey(src) && hhadj.containsKey(dest) && hhadj.get(src).get(dest) != null;
    }

    /**
     * Renvoie une représentation sous forme de chaîne de caractères du graphe.
     *
     * @return La représentation sous forme de chaîne de caractères du graphe.
     */
    public String toString() {
        return toAString();
    }
}