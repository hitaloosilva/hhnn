/**
 * 
 * ShortestPathHopfield
 * 
 * Esta classe implementa o cÃ¡lculo de caminhos mÃ­nimos atravÃ©s da Rede de Hopfield, conforme
 * as especificaÃ§Ãµes do artigo "Neural networks for shortest path computation and routing in 
 * computer networks" (Mehmet Ali e Faouzi Kamoun, 1993).
 * 
 * @author	Maicon de Brito do Amarante. MASLAB UFRGS. [http://www.inf.ufrgs.br/maslab/]
 * 
 */
package hopfield;

import org.ujmp.core.*;
import java.util.Random;

public class ShortestPath {

	private int epocas = 0;
	private String rotaHopfield = "";

	// nÃºmero de cidades
	private int NC = 16;
	// source, destionation
	private int s = 1, d = 3;
	// constantes
	double tau = 1, lambda = 1, limiar = 0.00001, t = 0.00001;
	// controlador da convergencia da rede
	int finish = 0;

	// W=Matriz de Pesos
	private double[][][][] W = new double[NC][NC][NC][NC];
	// P=matriz de topologia
	private double[][] P = new double[NC][NC];
	// I=Matriz Bias
	private double[][] I = new double[NC][NC];
	// U=entrada (comeÃ§a com ruÃ­do aleatÃ³rio para cada neurÃ´nio)
	private double[][] U = new double[NC][NC];
	private double[][] U_old = new double[NC][NC];
	// V=matriz de saÃ­da
	private double[][] V = new double[NC][NC];

	// constantes micro (fx de energia, calculo dos pesos;
	private static double micro1 = 950;
	private static double micro2 = 2500;
	private static double micro3 = 2500;
	private static double micro4 = 475;
	private static double micro5 = 2500;

	// C=Matriz de Custos (distÃ¢ncia entre cidades) - linha = origem; coluna =
	// destino.
	private double[][] C = new double[NC][NC];

	/**
	 * MÃ©todo Construtor. Inicializa os parÃ¢metros e inicia a simulaÃ§Ã£o.
	 * 
	 * @param nc
	 *            NÃºmero de nÃ³s do grafo
	 * @param s
	 *            NÃ³ de partida
	 * @param d
	 *            NÃ³ de chegada
	 * @author Maicon de Brito do Amarante
	 */
	ShortestPath(int nc, double[][] C) {

		this.C = C;
		this.NC = nc;

		// W=Matriz de Pesos
		W = new double[NC][NC][NC][NC];
		// P=matriz de topologia
		P = new double[NC][NC];
		// I=Matriz Bias
		I = new double[NC][NC];
		// U=entrada (comeÃ§a com ruÃ­do aleatÃ³rio para cada neurÃ´nio)
		U = new double[NC][NC];
		U_old = new double[NC][NC];
		// V=matriz de saÃ­da
		V = new double[NC][NC];

		setWt();

	}

	/**
	 * MÃ©todo Construtor. Inicializa os parÃ¢metros, gera custos aleatÃ³rios e
	 * inicia a simulaÃ§Ã£o.
	 * 
	 * @param nc
	 *            NÃºmero de nÃ³s do grafo
	 * @param s
	 *            NÃ³ de partida
	 * @param d
	 *            NÃ³ de chegada
	 * @author Maicon de Brito do Amarante
	 */
	ShortestPath(int nc, int s, int d) {

		this.NC = nc;
		this.s = s;
		this.d = d;
		simula();

	}

	/**
	 * MÃ©todo Construtor. Inicializa parÃ¢metros com valor padrÃ£o e inicia a
	 * simulaÃ§Ã£o.
	 * 
	 * @param nc
	 *            NÃºmero de nÃ³s do grafo
	 * @param s
	 *            NÃ³ de partida
	 * @param d
	 *            NÃ³ de chegada
	 * @author Maicon de Brito do Amarante
	 */
	ShortestPath() {

		simula();
	}

