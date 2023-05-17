package graphe.implems;

import graphe.core.IGraphe;

import java.util.*;

public class GrapheHHAdj implements IGraphe {
    private Map<String, Map<String, Integer>> hhadj;
    private static final int MAUVAISE_VALUATION = -1;

    public GrapheHHAdj() {
        hhadj = new HashMap<>();
    }

    public GrapheHHAdj(String graphe) {
        this();
        peupler(graphe);
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud))
            hhadj.put(noeud, new HashMap<>());
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (contientArc(source, destination) || valeur < 0)
            throw new IllegalArgumentException();

        ajouterSommet(source);
        ajouterSommet(destination);

        hhadj.get(source).put(destination, valeur);
    }

    @Override
    public void oterSommet(String noeud) {
        if (!contientSommet(noeud))
            return;

        hhadj.remove(noeud);
        for (Map.Entry<String, Map<String, Integer>> entry : hhadj.entrySet()) {
            if (hhadj.get(entry.getKey()).get(noeud) != null)
                hhadj.get(entry.getKey()).remove(noeud);
        }
    }
    @Override
    public void oterArc(String source, String destination) {
        if(!contientArc(source, destination)) {
            throw new IllegalArgumentException();
        }
        hhadj.get(source).remove(destination);
    }

    @Override
    public List<String> getSommets() {
        List<String> sommet = new ArrayList<>(hhadj.keySet());
        Collections.sort(sommet);
        return sommet;
    }

    @Override
    public List<String> getSucc(String sommet) {
        if (hhadj.get(sommet) == null)
            return new ArrayList<>();
        //Rappel : meme interface ducoup peut faire directement une array list des clefs "keySet()"
        return new ArrayList<>(hhadj.get(sommet).keySet());
    }

    @Override
    public int getValuation(String src, String dest) {
        if (contientArc(src,dest))
            return hhadj.get(src).get(dest);
        return MAUVAISE_VALUATION;
    }

    @Override
    public boolean contientSommet(String sommet) {
        return hhadj.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        return hhadj.containsKey(src) && hhadj.containsKey(dest) && hhadj.get(src).get(dest) != null;
    }

    public String toString() {
        return toAString();
    }
}