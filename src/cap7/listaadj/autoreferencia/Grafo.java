package cap7.listaadj.autoreferencia;

import cap3.autoreferencia.Lista; /*-- vide Programa@{\it ~\ref{Fig2.1.5}@ --*/

public class Grafo {

	public static class Aresta {
		private int v1, v2;
		double peso;

		public Aresta(int v1, int v2, double peso) {
			this.v1 = v1;
			this.v2 = v2;
			this.peso = peso;
		}

		public double peso() {
			return this.peso;
		}

		public int v1() {
			return this.v1;
		}

		public int v2() {
			return this.v2;
		}
	}

	public static class Celula {
		int vertice;
		double peso;

		public Celula(int v, double p) {
			this.vertice = v;
			this.peso = p;
		}

		public boolean equals(Object obj) {
			Celula item = (Celula) obj;
			return (this.vertice == item.vertice);
		}
	}

	private Lista adj[];
	private int numVertices;

	public Grafo(int numVertices) {
		this.adj = new Lista[numVertices];
		this.numVertices = numVertices;
		for (int i = 0; i < this.numVertices; i++)
			this.adj[i] = new Lista();
	} // @\lstcontinue@

	public void insereAresta(int v1, int v2, double peso) {
		Celula item = new Celula(v2, peso);
		this.adj[v1].insere(item);
	}

	public boolean existeAresta(int v1, int v2) {
		Celula item = new Celula(v2, 0);
		return (this.adj[v1].pesquisa(item) != null);
	}

	public boolean listaAdjVazia(int v) {
		return this.adj[v].vazia();
	}

	public Aresta primeiroListaAdj(int v) {
		// @{\it Retorna a primeira aresta que o v\'ertice v participa ou}@
		// @{\it {\bf NULL} se a lista de adjac\^encia de v for vazia}@
		Celula item = (Celula) this.adj[v].primeiro();
		return item != null ? new Aresta(v, item.vertice, item.peso) : null;
	}

	public Aresta proxAdj(int v) {
		// @{\it Retorna a pr\'oxima aresta que o v\'ertice v participa ou}@
		// @{\it {\bf null} se a lista de adjac\^encia de v estiver no fim}@
		Celula item = (Celula) this.adj[v].proximo();
		return item != null ? new Aresta(v, item.vertice, item.peso) : null;
	}

	public Aresta retiraAresta(int v1, int v2) throws Exception {
		Celula chave = new Celula(v2, 0);
		Celula item = (Celula) this.adj[v1].retira(chave);
		return item != null ? new Aresta(v1, v2, item.peso) : null;
	}

	public void imprime() {
		for (int i = 0; i < this.numVertices; i++) {
			System.out.println("Vertice " + i + ":");
			Celula item = (Celula) this.adj[i].primeiro();
			while (item != null) {
				System.out
						.println("  " + item.vertice + " (" + item.peso + ")");
				item = (Celula) this.adj[i].proximo();
			}
		}
	}

	public int numVertices() {
		return this.numVertices;
	}

	public Grafo grafoTransposto() {
		Grafo grafoT = new Grafo(this.numVertices);
		for (int v = 0; v < this.numVertices; v++)
			if (!this.listaAdjVazia(v)) {
				Aresta adj = this.primeiroListaAdj(v);
				while (adj != null) {
					grafoT.insereAresta(adj.v2(), adj.v1(), adj.peso());
					adj = this.proxAdj(v);
				}
			}
		return grafoT;
	}
}
