package hnn;

import hnn.models.Node;
import hnn.models.Operators;
import hnn.models.Pair;
import hnn.models.Path;
import hnn.util.DijkstraPathCost;
import hnn.util.HHNNLoggerRoutingTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.print.attribute.standard.Destination;

public class HNNSimulator {

	private NetworkTopologyFactory factory = null;

	public HNNSimulator() {
		this.factory = new NetworkTopologyFactory();
	}

	public void simulateRoutingNetworkWith4Nodes(Operators operators) {
		Node net = factory.createNetworkWith4Nodes();
		HnnRWA hnnRWA = new HnnRWA(net, operators);
		simulateRouting(net, hnnRWA);
	}

	public void simulateRoutingNetworkWith16Nodes(Operators operators) {
		Node net;
		net = factory.createNetworkWith16Nodes();
		HnnRWA hnnRWA = new HnnRWA(net, operators);
		simulateRouting(net, hnnRWA);
	}

	public void simulateRoutingNetworkWith64Nodes(Operators operators) {
		Node net;
		net = factory.createNetworkWith64Nodes();
		// net.printNode();
		HnnRWA hnnRWA = new HnnRWA(net, operators);
		simulateRouting(net, hnnRWA);

	}

	private void simulateRouting(Node net, HnnRWA hnnRWA) {
		Pair pair;
		Map<Pair, List<RoutingTest>> timesMap = new HashMap<Pair, List<RoutingTest>>();
		HHNNLoggerRoutingTest logger = new HHNNLoggerRoutingTest();
		long time = System.nanoTime();
		int netSize = net.getNodes().size();
		for (int i = 0; i < netSize; i++) {
			for (int j = 0; j < netSize; j++) {
				if (i != j) {
					pair = new Pair(net.getNodes().get(i), net.getNodes()
							.get(j));
					System.out.println(pair.getFrom() + "->" + pair.getTo());
					calculateRouteByPair(pair, timesMap, hnnRWA);
					List<StaticalRoutingTest> list = calculateMedia(timesMap);
					logger.exportToCSV(list);
					timesMap = new HashMap<Pair, List<RoutingTest>>();
				}
			}
		}
		System.out.println("Tempo = " + (System.nanoTime() - time));
		System.out.println("Gerando log...");

		System.out.println("Fim!");
	}

	private double simulateRoutingPSO(Node net, HnnRWA hnnRWA) {
		Pair pair;
		Random rand = new Random();
		int source;
		int dest;
		Execution execution = null;
		int sumIterations = 0;
		double sumPaths = 0;
		DijkstraPathCost costs = new DijkstraPathCost();
		double sim = 5.0;
		double custoDijkstra = 0;
		for (int i = 0; i < sim; i++) {
			source = rand.nextInt(63);
			dest = rand.nextInt(63);
			if (source != dest) {

				pair = new Pair(net.getNodes().get(source), net.getNodes().get(
						dest));
				// System.out.println(pair.getFrom() + "->" + pair.getTo());
				execution = hnnRWA.genereteBestLightpathTest(pair);
				sumIterations += execution.getIterations();
				sumPaths += execution.calcularCost() == costs
						.getCostByPair(pair) ? 0 : 1;
			} else {
				i--;
			}
		}

		return (sumPaths * 100) / sim;
	}

	public void simulateMultiLayerRoutingInNetwork(Operators operators) {
		Node net;
		Map<Pair, List<RoutingTest>> timesMap = new HashMap<Pair, List<RoutingTest>>();
		net = factory.createMultiLevelNetwork();
		HHnnRWA hnnRWA = new HHnnRWA(net, operators);
		HHNNLoggerRoutingTest logger = new HHNNLoggerRoutingTest(
				"d:/log_hhnn_rwa_4x4.txt");
		simulateMultiLayerNetwork(net, timesMap, hnnRWA, logger);
	}

