package mlp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TesteMLP {

	private double n;
	private int qtdNeuronioCamadasEscondidas;
	private int numeroIteracoes;
	private boolean isShuffle;
	private List<Double> EQRTreinamentoList;
	private List<Double> EQRValidacaoList;
	private List<Double> epocas;

	public TesteMLP(double n, int qtdNeuronioCamadasEscondidas,
			int numeroIteracoes, boolean isShuffle) {
		this.n = n;
		this.qtdNeuronioCamadasEscondidas = qtdNeuronioCamadasEscondidas;
		this.numeroIteracoes = numeroIteracoes;
		this.isShuffle = isShuffle;
		this.EQRTreinamentoList = new ArrayList<Double>();
		this.EQRValidacaoList = new ArrayList<Double>();
		this.epocas = new ArrayList<Double>();
	}

	public double getN() {
		return n;
	}

	public void setN(double n) {
		this.n = n;
	}

	public int getQtdNeuronioCamadasEscondidas() {
		return qtdNeuronioCamadasEscondidas;
	}

	public void setQtdNeuronioCamadasEscondidas(int qtdNeuronioCamadasEscondidas) {
		this.qtdNeuronioCamadasEscondidas = qtdNeuronioCamadasEscondidas;
	}

	public int getNumeroIteracoes() {
		return numeroIteracoes;
	}

	public void setNumeroIteracoes(int numeroIteracoes) {
		this.numeroIteracoes = numeroIteracoes;
	}

	public boolean isShuffle() {
		return isShuffle;
	}

	public void setShuffle(boolean isShuffle) {
		this.isShuffle = isShuffle;
	}

	public List<Double> getEQRTreinamentoList() {
		return EQRTreinamentoList;
	}

	public void setEQRTreinamentoList(List<Double> eQRTreinamentoList) {
		EQRTreinamentoList = eQRTreinamentoList;
	}

	public List<Double> getEQRValidacaoList() {
		return EQRValidacaoList;
	}

	public void setEQRValidacaoList(List<Double> eQRValidacaoList) {
		EQRValidacaoList = eQRValidacaoList;
	}

	public List<Double> getEpocas() {
		return epocas;
	}

	public void setEpocas(List<Double> epocas) {
		this.epocas = epocas;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(this.n);
		buf.append("\t");
		buf.append(this.qtdNeuronioCamadasEscondidas);
		buf.append("\t");
		buf.append(this.isShuffle);
		buf.append("\t");
		buf.append(this.numeroIteracoes);
		buf.append("\t");
		
		Collections.sort(this.EQRTreinamentoList);
		Collections.sort(this.EQRValidacaoList);
		Collections.sort(this.epocas);

		double media = caulcularMedia(this.epocas);
		double desvioPadrao = calcularDesvioPadrao(this.epocas, media);
		buf.append(media);
		buf.append("\t");
		buf.append(desvioPadrao);
		buf.append("\t");
		buf.append(this.epocas.get(this.epocas.size() - 1));
		buf.append("\t");
		buf.append(this.epocas.get(0));
		buf.append("\t");
		
		media = caulcularMedia(this.EQRTreinamentoList);
		desvioPadrao = calcularDesvioPadrao(this.EQRTreinamentoList, media);
		buf.append(media);
		buf.append("\t");
		buf.append(desvioPadrao);
		buf.append("\t");
		buf.append(this.EQRTreinamentoList.get(this.EQRTreinamentoList.size() - 1));
		buf.append("\t");
		buf.append(this.EQRTreinamentoList.get(0));
		buf.append("\t");
		
		media = caulcularMedia(this.EQRValidacaoList);
		desvioPadrao = calcularDesvioPadrao(this.EQRValidacaoList, media);
		buf.append(media);
		buf.append("\t");
		buf.append(desvioPadrao);
		buf.append("\t");
		buf.append(this.EQRValidacaoList.get(this.EQRValidacaoList.size() - 1));
		buf.append("\t");
		buf.append(this.EQRValidacaoList.get(0));
		return buf.toString();
	}

	private double calcularDesvioPadrao(List<Double> list, double media) {
		double sum = 0;
		for (Double double1 : list) {
			sum += Math.pow(double1 - media, 2);
		}
		return Math.sqrt(sum / (list.size() - 1));
	}

	private double caulcularMedia(List<Double> list) {
		double sum = 0;
		for (Double double1 : list) {
			sum += double1;
		}
		return sum / list.size();
	}

}
