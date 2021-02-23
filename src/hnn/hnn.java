package hnn;

import java.util.Random;

public class hnn {

	double[] entradas;
	double saida;

	public static void main(String[] args) {
		// Entradas
		int quantidadeNos = 4;
		int numeroIteracoes = 500;
		int[] u = new int[] { 950, 2500, 1500, 475, 2500 };
		int s = 0, d = 3;
		double A = 0.001, B = 0.001, C = 0.001;
		double lambda = 1;
		double[][] matrizCustos = gerarMatrizCustos(quantidadeNos);
		double[][] matriztopologia = gerarMatrizTopologia(quantidadeNos);

		double[][][][] T = new double[quantidadeNos][quantidadeNos][quantidadeNos][quantidadeNos];
		double[][] entradasPonderadasAtrasadas = new double[quantidadeNos][quantidadeNos];
		double[][] entradasPonderadas = new double[quantidadeNos][quantidadeNos];
		double[][] entradasPonderadasTemp = new double[quantidadeNos][quantidadeNos];
		double[][] V = new double[quantidadeNos][quantidadeNos];
		double[][] V_temp = new double[quantidadeNos][quantidadeNos];
		double[][] I = new double[quantidadeNos][quantidadeNos];

		inicializarMatrizSinapses(T, u);
		inicializarMatrizBias(I, u, matrizCustos, matriztopologia, s, d);
		adicionarRuido(entradasPonderadas, 0.0002);

		calcularSaidas(entradasPonderadas, V, lambda);

		double energiaInicial;
		double energiaFinal;

		energiaInicial = energiaFinal = calcularEnergiaTotal(V, matrizCustos,
				matriztopologia, u, quantidadeNos, s, d);
		double somatorioSaidas = 0;
		System.out.println(energiaInicial);
		do {
			for (int x = 0; x < quantidadeNos; x++) {
				for (int i = 0; i < quantidadeNos; i++) {
					somatorioSaidas = entradasPonderadas[x][i] - A
							* entradasPonderadasAtrasadas[x][i] + B
							* evalorarEntradasPonderadas(V, T, x, i) + C
							* I[x][i];
					entradasPonderadasTemp[x][i] = somatorioSaidas;
					V_temp[x][i] = calcularLogistica(lambda, somatorioSaidas);
				}
			}
			entradasPonderadasAtrasadas = entradasPonderadas;
			entradasPonderadas = entradasPonderadasTemp;
			entradasPonderadasTemp = new double[quantidadeNos][quantidadeNos];
			V = V_temp;
			V_temp = new double[quantidadeNos][quantidadeNos];
			energiaInicial = energiaFinal;
			energiaFinal = calcularEnergiaTotal(V, matrizCustos,
					matriztopologia, u, quantidadeNos, s, d);
		} while (numeroIteracoes-- > 0);

		System.out.println("Energia Final = " + energiaFinal);

		for (int x = 0; x < quantidadeNos; x++) {
			for (int i = 0; i < quantidadeNos; i++) {
				System.out.println("x," + x + ",i " + i + " = "
						+ (V[x][i] >= 0.5 ? 1 : 0));
			}
		}
	}

	private static void calcularSaidas(double[][] entradasPonderadas,
			double[][] v, double lambda) {
		for (int x = 0; x < v.length; x++) {
			for (int i = 0; i < v.length; i++) {
				v[x][i] = calcularLogistica(lambda, entradasPonderadas[x][i]);
			}
		}
	}

	private static double evalorarEntradasPonderadas(double[][] v,
			double[][][][] t, int x, int i) {
		double somatorio = 0;
		for (int y = 0; y < t.length; y++) {
			for (int j = 0; j < t.length; j++) {
				if (j != y)
					somatorio += t[x][i][y][j] * v[y][j];
			}
		}
		return somatorio;
	}

	private static double[][] gerarMatrizCustos(int quantidadeNos) {
		double[][] matrizCustos = new double[quantidadeNos][quantidadeNos];
		matrizCustos[0][1] = 0.2;
		matrizCustos[0][2] = 0.5;

		matrizCustos[1][0] = 0.2;
		matrizCustos[1][2] = 0.1;
		matrizCustos[1][3] = 0.3;

		matrizCustos[2][0] = 0.5;
		matrizCustos[2][1] = 0.1;
		matrizCustos[2][3] = 0.1;

		matrizCustos[3][1] = 0.3;
		matrizCustos[3][2] = 0.1;

		return matrizCustos;
	}

	private static double[][] gerarMatrizTopologia(int quantidadeNos) {
		double[][] gerarMatrizTopologia = new double[quantidadeNos][quantidadeNos];
		gerarMatrizTopologia[0][0] = 1;
		gerarMatrizTopologia[0][3] = 1;

		gerarMatrizTopologia[1][1] = 1;

		gerarMatrizTopologia[2][2] = 1;

		gerarMatrizTopologia[3][3] = 1;
		gerarMatrizTopologia[3][0] = 1;

		return gerarMatrizTopologia;
	}

