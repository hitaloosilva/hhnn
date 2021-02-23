package hnn;

import java.util.ArrayList;
import java.util.List;

public class Resultado {
	private String from;
	private String to;
	private String ite;
	private String time;
	private String level;
	private String parent;
	private List<Resultado> resultados;

	public Resultado(String from, String to, String ite, String time,
			String level, String parent) {
		this.from = from;
		this.to = to;
		this.ite = ite;
		this.time = time;
		this.level = level;
		this.parent = parent;
		resultados = new ArrayList<Resultado>();
	}

	public Resultado(String[] fields) {
		this.from = fields[0];
		this.to = fields[1];
		this.ite = fields[2];
		this.time = fields[3];
		this.level = fields[4];
		this.parent = fields[5];
		resultados = new ArrayList<Resultado>();
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getIte() {
		return ite;
	}

	public void setIte(String ite) {
		this.ite = ite;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<Resultado> getResultados() {
		return resultados;
	}

	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}

}