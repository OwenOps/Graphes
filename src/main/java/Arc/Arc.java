package Arc;

import java.util.Objects;

public class Arc {
    private String src, dest;
    private int valuation;

    public Arc(String src, String dest, int valuation) {
        this.src = src;
        this.dest = dest;
        this.valuation = valuation;
    }

    public Arc(String src, String dest) {
        this.src = src;
        this.dest = dest;
    }

    public String getDestination() {
        return dest;
    }

    public String getSource() {
        return src;
    }

    public int getValuation() {
        return valuation;
    }

    public void removeSource() {
        src = "";
        valuation = -1;
    }

    public void removeDestination() {
        dest = "";
        valuation = -1;
    }

    public boolean equals(Arc c) {
        // Vérifier si l'un des deux objets est null pour eviter lever exception en utilisant un equals
        if (c.dest.equals("") || dest.equals("")) {
            return false;
        }

        return src.equals(c.src) && dest.equals(c.dest);
    }

    public String toString() {
        if (dest.equals(""))
            return src + ":";
        if (src.equals(""))
            return  ":" + dest;
        return src + "-" + dest + "(" + valuation + ")";
    }
}
