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
        //Cette fonction ajoute deja les sommets et arcs.
        peupler(graphe);
    }

    public List<Arc> getArcs() {
        return arcs;
    }

    private boolean isNull(Arc arc) {
        return arc.getDest() == null && arc.getSrc() == null;
    }

//    private Arc setArcFactice(Arc arc) {
//        arc.setValuation(0);
//        arc.setDest("");
//        return arc;
//    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud))
            arcs.add(new Arc(noeud));
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        for (int i = 0; i < arcs.size(); i++) {
            if (!(arcs.get(i).equals(new Arc(source, destination)))) {
                arcs.add(new Arc(source, destination, valeur));
                return;
            }
        }
    }

    @Override
    public void oterSommet(String noeud) {
        if (!contientSommet(noeud))
            return;

        for (int i = 0; i < arcs.size(); i++) {
            
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        for (int i = 0; i < arcs.size(); i++) {
            if (arcs.get(i).getSrc().equals(source) && arcs.get(i).getDest().equals(destination)) {
                arcs.remove(i);
                return;
            }
        }
    }

    @Override
    public List<String> getSommets() {
        //Un type d'ensemble qui n'accepte pas les doublons
        Set<String> sommet = new HashSet<>();

        for (Arc arc : arcs) {
            sommet.add(arc.getSrc());
            //J n'a pas de destination, donc cree un probleme.
            sommet.add(arc.getDest());
        }
        if (sommet.contains(null))
            sommet.removeAll(Collections.singleton(null));

        return new ArrayList<>(sommet);
    }
    @Override
    public List<String> getSucc(String sommet) {
        List<String> arcSucc = new ArrayList<>();
        for (Arc arc : arcs) {
            if (arc.getSrc().equals(sommet)) {
                arcSucc.add(arc.getDest());
            }
        }
        return arcSucc;
    }

    @Override
    public int getValuation(String src, String dest) {
        for (Arc arc : arcs) {
            if (arc.equals(new Arc(src,dest)))
                return arc.getValuation();
        }
        return -1;
    }

    @Override
    public boolean contientSommet(String sommet) {
        //Il va chercher dans la liste tout les sommets.
        for (Arc arc : arcs) {
            if (arc.getDest() == sommet)
                return true;
            if (arc.getSrc().equals(sommet))
                return true;
        }
        return false;
    }

    @Override
    public boolean contientArc(String src, String dest) {
        Arc a = new Arc(src,dest);
        for (Arc arc : arcs) {
            if (arc.equals(a))
                return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Arc arc : arcs) {
            sb.append(arc.toString());
            sb.append(", ");
        }
        //Enlever la derniere virgule
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}
