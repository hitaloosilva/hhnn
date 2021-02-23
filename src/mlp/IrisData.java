package mlp;

import java.util.ArrayList;

public class IrisData {

	private String[] dadosTreinamento;
	private String[] dadosValidacao;
	private String[] dadosTeste;

	public static String[] DATA = { "5.1,3.5,1.4,0.2,Iris-setosa",
			"4.9,3.0,1.4,0.2,Iris-setosa", "4.7,3.2,1.3,0.2,Iris-setosa",
			"4.6,3.1,1.5,0.2,Iris-setosa", "5.0,3.6,1.4,0.2,Iris-setosa",
			"5.4,3.9,1.7,0.4,Iris-setosa", "4.6,3.4,1.4,0.3,Iris-setosa",
			"5.0,3.4,1.5,0.2,Iris-setosa", "4.4,2.9,1.4,0.2,Iris-setosa",
			"4.9,3.1,1.5,0.1,Iris-setosa", "5.4,3.7,1.5,0.2,Iris-setosa",
			"4.8,3.4,1.6,0.2,Iris-setosa", "4.8,3.0,1.4,0.1,Iris-setosa",
			"4.3,3.0,1.1,0.1,Iris-setosa", "5.8,4.0,1.2,0.2,Iris-setosa",
			"5.7,4.4,1.5,0.4,Iris-setosa", "5.4,3.9,1.3,0.4,Iris-setosa",
			"5.1,3.5,1.4,0.3,Iris-setosa", "5.7,3.8,1.7,0.3,Iris-setosa",
			"5.1,3.8,1.5,0.3,Iris-setosa", "5.4,3.4,1.7,0.2,Iris-setosa",
			"5.1,3.7,1.5,0.4,Iris-setosa", "4.6,3.6,1.0,0.2,Iris-setosa",
			"5.1,3.3,1.7,0.5,Iris-setosa", "4.8,3.4,1.9,0.2,Iris-setosa",
			"5.0,3.0,1.6,0.2,Iris-setosa", "5.0,3.4,1.6,0.4,Iris-setosa",
			"5.2,3.5,1.5,0.2,Iris-setosa", "5.2,3.4,1.4,0.2,Iris-setosa",
			"4.7,3.2,1.6,0.2,Iris-setosa", "4.8,3.1,1.6,0.2,Iris-setosa",
			"5.4,3.4,1.5,0.4,Iris-setosa", "5.2,4.1,1.5,0.1,Iris-setosa",
			"5.5,4.2,1.4,0.2,Iris-setosa", "4.9,3.1,1.5,0.1,Iris-setosa",
			"5.0,3.2,1.2,0.2,Iris-setosa", "5.5,3.5,1.3,0.2,Iris-setosa",
			"4.9,3.1,1.5,0.1,Iris-setosa", "4.4,3.0,1.3,0.2,Iris-setosa",
			"5.1,3.4,1.5,0.2,Iris-setosa", "5.0,3.5,1.3,0.3,Iris-setosa",
			"4.5,2.3,1.3,0.3,Iris-setosa", "4.4,3.2,1.3,0.2,Iris-setosa",
			"5.0,3.5,1.6,0.6,Iris-setosa", "5.1,3.8,1.9,0.4,Iris-setosa",
			"4.8,3.0,1.4,0.3,Iris-setosa", "5.1,3.8,1.6,0.2,Iris-setosa",
			"4.6,3.2,1.4,0.2,Iris-setosa", "5.3,3.7,1.5,0.2,Iris-setosa",
			"5.0,3.3,1.4,0.2,Iris-setosa", "7.0,3.2,4.7,1.4,Iris-versicolor",
			"6.4,3.2,4.5,1.5,Iris-versicolor",
			"6.9,3.1,4.9,1.5,Iris-versicolor",
			"5.5,2.3,4.0,1.3,Iris-versicolor",
			"6.5,2.8,4.6,1.5,Iris-versicolor",
			"5.7,2.8,4.5,1.3,Iris-versicolor",
			"6.3,3.3,4.7,1.6,Iris-versicolor",
			"4.9,2.4,3.3,1.0,Iris-versicolor",
			"6.6,2.9,4.6,1.3,Iris-versicolor",
			"5.2,2.7,3.9,1.4,Iris-versicolor",
			"5.0,2.0,3.5,1.0,Iris-versicolor",
			"5.9,3.0,4.2,1.5,Iris-versicolor",
			"6.0,2.2,4.0,1.0,Iris-versicolor",
			"6.1,2.9,4.7,1.4,Iris-versicolor",
			"5.6,2.9,3.6,1.3,Iris-versicolor",
			"6.7,3.1,4.4,1.4,Iris-versicolor",
			"5.6,3.0,4.5,1.5,Iris-versicolor",
			"5.8,2.7,4.1,1.0,Iris-versicolor",
			"6.2,2.2,4.5,1.5,Iris-versicolor",
			"5.6,2.5,3.9,1.1,Iris-versicolor",
			"5.9,3.2,4.8,1.8,Iris-versicolor",
			"6.1,2.8,4.0,1.3,Iris-versicolor",
			"6.3,2.5,4.9,1.5,Iris-versicolor",
			"6.1,2.8,4.7,1.2,Iris-versicolor",
			"6.4,2.9,4.3,1.3,Iris-versicolor",
			"6.6,3.0,4.4,1.4,Iris-versicolor",
			"6.8,2.8,4.8,1.4,Iris-versicolor",
			"6.7,3.0,5.0,1.7,Iris-versicolor",
			"6.0,2.9,4.5,1.5,Iris-versicolor",
			"5.7,2.6,3.5,1.0,Iris-versicolor",
			"5.5,2.4,3.8,1.1,Iris-versicolor",
			"5.5,2.4,3.7,1.0,Iris-versicolor",
			"5.8,2.7,3.9,1.2,Iris-versicolor",
			"6.0,2.7,5.1,1.6,Iris-versicolor",
			"5.4,3.0,4.5,1.5,Iris-versicolor",
			"6.0,3.4,4.5,1.6,Iris-versicolor",
			"6.7,3.1,4.7,1.5,Iris-versicolor",
			"6.3,2.3,4.4,1.3,Iris-versicolor",
			"5.6,3.0,4.1,1.3,Iris-versicolor",
			"5.5,2.5,4.0,1.3,Iris-versicolor",
			"5.5,2.6,4.4,1.2,Iris-versicolor",
			"6.1,3.0,4.6,1.4,Iris-versicolor",
			"5.8,2.6,4.0,1.2,Iris-versicolor",
			"5.0,2.3,3.3,1.0,Iris-versicolor",
			"5.6,2.7,4.2,1.3,Iris-versicolor",
			"5.7,3.0,4.2,1.2,Iris-versicolor",
			"5.7,2.9,4.2,1.3,Iris-versicolor",
			"6.2,2.9,4.3,1.3,Iris-versicolor",
			"5.1,2.5,3.0,1.1,Iris-versicolor",
			"5.7,2.8,4.1,1.3,Iris-versicolor",
			"6.3,3.3,6.0,2.5,Iris-virginica", "5.8,2.7,5.1,1.9,Iris-virginica",
			"7.1,3.0,5.9,2.1,Iris-virginica", "6.3,2.9,5.6,1.8,Iris-virginica",
			"6.5,3.0,5.8,2.2,Iris-virginica", "7.6,3.0,6.6,2.1,Iris-virginica",
			"4.9,2.5,4.5,1.7,Iris-virginica", "7.3,2.9,6.3,1.8,Iris-virginica",
			"6.7,2.5,5.8,1.8,Iris-virginica", "7.2,3.6,6.1,2.5,Iris-virginica",
			"6.5,3.2,5.1,2.0,Iris-virginica", "6.4,2.7,5.3,1.9,Iris-virginica",
			"6.8,3.0,5.5,2.1,Iris-virginica", "5.7,2.5,5.0,2.0,Iris-virginica",
			"5.8,2.8,5.1,2.4,Iris-virginica", "6.4,3.2,5.3,2.3,Iris-virginica",
			"6.5,3.0,5.5,1.8,Iris-virginica", "7.7,3.8,6.7,2.2,Iris-virginica",
			"7.7,2.6,6.9,2.3,Iris-virginica", "6.0,2.2,5.0,1.5,Iris-virginica",
			"6.9,3.2,5.7,2.3,Iris-virginica", "5.6,2.8,4.9,2.0,Iris-virginica",
			"7.7,2.8,6.7,2.0,Iris-virginica", "6.3,2.7,4.9,1.8,Iris-virginica",
			"6.7,3.3,5.7,2.1,Iris-virginica", "7.2,3.2,6.0,1.8,Iris-virginica",
			"6.2,2.8,4.8,1.8,Iris-virginica", "6.1,3.0,4.9,1.8,Iris-virginica",
			"6.4,2.8,5.6,2.1,Iris-virginica", "7.2,3.0,5.8,1.6,Iris-virginica",
			"7.4,2.8,6.1,1.9,Iris-virginica", "7.9,3.8,6.4,2.0,Iris-virginica",
			"6.4,2.8,5.6,2.2,Iris-virginica", "6.3,2.8,5.1,1.5,Iris-virginica",
			"6.1,2.6,5.6,1.4,Iris-virginica", "7.7,3.0,6.1,2.3,Iris-virginica",
			"6.3,3.4,5.6,2.4,Iris-virginica", "6.4,3.1,5.5,1.8,Iris-virginica",
			"6.0,3.0,4.8,1.8,Iris-virginica", "6.9,3.1,5.4,2.1,Iris-virginica",
			"6.7,3.1,5.6,2.4,Iris-virginica", "6.9,3.1,5.1,2.3,Iris-virginica",
			"5.8,2.7,5.1,1.9,Iris-virginica", "6.8,3.2,5.9,2.3,Iris-virginica",
			"6.7,3.3,5.7,2.5,Iris-virginica", "6.7,3.0,5.2,2.3,Iris-virginica",
			"6.3,2.5,5.0,1.9,Iris-virginica", "6.5,3.0,5.2,2.0,Iris-virginica",
			"6.2,3.4,5.4,2.3,Iris-virginica", "5.9,3.0,5.1,1.8,Iris-virginica" };

