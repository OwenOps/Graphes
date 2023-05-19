package graphe.implems;

import graphe.core.IGraphe;
import graphe.core.Arc;
import java.util.*;

public class GrapheLArcs implements IGraphe {
    private List<Arc> arcs;
    private static final int MAUVAISE_VALUATION = -1;

    public GrapheLArcs() {
        arcs = new ArrayList<>();
    }

    public GrapheLArcs(String graphe) {
        this();
        peupler(graphe);
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud)) {
            arcs.add(new Arc(noeud, "", 0));
        }
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (contientArc(source, destination) || valeur < 0) {
            throw new IllegalArgumentException();
        }

        ajouterSommet(source);
        ajouterSommet(destination);
        arcs.add(new Arc(source, destination, valeur));

        //Enleve les sommets en trop dans la liste d'arc
        arcs.removeIf(arc -> arc.getDestination().equals("") && !getSucc(arc.getSource()).isEmpty());
    }

    @Override
    public void oterSommet(String noeud) {
        if (!contientSommet(noeud))
            return;

        List<String> newSommet = getSommets();
        newSommet.remove(noeud);
        arcs.removeIf(arc -> arc.getSource().equals(noeud) || arc.getDestination().equals(noeud));

        for (String sommet : newSommet) {
            if (!getSommets().contains(sommet) && !sommet.equals(noeud)) {
                ajouterSommet(sommet);
            }
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination))
            throw new IllegalArgumentException();

        List<String> sommet = getSommets();
        arcs.removeIf(arc -> arc.equals(new Arc(source,destination)));

        for (String s : sommet) {
            if (getSucc(s).isEmpty() && !contientArc(s, ""))
                arcs.add(new Arc(s, "", 0));
        }
    }

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

    @Override
    public int getValuation(String src, String dest) {
        for (Arc arc : arcs) {
            if (arc.equals(new Arc(src, dest)))
                return arc.getValuation();
        }
        return MAUVAISE_VALUATION;
    }

    @Override
    public boolean contientSommet(String sommet) {
        if (sommet.equals(""))
            return false;
        return getSommets().contains(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        return getValuation(src,dest) != MAUVAISE_VALUATION;
    }

    @Override
    public String toString() {
        return toAString();
    }
}