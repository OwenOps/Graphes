package Representation;

import Interface.IGraphe;
import Arc.Arc;

import java.util.ArrayList;
import java.util.List;

public class GrapheLArcs implements IGraphe {
    private List<Arc> arcs;

    public GrapheLArcs(Arc arc) {
        this.arcs = new ArrayList<>();
        if (isNull(arc))
            arcs.add(setArcFactice(arc));
        else
            arcs.add(arc);
    }

    public List<Arc> getArcs() {
        return arcs;
    }

    private boolean isNull(Arc arc) {
        return arc.getDest() == null && arc.getSrc() == null;
    }

    private Arc setArcFactice(Arc arc) {
        arc.setValuation(0);
        arc.setDest("");
        return arc;
    }

    @Override
    public void ajouterSommet(String noeud) {

    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {

    }

    @Override
    public void oterSommet(String noeud) {

    }

    @Override
    public void oterArc(String source, String destination) {

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
}