	public IrisData() {
		dadosTreinamento = new String[75];
		dadosValidacao = new String[39];
		dadosTeste = new String[36];
		String[] irisDataNormalizada = normalizarIrisData();
		inicializaConjuntos(irisDataNormalizada);
	}

	public void test() {

		String[] irisDataNormalizada = normalizarIrisData();

		for (int i = 0; i < 150; i++) {

			System.out.println(irisDataNormalizada[i]);

		}

		inicializaConjuntos(irisDataNormalizada);

		for (int i = 0; i < 36; i++) {
			for (int j = 0; j < 39; j++) {
				if (dadosTeste[i].equalsIgnoreCase(dadosValidacao[j])) {
					System.out.println("tem igual");
				}
			}
		}

		for (int i = 0; i < 36; i++) {
			for (int j = 0; j < 75; j++) {
				if (dadosTeste[i].equalsIgnoreCase(dadosTreinamento[j])) {
					System.out.println("tem igual " + dadosTeste[i] + " "
							+ dadosTreinamento[j]);
				}
			}
		}

		System.out.println("fim");

	}// fim test

	public String[] getDadosTreinamento() {
		return dadosTreinamento;
	}

	public void setDadosTreinamento(String[] dadosTreinamento) {
		this.dadosTreinamento = dadosTreinamento;
	}

