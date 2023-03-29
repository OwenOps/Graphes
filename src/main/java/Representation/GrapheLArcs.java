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
        //Cette fonction appelle ajout Sommet et arc.
        peupler(graphe);
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
        if (!(arcs.contains(new Arc(source,destination))))
            arcs.add(new Arc(source,destination,valeur));

        //Apres avoir mis les arcs, on enleve les sommets qui sont seuls.
        for (int i = 0; i < arcs.size(); i++) {
            if (arcs.get(i).getDest() == null && trouveSucc(arcs.get(i).getSrc())) {
                arcs.remove(i);
            }
        }
    }

    //Fonction pour enlever les sommet que l'on a ajoute avant.
    public boolean trouveSucc(String source) {
        for (Arc arc : arcs) {
            if (arc.getSrc().equals(source) && arc.getDest() != null)
                return true;
        }
        return false;
    }

    @Override
    public void oterSommet(String noeud) {
        if (!contientSommet(noeud))
            return;

        for (Arc arc : arcs) {
            if (!(arc.getDest() == null || arc.getSrc() == null)) {
                if (arc.getSrc().equals(noeud)) {
                    arc.removeSrc();
                }

                if (arc.getDest().equals(noeud))
                    arc.removeDst();
            }
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
        if (dest == null)
            return false;

        Arc a = new Arc(src,dest);
        for (Arc arc : arcs) {
            if (!(arc.getDest() == null))
                if (arc.getSrc().equals(src) && arc.getDest().equals(dest))
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
