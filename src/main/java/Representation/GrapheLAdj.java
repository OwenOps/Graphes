package Representation;
import graphe.IGraphe;
import Arc.Arc;

import java.util.*;

public class GrapheLAdj implements IGraphe {
    private Map<String, List<Arc>> ladj;

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
        if (valeur < 0 || contientArc(source,destination))
            throw new IllegalArgumentException();

        ajouterSommet(source);
        ajouterSommet(destination);

        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            if (entry.getKey().equals(source)) {
                entry.getValue().add(new Arc(source, destination, valeur));
                ladj.put(source, entry.getValue());
            }
        }
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
                if (newLi.get(i).getSource().equals(noeud)) {
                    newLi.remove(i);
                    i--;
                    continue;
                }

                if (newLi.get(i).getDestination().equals(noeud)) {
                    newLi.remove(i);
                    i--;
                    continue;
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

        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            List<Arc> newLi = entry.getValue();

            if (!newLi.isEmpty()) {
                for (int i = 0; i < newLi.size(); i++) {
                    if (newLi.get(i).equals(new Arc(source, destination))) {
                        newLi.remove(i);
                        break;
                    }
                }
            }
            //Mise à jour de la liste qui correspond au sommet
            ladj.put(entry.getKey(), newLi);
        }
    }

    @Override
    public List<String> getSommets() {
        List<String> arc = new ArrayList<>();
        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            arc.add(entry.getKey());
        }
        return arc;
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<Arc> listeArc = ladj.get(sommet);

        List<String> succ = new ArrayList<>();
        if (!(listeArc == null || listeArc.isEmpty())) {
            for (Arc arc : listeArc) {
                if (!arc.getDestination().equals(""))
                    succ.add(arc.getDestination());
            }
        }
        return succ;
    }

    @Override
    public int getValuation(String src, String dest) {
        List<Arc> listeArc = ladj.get(src);
        int valuation = -1;

        if (!(listeArc == null || listeArc.isEmpty())) {
            for (Arc arc : listeArc) {
                if (arc.getSource().equals(src) && arc.getDestination().equals(dest))
                    valuation = arc.getValuation();
            }
        }
        return valuation;
    }

    @Override
    public boolean contientSommet(String sommet) {
        return ladj.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            for (Arc arc : entry.getValue()) {
                if (arc.getSource().equals(src) && arc.getDestination().equals(dest))
                    return true;
            }
        }
        return false;
    }

    public List<String> triee() {
        List<String> someTrie = new ArrayList<>();
        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            if (entry.getValue().isEmpty()) {
                someTrie.add(entry.getKey() + ":");
            }
            for (Arc a : entry.getValue()) {
                someTrie.add(a.toString());
            }
        }
        Collections.sort(someTrie);
        return someTrie;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String st : triee()) {
            sb.append(st);
            sb.append(", ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}