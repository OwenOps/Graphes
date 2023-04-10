

import graphe.IGrapheConst;

import java.util.*;

public class Main {
    /***
     *
     * @param graphe Le graphe (representation) sur lequel appliquer l'algorithme.
     * @param source source est le sommet de départ,
     * @param dist dist est retourné par l'algorithme
     * @param pred  chaque sommet est associé son prédécesseur dans un plus court chemin depuis la source
     * @return  La map "dist" remplie avec les distances minimales
     * ***/
    public static void dijkstra(IGrapheConst graphe, String source, Map<String, Integer> dist, Map<String, String> pred) {
        if (!graphe.getSommets().contains(source))
            return;

        dist = new HashMap<>();
        pred = new HashMap<>();
        List<String> sommet = new ArrayList<>(graphe.getSommets());
        Map<String, Integer> dest = new HashMap<>();

        if (graphe.getSucc(source).isEmpty()) {
            //Si plus de succ et il y a encore des sommets
            //On recherche la plus petite valeur.
        }

        for (String st : graphe.getSucc(source)) {
            dest.put(st, graphe.getValuation(source,st));
        }
        int plusPetiteVal = Collections.min(dest.values());

        for (Map.Entry<String, Integer> entry : dest.entrySet()) {
            if (entry.getValue() == plusPetiteVal) {
                dist.put(entry.getKey(), plusPetiteVal);
                pred.put(entry.getKey(), source);
                source = entry.getKey();
                sommet.remove(source);
                break;
            }
        }

        //On va remove les sommets au fur et à mesure qu'on va prendre les successeurs
        while (!sommet.isEmpty()) {


        }

    }
}