	/**
	 * Simula a rede neural.
	 * 
	 * @param gerarCustos
	 *            "True" para gerar custos aleatÃ³rios; "False" quando custos
	 *            sÃ£o informados pelo usuÃ¡rio.
	 */
	public void simula() {

		int ct = 0;

		// Setar MATRIZ DE PESOS
		setWt();

		// gerar custos dos links (distÃ¢ncias entre nodos) aleatÃ³rios
		// MATRIZ DE CUSTOS
		gerarCustos(NC);

		// System.out.println("Custos:");
		// printBiDimMatrix(C);

		// Setar MATRIZ DE TOPOLOGIA
		setP();

		// setar MATRIZ DE POLARIZAÃ‡ÃƒO
		// bias
		setI();

		// Inicializa MATRIZ DE ENTRADAS
		// atribuir entradas aleatÃ³ria ao primeiro estado da rede (estado zero)
		gerarPrimeiraEntrada();

		// printNetState();

		// calcular MATRIZ DE SAÃ�DA
		setV();

		do {
			ct++;
			// calcular NOVA MATRIZ DE ENTRADA DOS NEURONIOS
			RungeKutta();

			// schuler();
			// calcular MATRIZ DE SAÃ�DA
			setV();

			// simular atÃ© estabilizar ou um nÃºmero mÃ¡ximo de Ã©pocas
			// prÃ©-definido
		} while (finish == 0); // ;ct < 20000

		epocas = ct;

		// printNetState();

		// DETERMINA ROTA
		setRota();
		montaRota();

	}

	public void simula(int s, int d) {
		this.s = s;
		this.d = d;
		int ct = 0;

		setP();
		setI();
		gerarPrimeiraEntrada();
		setV();

		do {
			ct++;
			// RungeKutta();
			schuler();
			// System.out.println("------------------------");
			setV();
		} while (finish == 0); // ;ct < 20000

		epocas = ct;

		// printNetState();

		setRota();
		montaRota();
	}

	/**
	 * 
	 * Gerar primeira entrada dos dados aleatoriamente para gerar "ruÃ­do", que
	 * facilita o aprendizado em redes de Hopfield.
	 * 
	 * @author Maicon de Brito do Amarante
	 */
	private void gerarPrimeiraEntrada() {

		double mat[][] = new double[NC][NC];

		for (int i = 0; i < NC; i++) {
			for (int j = 0; j < NC; j++) {
				if (i != j)
					U[i][j] = randomInput(-2, 2);
				// U[i][j] = 0.0002;
				else
					mat[i][j] = 0;
			}
		}

	}

	/**
	 * 
	 * Gerar aleatoriamente matriz de custos, que podem representar a distÃ¢ncia
	 * ou o trÃ¡fego entre os nÃ³s.
	 * 
	 * @author Maicon de Brito do Amarante
	 */
	private void gerarCustos(int tamanho) {

		for (int i = 0; i < tamanho; i++) {
			for (int j = 0; j < tamanho; j++) {
				if (i != j)
					C[i][j] = 1 * Math.random();
				else
					C[i][j] = 0;
			}
		}

	}

	/*
	 * private void gerarCustosFixos(){
	 * 
	 * 
	 * //linha = origem coluna destino!
	 * 
	 * int mat[][] = {{00,30,20,10,00}, {00,00,00,00,00}, {00,00,00,00,00},
	 * {00,00,00,00,20}, {00,00,00,00,00}, };
	 * 
	 * for (int i = 0; i < NC; i++) { for (int j = 0; j < NC; j++) { C[i][j] =
	 * (((double)mat[i][j])/100); } } }
	 */

	/**
	 * 
	 * Kronecker delta. Compara duas variÃ¡veis. Se sÃ£o iguais, Kronecker delta
	 * serÃ¡ igual a 1, senÃ£o serÃ¡ igual a zero.
	 * 
	 * @param i
	 *            variÃ¡vel 1
	 * @param j
	 *            variÃ¡vel 2
	 * @author Maicon de Brito do Amarante
	 */
	private int kD(int i, int j) {

		if (i == j)
			return 1;
		else
			return 0;
	}

	/*
	 * //nÃ£o resolver numericamente deste modo - resolver usando Runge-Kutta.
	 * private double[] eqMotion_Erro(int x, int i){
	 * 
	 * double vet[] = {0,0};
	 * 
	 * if (x != i){
	 * 
	 * vet[0] = U[x][i]/tau;
	 * 
	 * //for (int x=0;x<NC;x++){ //for (int i=0;i<NC;i++){ vet[1] = micro1/2 *
	 * C[x][i] * (1 - kD(x,d) * kD(i,s) - micro2/2 * P[x][i] * (1 - kD(x,d) *
	 * kD(i,s)) - micro3 * Soma(x) + micro3 * Soma(i) - micro4/2 * (1 -
	 * 2*V[x][i]) * micro5/2 * kD(x,d) * kD(i,s)); //} //} }
	 * 
	 * return vet; }
	 */

