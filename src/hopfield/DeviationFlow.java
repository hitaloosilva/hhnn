package hopfield;

import java.util.HashMap;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;

public class DeviationFlow {


	//L links e P paths
	int L = 14, NK = 30, N=5;

	//cada linha: source, destination, path ini, path fim, traffic input
	int COM[][] = {{0,4,0,6,50}, {0,2,7,11,45}, {1,3,12,18,35}, {3,2,19,24,15}, {4,0,25,29,30}};

	int [][] P = {{1,12,0,0},
			{1,7,8,0},
			{1,7,14,10},
			{5,6,12,0},
			{5,8,0,0},
			{5,14,10,0},
			{3,10,0,0},

			{5,0,0,0},
			{1,7,0,0},
			{1,12,9,0},
			{3,10,9,0},
			{3,10,13,12},

			{7,14,0,0},
			{2,3,0,0},
			{12,11,0,0},
			{12,9,14,0},
			{12,9,14,0},
			{2,5,14,0},
			{2,5,8,11},

			{4,5,0,0},
			{4,1,7,0},
			{4,1,12,9},
			{10,9,0,0},
			{10,13,7,0},
			{10,13,2,5},

			{12,2,0,0},
			{13,7,14,4},
			{9,6,2,0},
			{9,14,4,0},
			{11,4,0,0}
	};

	String[] NS = {"0;1;4;", "0;1;2;4;", "0;1;2;3;4;", "0;2;1;4;", "0;2;4;", "0;2;3;4;", "0;3;4;",
	"0;2;", "0;1;2;", "0;1;4;2;", "0;3;4;2;", "0;3;4;1;2;", 
	"1;2;3;", "1;0;3;", "1;4;3;", "1;4;2;3;", "1;4;2;3;", "1;0;2;3;", "1;0;2;4;3;",
	"3;0;2;", "3;0;1;2;", "3;0;1;4;2;", "3;4;2;", "3;4;1;2;", "3;4;1;0;2;",
	"4;1;0;", "4;1;2;3;0;", "4;2;1;0;", "4;2;3;0;", "4;3;0;"};	

	double[] C = {9.6, 72, 56, 56, 56, 9.6, 56, 9.6, 72, 56, 56, 56, 56, 72};
	double[] D1 = new double[L];
	double[] D2 = new double[L];

	double f[] = {0,0,0,0,0,0,50,45,0,0,
			0,0,35,0,0,0,0,0,0,10,
			5,0,0,0,0,30,0,0,0,0};
	int f2[] = new int[NK];



	DeviationFlow() throws Exception{

		int commodityNum = 0;

		//System.out.println(calcularCusto());
		//System.exit(0);
		
		//step1 = declaracao da matriz f		

		for (int t = 0; t < 38; t++) {

			for (int l = 0; l < NK; l++)
				f2[l] = 0;
			
			//step2
			derivada1();

			double[][] matHop = montarCustoHopfield();
			
			/*
			Matrix mm1 = MatrixFactory.importFromArray( matHop );
			System.out.println(mm1);
			System.exit(0);
			*/
			
			//encontrar os caminhos mínimos		
			for (int i = 0; i < 5; i++) {
				//step2				
				//ShortestPathV3 sp = new ShortestPathV3(5, COM[i][0], COM[i][1], matHop);
				ShortestPathDijkstra dk = new ShortestPathDijkstra(5, COM[i][0], COM[i][1], matHop); 
				String rota = dk.getRotaDijkstra(); //sp.getRotaHopfield()
				//comparando commodity com a saida do hopfield, ou seja, qual o caminho mínimo escolhido agora.

				//step3
				commodityNum = comparaComodHop(rota);			
				f2[commodityNum] = custoCOM(commodityNum);			
			}
			
			//step4
			derivada2();
			
			double alfaFK = alfak();
			
			for (int r = 0; r < NK; r++) {
				//f[r] = f[r] + alfaFK * (f2[r] - f[r]);
				f[r] = f[r] + alfaFK * (f2[r] - f[r]);
			}
			
			atualizarCusto();

		}

		
		Matrix mm1 = MatrixFactory.importFromArray( f );
		System.out.println(mm1);
		//System.exit(0);
				
		
		//mostra custo final!
		System.out.println(calcularCusto());


	}

