package Representation;

import Interface.IGraphe;
import Arc.Arc;

import java.util.*;

public class GrapheLAdj implements IGraphe {
    private Map<String, List<Arc>> ladj;

    public GrapheLAdj() {
        ladj = new HashMap<>();
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!(ladj.containsKey(noeud)))
            ladj.put(noeud, new ArrayList<>());
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (!(ladj.containsKey(source)))
            return;

        List<Arc> ajoutArc = new ArrayList<>();
        //Verification si arc est deja present
        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            if (entry.getKey().equals(source)) {
                //On stocke les liste d'arcs dans une autre liste pour y acceder plus facilement
                List<Arc> arcs = entry.getValue();
                if (arcs.contains(new Arc(source,destination)))
                    return;
                else {
                    arcs.add(new Arc(source, destination, valeur));
                }
            }
        }
        //On ajoute la nouvelle liste d'arc pour le sommet
        ladj.put(source, ajoutArc);
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
        return noeud == null;
    }

    @Override
    public void oterArc(String source, String destination) {
        if (!(ladj.containsKey(source) || ladj.containsKey(destination)))
            return;

        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            List<Arc> newLi = entry.getValue();

            for (int i = 0; i < newLi.size(); i++) {
                if (isNotNull(newLi.get(i).getSrc()) && isNotNull(newLi.get(i).getDest())) {
                    if (newLi.get(i).getSrc().equals(source))
                        newLi.get(i).removeSrc();
                    else
                        newLi.get(i).removeDst();
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
        if (!listeArc.isEmpty()) {
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
        int valuation = 0;

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

        for (Map.Entry<String, List<Arc>> entry: ladj.entrySet()) {
            sb.append(entry.getKey());
            sb.append(", ");

            for (int i = 0; i < entry.getValue().size(); i++) {
                sb.append(entry.getValue().get(i).getDest() + ",");
            }
            sb.append(" -- ");
        }
        return sb.toString();
    }
}
