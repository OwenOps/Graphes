package Sommet_Arc;

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

    public int getValuation() {
        return valuation;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setValuation(int valuation) {
        this.valuation = valuation;
    }
}
