package Representation;

import graphe.IGraphe;

import java.util.*;

public class GrapheMAdj implements IGraphe {
    private int[][] matrice;
    private Map<String, Integer> indice;

    public GrapheMAdj() {
        matrice = new int[0][0];
        indice = new HashMap<>();
    }

    public GrapheMAdj(String graphe){
        this();
        peupler(graphe);
    }

    @Override
    public void ajouterSommet(String noeud) {
        if(indice.containsKey(noeud))
            return;

        indice.put(noeud, matrice.length);
        int[][] matrice2= new int[matrice.length+1][matrice.length+1];
        for(int i=0;i< matrice2.length;i++){
            for(int j=0;j< matrice2.length;j++){
                if (i == indice.get(noeud) || j == indice.get(noeud)){
                    matrice2[i][j]=-1;
                }
                else {
                    matrice2[i][j]=matrice[i][j];
                }
            }
        }
        matrice=matrice2;
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (contientArc(source, destination) || valeur < 0)
            throw new IllegalArgumentException();

        ajouterSommet(source);
        ajouterSommet(destination);

        matrice[indice.get(source)][indice.get(destination)] = valeur;
    }

    @Override
    public void oterSommet(String noeud) {
        if (!contientSommet(noeud))
            return;

        Map<String, Integer> indice2 = new HashMap<>();
        int indice_sommet=indice.get(noeud);
        Set<String> cles = indice.keySet();

        for (String cle : cles) {
            int ex_indice=indice.get(cle);
            if (ex_indice < indice_sommet){
                indice2.put(cle,ex_indice);
            } else if (ex_indice > indice_sommet) {
                indice2.put(cle,ex_indice-1);
            }
        }

        indice=indice2;
        int[][] matrice2= new int[matrice.length-1][matrice.length-1];
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice.length; j++) {
                if (i < indice_sommet && j < indice_sommet){
                    matrice2[i][j]=matrice[i][j];
                } else if (i < indice_sommet && j > indice_sommet) {
                    matrice2[i][j-1]=matrice[i][j];
                } else if (i > indice_sommet && j < indice_sommet) {
                    matrice2[i-1][j]=matrice[i][j];
                } else if (i > indice_sommet && j > indice_sommet) {
                    matrice2[i-1][j-1]=matrice[i][j];
                }
            }
        }
        matrice=matrice2;
    }

    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination))
            throw new IllegalArgumentException();

        matrice[indice.get(source)][indice.get(destination)] = -1;
    }

    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<>(indice.keySet());
        Collections.sort(sommets);
        return sommets;
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> succ = new ArrayList<String>();
        if (contientSommet(sommet)){
            int i_sommet = indice.get(sommet);
            for (int j = 0; j < matrice.length; j++){
                if (matrice[i_sommet][j] >= 0){
                    for (Map.Entry<String, Integer> successeur : indice.entrySet()) {
                        String key = successeur.getKey();
                        if(indice.get(key)==j)
                            succ.add(key);
                    }
                }
            }
        }
        return succ;
    }

    @Override
    public int getValuation(String src, String dest) {
        if (contientArc(src, dest)){
            return matrice[indice.get(src)][indice.get(dest)];
        }
        return -1;
    }

    @Override
    public boolean contientSommet(String sommet) {
        Set<String> cles = indice.keySet();
        for (String cle : cles) {
            if (cle.equals(sommet))
                return true;
        }
        return false;
    }

    @Override
    public boolean contientArc(String src, String dest) {
        if (indice.get(src) == null || indice.get(dest) == null)
            return false;

        return matrice[indice.get(src)][indice.get(dest)] != -1;
    }

    public List<String> triee(Set<String> cle) {
        List<String> listeTriee = new ArrayList<>();

        for (String cles : cle) {
            listeTriee.add(cles);
        }
        Collections.sort(listeTriee);
        return listeTriee;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Set<String> sommet = indice.keySet();

        for (String cle1 : triee(sommet)) {
            int cpt = 0;
            for (String cle2 : triee(sommet)) {
                if (matrice[indice.get(cle1)][indice.get(cle2)] >= 0) {
                    sb.append(cle1 + "-" + cle2 + "(" + getValuation(cle1, cle2) + ")" + ", ");
                    ++cpt;
                }
            }
            if (cpt == 0) {
                sb.append(cle1 + ":" + ", ");
            }
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}