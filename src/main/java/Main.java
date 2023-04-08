import Interface.IGrapheConst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        /*//On va remove les sommets
        while (!sommet.isEmpty()) {
            List<String> succ = new ArrayList<>(graphe.getSucc(source));
            for (int i = 0; i < ; i++) {
                
            }

        }*/

    }
}
