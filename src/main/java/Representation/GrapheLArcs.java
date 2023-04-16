package Representation;

import graphe.IGraphe;
import Arc.Arc;
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

        enleveSommetEnTrop();
    }

    private void enleveSommetEnTrop() {
        for (int i = 0; i < arcs.size(); i++) {
            if (arcs.get(i).getDestination().equals("") && !getSucc(arcs.get(i).getSource()).isEmpty()) {
                arcs.remove(i);
            }
        }
    }

    @Override
    public void oterSommet(String noeud) {
        if (!contientSommet(noeud))
            return;

        List<String> newSommet = getSommets();
        for (int i = 0; i < newSommet.size(); i++) {
            if (newSommet.get(i).equals(noeud)) {
                newSommet.remove(i);
                break;
            }
        }

        int i = arcs.size() - 1;
        while (i >= 0) {
            if (arcs.get(i).getSource().equals(noeud) || arcs.get(i).getDestination().equals(noeud)) {
                arcs.remove(i);
            }
            i--;
        }

        // Vérifie si un sommet manque et est different du noeud
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
        for (int i = 0; i < arcs.size(); i++) {
            if (arcs.get(i).equals(new Arc(source, destination))) {
                arcs.remove(i);
                break;
            }
        }

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

        for (Arc arc : arcs) {
            if (arc.getDestination().equals(sommet) || arc.getSource().equals(sommet))
                return true;
        }
        return false;
    }

    @Override
    public boolean contientArc(String src, String dest) {
        for (Arc arc : arcs) {
            if (arc.getSource().equals(src) && arc.getDestination().equals(dest))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return toAString();
    }
}