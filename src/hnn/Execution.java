package hnn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hnn.models.HNN;
import hnn.models.Interface;
import hnn.models.Node;
import hnn.models.Pair;
import hnn.models.Path;
import hnn.models.Stretch;

public class Execution {

	private long time;
	private int iterations;
	private Pair pair;
	private HNN hnn;
	private List<Interface> interfaces;
	private List<Execution> executions;

	public Execution() {
		// TODO Auto-generated constructor stub
	}

	public Execution(long time, int iterations, HNN hnn) {
		this.time = time;
		this.iterations = iterations;
		this.executions = new ArrayList<Execution>();
		this.setHnn(hnn);
	}

	public Path createPath() {
		Map<Node, Stretch> neuronsMap = new HashMap<Node, Stretch>();
		List<Interface> interfaces = new ArrayList<Interface>();
		Path path = new Path(this.pair);
		fillIntefaceList(this, interfaces);
		for (Interface inf : interfaces) {
			neuronsMap.put(inf.getPair().getFrom(),
					interfaceToStretchParser(path, inf));
		}

		Node from = pair.getFrom();
		Stretch stretch = null;
		for (int i = 0; i < interfaces.size(); i++) {
			stretch = neuronsMap.remove(from);
			if (stretch != null) {
				path.addStretch(stretch);
				from = stretch.getPair().getTo();
			} else {
				System.out.println("Erro na formação do caminho " + this.pair);
				break;
			}
			// System.out.println(stretch.getPair().getFrom());
		}

		return path;
	}

	public double calcularCost() {
		double cost = 0;
		for (Interface inf : interfaces) {
			cost += inf.getCost();
		}
		return cost;
	}

	private Stretch interfaceToStretchParser(Path path, Interface inf) {
		return new Stretch(path, inf.getPair(), inf.getCost());
	}

	private void fillIntefaceList(Execution exec, List<Interface> interfaces) {
		List<Interface> infsAux = exec.getInterfaces();
		List<Execution> execsAux = null;
		if (infsAux != null && infsAux.size() > 0) {
			interfaces.addAll(infsAux);
		}
		execsAux = exec.getExecutions();
		if (execsAux != null && execsAux.size() > 0) {
			for (Execution execution : execsAux) {
				fillIntefaceList(execution, interfaces);
			}
		}
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public List<Execution> getExecutions() {
		return executions;
	}

	public void setExecutions(List<Execution> executions) {
		this.executions = executions;
	}

	public Pair getPair() {
		return pair;
	}

	public void setPair(Pair pair) {
		this.pair = pair;
	}

	public List<Interface> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<Interface> interfaces) {
		this.interfaces = interfaces;
	}

	public HNN getHnn() {
		return hnn;
	}

	public void setHnn(HNN hnn) {
		this.hnn = hnn;
	}

}
