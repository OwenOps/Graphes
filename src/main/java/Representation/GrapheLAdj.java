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
        if (!(ladj.containsKey(noeud)))
            ladj.put(noeud, new ArrayList<>());
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        List<Arc> ajoutArc = new ArrayList<>();

        //Verification si arc est deja present
        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            if (entry.getKey().equals(source)) {
                //On stocke les liste d'arcs dans une autre liste pour y acceder plus facilement
                List<Arc> arcs = entry.getValue();
                if (arcs.contains(new Arc(source,destination)))
                    return;
                else
                    arcs.add(new Arc(source,destination,valeur));
            }
        }
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
        return ladj.containsKey(sommet);
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