	public void simulateMultiLayerRoutingInNetworkWith64Nodes(
			Operators operators) {
		Node net;
		Pair pair;

		Map<Pair, List<RoutingTest>> timesMap = new HashMap<Pair, List<RoutingTest>>();
		net = factory.createMultiLevelNetworkWith64Nodes();
		HHnnRWA hnnRWA = new HHnnRWA(net, operators);
		HHNNLoggerRoutingTest logger = new HHNNLoggerRoutingTest(
				"d:/log_hhnn_rwa_4x4x4.txt");
		System.out.println("Inicializando Memória...");
//		for (int h = 0; h < 5; h++) {
//			pair = new Pair(net.getNodes().get(0).getNodes().get(0).getNodes()
//					.get(0), net.getNodes().get(3).getNodes().get(3).getNodes()
//					.get(3));
//			calculateRouteByPairUsingMultLayerHNN(pair, timesMap, hnnRWA);
//		}
		System.out.println("Inicializando Testes...");
		timesMap = new HashMap<Pair, List<RoutingTest>>();
		long time = System.nanoTime();
		for (int x = 0; x < 4; x++) {
			for (int i = 0; i < 4; i++) {
				for (int z = 0; z < 4; z++) {
					for (int y = 0; y < 4; y++) {
						for (int j = 0; j < 4; j++) {
							for (int k = 0; k < 4; k++) {
								if (x != y || i != j || z != k) {

									pair = new Pair(net.getNodes().get(x)
											.getNodes().get(i).getNodes()
											.get(z), net.getNodes().get(y)
											.getNodes().get(j).getNodes()
											.get(k));
									System.out.println(pair.getFrom() + "->"
											+ pair.getTo());
									calculateRouteByPairUsingMultLayerHNN(pair,
											timesMap, hnnRWA);
								}
							}
						}

					}
				}
			}
		}

		System.out.println("Tempo = " + (System.nanoTime() - time));
		System.out.println("Gerando log...");
		List<StaticalRoutingTest> list = calculateMedia(timesMap);
		logger.exportToCSV(list);
		System.out.println("Fim!");

	}

	public void simulateMultiLayerRoutingInNetworkWith64NodesBy16(
			Operators operators) {
		Node net;
		Map<Pair, List<RoutingTest>> timesMap = new HashMap<Pair, List<RoutingTest>>();
		net = factory.createMultiLevelNetworkWith64NodesBy16();
//		net.printNode();
		HHnnRWA hnnRWA = new HHnnRWA(net, operators);
		HHNNLoggerRoutingTest logger = new HHNNLoggerRoutingTest(
				"d:/log_hhnn_rwa_4x16.txt");
		simulateMultiLayerNetwork(net, timesMap, hnnRWA, logger);
	}

	public void simulateMultiLayerRoutingInNetworkWith64NodesBy8(
			Operators operators) {
		Node net;
		Map<Pair, List<RoutingTest>> timesMap = new HashMap<Pair, List<RoutingTest>>();
		net = factory.createMultiLevelNetworkWith64NodesBy8();
		// net.printNode();
		HHnnRWA hnnRWA = new HHnnRWA(net, operators);
		HHNNLoggerRoutingTest logger = new HHNNLoggerRoutingTest(
				"d:/log_hhnn_rwa_8x8.txt");
		simulateMultiLayerNetwork(net, timesMap, hnnRWA, logger);
	}

	private void simulateMultiLayerNetwork(Node net,
			Map<Pair, List<RoutingTest>> timesMap, HHnnRWA hnnRWA,
			HHNNLoggerRoutingTest logger) {
		Pair pair;
		int sizeLayer1 = net.getNodes().size();
		int sizeLayer2 = net.getNodes().get(0).getNodes().size();
//		System.out.println("Inicializando Memória...");
//		for (int h = 0; h < 5; h++) {
//			pair = new Pair(net.getNodes().get(0).getNodes().get(0), net
//					.getNodes().get(sizeLayer1 - 1).getNodes()
//					.get(sizeLayer2 - 1));
//			calculateRouteByPairUsingMultLayerHNN(pair, timesMap, hnnRWA);
//		}
		System.out.println("Inicializando Testes...");
		timesMap = new HashMap<Pair, List<RoutingTest>>();
		long time = System.nanoTime();
		for (int x = 0; x < sizeLayer1; x++) {
			for (int i = 0; i < sizeLayer2; i++) {
				for (int y = 0; y < sizeLayer1; y++) {
					for (int j = 0; j < sizeLayer2; j++) {
						if (x != y || i != j) {
							pair = new Pair(net.getNodes().get(x).getNodes()
									.get(i), net.getNodes().get(y).getNodes()
									.get(j));
							System.out.println(pair.getFrom() + "->"
									+ pair.getTo());
							calculateRouteByPairUsingMultLayerHNN(pair,
									timesMap, hnnRWA);
						}
					}
				}

			}
		}
		System.out.println("Tempo = " + (System.nanoTime() - time));
		System.out.println("Gerando log...");
		List<StaticalRoutingTest> list = calculateMedia(timesMap);
		logger.exportToCSV(list);
		System.out.println("Fim!");
	}
	
	public void simulateRoutingNetworkWith4NodesCost(Operators operators) {
		Node net;
		net = factory.createNetworkWith4Nodes();
		HnnRWA hnnRWA = new HnnRWA(net, operators);
		simulateRoutingCost(net, hnnRWA, "d:/log_hhnn_rwa_4_cost.txt",
				operators);
	}

