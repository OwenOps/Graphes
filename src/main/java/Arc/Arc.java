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
    }

    public void removeDst() {
        dest = null;
    }

    public boolean equals(Arc c) {
        return src == c.src && dest == c.dest;
    }

    public void removeArc() {
        src = null;
        dest = null;
        valuation = 0;
    }

    public String toString() {
        if (dest == null)
            return src + ":";
        return src + "-" + dest + "(" + valuation + ")";
    }
}
