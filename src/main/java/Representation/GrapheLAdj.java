package Representation;
import Interface.IGraphe;
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

                List<Arc> arcs = entry.getValue();
                arcs.add(new Arc(source, destination, valeur));

                ladj.put(source, arcs);
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
                        if (newLi.get(i).getSource().equals(source))
                            newLi.get(i).removeDestination();
                        else
                            newLi.get(i).removeSource();
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
        //On accede pour chaque clef, le type associe --> la liste des arcs.
        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            //On stocke la liste pour ensuite la parcourir
            List<Arc> arcList = entry.getValue();
            for (Arc arc : arcList) {
                if (!arc.getSource().equals("") && !arc.getDestination().equals(""))
                    if (arc.getSource().equals(src) && arc.getDestination().equals(dest))
                        return true;
            }
        }
        return false;
    }

    public List<String> triee() {
        List<String> someTrie = new ArrayList<>();
        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            List<Arc> arc = entry.getValue();
            if (arc.isEmpty()) {
                someTrie.add(entry.getKey() + ":");
            }
            for (Arc a : arc) {
                someTrie.add(a.toString());
            }
        }
        Collections.sort(someTrie);
        return someTrie;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<String> sommetTrie = triee();

        for (String st : sommetTrie) {
            sb.append(st);
            sb.append(", ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}