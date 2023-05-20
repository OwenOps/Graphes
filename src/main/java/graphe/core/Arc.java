package graphe.core;

/**
 * Représente un arc dans un graphe.
 */
public class Arc {
    private String src, dest;
    private int valuation;

    /**
     * Constructeur d'un arc avec valuation.
     *
     * @param src       Le sommet source de l'arc.
     * @param dest      Le sommet destination de l'arc.
     * @param valuation La valuation associée à l'arc.
     */
    public Arc(String src, String dest, int valuation) {
        this.src = src;
        this.dest = dest;
        this.valuation = valuation;
    }

    /**
     * Constructeur d'un arc sans valuation.
     *
     * @param src  Le sommet source de l'arc.
     * @param dest Le sommet destination de l'arc.
     */
    public Arc(String src, String dest) {
        this.src = src;
        this.dest = dest;
    }

    /**
     * Récupère le sommet destination de l'arc.
     *
     * @return Le sommet destination de l'arc.
     */
    public String getDestination() {
        return dest;
    }

    /**
     * Récupère le sommet source de l'arc.
     *
     * @return Le sommet source de l'arc.
     */
    public String getSource() {
        return src;
    }

    /**
     * Récupère la valuation de l'arc.
     *
     * @return La valuation de l'arc.
     */
    public int getValuation() {
        return valuation;
    }

    /**
     * Vérifie l'égalité de l'arc avec un autre arc spécifié.
     *
     * @param c L'arc à comparer.
     * @return true si les arcs sont égaux (même source et même destination), false sinon.
     */
    public boolean equals(Arc c) {
        return src.equals(c.src) && dest.equals(c.dest);
    }

    /**
     * Renvoie une représentation sous forme de chaîne de caractères de l'arc.
     *
     * @return La représentation de l'arc sous forme de chaîne de caractères.
     */
    public String toString() {
        if (dest.equals(""))
            return src + ":";
        return src + "-" + dest + "(" + valuation + ")";
    }
}
