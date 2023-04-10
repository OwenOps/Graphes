package Arc;

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

    public boolean equals(Arc c) {
        return src.equals(c.src) && dest.equals(c.dest);
    }

    public String toString() {
        if (dest.equals(""))
            return src + ":";
        return src + "-" + dest + "(" + valuation + ")";
    }
}
