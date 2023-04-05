package Representation;

import Interface.IGraphe;

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
        indice.put(noeud, matrice.length);
        int[][] matrice2= new int[matrice.length+1][matrice.length+1];
        for(int i=0;i< matrice2.length;i++){
            for(int j=0;j< matrice2.length;j++){
                if (i==indice.get(noeud) || j== indice.get(noeud)){
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
        assert(matrice[indice.get(source)][indice.get(destination)]==-1);
        matrice[indice.get(source)][indice.get(destination)]=valeur;

    }

    @Override
    public void oterSommet(String noeud) {
        assert(matrice.length!=0);
        for (int i = 0 ; i < matrice.length; i++) {
            matrice[indice.get(noeud)][i] = -1;
            matrice[i][indice.get(noeud)] = -1;
        }
        indice.remove(noeud);
    }

    @Override
    public void oterArc(String source, String destination) {
        assert(matrice[indice.get(source)][indice.get(destination)]!=-1);
        matrice[indice.get(source)][indice.get(destination)] = -1;
    }

    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<String>();
        Set<String> cles = indice.keySet();
        for (String cle : cles) {
            sommets.add(cle);
        }
        return sommets;
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> succ = new ArrayList<String>();
        int i_sommet = indice.get(sommet);
        for (int j = 0; j < matrice.length; j++){
            if (matrice[i_sommet][j]!=-1){
                for (Map.Entry<String, Integer> successeur : indice.entrySet()) {
                    String key = successeur.getKey();
                    if(successeur.getValue()==j)
                        succ.add(successeur.getKey());
                }

            }
        }

        return succ;
    }

    @Override
    public int getValuation(String src, String dest) {
        return matrice[indice.get(src)][indice.get(dest)];
    }

    @Override
    public boolean contientSommet(String sommet) {
        Set<String> cles = indice.keySet();
        for (String cle : cles) {
            if (cle==sommet)
                return true;
        }
        return false;
    }

    @Override
    public boolean contientArc(String src, String dest) {
        return matrice[indice.get(src)][indice.get(dest)]!=-1;
    }

    /*public String toString() {

    }*/
}