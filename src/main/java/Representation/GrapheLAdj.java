package Representation;

import Interface.IGraphe;
import Arc.Arc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheLAdj implements IGraphe {
    private Map<String, List<Arc>> ladj;

    public GrapheLAdj() {
        ladj = new HashMap<>();
    }

    @Override
    public void ajouterSommet(String noeud) {
        // On ajoute le clef de type STRING = noeud et comme valeur une liste vide
        ladj.put(noeud, new ArrayList<>());
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        List<Arc> ajoutArc = new ArrayList<>();
        Arc a = new Arc(source,destination,valeur);
        ajoutArc.add(a);

        //On Ajoute l'arc pour le sommet source (a voir)
        ladj.put(source, ajoutArc);
    }

    @Override
    public void oterSommet(String noeud) {
        ladj.remove(noeud);
    }

    @Override
    public void oterArc(String source, String destination) {
        List<Arc> nouvelleListe;
        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            List<Arc> arcs = entry.getValue();
            for (int i = 0; i < arcs.size(); i++) {
                if (arcs.get(i).getSrc().equals(source) && arcs.get(i).getDest().equals(destination))
                    arcs.remove(i);
            }
        }
        //A finir, mettre a jour la Map .
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
        //On accede a la liste d'arc
        List<Arc> listeArc = ladj.get(sommet);

        //On cree la liste que l'on veut retourner
        List<String> succ = new ArrayList<>();
        if (!listeArc.isEmpty()) {
            for (int i = 0; i < listeArc.size(); i++) {
                succ.add(listeArc.get(i).getDest());
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
            if (arc.getSrc().equals(src) && arc.getDest().equals(dest))
                valuation = arc.getValuation();
        }
        return valuation;
    }

    @Override
    public boolean contientSommet(String sommet) {
        return ladj.get(sommet) != null;
    }

    @Override
    public boolean contientArc(String src, String dest) {
        List<Arc> contientArc = new ArrayList<>();

        //On accede pour chaque clef, le type associe --> la liste des arcs.
        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            //On stocke la liste pour ensuite la parcourir
            List<Arc> arcList = entry.getValue();
            for (Arc arc: arcList) {
                if (arc.getSrc().equals(src) && arc.getDest().equals(dest))
                    return true;
            }
        }
        return false;
    }
}
