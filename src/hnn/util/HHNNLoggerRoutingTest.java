package hnn.util;

import hnn.Execution;
import hnn.StaticalExecution;
import hnn.HHnnRWA;
import hnn.HnnRWA;
import hnn.RoutingTest;
import hnn.StaticalRoutingTest;
import hnn.models.Interface;
import hnn.models.Neuron;
import hnn.models.Node;
import hnn.models.Operators;
import hnn.models.Pair;
import hnn.models.Path;
import hnn.models.Stretch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import benchmarks.dijkstra.Vertex;

public class HHNNLoggerRoutingTest {

	private Logger loggerStatus = Logger.getLogger(HHnnRWA.class);

	public HHNNLoggerRoutingTest(String file, boolean append) {
		initLogger(file, append);
	}

	public HHNNLoggerRoutingTest(String file) {
		initLogger(file, false);
	}

	public HHNNLoggerRoutingTest() {
		initLogger("d:/log_hhnn_rwa.txt", false);
	}

	private void initLogger(String file, boolean append) {
		FileAppender fileappender;
		try {
			fileappender = new FileAppender(new PatternLayout(), file);
			fileappender.setName(HnnRWA.class.toString());
			fileappender.setLayout(new PatternLayout("%m%n"));
			fileappender.setThreshold(Level.DEBUG);
			fileappender.setAppend(append);
			fileappender.activateOptions();
			loggerStatus.addAppender(fileappender);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void exportToCSV(Pair pair, int iterations, long time,
			List<Neuron> neurons) {
		List<Node> nodes = null;// this.node.getNodes();
		StringBuffer buff = new StringBuffer();
		// buff.append(pair.getFrom().getParent());
		// buff.append("-");
		buff.append(pair.getFrom());
		buff.append("-");
		// buff.append(pair.getTo().getParent());
		// buff.append("-");
		buff.append(pair.getTo());
		buff.append(",");
		buff.append(iterations);
		buff.append(",");
		buff.append(time);
		buff.append(",");
		buff.append(pair.getFrom().getLevel());
		buff.append(",");
		buff.append(pair.getFrom().getParent());

		if (neurons != null) {
			int indice = 0;
			Neuron neuron = null;
			Interface inf = null;
			for (int x = 0; x < nodes.size(); x++) {
				for (int i = 0; i < nodes.size(); i++) {
					if (x != i) {
						neuron = neurons.get(indice++);
						if (neuron.getOutput() >= 0.5) {
							inf = neuron.getInterfaceParent();
							buff.append(",");
							buff.append(inf.getPair().getFrom());
							buff.append("-");
							buff.append(inf.getPair().getTo());
						}
					}

				}
			}
		}
		loggerStatus.info(buff.toString());
	}

	public void exportToCSV(Pair pair, int iterations, RoutingTest routingTest,
			List<Neuron> neurons) {
		List<Node> nodes = null;// this.node.getNodes();
		StringBuffer buff = new StringBuffer();
		// buff.append(pair.getFrom().getParent());
		// buff.append("-");
		buff.append(pair.getFrom());
		buff.append("-");
		// buff.append(pair.getTo().getParent());
		// buff.append("-");
		buff.append(pair.getTo());
		buff.append(",");
		buff.append(iterations);
		buff.append(",");
		buff.append(routingTest.getTotalTime());
		buff.append(",");
		buff.append(pair.getFrom().getLevel());
		buff.append(",");
		buff.append(pair.getFrom().getParent());
		buff.append(",");
		Execution exec = routingTest.getExecution();
		if (exec != null) {

			List<Execution> executions = exec.getExecutions();
			executions.add(exec);
			Collections.sort(executions, new Comparator<Execution>() {
				@Override
				public int compare(Execution o1, Execution o2) {
					return o1.getHnn().getNode()
							.compareTo(o2.getHnn().getNode());
				}

			});
			int indice = 0;
			int order = 0;
			for (Execution execution : executions) {
				if (execution.getHnn().getNode().equals(new Node(0, 0, null))) {
					order = 0;
				} else if (execution.getHnn().getNode()
						.equals(new Node(0, 1, null))) {
					order = 1;
				} else if (execution.getHnn().getNode()
						.equals(new Node(1, 1, null))) {
					order = 2;
				} else if (execution.getHnn().getNode()
						.equals(new Node(2, 1, null))) {
					order = 3;
				} else if (execution.getHnn().getNode()
						.equals(new Node(3, 1, null))) {
					order = 4;
				}

				for (int i = 0; i < order - indice; i++) {
					buff.append(",,");
					indice++;
				}
				buff.append(execution.getIterations());
				buff.append(",");
				buff.append(execution.getTime());
				if (order < 4)
					buff.append(",");
				indice++;
			}

			for (int i = 0; i < 5 - indice; i++) {
				if (i == (4 - order - 1))
					buff.append(",");
				else
					buff.append(",,");
			}

		}
		loggerStatus.info(buff.toString());
	}

	public void exportToCSV(List<StaticalRoutingTest> staticalRoutingTests) {

		HashMap<String, String> hnnIds = new HashMap<String, String>();
		List<Map<StaticalRoutingTest, Map<String, StaticalExecution>>> list = new ArrayList<Map<StaticalRoutingTest, Map<String, StaticalExecution>>>();
		HashMap<StaticalRoutingTest, Map<String, StaticalExecution>> map = null;
		HashMap<String, StaticalExecution> hashTemp = null;
		for (StaticalRoutingTest staticalRoutingTest : staticalRoutingTests) {
			map = new HashMap<StaticalRoutingTest, Map<String, StaticalExecution>>();
			hashTemp = new HashMap<String, StaticalExecution>();
			for (StaticalExecution execution : staticalRoutingTest
					.getExecutions()) {
				if (!hnnIds.containsKey(execution.getHnn().toString())) {
					hnnIds.put(execution.getHnn().toString(), execution
							.getHnn().toString());
				}
				hashTemp.put(execution.getHnn().toString(), execution);
			}
			map.put(staticalRoutingTest, hashTemp);
			list.add(map);
		}
		List<String> ids = new ArrayList<String>(hnnIds.keySet());
		Collections.sort(ids);
		Iterator<Entry<StaticalRoutingTest, Map<String, StaticalExecution>>> ite = null;
		Entry<StaticalRoutingTest, Map<String, StaticalExecution>> entry;
		StaticalRoutingTest staticalRoutingTest = null;
		StaticalExecution execution = null;
		List<Node> nodes = null;// this.node.getNodes();
		StringBuffer buff = new StringBuffer();
		buff.append("Pair;");
		for (String string : ids) {
			buff.append("HNN ");
			buff.append(string);
			buff.append(";;");
		}
		buff.append("Time ; Iterations");
		loggerStatus.info(buff.toString());

		for (Map<StaticalRoutingTest, Map<String, StaticalExecution>> map2 : list) {
			ite = map2.entrySet().iterator();
			while (ite.hasNext()) {
				entry = ite.next();
				staticalRoutingTest = entry.getKey();
				buff = new StringBuffer();
				buff.append(String.format("%02d-%02d;", staticalRoutingTest
						.getPair().getFrom().getId(), staticalRoutingTest
						.getPair().getTo().getId()));
				hashTemp = (HashMap<String, StaticalExecution>) entry
						.getValue();
				for (String string : ids) {
					execution = hashTemp.get(string);
					if (execution != null) {
						buff.append(String.format("%.2f (%.2f);%.2f (%.2f);",
								execution.getMeanTime() / 1000,
								execution.getStandardDeviationTime() / 1000,
								execution.getMeanIterations(),
								execution.getStandardDerivationIterations()));
					} else {
						buff.append("00.00 (00.00);00.00 (00.00);");
					}
				}
				buff.append(String.format("%.2f (%.2f); %.2f (%.2f);",
						staticalRoutingTest.getMeanTime() / 1000,
						staticalRoutingTest.getStandardDeviationTime() / 1000,
						staticalRoutingTest.getMeanIteration(),
						staticalRoutingTest.getStandardDeviationIteration()));

			}
			loggerStatus.info(buff.toString());
		}

	}

	public void exportToCSVCost(List<Execution> executions) {
		StringBuffer buff = new StringBuffer();
		buff.append("Pair;Path;Cost");
		loggerStatus.info(buff.toString());

		Path path = null;
		List<Stretch> stretchs = null;
		Stretch stretch = null;
		for (Execution execution : executions) {
			buff = new StringBuffer();
			buff.append(String.format("%02d-%02d;", execution.getPair()
					.getFrom().getId(), execution.getPair().getTo().getId()));
			path = execution.createPath();
			stretchs = path.getStretchs();
			for (int i = 0; i < stretchs.size(); i++) {
				stretch = stretchs.get(i);
				buff.append(stretch.getPair().getFrom().getId());
				buff.append("-");
			}
			buff.append(execution.getPair().getTo().getId());
			buff.append(";");
			buff.append(String.format("%2.2f", path.getTotalCost()));
			loggerStatus.info(buff.toString());
		}
	}

	public void exportToCSVCost(Execution execution) {
		StringBuffer buff = new StringBuffer();
		Path path = null;
		List<Stretch> stretchs = null;
		Stretch stretch = null;
		buff = new StringBuffer();
		buff.append(String.format("%02d-%02d;", execution.getPair().getFrom()
				.getId(), execution.getPair().getTo().getId()));
		path = execution.createPath();
		stretchs = path.getStretchs();
		for (int i = 0; i < stretchs.size(); i++) {
			stretch = stretchs.get(i);
			buff.append(stretch.getPair().getFrom().getId());
			buff.append("-");
		}
		buff.append(execution.getPair().getTo().getId());
		buff.append(";");
		buff.append(String.format("%2.2f", path.getTotalCost()));
		loggerStatus.info(buff.toString());
	}

	public void exportToCSVCostPSO(Execution execution) {
		StringBuffer buff = new StringBuffer();
		Path path = null;
		List<Stretch> stretchs = null;
		Stretch stretch = null;
		buff = new StringBuffer();
		buff.append(String.format("%02d-%02d;", execution.getPair().getFrom()
				.getId(), execution.getPair().getTo().getId()));
		Operators operators = execution.getHnn().getNeurons().get(0)
				.getOperators();
		buff.append(String.format("%d;%d;", operators.getConfig().getU()[0],
				operators.getConfig().getU()[3]));
		path = execution.createPath();
		stretchs = path.getStretchs();
		for (int i = 0; i < stretchs.size(); i++) {
			stretch = stretchs.get(i);
			buff.append(stretch.getPair().getFrom().getId());
			buff.append("-");
		}
		buff.append(execution.getPair().getTo().getId());
		buff.append(";");
		buff.append(String.format("%2.2f;", path.getTotalCost()));
		buff.append(execution.getIterations());
		loggerStatus.info(buff.toString());
	}

	public void exportToCSVDijkstra(Vertex[] vertices, Vertex v, int i,
			List<Vertex> path) {
		StringBuffer buff = new StringBuffer();
		buff.append(String.format("%02d-%02d;%s;%2.2f",
				Integer.parseInt(vertices[i].toString()),
				Integer.parseInt(v.toString()), path, v.minDistance));
		loggerStatus.info(buff.toString());
	}
}
