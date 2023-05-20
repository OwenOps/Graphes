package graphe.implems;

import graphe.core.IGraphe;
import graphe.core.Arc;
import java.util.*;

public class GrapheLArcs implements IGraphe {
    private List<Arc> arcs;
    private static final int MAUVAISE_VALUATION = -1;

    /**
     * Constructeur par défaut de la classe GrapheLArcs sans graphe.
     */
    public GrapheLArcs() {
        arcs = new ArrayList<>();
    }

    /**
     * Constructeur de la classe GrapheLArcs avec un graphe.
     *
     * @param graphe la chaîne de caractères représentant le graphe.
     */
    public GrapheLArcs(String graphe) {
        this();
        peupler(graphe);
    }


    /**
     * Ajoute un sommet au graphe s'il n'est pas déjà présent.
     *
     * @param noeud Le nom du sommet à ajouter.
     */
    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud)) {
            arcs.add(new Arc(noeud, "", 0));
        }
    }

    /**
     * Ajoute un arc au graphe entre un sommet source et un sommet destination avec une valeur spécifiée.
     * Les sommets source et destination seront créés s'ils n'existent pas déjà dans le graphe.
     *
     * @param source      Le nom du sommet source de l'arc.
     * @param destination Le nom du sommet destination de l'arc.
     * @param valeur      La valeur de l'arc.
     * @throws IllegalArgumentException si l'arc existe deja ou la valeur passe en argument est inferieur à zéro
     */
    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (contientArc(source, destination) || valeur < 0) {
            throw new IllegalArgumentException();
        }

        ajouterSommet(source);
        ajouterSommet(destination);
        arcs.add(new Arc(source, destination, valeur));

        arcs.removeIf(arc -> arc.getDestination().equals("") && !getSucc(arc.getSource()).isEmpty());
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

        List<String> nouveauxSommets = getSommets();
        nouveauxSommets.remove(noeud);
        arcs.removeIf(arc -> arc.getSource().equals(noeud) || arc.getDestination().equals(noeud));

        for (String sommet : nouveauxSommets) {
            if (!getSommets().contains(sommet) && !sommet.equals(noeud)) {
                ajouterSommet(sommet);
            }
        }
    }

    /**
     * Supprime l'arc spécifié du graphe.
     *
     * @param source      Le sommet source de l'arc à supprimer.
     * @param destination Le sommet destination de l'arc à supprimer.
     * @throws IllegalArgumentException si l'arc spécifié n'existe pas dans le graphe.
     */
    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination))
            throw new IllegalArgumentException();

        List<String> sommet = getSommets();
        arcs.removeIf(arc -> arc.equals(new Arc(source, destination)));

        for (String s : sommet) {
            if (getSucc(s).isEmpty() && !contientArc(s, ""))
                arcs.add(new Arc(s, "", 0));
        }
    }

    /**
     * Récupère la liste des sommets présents dans le graphe.
     * Les sommets sont triés par ordre croissant.
     *
     * @return La liste des sommets du graphe.
     */
    @Override
    public List<String> getSommets() {
        Set<String> sommet = new HashSet<>();

        for (Arc arc : arcs) {
            sommet.add(arc.getSource());
            if (!arc.getDestination().equals(""))
                sommet.add(arc.getDestination());
        }

        List<String> sommetTrie = new ArrayList<>(sommet);
        Collections.sort(sommetTrie);
        return sommetTrie;
    }

    /**
     * Récupère la liste des successeurs d'un sommet spécifié dans le graphe.
     *
     * @param sommet Le sommet pour lequel on souhaite obtenir les successeurs.
     * @return La liste des successeurs du sommet spécifié par ordre croissant.
     */
    @Override
    public List<String> getSucc(String sommet) {
        List<String> arcSucc = new ArrayList<>();
        for (Arc arc : arcs) {
            if (arc.getSource().equals(sommet) && !arc.getDestination().equals("")) {
                arcSucc.add(arc.getDestination());
            }
        }
        Collections.sort(arcSucc);
        return arcSucc;
    }

    /**
     * Récupère la valuation de l'arc spécifié dans le graphe.
     *
     * @param src  Le sommet source de l'arc.
     * @param dest Le sommet destination de l'arc.
     * @return La valuation de l'arc spécifié ou la constante MAUVAISE_VALUATION si l'arc n'est pas trouvé.
     */
    @Override
    public int getValuation(String src, String dest) {
        for (Arc arc : arcs) {
            if (arc.equals(new Arc(src, dest)))
                return arc.getValuation();
        }
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
        if (sommet.equals(""))
            return false;
        return getSommets().contains(sommet);
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
        return getValuation(src, dest) != MAUVAISE_VALUATION;
    }

    /**
     * Renvoie une représentation sous forme de chaîne de caractères du graphe.
     *
     * @return La représentation sous forme de chaîne de caractères du graphe.
     */
    @Override
    public String toString() {
        return toAString();
    }
}