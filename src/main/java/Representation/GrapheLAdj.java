package Representation;

import Sommet_Arc.Arc;
import Sommet_Arc.Sommet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheLAdj {
    private Map<String, List<Arc>> ladj;

    public GrapheLAdj(Sommet sommet) {
        ladj = new HashMap<>();

    }
}
