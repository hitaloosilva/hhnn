package cap7;
import cap7.listaadj.autoreferencia.Grafo; // @{\it vide Programa~\ref{prog:estruturaslistaap}}@
public class Dijkstra {
  String caminho = "";
  private int antecessor[];
  private double p[];
  private Grafo grafo;

  public Dijkstra (Grafo grafo) { this.grafo = grafo; }  
  public void obterArvoreCMC (int raiz) throws Exception {
    int n = this.grafo.numVertices();
    this.p = new double[n]; // @{\it peso dos v\'ertices}@
    int vs[] = new int[n+1]; // @{\it v\'ertices}@
    this.antecessor = new int[n];
    for (int u = 0; u < n; u ++) {
      this.antecessor[u] = -1;
      p[u] = Integer.MAX_VALUE; // @$\infty$@
      vs[u+1] = u; // @{\it Heap indireto a ser constru\'{\i}do}@
    }
    p[raiz] = 0;
    FPHeapMinIndireto heap = new FPHeapMinIndireto (p, vs); 
    heap.constroi ();
    while (!heap.vazio ()) {
      int u = heap.retiraMin (); 
      if (!this.grafo.listaAdjVazia (u)) {
        Grafo.Aresta adj = grafo.primeiroListaAdj (u);
        while (adj != null) {
          int v = adj.v2 ();
          if (this.p[v] > (this.p[u] + adj.peso ())) {
            antecessor[v] = u; 
            heap.diminuiChave (v, this.p[u] + adj.peso ());
          }
          adj = grafo.proxAdj (u);
        }
      }
    }
  }
  public int antecessor (int u) { return this.antecessor[u]; }
  public double peso (int u) { return this.p[u]; }
  public void imprime () {
    for (int u = 0; u < this.p.length; u++)
      if (this.antecessor[u] != -1) 
        System.out.println ("(" +antecessor[u]+ "," +u+ ") -- p:" +peso (u));

  }
  
  public void imprimeCaminho (int origem, int v) {
	    if (origem == v) System.out.print (origem + ";");
	    else if (this.antecessor[v] == -1) 
	      System.out.println ("Nao existe caminho de " +origem+ " ate " +v);
	    else {
	    	imprimeCaminho (origem, this.antecessor[v]);
	      System.out.print (v + ";");
	    }
  }
  
  public String retornaCaminho (int origem, int v) {
    if (origem == v) return origem + ";";
    else if (this.antecessor[v] == -1) 
    	return "Nao existe caminho de " +origem+ " ate " +v;
    else {
      caminho += retornaCaminho (origem, this.antecessor[v]);
      caminho +=  (v + ";");
      return caminho;
    }    
  }

}