	public void derivada1(){

		for (int l = 0; l < L; l++){			
			D1[l] = C[l] / ( (C[l]-fl(l)) * (C[l]-fl(l)) );
		}		
	}

	public void derivada2(){

		for (int l = 0; l < L; l++){			
			D2[l] = 2 * C[l] / ( (C[l]-fl(l)) * (C[l]-fl(l)) * (C[l]-fl(l)));
		}		
	}	

	public double fl(int l){

		double num = 0;

		/*for (int n = 0; n < NK; n++){
			num += f[n] * NodoContemLink(n,l+1);					
		}*/	
		
		for (int n = 0; n < NK; n++)
			num += f[n] * NodoContemLink(n,l+1);					

		return num;
	}

	public double ftl(int l){

		double num = 0;

		for (int n = 0; n < NK; n++)
			num += f2[n] * NodoContemLink(n,l+1);					

		return num;
	}	

	public double calcularCusto(){ 

		double num = 0, den = 0, soma = 0;
		int l;

		for (l = 0; l < L; l++){

			num = 0;
			den = 0;

			
		for (int n = 0; n < NK; n++){
				num += f[n] * NodoContemLink(n,l+1);					
			}
			 

			//num = fl(l);

			den += C[l] - num;
			soma += num/den;
		}

		//System.out.println(soma);
		return soma;
	}

	private int NodoContemLink(int n, int l){

		for (int i=0;i<4;i++)
			if (P[n][i] == l)
				return 1;

		return 0;

	}						


	private double[][] montarCustoHopfield(){


		double[][] mat = new double[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				mat[i][j] = 0;
			}
		}

		//linha = origem; coluna = destino.
		
		//partindo do nó 1
		mat[0][1] = D1[0];
		mat[0][2] = D1[4];
		mat[0][3] = D1[2];

		//partindo do nó 2
		mat[1][0] = D1[1];
		mat[1][2] = D1[6];
		mat[1][4] = D1[11];

		//partindo do nó 3
		mat[2][1] = D1[5];
		mat[2][3] = D1[13];
		mat[2][4] = D1[7];

		//partindo do nó 4
		mat[3][0] = D1[3];
		mat[3][3] = D1[9];

		//partindo do nó 5
		mat[4][1] = D1[12];
		mat[4][2] = D1[8];
		mat[4][3] = D1[10];

		return mat;
	}

	private int comparaComodHop(String hop){

		for (int i = 0; i < NK; i++) {
			if (NS[i].equals(hop))
				return i;
		}		

		return 0;
	}

	private int custoCOM(int com){

		for (int i = 0; i < N; i++) {
			if (com >= COM[i][2] && com <= COM[i][3])
				return COM[i][4];
		}

		return 0;	
	}

	private double fkl(int l){

		double soma = 0;

		for (int n = 0; n < NK; n++) {
			soma += f[n] * NodoContemLink(n, l);
		}			
		return soma;
	}

	private double ftkl(int l){

		double soma = 0;

		for (int n = 0; n < NK; n++) {
			soma += f2[n] * NodoContemLink(n, l);
		}			
		return soma;
	}	

	private double alfak(){

		double r1 = 0, r2 = 0;

		for (int l = 0; l < L; l++){
			r1 += (ftkl(l)-fkl(l)) * D1[l];
			r2 += (ftkl(l)-fkl(l)) * D2[l];
		}

		return Math.min(1, -(r1/r2));
	}

	public void atualizarCusto(){

		for (int l = 0; l < L; l++){			
			C[l] = C[l] / ( (C[l]-fl(l)) * (C[l]-fl(l)) );
		}		
	}			
	
	
}
