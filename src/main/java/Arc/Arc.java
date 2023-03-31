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

    public Arc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public String getSrc() {
        return src;
    }

    public int getValuation() {
        return valuation;
    }

    public void removeSrc() {
        src = null;
        valuation = -1;
    }

    public void removeDst() {
        dest = null;
        valuation = -1;
    }

    public boolean equals(Arc c) {
        // Vérifier si l'un des deux objets est null pour eviter lever exception en utilisant un equals
        if (c.dest == null || dest == null) {
            return false;
        }

        return src.equals(c.src) && dest.equals(c.dest);
    }

    public String toString() {
        if (dest == null)
            return src + ":";
        if (src == null)
            return  ":" + dest;
        return src + "-" + dest + "(" + valuation + ")";
    }
}
