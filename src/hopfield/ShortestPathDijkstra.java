package hopfield;

import cap7.Dijkstra;
import cap7.listaadj.autoreferencia.Grafo;

public class ShortestPathDijkstra {

	   private String caminho;
		
	   public Grafo.Aresta leiaAresta (int u, int v, double p) {
	        int v1 = u, v2 = v;
	        double peso = p;
	        return new Grafo.Aresta (v1, v2, peso);
	      }    
	    
	    ShortestPathDijkstra(int NC, int s, int d, double[][] C) throws Exception{
	    //System.out.print ("No. vertices:"); 
	    int nVertices = NC;
	    //System.out.print ("No. arestas:"); 
	    int nArestas = NC*NC;
	    
	    //System.out.print ("Raiz da ACMC:"); 
	    int raiz = s; //cidade de origem!
	    
	    Grafo grafo = new Grafo (nVertices);
	    
	    //System.out.println("DJK**************");
	    for (int i = 0; i < NC; i++) {
	    	for (int j = 0; j < NC; j++) {   
	    		if (i != j && C[i][j] > 0){
	    			//System.out.println(Cdj[i][j]);
	    			Grafo.Aresta a = leiaAresta (i, j, C[i][j]);
	    			//@{\it Duas chamadas porque o grafo \'e n\~ao direcionado}@
	    			grafo.insereAresta (a.v1 (), a.v2 (), a.peso ());     
	    			//grafo.insereAresta (a.v2 (), a.v1 (), a.peso ());
	    		}
	    	}
	    }
	    //grafo.imprime ();
	    Dijkstra dj = new Dijkstra (grafo);
	    dj.obterArvoreCMC (raiz);
	    //dj.imprime ();
	    //System.out.println(dj.retornaCaminho (s, d));
	    caminho = dj.retornaCaminho (s, d);
	  }
	
	    public String getRotaDijkstra(){
	    	
	    	return caminho;	    	
	    }
}