	private static int deltaKronecker(double a, double b) {
		return a == b ? 1 : 0;
	}

	private static double calcularLogistica(double lambda, double s) {
		return 1.0 / (1 + Math.exp(-1 * lambda * s));
	}

	private static double calcularEnergiaTotal(double[][] V, double[][] C,
			double[][] P, int[] u, int quantidadeNeuronios, int s, int d) {
		double energiaTotal = calcularE1(V, C, u[0], quantidadeNeuronios, s, d)
				+ calcularE2(V, P, u[1], quantidadeNeuronios, s, d)
				+ calcularE3(V, u[2], quantidadeNeuronios)
				+ calcularE4(V, u[3], quantidadeNeuronios)
				+ calcularE5(V, u[4], quantidadeNeuronios, s, d);
		return energiaTotal;
	}

	private static double calcularE1(double[][] V, double[][] C, double u,
			int quantdadeNeuronios, int s, int d) {
		double somatorio = 0;
		for (int x = 0; x < quantdadeNeuronios; x++) {
			for (int i = 0; i < quantdadeNeuronios; i++) {
				if (!(x == d && i == s) && x != i)
					somatorio += C[x][i] * V[i][x];
			}
		}
		return 0.5 * u * somatorio;
	}

	private static double calcularE2(double[][] V, double[][] P, double u,
			int quantdadeNeuronios, int s, int d) {
		double somatorio = 0;
		for (int x = 0; x < quantdadeNeuronios; x++) {
			for (int i = 0; i < quantdadeNeuronios; i++) {
				if (!(x == d && i == s) && x != i)
					somatorio += P[x][i] * V[x][i];
			}
		}
		return 0.5 * u * somatorio;
	}

	private static double calcularE3(double[][] V, double u,
			int quantdadeNeuronios) {
		double somatorio1;
		double somatorio2;
		double somatorioGeral = 0;
		for (int x = 0; x < quantdadeNeuronios; x++) {
			somatorio1 = 0;
			somatorio2 = 0;
			for (int i = 0; i < quantdadeNeuronios; i++) {
				if (x != i)
					somatorio1 += V[x][i];
			}
			for (int i = 0; i < quantdadeNeuronios; i++) {
				if (x != i)
					somatorio2 += V[i][x];
			}
			somatorioGeral = Math.pow(somatorio1 - somatorio2, 2);
		}
		return 0.5 * u * somatorioGeral;
	}

	private static double calcularE4(double[][] V, double u,
			int quantdadeNeuronios) {
		double somatorioGeral = 0;
		for (int x = 0; x < quantdadeNeuronios; x++) {
			for (int i = 0; i < quantdadeNeuronios; i++) {
				if (x != i)
					somatorioGeral += V[x][i] * (1 - V[i][x]);
			}
		}
		return 0.5 * u * somatorioGeral;
	}

	private static double calcularE5(double[][] V, double u,
			int quantdadeNeuronios, int s, int d) {
		return 0.5 * u * (1 - V[d][s]);
	}

	private static void inicializarMatrizBias(double I[][], int u[],
			double[][] C, double[][] P, int s, int d) {
		for (int x = 0; x < I.length; x++) {
			for (int i = 0; i < I.length; i++) {
				I[x][i] = -0.5 * u[0] * C[x][i] * (1 - deltaKronecker(x, d))
						- 0.5 * u[1] * P[x][i]
						* (1 - deltaKronecker(x, d) * deltaKronecker(i, s))
						- 0.5 * u[3] + 0.5 * u[4] * deltaKronecker(x, d)
						* deltaKronecker(i, s);
			}
		}
	}

	private static void inicializarMatrizSinapses(double T[][][][], int u[]) {
		for (int x = 0; x < T.length; x++) {
			for (int i = 0; i < T.length; i++) {
				for (int j = 0; j < T.length; j++) {
					for (int y = 0; y < T.length; y++) {
						T[x][i][y][j] = u[3] * deltaKronecker(x, y)
								* deltaKronecker(i, j) - u[2]
								* deltaKronecker(x, y) - u[2]
								* deltaKronecker(i, j) + u[2]
								* deltaKronecker(j, x) + u[2]
								* deltaKronecker(i, y);
					}
				}
			}
		}
	}

	private static void adicionarRuido(double U[][], double limiar) {
		Random random = new Random();
		for (int x = 0; x < U.length; x++) {
			for (int i = 0; i < U.length; i++) {
				U[x][i] = (random.nextDouble() % limiar)
						* (random.nextBoolean() ? 1 : -1);
			}
		}
	}

}
