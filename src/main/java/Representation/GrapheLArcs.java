package Representation;

import Interface.IGraphe;
import Arc.Arc;

import java.util.*;

public class GrapheLArcs implements IGraphe {
    private List<Arc> arcs;

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
            if (arcs.get(i).getDestination().equals("") && trouveSucc(arcs.get(i).getSource())) {
                arcs.remove(i);
            }
        }
    }

    //Fonction pour trouver au minimum 1 successeur
    public boolean trouveSucc(String source) {
        for (Arc arc : arcs) {
            if (arc.getSource().equals(source) && !arc.getDestination().equals(""))
                return true;
        }
        return false;
    }

    @Override
    public void oterSommet(String noeud) {
        if (!contientSommet(noeud))
            return;

        int i = arcs.size() - 1;
        while (i >= 0) {
            if (arcs.get(i).getSource().equals(noeud)) {
                arcs.remove(i);
                i--;
                continue;
            }

            if (arcs.get(i).getDestination().equals(noeud)) {
                arcs.remove(i);
                i--;
                continue;
            }
            i--;
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination))
            throw new IllegalArgumentException();

        for (int i = 0; i < arcs.size(); i++) {
            if (arcs.get(i).equals(new Arc(source, destination))) {
                if (arcs.get(i).getSource().equals(source))
                    arcs.get(i).removeDestination();
                else
                    arcs.get(i).removeSource();
            }
        }
    }

    @Override
    public List<String> getSommets() {
        Set<String> sommet = new HashSet<>();

        for (Arc arc : arcs) {
            sommet.add(arc.getSource());
            sommet.add(arc.getDestination());
        }
        sommet.remove("");

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
        return arcSucc;
    }

    @Override
    public int getValuation(String src, String dest) {
        for (Arc arc : arcs) {
            if (arc.equals(new Arc(src, dest)))
                return arc.getValuation();
        }
        return -1;
    }

    @Override
    public boolean contientSommet(String sommet) {
        if (sommet.equals(""))
            return false;

        for (Arc arc : arcs) {
            if (arc.getDestination().equals(sommet))
                return true;

            if (arc.getSource().equals(sommet))
                return true;
        }
        return false;
    }

    @Override
    public boolean contientArc(String src, String dest) {
        Arc a = new Arc(src, dest);
        for (Arc arc : arcs) {
            if (arc.getSource().equals(src) && arc.getDestination().equals(dest))
                return true;
        }
        return false;
    }

    public List<String> triee() {
        List<String> sommetDest = new ArrayList<>();
        for (Arc arc : arcs) {
            sommetDest.add(arc.toString());
        }
        Collections.sort(sommetDest);

        return sommetDest;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        List<String> sommet = triee();
        for (String st : sommet) {
            sb.append(st);
            sb.append(", ");
        }
        //Enlever la derniere virgule
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}