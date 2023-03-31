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
    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud))
            arcs.add(new Arc(noeud));
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (contientArc(source,destination)) {
            throw new IllegalArgumentException();
        };
        ajouterSommet(source);
        ajouterSommet(destination);
        arcs.add(new Arc(source,destination,valeur));

        //Apres avoir mis les arcs, on enleve les sommets qui sont seuls.
        enleveSommetEnTrop();
    }

    private void enleveSommetEnTrop() {
        for (int i = 0; i < arcs.size(); i++) {
            if (arcs.get(i).getDest() == null && trouveSucc(arcs.get(i).getSrc())) {
                arcs.remove(i);
            }
        }
    }

    //Fonction pour trouver au minimum 1 successeur
    public boolean trouveSucc(String source) {
        for (Arc arc : arcs) {
            if (arc.getSrc().equals(source) && isNotNull(arc.getDest()))
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
            if (isNotNull(arcs.get(i).getSrc())) {
                if (arcs.get(i).getSrc().equals(noeud)) {
                    arcs.remove(i);
                    i--;
                    continue;
                }
            }

            if (isNotNull(arcs.get(i).getDest())) {
                if (arcs.get(i).getDest().equals(noeud)) {
                    arcs.remove(i);
                    i--;
                    continue;
                }
            }
            i--;
        }
    }

    //Quand on utilise equals et que ca renvoie un null, le programme plante.
    public boolean isNotNull(String noeud) {
        return noeud != null;
    }
    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source,destination))
            throw new IllegalArgumentException();

        for (int i = 0; i < arcs.size(); i++) {
            if (isNotNull(arcs.get(i).getSrc()) && isNotNull(arcs.get(i).getDest())) {
                if (arcs.get(i).equals(new Arc(source,destination))) {
                    if (arcs.get(i).getSrc().equals(source))
                        arcs.get(i).removeDst();
                    else
                        arcs.get(i).removeSrc();
                }
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
            if (arc.getSrc().equals(sommet) && arc.getDest() != null) {
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
        if (sommet == null)
            return false;
        //Il va chercher dans la liste tout les sommets.
        for (Arc arc : arcs) {
            if (isNotNull(arc.getDest())) {
                if (arc.getDest().equals(sommet))
                    return true;
            }

            if (isNotNull(arc.getSrc())) {
                if (arc.getSrc().equals(sommet))
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean contientArc(String src, String dest) {
        if (dest == null)
            return false;

        Arc a = new Arc(src,dest);
        for (Arc arc : arcs) {
            if (isNotNull(arc.getDest()))
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