	/**
	 * 
	 * Equation of Motion. Movimenta a entrada, ou seja, atualiza os dados de
	 * entrada. Esta equaÃ§Ã£o Ã© resolvida atravÃ©s do mÃ©todo Runge-Kutta de
	 * quarta-ordem (RK4), por se tratar de uma equaÃ§Ã£o de diferencial.
	 * 
	 * @param ltau
	 *            parametro ltau, ou seja, tempo.
	 * @param x
	 *            Ã­ndice dos pesos e bias.
	 * @param i
	 *            Ã­ndice dos pesos e bias.
	 * @param lU
	 *            valor de entrada.
	 * @author Maicon de Brito do Amarante
	 */
	private double eqMotion(double ltau, int x, int i, double lU) {

		double res = 0, soma = 0;

		for (int y = 0; y < NC; y++) {
			for (int j = 0; j < NC; j++) {
				soma += W[x][i][y][j] * V[y][j];
			}
		}

		return -lU / ltau + soma + I[x][i];
	}

	/**
	 * 
	 * Setar matriz de pesos.
	 * 
	 * @author Maicon de Brito do Amarante
	 */
	public void setWt() {

		for (int x = 0; x < NC; x++) {
			for (int i = 0; i < NC; i++) {
				if (x != i)
					for (int y = 0; y < NC; y++) {
						for (int j = 0; j < NC; j++) {
							if (y != j) {
								W[x][i][y][j] = micro4 * kD(x, y) * kD(i, j)
										- micro3 * kD(x, y) - micro3 * kD(i, j)
										+ micro3 * kD(j, x) + micro3 * kD(i, y);
								// System.out.println(x + "" + i + "" + y + "" +
								// j
								// + ":" + W[x][i][y][j]);
							}
						}
					}
			}
		}

	}

	/**
	 * 
	 * Setar bias.
	 * 
	 * @author Maicon de Brito do Amarante
	 */
	public void setI() {

		for (int x = 0; x < NC; x++) {
			for (int i = 0; i < NC; i++) {
				if (x != i) {
					if (x == d && i == s)
						I[x][i] = micro5 / 2 - micro4 / 2;
					else
						I[x][i] = -(micro1 / 2) * C[x][i] - (micro2 / 2)
								* P[x][i] - (micro4 / 2);
					// System.out.println(x+"-"+i+":"+I[x][i]);
				}
			}
		}

	}

	/**
	 * 
	 * Setar matriz de topologia.
	 * 
	 * @author Maicon de Brito do Amarante
	 */
	private void setP() {

		for (int x = 0; x < NC; x++) {
			for (int i = 0; i < NC; i++) {

				if (C[x][i] == 0) // se nÃ£o existe link entre nÃ³ x e i
					P[x][i] = 1;
				else
					P[x][i] = 0;
			}
		}

	}

	/**
	 * 
	 * Setar saÃ­das.
	 * 
	 * @author Maicon de Brito do Amarante
	 */
	private void setV() {

		double ant = 0;
		int nConv = 0;

		for (int x = 0; x < NC; x++) {
			for (int i = 0; i < NC; i++) {

				if (x != i) { // se nÃ£o existe link entre nÃ³ x e i

					ant = V[x][i];
					V[x][i] = 1.0D / (1.0D + Math.exp(-lambda * U[x][i]));

					if (Math.abs(V[x][i] - ant) < limiar)
						nConv++;

				} else
					V[x][i] = 0;
			}
		}

		if (nConv == NC * (NC - 1))
			finish = 1;
	}

	/**
	 * 
	 * Setar rota. Marcar na matriz de saÃ­das 1 para neurÃ´nios ativados, ou
	 * seja, aqueles que tem valor acima ou igual a 0.5 e que serÃ£o nÃ³s de
	 * caminhos mÃ­nimos; ou 0 para neurÃ´nios nÃ£o ativados, com valor abaixo
	 * de 0.5.
	 * 
	 * @author Maicon de Brito do Amarante
	 */
	private void setRota() {

		for (int x = 0; x < NC; x++) {
			for (int i = 0; i < NC; i++) {
				if (x != i) {
					if (V[x][i] >= 0.5)
						V[x][i] = 1;
					else
						V[x][i] = 0;
				}
			}
		}

	}

	/*
	 * private void mostrarRota(){
	 * 
	 * montaRota(); System.out.println(rotaHopfield); }
	 */

