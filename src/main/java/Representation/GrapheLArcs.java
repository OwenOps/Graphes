package Representation;

import Interface.IGraphe;
import Arc.Arc;

import java.util.*;

public class GrapheLArcs implements IGraphe {
    private List<Arc> arcs;
    private List<String> sommets;
    public GrapheLArcs() {
        arcs = new ArrayList<>();
        sommets = new ArrayList<>();
    }
    public GrapheLArcs(String graphe) {
        this();
        peupler(graphe);
    }

    public List<Arc> getArcs() {
        return arcs;
    }

    /*private boolean isNull(Arc arc) {
        return arc.getDest() == null && arc.getSrc() == null;
    }

    private Arc setArcFactice(Arc arc) {
        arc.setValuation(0);
        arc.setDest("");
        return arc;
    }*/

    @Override
    public void ajouterSommet(String noeud) {
        for (String st : sommets) {
            if (!st.equals(noeud))
                sommets.add(noeud);
        }
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        for (Arc arc : arcs) {
            if (!(arc.getSrc().equals(source) && arc.getDest().equals(destination)))
                arcs.add(new Arc(source, destination, valeur));
        }
    }

    @Override
    public void oterSommet(String noeud) {
        for (int i = 0; i < arcs.size(); i++) {
            if (arcs.get(i).getSrc().equals(noeud))
                arcs.get(i).removeSrc();
        }
    }

    /*public void oterSommet2(String noeud) {
            sommets.remove(noeud);
    }*/

    @Override
    public void oterArc(String source, String destination) {
        for (int i = 0; i < arcs.size(); i++) {
            if (arcs.get(i).getSrc().equals(source) && arcs.get(i).getDest().equals(destination))
                arcs.remove(i);
        }
    }

    /*@Override
    public List<String> getSommets() {
        //Un type d'ensemble qui n'accepte pas les doublons
        Set<String> sommet = new HashSet<>();
        List<String> sommetFinal = new ArrayList<>();

        for (Arc arc: arcs) {
            sommet.add(arc.getSrc());
            sommet.add(arc.getDest());
        }
        //Pour tout ajouter directement.
        sommetFinal.addAll(sommet);
        //Range dans ordre les sommets.
        Collections.sort(sommetFinal);

        return sommetFinal;
    }*/
    @Override
    public List<String> getSommets() {
        return sommets;
        //return new ArrayList<>(sommets);
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> arcSucc = new ArrayList<>();
        for (Arc arc : arcs) {
            if (arc.getDest().equals(sommet))
                arcSucc.add(arc.getDest());
        }
        return arcSucc;
    }

    @Override
    public int getValuation(String src, String dest) {
        for (Arc arc : arcs) {
            if (arc.getSrc().equals(src) && arc.getDest().equals(dest))
                return arc.getValuation();
        }
        return -1;
    }

    @Override
    public boolean contientSommet(String sommet) {
        //Il va chercher dans la liste tout les sommets.
        return sommets.contains(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        for (Arc arc: arcs) {
            if (arc.getSrc().equals(src) && arc.getDest().equals(dest))
                return true;
        }
        return false;
    }

    /*public String toString() {

    }*/
}