	public void simulateRoutingNetworkWith16NodesCost(Operators operators) {
		Node net;
		net = factory.createNetworkWith16Nodes();
		HnnRWA hnnRWA = new HnnRWA(net, operators);
		simulateRoutingCost(net, hnnRWA, "d:/log_hhnn_rwa_16_cost.txt",
				operators);
	}

	public void simulateRoutingNetworkWith64NodesCost(Operators operators) {
		Node net;
		net = factory.createNetworkWith64Nodes();
		// net.printNode();
		HnnRWA hnnRWA = new HnnRWA(net, operators);
		simulateRoutingCost(net, hnnRWA, "d:/log_hhnn_rwa_64_cost.txt",
				operators);
	}

	public double simulateRoutingNetworkWith64NodesPSO(Operators operators) {
		Node net;
		net = factory.createNetworkWith64Nodes();
		HnnRWA hnnRWA = new HnnRWA(net, operators);
		return simulateRoutingPSO(net, hnnRWA);
	}

	private void simulateRoutingCost(Node net, HnnRWA hnnRWA, String file,
			Operators operators) {
		Pair pair;
		HHNNLoggerRoutingTest logger = new HHNNLoggerRoutingTest(file, false);
		List<Execution> executions = new ArrayList<Execution>();
		int u[] = operators.getConfig().getU();
		long time = System.nanoTime();
		int netSize = net.getNodes().size();
		Execution execution = null;

		// for (int u0 = u[0]; u0 < 1700; u0 += 5) {
		// for (int u3 = u[3]; u3 > 100; u3 -= 5) {
		// u[0] = u0;
		// u[3] = u3;
		for (int i = 0; i < netSize; i++) {
			for (int j = 0; j < netSize; j++) {
				if (i != j) {
					pair = new Pair(net.getNodes().get(i), net.getNodes()
							.get(j));
					System.out.println(pair.getFrom() + "->" + pair.getTo());
					execution = hnnRWA.genereteBestLightpathTest(pair);
					logger.exportToCSVCost(execution);
					executions.add(execution);
				}
			}
		}
		// }
		// }
		System.out.println("Tempo = " + (System.nanoTime() - time));
		System.out.println("Gerando log...");
		// logger.exportToCSVCost(executions);
		System.out.println("Fim!");
	}

	public void simulateMultiLayerRoutingInNetworkCost(Operators operators) {
		Node net;
		net = factory.createMultiLevelNetwork();
		HHnnRWA hnnRWA = new HHnnRWA(net, operators);
		HHNNLoggerRoutingTest logger = new HHNNLoggerRoutingTest(
				"d:/log_hhnn_rwa_4x4_cost.txt");
		simulateMultiLayerNetworkCost(net, hnnRWA, logger);
	}

	public void simulateMultiLayerRoutingInNetworkWith64NodesCost(
			Operators operators) {
		Node net;
		Pair pair;
		List<Execution> executions = new ArrayList<Execution>();
		net = factory.createMultiLevelNetworkWith64Nodes();
		HHnnRWA hnnRWA = new HHnnRWA(net, operators);
		HHNNLoggerRoutingTest logger = new HHNNLoggerRoutingTest(
				"d:/log_hhnn_rwa_4x4x4_cost.txt");
		System.out.println("Inicializando Testes...");
		long time = System.nanoTime();
		for (int x = 0; x < 4; x++) {
			for (int i = 0; i < 4; i++) {
				for (int z = 0; z < 4; z++) {
					for (int y = 0; y < 4; y++) {
						for (int j = 0; j < 4; j++) {
							for (int k = 0; k < 4; k++) {
								if (x != y || i != j || z != k) {

									pair = new Pair(net.getNodes().get(x)
											.getNodes().get(i).getNodes()
											.get(z), net.getNodes().get(y)
											.getNodes().get(j).getNodes()
											.get(k));
									System.out.println(pair.getFrom() + "->"
											+ pair.getTo());
									executions.add(hnnRWA
											.generateBestLightpathTest(pair));
								}
							}
						}

					}
				}
			}
		}

		System.out.println("Tempo = " + (System.nanoTime() - time));
		System.out.println("Gerando log...");
		logger.exportToCSVCost(executions);
		System.out.println("Fim!");

	}

	public void simulateMultiLayerRoutingInNetworkWith64NodesBy16Cost(
			Operators operators) {
		Node net;
		net = factory.createMultiLevelNetworkWith64NodesBy16();
		HHnnRWA hnnRWA = new HHnnRWA(net, operators);
		HHNNLoggerRoutingTest logger = new HHNNLoggerRoutingTest(
				"d:/log_hhnn_rwa_4x16_cost.txt");
		simulateMultiLayerNetworkCost(net, hnnRWA, logger);
	}

