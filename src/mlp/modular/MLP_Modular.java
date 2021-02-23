package mlp.modular;

import java.util.Iterator;
import java.util.Random;

import mlp.DataSet;
import mlp.DataSetRow;

public class MLP_Modular {

	public static void main(String[] args) {

		int qtdCamadasEscondidas = 1;
		int numeroEntradas = 4;
		int numeroNeuroniosCamadasEscondidas = 5;
		int qtdNeuroniosCamadaSaida = 3;
		double n = 0.3; // taxa aprendizado
		
		for (int i = 0; i < args.length; i++) {
			
		}
		
		double[][][] w = new double[qtdCamadasEscondidas + 2][][];
		w[0] = new double[4][4];
		w[1] = new double[4][5];
		w[2] = new double[5][3];
		inicializarPesos(w);
		double[][] y = new double[4][];
		y[1] = new double[4];
		y[2] = new double[5];
		y[3] = new double[3];
		double[][] net = new double[3][];
		net[0] = new double[4];
		net[1] = new double[5];
		net[2] = new double[3];
		double[][] delta = new double[3][];
		delta[0] = new double[4];
		delta[1] = new double[5];
		delta[2] = new double[3];
		DataSet totalDataSet = DataSet.createFromFile(
				"iris_data_normalised.txt", 4, 3, ",");
		DataSet[] sets = totalDataSet.sample(70);
		DataSet treino = sets[0];
		DataSet validacao = sets[1];
		double sum;
		Iterator<DataSetRow> iterator = null;
		double errorMedioQuadratico = 0;
		double errorMomentaneo = 0;
		int iteracao = 1;
		do {
			iterator = treino.iterator();
			while (iterator.hasNext()) {
				DataSetRow dataSetRow = (DataSetRow) iterator.next();
				y[0] = dataSetRow.getInput();
				for (int h = 0; h < 3; h++) {
					for (int i = 0; i < y[h + 1].length; i++) {
						net[h][i] = Math.random(); // bias
						for (int j = 0; j < w[h].length; j++) {
							net[h][i] += y[h][j] * w[h][j][i];
						}
						y[h + 1][i] = funcaoAtivacao(net[h][i]);
					}
				}
				sum = 0.0;
				for (int i = 0; i < dataSetRow.getDesiredOutput().length; i++) {
					delta[2][i] = (dataSetRow.getDesiredOutput()[i] - y[3][i])
							* funcaoAtivacaoDerivada(net[2][i]);
				}
				errorMomentaneo = (Math.pow(delta[2][0], 2)
						+ Math.pow(delta[2][1], 2) + Math.pow(delta[2][2], 2)) / 2.0;

				for (int h = 2; h > 0; h--) {
					for (int i = 0; i < w[h].length; i++) {
						for (int j = 0; j < w[h][i].length; j++) {
							delta[h - 1][i] += w[h][i][j] * delta[h][j];
						}
					}
				}

				for (int h = 0; h < 3; h++) {
					for (int i = 0; i < w[h].length; i++) {
						for (int j = 0; j < w[h][i].length; j++) {
							w[h][i][j] = w[h][i][j]
									+ (n * delta[h][j]
											* funcaoAtivacaoDerivada(net[h][j]) * y[h][i]);
						}
					}
				}
			}
			errorMedioQuadratico = (errorMedioQuadratico + errorMomentaneo)
					/ iteracao;
			System.out.println("Iteração " + iteracao + " Error "
					+ errorMedioQuadratico);
			iteracao++;
		} while (errorMedioQuadratico > 0.001);
		
		

	}

	private static void inicializarPesos(double[][][] w) {
		Random r = new Random();
		for (int i = 0; i < w.length; i++) {
			for (int j = 0; j < w[i].length; j++) {
				for (int k = 0; k < w[i][j].length; k++) {
					w[i][j][k] = 0.2 * r.nextDouble();
				}
			}
		}
	}

	public static double calcularSaida(double[] x, double[] w) {
		double sum = 1 * w[0];
		for (int i = 1; i < w.length; i++) {
			sum += x[i - 1] * w[i];
		}
		return funcaoAtivacao(sum);
	}

	public static double funcaoAtivacao(double net) {
		double lambda = 1.0;
		return 1.0 / (1d + Math.exp(-1d * lambda * net));
	}

	public static double funcaoAtivacaoDerivada(double net) {
		double lambda = 1.0;
		double y = funcaoAtivacao(net);
		return lambda * y * (1 - y);
	}

}
