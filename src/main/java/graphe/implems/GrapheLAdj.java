package graphe.implems;
import graphe.core.IGraphe;
import graphe.core.Arc;

import java.util.*;

public class GrapheLAdj implements IGraphe {
    private Map<String, List<Arc>> ladj;
    private static final int MAUVAISE_VALUATION = -1;

    /**
     * Constructeur par défaut qui initialise un graphe vide.
     */
    public GrapheLAdj() {
        ladj = new HashMap<>();
    }

    /**
     * Constructeur qui crée un graphe à partir d'une représentation de graphe donnée.
     * La représentation du graphe est fournie en tant que chaîne de caractères.
     *
     * @param graphe La représentation du graphe à utiliser pour peupler le graphe.
     */
    public GrapheLAdj(String graphe) {
        this();
        peupler(graphe);
    }

    /**
     * Ajoute un sommet au graphe.
     * Si le graphe ne contient pas déjà le sommet, il est ajouté avec une liste vide d'arcs.
     *
     * @param noeud Le sommet à ajouter.
     */
    @Override
    public void ajouterSommet(String noeud) {
        if (!ladj.containsKey(noeud)) {
            ladj.put(noeud, new ArrayList<>());
        }
    }

    /**
     * Ajoute un arc au graphe avec une valeur donnée.
     * Si la valeur est négative ou si l'arc existe déjà dans le graphe, une exception IllegalArgumentException est lancée.
     * Les sommets source et destination de l'arc sont ajoutés au graphe s'ils n'existent pas déjà.
     *
     * @param source      Le sommet source de l'arc.
     * @param destination Le sommet destination de l'arc.
     * @param valeur      La valeur associée à l'arc.
     * @throws IllegalArgumentException si la valeur est négative ou si l'arc existe déjà dans le graphe.
     */
    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (valeur < 0 || contientArc(source, destination))
            throw new IllegalArgumentException();

        ajouterSommet(source);
        ajouterSommet(destination);

        // Pour la source, on ajoute un Arc à la liste d'arcs
        ladj.get(source).add(new Arc(source, destination, valeur));
    }

    /**
     * Supprime un sommet spécifié du graphe, ainsi que tous les arcs liés à ce sommet.
     * Si le graphe ne contient pas le sommet, la méthode se termine sans effectuer d'action.
     *
     * @param noeud Le nom du sommet à supprimer.
     */
    @Override
    public void oterSommet(String noeud) {
        if (!(ladj.containsKey(noeud)))
            return;

        ladj.remove(noeud);
        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            List<Arc> newLi = entry.getValue();

            int i = newLi.size() - 1;
            while (i >= 0) {
                if (newLi.get(i).getSource().equals(noeud) || newLi.get(i).getDestination().equals(noeud)) {
                    newLi.remove(i);
                }
                i--;
            }
            ladj.put(entry.getKey(), newLi);
        }
    }

    /**
     * Supprime l'arc spécifié du graphe.
     *
     * @param source      Le sommet source de l'arc.
     * @param destination Le sommet destination de l'arc.
     * @throws IllegalArgumentException si l'arc n'existe pas dans le graphe.
     */
    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination))
            throw new IllegalArgumentException();

        List<Arc> sommet = ladj.get(source);
        for (int i = 0; i < sommet.size(); i++) {
            if (sommet.get(i).equals(new Arc(source, destination))) {
                sommet.remove(i);
                break;
            }
        }
        ladj.put(source, sommet);
    }

    /**
     * Retourne la liste de tous les sommets du graphe.
     *
     * @return La liste des sommets du graphe par odre alpahbétique.
     */
    @Override
    public List<String> getSommets() {
        List<String> arc = new ArrayList<>(ladj.keySet());
        Collections.sort(arc);
        return arc;
    }

    /**
     * Retourne la liste des successeurs d'un sommet donné.
     *
     * @param sommet Le sommet pour lequel récupérer les successeurs.
     * @return La liste des successeurs du sommet.
     */
    @Override
    public List<String> getSucc(String sommet) {
        List<String> succ = new ArrayList<>();
        if (ladj.get(sommet) == null)
            return succ;

        for (Arc arc : ladj.get(sommet)) {
            if (!arc.getDestination().equals(""))
                succ.add(arc.getDestination());
        }
        return succ;
    }

    /**
     * Retourne la valeur de l'arc entre deux sommets donnés.
     * Si l'arc n'existe pas dans le graphe, la valeur MAUVAISE_VALUATION est retournée.
     *
     * @param src  Le sommet source de l'arc.
     * @param dest Le sommet destination de l'arc.
     * @return La valeur de l'arc entre les sommets source et destination.
     */
    @Override
    public int getValuation(String src, String dest) {
        if (ladj.get(src) == null)
            return MAUVAISE_VALUATION;

        for (Arc arc : ladj.get(src)) {
            if (arc.getSource().equals(src) && arc.getDestination().equals(dest))
                return arc.getValuation();
        }
        return MAUVAISE_VALUATION;
    }

    /**
     * Vérifie si le graphe contient un sommet spécifié.
     *
     * @param sommet Le sommet à rechercher.
     * @return true si le graphe contient le sommet, sinon false.
     */
    @Override
    public boolean contientSommet(String sommet) {
        return ladj.containsKey(sommet);
    }

    /**
     * Vérifie si le graphe contient un arc entre deux sommets spécifiés.
     *
     * @param src  Le sommet source de l'arc.
     * @param dest Le sommet destination de l'arc.
     * @return true si le graphe contient l'arc entre les sommets source et destination, sinon false.
     */
    @Override
    public boolean contientArc(String src, String dest) {
        if (ladj.get(src) == null)
            return false;

        for (Arc arc : ladj.get(src)) {
            if (arc.equals(new Arc(src, dest)))
                return true;
        }
        return false;
    }

    /**
     * Retourne une représentation en chaîne de caractères du graphe.
     *
     * @return La représentation en chaîne de caractères du graphe.
     */
    @Override
    public String toString() {
        return toAString();
    }
}