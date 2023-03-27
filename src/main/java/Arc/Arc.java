package Arc;

public class Arc {
    private String src, dest;
    private int valuation;

    public Arc(String src, String dest, int valuation) {
        this.src = src;
        this.dest = dest;
        this.valuation = valuation;
    }

    public String getDest() {
        return dest;
    }

    public String getSrc() {
        return src;
    }

    /*private int isNull(String src, String dest, int valuation) {

    }*/

    public int getValuation() {
        return valuation;
    }

    public void removeSrc() {
        src = null;
    }

    public void removeDst() {
        dest = null;
    }

    public void removeArc() {
        src = null;
        dest = null;
        valuation = 0;
    }

    public String toString() {
        return src + "-" + dest + "(" + valuation + ")";
    }
}