	public String[] getDadosValidacao() {
		return dadosValidacao;
	}

	public void setDadosValidacao(String[] dadosValidacao) {
		this.dadosValidacao = dadosValidacao;
	}

	public String[] getDadosTeste() {
		return dadosTeste;
	}

	public void setDadosTeste(String[] dadosTeste) {
		this.dadosTeste = dadosTeste;
	}

	public void inicializaConjuntos(String[] dataNormalizado) {
		int indexTreinamento = 0;
		int indexValidacao = 0;
		int indexTeste = 0;

		// Setosa
		for (int i = 0; i < 25; i++, indexTreinamento++) {
			dadosTreinamento[indexTreinamento] = dataNormalizado[i];
		}

		for (int i = 25; i < 38; i++, indexValidacao++) {
			dadosValidacao[indexValidacao] = dataNormalizado[i];
		}

		for (int i = 38; i < 50; i++, indexTeste++) {
			dadosTeste[indexTeste] = dataNormalizado[i];
		}

		// Versicolor
		for (int i = 50; i < 75; i++, indexTreinamento++) {
			dadosTreinamento[indexTreinamento] = dataNormalizado[i];
		}

		for (int i = 75; i < 88; i++, indexValidacao++) {
			dadosValidacao[indexValidacao] = dataNormalizado[i];
		}

		for (int i = 88; i < 100; i++, indexTeste++) {
			dadosTeste[indexTeste] = dataNormalizado[i];
		}

		// Virginica
		for (int i = 100; i < 125; i++, indexTreinamento++) {
			dadosTreinamento[indexTreinamento] = dataNormalizado[i];
		}

		for (int i = 125; i < 138; i++, indexValidacao++) {
			dadosValidacao[indexValidacao] = dataNormalizado[i];
		}

		for (int i = 138; i < 150; i++, indexTeste++) {
			dadosTeste[indexTeste] = dataNormalizado[i];
		}

	} // inicializa conjuntos

