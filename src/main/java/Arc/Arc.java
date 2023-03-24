package Arc;

public class Arc {
    private String src, dest;
    private int valuation;

    public Arc(String src, String dest, int valuation) {
        assert (valuation < 0);
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

    public Arc getArc() {
        return this;
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
