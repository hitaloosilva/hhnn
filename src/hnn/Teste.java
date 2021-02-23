package hnn;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teste {

	public static void main(String[] args) {
		compilarSaidaHHNN();
	}

	private static void compilarSaidaHNN() {
		String csvFile = "d:/file.txt";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			List<Resultado> resultados = new ArrayList<Resultado>();
			while ((line = br.readLine()) != null) {
				resultados.add(new Resultado(line.split(cvsSplitBy)));
			}

			Map<String, Resultado> mapResults = new HashMap<String, Resultado>();
			String key;
			Resultado temp = null;
			for (Resultado resultado : resultados) {
				key = resultado.getFrom() + "-" + resultado.getTo();
				temp = mapResults.get(key);
				if (temp == null) {
					temp = resultado;
					mapResults.put(key, temp);
				} else {
					temp.setIte(Integer.parseInt(temp.getIte())
							+ Integer.parseInt(resultado.getIte()) + "");
					temp.setTime(Integer.parseInt(temp.getTime())
							+ Integer.parseInt(resultado.getTime()) + "");
				}
			}

			resultados = new ArrayList<Resultado>(mapResults.values());
			for (Resultado resultado : resultados) {
				System.out.println(resultado.getFrom() + ","
						+ resultado.getTo() + "," + resultado.getIte() + ","
						+ resultado.getTime());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
	}

	private static void compilarSaidaHHNN() {
		String csvFile = "d:/log_hhnnrwa.txt";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			List<Resultado> resultados = new ArrayList<Resultado>();
			while ((line = br.readLine()) != null) {
				resultados.add(new Resultado(line.split(cvsSplitBy)));
			}

			List<Resultado> aux = new ArrayList<Resultado>();
			List<Resultado> resul = new ArrayList<Resultado>();
			for (Resultado resultado : resultados) {
				if (resultado.getIte().equals("0")) {
					resultado.setResultados(aux);
					aux = new ArrayList<Resultado>();
					resul.add(resultado);
				} else {
					aux.add(resultado);
				}
			}

			Map<String, Resultado> mapResults = new HashMap<String, Resultado>();
			String key;
			Resultado temp = null;
			Resultado resultTemp = null;
			Resultado resultTemporario = null;
			for (Resultado resultado : resul) {
				key = resultado.getFrom() + "-" + resultado.getTo();
				temp = mapResults.get(key);
				if (temp == null) {
					temp = resultado;
					mapResults.put(key, temp);
				} else {
					temp.setIte(Integer.parseInt(temp.getIte())
							+ Integer.parseInt(resultado.getIte()) + "");
					temp.setTime(Integer.parseInt(temp.getTime())
							+ Integer.parseInt(resultado.getTime()) + "");

					for (int i = 0; i < resultado.getResultados().size(); i++) {
						resultTemp = resultado.getResultados().get(i);
						resultTemporario = temp.getResultados().get(i);
						resultTemporario.setIte(Integer
								.parseInt(resultTemporario.getIte())
								+ Integer.parseInt(resultTemp.getIte()) + "");
						resultTemporario.setTime(Integer
								.parseInt(resultTemporario.getTime())
								+ Integer.parseInt(resultTemp.getTime()) + "");
					}
				}
			}
			resultados = new ArrayList<Resultado>(mapResults.values());
			for (Resultado resultado : resultados) {
				System.out.print(resultado.getFrom() + "," + resultado.getTo()
						+ ",");

				for (Resultado resultTempo : resultado.getResultados()) {
					System.out.print(resultTempo.getIte() + ","
							+ resultTempo.getTime() + ",");
				}
				for (int i = resultado.getResultados().size(); i < 5; i++) {
					System.out.print("0,0,");
				}
				System.out.println(resultado.getIte() + ","
						+ resultado.getTime());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
	}

}
