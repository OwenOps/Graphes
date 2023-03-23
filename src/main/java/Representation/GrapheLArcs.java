package Representation;

import Sommet_Arc.Arc;

import java.util.ArrayList;
import java.util.List;

public class GrapheLArcs {
    private List<Arc> arcs;

    public GrapheLArcs(Arc arc) {
        this.arcs = new ArrayList<>();
        if (isNull(arc))
            arcs.add(setArcFactice(arc));
        else
            arcs.add(arc);
    }

    public List<Arc> getArcs() {
        return arcs;
    }

    private boolean isNull(Arc arc) {
        return arc.getDest() == null && arc.getSrc() == null;
    }

    private Arc setArcFactice(Arc arc) {
        arc.setValuation(0);
        arc.setDest("");
        return arc;
    }
}