	public static String[] normalizarIrisData() {

		String[] tempDataSet = IrisData.DATA;

		int tamanhoDataSet = tempDataSet.length;
		ArrayList<String[]> dataSet = new ArrayList<String[]>();

		for (int i = 0; i < tamanhoDataSet; i++) {

			String[] padrao = tempDataSet[i].split(",");

			dataSet.add(padrao);

		} // fim for

		double x0Maximo = 0;
		double x0Minimo = 999;
		double x1Maximo = 0;
		double x1Minimo = 999;
		double x2Maximo = 0;
		double x2Minimo = 999;
		double x3Maximo = 0;
		double x3Minimo = 999;
		double x0 = 0;
		double x1 = 0;
		double x2 = 0;
		double x3 = 0;

		for (int i = 0; i < dataSet.size(); i++) {

			String[] padrao = dataSet.get(i);
			x0 = Double.parseDouble(padrao[0]);

			if (x0Maximo < x0) {
				x0Maximo = x0;
			}

			if (x0Minimo > x0) {
				x0Minimo = x0;
			}

			x1 = Double.parseDouble(padrao[1]);

			if (x1Maximo < x1) {
				x1Maximo = x1;
			}

			if (x1Minimo > x1) {
				x1Minimo = x1;
			}

			x2 = Double.parseDouble(padrao[2]);

			if (x2Maximo < x2) {
				x2Maximo = x2;
			}

			if (x2Minimo > x2) {
				x2Minimo = x2;
			}

			x3 = Double.parseDouble(padrao[3]);

			if (x3Maximo < x3) {
				x3Maximo = x3;
			}

			if (x3Minimo > x3) {
				x3Minimo = x3;
			}

		}

		StringBuffer bff = new StringBuffer();

		String[] normalizado = new String[tempDataSet.length];

		for (int i = 0; i < dataSet.size(); i++) {

			String[] padrao = dataSet.get(i);

			x0 = (Double.parseDouble(padrao[0]) - x0Minimo)
					/ (x0Maximo - x0Minimo);

			x1 = (Double.parseDouble(padrao[1]) - x1Minimo)
					/ (x1Maximo - x1Minimo);

			x2 = (Double.parseDouble(padrao[2]) - x2Minimo)
					/ (x2Maximo - x2Minimo);

			x3 = (Double.parseDouble(padrao[3]) - x3Minimo)
					/ (x3Maximo - x3Minimo);

			bff.setLength(0);

			bff.append(x0);

			bff.append(",");

			bff.append(x1);

			bff.append(",");

			bff.append(x2);

			bff.append(",");

			bff.append(x3);

			bff.append(",");

			bff.append(obterVetorDeSaidaDesejada(padrao[4]));
			if (x0 > 1 || x1 > 1 | x2 > 1 || x3 > 1) {
				System.out.println("Erro");
			}
			normalizado[i] = bff.toString();
		}

		return normalizado;

	}

	public static String obterVetorDeSaidaDesejada(String classeDesejada) {
		String saidaDesejada = "";
		if ("Iris-setosa".equalsIgnoreCase(classeDesejada)) {
			saidaDesejada = "1.0,0.0,0.0";
		} else if ("Iris-versicolor".equalsIgnoreCase(classeDesejada)) {
			saidaDesejada = "0.0,1.0,0.0";
		} else if ("Iris-virginica".equalsIgnoreCase(classeDesejada)) {
			saidaDesejada = "0.0,0.0,1.0";
		}
		return saidaDesejada;

	}
	
	
	
}// fim IrisData
