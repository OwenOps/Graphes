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
        if (!(ladj.containsKey(noeud)))
            ladj.put(noeud, new ArrayList<>());
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (!(ladj.containsKey(source) || ladj.containsKey(destination))) {
            ajouterSommet(source);
            ajouterSommet(destination);
        }

        //Verification si arc est deja present
        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            if (entry.getKey().equals(source)) {
                //On stocke les liste d'arcs dans une autre liste pour y acceder plus facilement
                List<Arc> arcs = entry.getValue();
                if (contientArc(source,destination)) {
                    throw new IllegalArgumentException();
                }
                else
                {
                    ajouterSommet(source);
                    ajouterSommet(destination);
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

            //Quand on surpprime un arc, il faut mettre a jour pour ne pas depasser la limite
            int i = newLi.size() - 1;
            while (i >= 0) {
                if (isNotNull(newLi.get(i).getSrc())) {
                    if (newLi.get(i).getSrc().equals(noeud)) {
                        newLi.remove(i);
                        i--;
                        continue;
                    }
                }

                if (isNotNull(newLi.get(i).getDest())) {
                    if (newLi.get(i).getDest().equals(noeud)) {
                        newLi.remove(i);
                        i--;
                        continue;
                    }
                }
                i--;
            }
            //Mise a jour de la liste qui correspond au sommet
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
                if (arc.getSrc().equals(noeud) || arc.getDest().equals(noeud)) {
                    iterator.remove();
                }
            }
        }
    }*/




    //Quand on utilise equals et que ca renvoie un null, le programme plante.
    public boolean isNotNull(String noeud) {
        return noeud != null;
    }

    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source,destination))
            throw new IllegalArgumentException();

        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            List<Arc> newLi = entry.getValue();

            for (int i = 0; i < newLi.size(); i++) {
                if (isNotNull(newLi.get(i).getSrc()) && isNotNull(newLi.get(i).getDest())) {
                    if (newLi.get(i).equals(new Arc(source,destination))) {
                        if (newLi.get(i).getSrc().equals(source))
                            newLi.get(i).removeDst();
                        else
                            newLi.get(i).removeSrc();
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
                if (isNotNull(arc.getSrc()) && isNotNull(arc.getDest())) {
                    if (arc.getSrc().equals(source))
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
                if (isNotNull(arc.getDest()))
                    succ.add(arc.getDest());
            }
        }
        return succ;
    }

    @Override
    public int getValuation(String src, String dest) {
        List<Arc> listeArc = ladj.get(src);
        int valuation = -1;

        //On veut la valuation pour laquelle l'arc partant du sommet vaut celle de la destination
        for (Arc arc : listeArc) {
            if (isNotNull(arc.getSrc()) && isNotNull(arc.getDest())) {
                if (arc.getSrc().equals(src) && arc.getDest().equals(dest))
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
            for (Arc arc: arcList) {
                if (isNotNull(arc.getSrc()) && isNotNull(arc.getDest()))
                    if (arc.getSrc().equals(src) && arc.getDest().equals(dest))
                        return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            if (entry.getValue().isEmpty()) {
                sb.append(entry.getKey() + ":");
                sb.append(", ");
            }

            for (Arc arc : entry.getValue()) {
                sb.append(arc.toString());
                sb.append(", ");
            }
        }
        //Enlever la derniere virgule
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}
