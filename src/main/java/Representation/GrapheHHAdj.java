package Representation;

import graphe.IGraphe;

import java.util.*;

public class GrapheHHAdj implements IGraphe {
    private Map<String, Map<String, Integer>> hhadj;

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
            if (entry.getValue().get(noeud) != null) {
                Map<String, Integer> sommet2 = entry.getValue();
                //Parcours de la map du sommet qui a comme destination le noeud en question
                for (Map.Entry<String, Integer> entry2 : sommet2.entrySet()) {
                    //Si pour une destination il trouve le noeud, on recupere ce sommet puis on enleve le noeud
                    hhadj.get(entry2.getKey()).remove(noeud);
                }
            }
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination))
            throw new IllegalArgumentException();

        if (!(hhadj.get(source).containsKey("")) && getSucc(source).isEmpty())
            hhadj.get(source).put("", 0);
        hhadj.get(source).remove(destination);
    }

    @Override
    public List<String> getSommets() {
        List<String> sommet = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : hhadj.entrySet()) {
            sommet.add(entry.getKey());
        }
        Collections.sort(sommet);
        return sommet;
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> succ = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : hhadj.entrySet()) {
            if (entry.getKey().equals(sommet)) {
                Map<String, Integer> dest = entry.getValue();
                for (Map.Entry<String, Integer> destEntry : dest.entrySet()) {
                    if (!destEntry.getKey().equals("")) {
                        succ.add(destEntry.getKey());
                    }
                }
            }
        }
        return succ;
    }

    @Override
    public int getValuation(String src, String dest) {
        for (Map.Entry<String, Map<String, Integer>> entry : hhadj.entrySet()) {
            if (entry.getKey().equals(src) && !entry.getValue().isEmpty()) {
                Map<String, Integer> destVal = entry.getValue();
                for (Map.Entry<String, Integer> entryVal : destVal.entrySet()) {
                    if (entryVal.getKey().equals(dest)) {
                        //La valeur de entryVal c'est un Integer
                        return entryVal.getValue();
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public boolean contientSommet(String sommet) {
        if (hhadj.containsKey(sommet))
            return true;

        for (Map.Entry<String, Map<String, Integer>> entry : hhadj.entrySet()) {
            if (entry.getValue().containsKey(sommet))
                return true;
        }
        return false;
    }

    @Override
    public boolean contientArc(String src, String dest) {
        for (Map.Entry<String, Map<String, Integer>> entry : hhadj.entrySet()) {
            if (src != null) {
                if (entry.getKey().equals(src) && entry.getValue().containsKey(dest))
                    return true;
            }
        }
        return false;
    }

    public List<String> triee() {
        List<String> newSomm = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : hhadj.entrySet()) {
            if (entry.getValue().isEmpty()) {
                newSomm.add(entry.getKey() + ":");
            }
            for (Map.Entry<String, Integer> entry2 : entry.getValue().entrySet()) {
                if (entry2.getKey().equals("")) {
                    newSomm.add(entry.getKey() + ":");
                }
                else
                {
                    newSomm.add(entry.getKey() + "-" + entry2.getKey() + "(" + entry2.getValue() + ")");
                }
            }
        }
        Collections.sort(newSomm);
        return newSomm;
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