	/**
	 * 
	 * Montar Rota. AtravÃ©s da matriz de saÃ­das com valores 0 e 1 (neurÃ´nio
	 * fora ou dentro da rota de camingos mÃ­nimos), setar a rota. Os pontos de
	 * partida e chegada sÃ£o conhecidos; percorrendo toda a linha da matriz de
	 * saÃ­da encontrar qual a coluna que estÃ¡ marcada com 1. A coluna marcada
	 * representa o neurÃ´nio escolhido para a rota naquele momento.
	 * 
	 * @author Maicon de Brito do Amarante
	 */
	private void montaRota() {

		rotaHopfield = s + ";";
		double cost = 0;
		for (int x = 0; x < NC; x++)
			for (int i = 0; i < NC; i++) {
				if (i != s && i != d && V[x][i] > 0) {// escapar de origem e
														// destino
					rotaHopfield += i + ";";
					cost += this.C[x][i];
				}
			}

		rotaHopfield += d + ";" + cost;
	}

	/**
	 * 
	 * Gerar entradas aleatÃ³rias. Num intervalo de nÃºmeros negativos e
	 * positivos.
	 * 
	 * @param min
	 *            valor mÃ­nimo do intervalo das entradas aleatÃ³rias.
	 * @param max
	 *            valor mÃ¡ximo do intervalo das entradas aleatÃ³rias.
	 * @author Maicon de Brito do Amarante
	 */
	private double randomInput(int min, int max) {

		double numero = (double) (2 * Math.random()) / 10000;
		int sinal = 1;
		if (1 * Math.random() < 0.5)
			sinal = -1;
		return numero * sinal;
	}

	/**
	 * 
	 * Imprimir uma matriz bidimensional na saÃ­da padrÃ£o.
	 * 
	 * @param mat
	 *            matriz bidimensional.
	 * @author Maicon de Brito do Amarante
	 */
	public void printBiDimMatrix(double mat[][]) {

		Matrix m1 = MatrixFactory.importFromArray(mat);
		System.out.println(m1);
	}

	/**
	 * 
	 * Iterar o mÃ©todo Runge-Kutta para toda a matriz de entradas.
	 * 
	 * @author Maicon de Brito do Amarante
	 */
	private void RungeKutta() {

		for (int x = 0; x < NC; x++) {
			for (int i = 0; i < NC; i++) {
				RK4Iterate(x, i);
			}
		}
	}

	private void schuler() {

		double soma = 0;
		double netTemp = 0;
		for (int x = 0; x < NC; x++) {
			for (int i = 0; i < NC; i++) {
				if (x != i) {
					soma = 0;
					for (int y = 0; y < NC; y++) {
						for (int j = 0; j < NC; j++) {
							if (y != j)
								soma += W[x][i][y][j] * V[y][j];
						}
					}
					netTemp = U[x][i] - 0.001 * U_old[x][i] + 0.001 * soma
							+ 0.001 * I[x][i];
					U_old[x][i] = U[x][i];
					U[x][i] = netTemp;
					// System.out.println(x + "-" + i + ": " + netTemp);

				}
			}
		}

	}

	/**
	 * 
	 * MÃ©todo Runge-Kutta de quarta-ordem (RK4) para resolver equaÃ§Ã£o de
	 * diferencial.
	 * 
	 * @param mat
	 *            matriz bidimensional.
	 * @author Maicon de Brito do Amarante
	 */
	private void RK4Iterate(int x, int i) {

		double k1, k2, k3, k4;
		// First step
		k1 = eqMotion(tau, x, i, U[x][i]);
		// Second step
		k2 = eqMotion(tau + t / 2, x, i, U[x][i] + t * k1 / 2);
		// k2 = 0;
		// Third step
		k3 = eqMotion(tau + t / 2, x, i, U[x][i] + t * k2 / 2);
		// k3 = 0;
		// Fourth Step
		k4 = eqMotion(tau + t, x, i, U[x][i] + t * k3);
		// k4 = 0;
		// Calculate next point
		U[x][i] += t * (k1 + (2 * k2) + (2 * k3) + k4) / 6;
	}

	/**
	 * 
	 * @return epocas NÃºmero de Ã©pocas atÃ© a rede convergir o treinamento.
	 * @author Maicon de Brito do Amarante
	 */
	public int getEpocas() {

		return epocas;
	}

	/**
	 * 
	 * @return rotaHopfield retorna a rota encontrada atravÃ©s do treinamento da
	 *         rede.
	 * @author Maicon de Brito do Amarante
	 */
	public String getRotaHopfield() {

		return rotaHopfield;
	}

	/*
	 * public double[][] getSaidaFinal(){
	 * 
	 * return V; }
	 */

	/**
	 * 
	 * @return C retorna a matriz de custos (distÃ¢ncias) para possibilitar a
	 *         comparaÃ§Ã£o do mÃ©todos com outros (dijkstra por exemplo).
	 * @author Maicon de Brito do Amarante
	 */
	public double[][] getCustos() {

		return C;
	}

	public void printNetState() {

		printBiDimMatrix(V);
	}

}