	public void simulateMultiLayerRoutingInNetworkWith64NodesBy8Cost(
			Operators operators) {
		Node net;
		net = factory.createMultiLevelNetworkWith64NodesBy8();
		HHnnRWA hnnRWA = new HHnnRWA(net, operators);
		HHNNLoggerRoutingTest logger = new HHNNLoggerRoutingTest(
				"d:/log_hhnn_rwa_8x8_cost.txt");
		simulateMultiLayerNetworkCost(net, hnnRWA, logger);
	}

	private void simulateMultiLayerNetworkCost(Node net, HHnnRWA hnnRWA,
			HHNNLoggerRoutingTest logger) {
		Pair pair;
		int sizeLayer1 = net.getNodes().size();
		int sizeLayer2 = net.getNodes().get(0).getNodes().size();
		System.out.println("Inicializando Testes...");
		List<Execution> executions = new ArrayList<Execution>();
		long time = System.nanoTime();
		for (int x = 0; x < sizeLayer1; x++) {
			for (int i = 0; i < sizeLayer2; i++) {
				for (int y = 0; y < sizeLayer1; y++) {
					for (int j = 0; j < sizeLayer2; j++) {
						if (x != y || i != j) {
							pair = new Pair(net.getNodes().get(x).getNodes()
									.get(i), net.getNodes().get(y).getNodes()
									.get(j));
							System.out.println(pair.getFrom() + "->"
									+ pair.getTo());
							executions.add(hnnRWA
									.generateBestLightpathTest(pair));
						}
					}
				}

			}
		}
		System.out.println("Tempo = " + (System.nanoTime() - time));
		System.out.println("Gerando log...");
		logger.exportToCSVCost(executions);
		System.out.println("Fim!");
	}

	private void calculateRouteByPairUsingMultLayerHNN(Pair pair,
			Map<Pair, List<RoutingTest>> timesMap, HHnnRWA hnnRWA) {
		List<RoutingTest> routingTests;
		RoutingTest routingTest;
		routingTests = new ArrayList<RoutingTest>();
		if(timesMap.containsKey(pair)){
			System.out.println("deu merda");
		}
		timesMap.put(pair, routingTests);
		for (int h = 0; h < 30; h++) {
			routingTest = new RoutingTest();
			routingTest.setPair(pair);
			routingTest.setStartTime(System.nanoTime());
			routingTest.setExecution(hnnRWA.generateBestLightpathTest(pair));
			routingTest.getExecution().createPath();
			routingTest.setFinishTime(System.nanoTime());
			routingTests = timesMap.get(pair);
			routingTests.add(routingTest);
		}
	}

	private void calculateRouteByPair(Pair pair,
			Map<Pair, List<RoutingTest>> timesMap, HnnRWA hnnRWA) {
		List<RoutingTest> routingTests;
		RoutingTest routingTest;
		routingTests = new ArrayList<RoutingTest>();
		Execution execution = null;
		long time;
		timesMap.put(pair, routingTests);
		for (int h = 0; h < 30; h++) {
			routingTest = new RoutingTest();
			routingTest.setPair(pair);
			time = System.nanoTime();
			routingTest.setStartTime(time);
			execution = hnnRWA.genereteBestLightpathTest(pair);
			execution.createPath();
			routingTest.setFinishTime(System.nanoTime());
			execution.setTime(routingTest.getFinishTime() - time);
			routingTest.setExecution(execution);
			routingTests = timesMap.get(pair);
			routingTests.add(routingTest);
		}

	}

	private List<StaticalRoutingTest> calculateMedia(
			Map<Pair, List<RoutingTest>> timesMap) {
		Entry<Pair, List<RoutingTest>> entry = null;
		RoutingTestConverter converter = null;
		List<StaticalRoutingTest> list = new ArrayList<StaticalRoutingTest>();
		StaticalRoutingTest staticalRoutingTest = null;
		for (Iterator<Entry<Pair, List<RoutingTest>>> iterator = timesMap
				.entrySet().iterator(); iterator.hasNext();) {
			entry = (Entry<Pair, List<RoutingTest>>) iterator.next();
			converter = new RoutingTestConverter(entry.getValue());
			staticalRoutingTest = converter.convert();
			staticalRoutingTest.setPair(entry.getKey());
			// staticalRoutingTest.print();
			list.add(staticalRoutingTest);
		}

		return list;
	}

}
