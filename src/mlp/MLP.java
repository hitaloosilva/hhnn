package mlp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MLP {

	public static void main(String[] args) throws IOException {

		List<TesteMLP> testeMLPs = new ArrayList<>();
		testeMLPs.add(new TesteMLP(0.1, 4, 30, true));
		testeMLPs.add(new TesteMLP(0.1, 4, 30, false));
		testeMLPs.add(new TesteMLP(0.2, 4, 30, true));
		testeMLPs.add(new TesteMLP(0.2, 4, 30, false));
		testeMLPs.add(new TesteMLP(0.3, 4, 30, true));
		testeMLPs.add(new TesteMLP(0.3, 4, 30, false));
		testeMLPs.add(new TesteMLP(0.4, 4, 30, true));
		testeMLPs.add(new TesteMLP(0.4, 4, 30, false));
		testeMLPs.add(new TesteMLP(0.5, 4, 30, true));
		testeMLPs.add(new TesteMLP(0.5, 4, 30, false));
		testeMLPs.add(new TesteMLP(0.6, 4, 30, true));
		testeMLPs.add(new TesteMLP(0.6, 4, 30, false));
		testeMLPs.add(new TesteMLP(0.7, 4, 30, true));
		testeMLPs.add(new TesteMLP(0.7, 4, 30, false));

		testeMLPs.add(new TesteMLP(0.1, 5, 30, true));
		testeMLPs.add(new TesteMLP(0.1, 5, 30, false));
		testeMLPs.add(new TesteMLP(0.2, 5, 30, true));
		testeMLPs.add(new TesteMLP(0.2, 5, 30, false));
		testeMLPs.add(new TesteMLP(0.3, 5, 30, true));
		testeMLPs.add(new TesteMLP(0.3, 5, 30, false));
		testeMLPs.add(new TesteMLP(0.4, 5, 30, true));
		testeMLPs.add(new TesteMLP(0.4, 5, 30, false));
		testeMLPs.add(new TesteMLP(0.5, 5, 30, true));
		testeMLPs.add(new TesteMLP(0.5, 5, 30, false));
		testeMLPs.add(new TesteMLP(0.6, 5, 30, true));
		testeMLPs.add(new TesteMLP(0.6, 5, 30, false));
		testeMLPs.add(new TesteMLP(0.7, 5, 30, true));
		testeMLPs.add(new TesteMLP(0.7, 5, 30, false));

		File saida = new File("./saidas/resumo.txt");
		saida.createNewFile();
		FileOutputStream fos = new FileOutputStream(saida, false);
		int id = 1;
		String resultadosTeste;
		for (TesteMLP testeMLP : testeMLPs) {
			realizarTesteMLP(testeMLP, id++);
			resultadosTeste = testeMLP.toString();
			System.out.println(resultadosTeste);
			fos.write((resultadosTeste + "\n").getBytes());
		}

	}

	private static void realizarTesteMLP(TesteMLP testeMLP, int testeId)
			throws IOException, FileNotFoundException {
		int qtdCamadasEscondidas = 1;
		int h = qtdCamadasEscondidas + 2;
		int numeroEntradas = 4;
		int qtdNeuroniosCamadaescondida = numeroEntradas + 1;
		double n = 0.0; // taxa aprendizado
		n = testeMLP.getN();
		qtdNeuroniosCamadaescondida = testeMLP
				.getQtdNeuronioCamadasEscondidas();
		double[][] y = null;
		double[][][] w;
		double[][] wBias;
		double[][] net;
		double[][] delta;
		double[][][] w_temp = null;
		DataSet irisTreinamento = DataSet.createFromFile(
				"iris_marilia_treinamento.txt", 4, 3, ",");
		DataSet irisValidacao = DataSet.createFromFile(
				"iris_marilia_validacao.txt", 4, 3, ",");
		DataSet irisTestes = DataSet.createFromFile("iris_testes.txt", 4, 3,
				",");

		Iterator<DataSetRow> iterator = null;
		double erro = 0;
		double erroMSEEntrada = 0;
		double erroEpoca = 0;
		double errorMedioQuadraticoTreinamento = 0;
		double errorMedioQuadraticoValidacaoAtual = 0;
		double errorMedioQuadraticoValidacao = 0;
		double diferencaMediaErrorMedioQuadratico = 0;
		int numeroIteracoesERQValidacaoCrescente = 0;
		File saida = null;
		FileOutputStream fos = null;
		String saidaArquivo;
		int epoca = 1;
		System.out
				.println("--------------------------------------------------");
		System.out.println("Teste " + testeId + "_" + testeMLP.getN() + "_"
				+ testeMLP.getQtdNeuronioCamadasEscondidas() + "_"
				+ testeMLP.isShuffle());
		for (int iteracoes = 0; iteracoes < testeMLP.getNumeroIteracoes(); iteracoes++) {
			System.out.println("Iteração - " + (iteracoes + 1));
			y = new double[h][];
			y[0] = new double[4];
			y[1] = new double[qtdNeuroniosCamadaescondida];
			y[2] = new double[3];

			w = new double[h][][];
			w[0] = new double[4][4];
			w[1] = new double[4][qtdNeuroniosCamadaescondida];
			w[2] = new double[qtdNeuroniosCamadaescondida][3];

			inicializarPesos(w);

			wBias = new double[h][];
			wBias[0] = new double[4];
			wBias[1] = new double[qtdNeuroniosCamadaescondida];
			wBias[2] = new double[3];
			inicializarPesos(wBias);

			net = new double[h][];
			net[0] = new double[4];
			net[1] = new double[qtdNeuroniosCamadaescondida];
			net[2] = new double[3];

			delta = new double[h][];
			delta[0] = new double[4];
			delta[1] = new double[qtdNeuroniosCamadaescondida];
			delta[2] = new double[3];

			erro = 0;
			erroMSEEntrada = 0;
			erroEpoca = 0;
			errorMedioQuadraticoTreinamento = 0;
			errorMedioQuadraticoValidacaoAtual = 0;
			errorMedioQuadraticoValidacao = 0;
			numeroIteracoesERQValidacaoCrescente = 0;
			diferencaMediaErrorMedioQuadratico = 0;
			epoca = 1;
			saida = new File("./saidas/teste_" + formatarNumero(testeId) + "_"
					+ testeMLP.getN() + "_"
					+ testeMLP.getQtdNeuronioCamadasEscondidas() + "_"
					+ testeMLP.isShuffle());
			saida.mkdirs();
			saida = new File(saida, "iteracao_" + iteracoes + "_saida.txt");
			saida.createNewFile();

			fos = new FileOutputStream(saida, false);

			do {
				if (testeMLP.isShuffle())
					irisTreinamento.shuffle();
				iterator = irisTreinamento.iterator();
				erroEpoca = 0;
				while (iterator.hasNext()) {
					erroMSEEntrada = 0;
					DataSetRow dataSetRow = (DataSetRow) iterator.next();

					delta = new double[3][];
					delta[0] = new double[4];
					delta[1] = new double[qtdNeuroniosCamadaescondida];
					delta[2] = new double[3];

					y[0] = dataSetRow.getInput();
					for (int k = 1; k < h; k++) {
						for (int i = 0; i < y[k].length; i++) {
							net[k][i] = wBias[k][i] * 1; // bias
							for (int j = 0; j < y[k - 1].length; j++) {
								net[k][i] += y[k - 1][j] * w[k][j][i];
							}
							y[k][i] = funcaoAtivacao(net[k][i]);
						}
					}

					for (int i = 0; i < dataSetRow.getDesiredOutput().length; i++) {
						erro = dataSetRow.getDesiredOutput()[i] - y[2][i];
						delta[2][i] = erro * funcaoAtivacaoDerivada(net[2][i]);
						erroMSEEntrada += (erro * erro);
					}
					// System.out.println("Erro Médio Entrada "
					// +erroMSEEntrada);
					erroEpoca += erroMSEEntrada * 0.5;

					for (int k = 2; k > 0; k--) {
						for (int i = 0; i < w[k].length; i++) {
							for (int j = 0; j < w[k][i].length; j++) {
								delta[k - 1][i] += w[k][i][j] * delta[k][j];
							}
							delta[k - 1][i] *= funcaoAtivacaoDerivada(net[k - 1][i]);
						}
					}
					w_temp = new double[h][][];
					w_temp[0] = new double[4][4];
					w_temp[1] = new double[4][qtdNeuroniosCamadaescondida];
					w_temp[2] = new double[qtdNeuroniosCamadaescondida][3];

					for (int k = 1; k < h; k++) {
						for (int i = 0; i < y[k].length; i++) {
							for (int j = 0; j < y[k - 1].length; j++) {
								w_temp[k][j][i] = w[k][j][i]
										+ (n * delta[k][i] * y[k - 1][j]);
							}
						}
					}
					w = w_temp;

					for (int k = 1; k < h; k++) {
						for (int i = 0; i < y[k].length; i++) {
							wBias[k][i] = wBias[k][i] * (n * delta[k][i] * 1);
						}
					}

				}

				errorMedioQuadraticoTreinamento = erroEpoca
						/ irisTreinamento.getRows().size();

				// calcular erro validação
				if (testeMLP.isShuffle())
					irisValidacao.shuffle();
				iterator = irisValidacao.iterator();
				erroEpoca = 0;
				while (iterator.hasNext()) {
					erroMSEEntrada = 0;
					DataSetRow dataSetRow = (DataSetRow) iterator.next();
					y[0] = dataSetRow.getInput();
					for (int k = 1; k < h; k++) {
						for (int i = 0; i < y[k].length; i++) {
							net[k][i] = wBias[k][i] * 1; // bias
							for (int j = 0; j < y[k - 1].length; j++) {
								net[k][i] += y[k - 1][j] * w[k][j][i];
							}
							y[k][i] = funcaoAtivacao(net[k][i]);
						}
					}

					for (int i = 0; i < dataSetRow.getDesiredOutput().length; i++) {
						erro = dataSetRow.getDesiredOutput()[i] - y[2][i];
						erroMSEEntrada += (erro * erro);
					}
					erroEpoca += erroMSEEntrada * 0.5;

				}
				errorMedioQuadraticoValidacao = erroEpoca
						/ irisValidacao.getRows().size();
//					System.out
//							.println("---------------------------------------");
//					System.out.println("Época " + epoca);
//					System.out.println(" Error Treinamento "
//							+ errorMedioQuadraticoTreinamento);
//					System.out.println(" Error Validacao "
//							+ errorMedioQuadraticoValidacao);
				saidaArquivo = epoca + "," + errorMedioQuadraticoTreinamento
						+ ",";
				saidaArquivo = saidaArquivo + errorMedioQuadraticoValidacao
						+ "\n";
				fos.write(saidaArquivo.getBytes());

				diferencaMediaErrorMedioQuadratico += errorMedioQuadraticoValidacao
						- errorMedioQuadraticoTreinamento;
				if (diferencaMediaErrorMedioQuadratico / epoca > 0.035) {
					if (errorMedioQuadraticoValidacao > errorMedioQuadraticoValidacaoAtual) {
						numeroIteracoesERQValidacaoCrescente++;
					} else {
						numeroIteracoesERQValidacaoCrescente = 0;
					}
				}
				epoca++;

				errorMedioQuadraticoValidacaoAtual = errorMedioQuadraticoValidacao;
			} while (numeroIteracoesERQValidacaoCrescente < 3 && epoca < 10000);
			testeMLP.getEQRTreinamentoList().add(
					errorMedioQuadraticoTreinamento);
			testeMLP.getEQRValidacaoList().add(
					errorMedioQuadraticoValidacaoAtual);
			testeMLP.getEpocas().add(1.0 * epoca);
		}
	}

	public static double calcularErroMedioQuadratico(
			List<Double> errosMomentaneos) {
		double sum = 0;
		for (Double double1 : errosMomentaneos) {
			sum += double1;
		}
		return sum / errosMomentaneos.size();
	}

	private static void inicializarPesos(double[][][] w) {
		for (int i = 0; i < w.length; i++) {
			for (int j = 0; j < w[i].length; j++) {
				for (int k = 0; k < w[i][j].length; k++) {
					w[i][j][k] = Math.random() * 2 - 1d;
				}
			}
		}
	}

	private static String formatarNumero(int numero) {
		String retorno = "";
		if (numero < 10) {
			retorno = "00" + numero;
		}else if(numero < 100){
			retorno = "0" + numero;
		}else if(numero < 1000){
			retorno = "" + numero;
		}
		return retorno;
	}

	private static void inicializarPesos(double[][] w) {
		for (int i = 0; i < w.length; i++) {
			for (int j = 0; j < w[i].length; j++) {
				w[i][j] = Math.random() * 2 - 1d;
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
