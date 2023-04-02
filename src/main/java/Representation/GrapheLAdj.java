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
        if (valeur < 0)
            throw new IllegalArgumentException();

        ajouterSommet(source);
        ajouterSommet(destination);

        //Verification si arc est deja present
        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            if (entry.getKey().equals(source)) {
                //On stocke les liste d'arcs dans une autre liste pour y acceder plus facilement
                List<Arc> arcs = entry.getValue();
                if (contientArc(source, destination)) {
                    throw new IllegalArgumentException();
                } else {
                    arcs.add(new Arc(source, destination, valeur));
                }
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

            //Quand on supprime un arc, il faut mettre à jour pour ne pas depasser la limite
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
            //Mise à jour de la liste qui correspond au sommet
            ladj.put(entry.getKey(), newLi);
        }
    }

    /*@Override
    public void oterSommet(String noeud) {
        if (!ladj.containsKey(noeud))
            return;

        // Supprime le sommet
        ladj.remove(noeud);

        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            Iterator<Arc> iterator = entry.getValue().iterator();
            while (iterator.hasNext()) {
                Arc arc = iterator.next();
                if (arc.getSource()().equals(noeud) || arc.getDestination()().equals(noeud)) {
                    iterator.remove();
                }
            }
        }
    }*/

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

    /*@Override
    public void oterArc(String source, String destination) {
        if (!(ladj.containsKey(source) || ladj.containsKey(destination)))
            return;

        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            //Comme en C++, un type qui va lire les valeurs de ce qu'on lui donne. Ici sera la liste.
            Iterator<Arc> iterator = entry.getValue().iterator();

            while (iterator.hasNext()) {
                Arc arc = iterator.next();
                if (isNotNull(arc.getSource()()) && isNotNull(arc.getDestination()())) {
                    if (arc.getSource()().equals(source))
                        arc.removeDst();
                    else
                        arc.removeSrc();
                }
            }
        }
    }*/

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
        //On accede a la liste d'arc
        List<Arc> listeArc = ladj.get(sommet);

        //On cree la liste que l'on veut retourner
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

        //On veut la valuation pour laquelle l'arc partant du sommet vaut celle de la destination
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

        //Enlever la derniere virgule
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}
