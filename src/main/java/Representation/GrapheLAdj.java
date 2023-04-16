package Representation;
import graphe.IGraphe;
import Arc.Arc;

import java.util.*;

public class GrapheLAdj implements IGraphe {
    private Map<String, List<Arc>> ladj;
    private static final int MAUVAISE_VALUATION = -1;

    public GrapheLAdj() {
        ladj = new HashMap<>();
    }

    public GrapheLAdj(String graphe) {
        this();
        peupler(graphe);
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!(ladj.containsKey(noeud))) {
            ladj.put(noeud, new ArrayList<>());
        }
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (valeur < 0 || contientArc(source, destination))
            throw new IllegalArgumentException();

        ajouterSommet(source);
        ajouterSommet(destination);

        //Pour la source, on ajoute un Arc
        ladj.get(source).add(new Arc(source,destination,valeur));
    }

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

    @Override
    public List<String> getSommets() {
        List<String> arc = new ArrayList<>(ladj.keySet());
        Collections.sort(arc);
        return arc;
    }

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

    @Override
    public boolean contientSommet(String sommet) {
        return ladj.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        if (ladj.get(src) == null)
            return false;

        for (Arc arc : ladj.get(src)) {
            if (arc.equals(new Arc(src,dest)))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return toAString();
    }
}