package graphe.Dijkstra;

import graphe.core.IGrapheConst;
import java.util.*;

public class Dijkstra {
    /**
     * Algorithme Dijsktra
     *
     * @param graphe le graphe à parcourir, ne doit pas être null.
     * @param prev un dictionnaire de prédecesseurs qui sera rempli et ne doit pas être nul.
     * @param depart Sommet de départ, doit etre present dans le graphe.
     * @param dist un dictionnaire de distance qui sera rempli et toutes les distances doivent être positives ou nulles
     * @return une liste contenant tous les sommets du graphe, triée par ordre alphabétique.
     */
    public static List<String> dijkstra(IGrapheConst graphe, String depart, Map<String, Integer> dist, Map<String, String> prev) {
        PriorityQueue<String> nonVisites = new PriorityQueue<>(graphe.getSommets().size(), Comparator.comparingInt(s -> dist.getOrDefault(s, Integer.MAX_VALUE)));

        dist.put(depart, 0);
        nonVisites.add(depart);

        while (!nonVisites.isEmpty()) {
            String sommetActuelle = nonVisites.poll();

            for (String succ : graphe.getSucc(sommetActuelle)) {
                if (!graphe.contientArc(sommetActuelle, succ)) {
                    continue;
                }

                int poidsArc = graphe.getValuation(sommetActuelle, succ);
                int distSommetActuelle = dist.get(sommetActuelle) + poidsArc;

                if (!dist.containsKey(succ) || distSommetActuelle < dist.get(succ)) {
                    dist.put(succ, distSommetActuelle);
                    prev.put(succ, sommetActuelle);
                    nonVisites.add(succ);
                }
            }
        }

        return new ArrayList<>(dist.keySet());
    }

    /**
     * Le plus court chemin entre deux sommets
     *
     * @param graphe Graphe choisi à parcourir
     * @param arrive Sommet d'arrivée
     * @param depart Sommet départ
     * @return liste avec le plus court chemin entre les deux sommets et si aucun, retourne null
     */
    public static List<String> plusCourtChemin(IGrapheConst graphe, String depart, String arrive) {
        Map<String, String> pred = new HashMap<>();
        Map<String, Integer> dist = new HashMap<>();

        dijkstra(graphe, depart, dist, pred);
        if (!pred.containsKey(arrive)) {
            return null;
        }

        List<String> route = new ArrayList<>();
        String sommet = arrive;

        while (!sommet.equals(depart)) {
            route.add(0, sommet);
            sommet = pred.get(sommet);
        }
        route.add(0, depart);
        return route;
    }
}