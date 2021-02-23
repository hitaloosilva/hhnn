package hnn;

import hnn.models.Interface;
import hnn.models.Node;
import hnn.models.Pair;

import java.util.Arrays;
import java.util.List;

public class NetworkTopologyFactory {

	public Node createNetworkWith4Nodes() {
		Node rede = new Node(0, 0, null, null);
		createNodesInParent(rede, 0, 1);
		redeToVector(rede);
		return rede;
	}

	public Node createNetworkWith16Nodes() {
		Node rede = new Node(0, 0, null, null);
		createNodesInParent(rede, 0, 1);
		createNodesInParent(rede, 4, 1);
		createNodesInParent(rede, 8, 1);
		createNodesInParent(rede, 12, 1);
		List<Node> nodes = rede.getNodes();

		Interface inf = null;

		inf = new Interface(1, new Pair(nodes.get(1), nodes.get(4)), 0.1);
		nodes.get(1).getInterfaces().add(inf);
		inf = new Interface(1, new Pair(nodes.get(4), nodes.get(1)), 0.1);
		nodes.get(4).getInterfaces().add(inf);

		inf = new Interface(1, new Pair(nodes.get(2), nodes.get(8)), 0.5);
		nodes.get(2).getInterfaces().add(inf);
		inf = new Interface(1, new Pair(nodes.get(8), nodes.get(2)), 0.5);
		nodes.get(8).getInterfaces().add(inf);

		inf = new Interface(1, new Pair(nodes.get(6), nodes.get(9)), 0.1);
		nodes.get(6).getInterfaces().add(inf);
		inf = new Interface(1, new Pair(nodes.get(9), nodes.get(6)), 0.1);
		nodes.get(9).getInterfaces().add(inf);

		inf = new Interface(1, new Pair(nodes.get(7), nodes.get(12)), 0.3);
		nodes.get(7).getInterfaces().add(inf);
		inf = new Interface(1, new Pair(nodes.get(12), nodes.get(7)), 0.3);
		nodes.get(12).getInterfaces().add(inf);

		inf = new Interface(1, new Pair(nodes.get(11), nodes.get(12)), 0.1);
		nodes.get(11).getInterfaces().add(inf);
		inf = new Interface(1, new Pair(nodes.get(12), nodes.get(11)), 0.1);
		nodes.get(12).getInterfaces().add(inf);

//		redeToVector(rede);

		return rede;
	}

	public Node createNetworkWith64Nodes() {

		Node rede = new Node(0, 0, null, null);

		Node[][][] areas = new Node[4][][];

		areas[0] = createNodesInParent16(rede, 0, 1);
		areas[1] = createNodesInParent16(rede, 16, 1);
		areas[2] = createNodesInParent16(rede, 32, 1);
		areas[3] = createNodesInParent16(rede, 48, 1);

		// --------------------------------------------------//
		areas[0][1][1].getInterfaces()
				.add(new Interface(2, new Pair(areas[0][1][1], areas[1][0][0]),
						0.1));

		areas[0][3][3].getInterfaces()
				.add(new Interface(2, new Pair(areas[0][2][2], areas[2][0][0]),
						0.5));
		// --------------------------------------------------//
		areas[1][0][0].getInterfaces()
				.add(new Interface(2, new Pair(areas[1][0][0], areas[0][1][1]),
						0.1));
		areas[1][2][2].getInterfaces()
				.add(new Interface(2, new Pair(areas[1][2][2], areas[2][1][1]),
						0.1));
		areas[1][3][3].getInterfaces()
				.add(new Interface(2, new Pair(areas[1][3][3], areas[3][0][0]),
						0.3));
		// --------------------------------------------------//
		areas[2][0][0].getInterfaces()
				.add(new Interface(2, new Pair(areas[2][0][0], areas[0][2][2]),
						0.5));
		areas[2][1][1].getInterfaces()
				.add(new Interface(2, new Pair(areas[2][1][1], areas[1][2][2]),
						0.1));
		areas[2][3][3].getInterfaces()
				.add(new Interface(2, new Pair(areas[2][3][3], areas[3][0][0]),
						0.1));
		// --------------------------------------------------//
		areas[3][0][0].getInterfaces()
				.add(new Interface(2, new Pair(areas[3][0][0], areas[1][3][3]),
						0.3));
		areas[3][0][0].getInterfaces()
				.add(new Interface(2, new Pair(areas[3][0][0], areas[2][3][3]),
						0.1));

		redeToVector(rede);

		return rede;
	}

	private void redeToVector(Node rede) {
		Node no = null;
		double d[] = null;
		List<Interface> interfaces = null;
		for (int i = 0; i < rede.getNodes().size(); i++) {
			no = rede.getNodes().get(i);
			d = new double[rede.getNodes().size()];
			no.getInterfaces();
			interfaces = no.getInterfaces();
			for (Interface interface1 : interfaces) {
				d[interface1.getPair().getTo().getId()] = interface1.getCost();
			}
			System.out.print("{");
			for (int j = 0; j < rede.getNodes().size(); j++) {
				System.out.print(d[j]);
				if (j < rede.getNodes().size() - 1)
					System.out.print(",");
			}
			System.out.println("},");
		}
	}

	public Node createMultiLevelNetwork() {
		Node rede = new Node(0, 0, null, null);

		Node[] areas = null;
		List<Interface> interfaces = null;

		areas = createNodesInParent(rede, 0, 1);
		createNodesInParent(areas[0], 0, 2);
		createNodesInParent(areas[1], 4, 2);
		createNodesInParent(areas[2], 8, 2);
		createNodesInParent(areas[3], 12, 2);

		// --------------------------------------------------//
		interfaces = areas[0].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[0].getNodes().get(1),
						areas[1].getNodes().get(0)), 0.1));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[0].getNodes().get(2),
						areas[2].getNodes().get(0)), 0.5));
		// --------------------------------------------------//
		interfaces = areas[1].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[1].getNodes().get(0),
						areas[0].getNodes().get(1)), 0.1));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[1].getNodes().get(2),
						areas[2].getNodes().get(1)), 0.1));
		interfaces
				.get(2)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[1].getNodes().get(3),
						areas[3].getNodes().get(0)), 0.3));
		// --------------------------------------------------//
		interfaces = areas[2].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[2].getNodes().get(0),
						areas[0].getNodes().get(2)), 0.5));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[2].getNodes().get(1),
						areas[1].getNodes().get(2)), 0.1));
		interfaces
				.get(2)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[2].getNodes().get(3),
						areas[3].getNodes().get(0)), 0.1));
		// --------------------------------------------------//
		interfaces = areas[3].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[3].getNodes().get(0),
						areas[1].getNodes().get(3)), 0.3));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[3].getNodes().get(0),
						areas[2].getNodes().get(3)), 0.1));

		return rede;
	}

	public Node createMultiLevelNetworkWith64Nodes() {
		Node rede = new Node(0, 0, null, null);

		Node[] areas;
		Interface inf = null;
		List<Interface> interfaces = null;

		areas = createNodesInParent(rede, 0, 1);

		createMultiLevelNetworkWith16Nodes(areas[0], 0);
		createMultiLevelNetworkWith16Nodes(areas[1], 16);
		createMultiLevelNetworkWith16Nodes(areas[2], 32);
		createMultiLevelNetworkWith16Nodes(areas[3], 48);

		// --------------------------------------------------//
		interfaces = areas[0].getInterfaces();

		// Interface 5->16
		inf = new Interface(2, new Pair(areas[0].getNodes().get(1), areas[1]
				.getNodes().get(0)), 0.1);

		inf.getInterfaces().add(
				new Interface(3, new Pair(areas[0].getNodes().get(1).getNodes()
						.get(1), areas[1].getNodes().get(0).getNodes().get(0)),
						0.1));
		interfaces.get(0).getInterfaces().add(inf);

		// Interface 10->32
		inf = new Interface(2, new Pair(areas[0].getNodes().get(2), areas[2]
				.getNodes().get(0)), 0.5);

		inf.getInterfaces().add(
				new Interface(3, new Pair(areas[0].getNodes().get(2).getNodes()
						.get(2), areas[2].getNodes().get(0).getNodes().get(0)),
						0.5));

		interfaces.get(1).getInterfaces().add(inf);

		// --------------------------------------------------//
		interfaces = areas[1].getInterfaces();

		// Interface 16->5

		inf = new Interface(2, new Pair(areas[1].getNodes().get(0), areas[0]
				.getNodes().get(1)), 0.1);

		inf.getInterfaces().add(
				new Interface(3, new Pair(areas[1].getNodes().get(0).getNodes()
						.get(0), areas[0].getNodes().get(1).getNodes().get(1)),
						0.1));

		interfaces.get(0).getInterfaces().add(inf);

		// Interface 26-37

		inf = new Interface(2, new Pair(areas[1].getNodes().get(2), areas[2]
				.getNodes().get(1)), 0.1);

		inf.getInterfaces().add(
				new Interface(3, new Pair(areas[1].getNodes().get(2).getNodes()
						.get(2), areas[2].getNodes().get(1).getNodes().get(1)),
						0.1));

		interfaces.get(1).getInterfaces().add(inf);

		// Interface 31-48

		inf = new Interface(2, new Pair(areas[1].getNodes().get(3), areas[3]
				.getNodes().get(0)), 0.3);

		inf.getInterfaces().add(
				new Interface(3, new Pair(areas[1].getNodes().get(3).getNodes()
						.get(3), areas[3].getNodes().get(0).getNodes().get(0)),
						0.3));

		interfaces.get(2).getInterfaces().add(inf);

		// --------------------------------------------------//
		interfaces = areas[2].getInterfaces();

		// Interface 32->10
		inf = new Interface(2, new Pair(areas[2].getNodes().get(0), areas[0]
				.getNodes().get(2)), 0.5);
		inf.getInterfaces().add(
				new Interface(3, new Pair(areas[2].getNodes().get(0).getNodes()
						.get(0), areas[0].getNodes().get(2).getNodes().get(2)),
						0.5));

		interfaces.get(0).getInterfaces().add(inf);

		// Interface 37->26

		inf = new Interface(2, new Pair(areas[2].getNodes().get(1), areas[1]
				.getNodes().get(2)), 0.1);
		inf.getInterfaces().add(
				new Interface(3, new Pair(areas[2].getNodes().get(1).getNodes()
						.get(1), areas[1].getNodes().get(2).getNodes().get(2)),
						0.1));
		interfaces.get(1).getInterfaces().add(inf);

		// Interface 47->48

		inf = new Interface(2, new Pair(areas[2].getNodes().get(3), areas[3]
				.getNodes().get(0)), 0.1);
		inf.getInterfaces().add(
				new Interface(3, new Pair(areas[2].getNodes().get(3).getNodes()
						.get(3), areas[3].getNodes().get(0).getNodes().get(0)),
						0.1));
		interfaces.get(2).getInterfaces().add(inf);

		// --------------------------------------------------//
		interfaces = areas[3].getInterfaces();
		// Interface 48->31
		inf = new Interface(2, new Pair(areas[3].getNodes().get(0), areas[1]
				.getNodes().get(3)), 0.3);
		inf.getInterfaces().add(
				new Interface(3, new Pair(areas[3].getNodes().get(0).getNodes()
						.get(0), areas[1].getNodes().get(3).getNodes().get(3)),
						0.3));
		interfaces.get(0).getInterfaces().add(inf);

		// Interface 48->47
		inf = new Interface(2, new Pair(areas[3].getNodes().get(0), areas[1]
				.getNodes().get(3)), 0.1);
		inf.getInterfaces().add(
				new Interface(3, new Pair(areas[3].getNodes().get(0).getNodes()
						.get(0), areas[2].getNodes().get(3).getNodes().get(3)),
						0.1));
		interfaces.get(1).getInterfaces().add(inf);

		return rede;
	}

	public Node createMultiLevelNetworkWith16Nodes(Node rede, int childrens) {

		Node[] areas = null;
		List<Interface> interfaces = null;

		int nivel = rede.getLevel();
		areas = createNodesInParent(rede, rede.getId() * 4, nivel + 1);
		createNodesInParent(areas[0], childrens, nivel + 2);
		createNodesInParent(areas[1], childrens + 4, nivel + 2);
		createNodesInParent(areas[2], childrens + 8, nivel + 2);
		createNodesInParent(areas[3], childrens + 12, nivel + 2);

		// --------------------------------------------------//
		interfaces = areas[0].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(nivel + 2, new Pair(areas[0].getNodes().get(
						1), areas[1].getNodes().get(0)), 0.1));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(nivel + 2, new Pair(areas[0].getNodes().get(
						2), areas[2].getNodes().get(0)), 0.5));
		// --------------------------------------------------//
		interfaces = areas[1].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(nivel + 2, new Pair(areas[1].getNodes().get(
						0), areas[0].getNodes().get(1)), 0.1));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(nivel + 2, new Pair(areas[1].getNodes().get(
						2), areas[2].getNodes().get(1)), 0.1));
		interfaces
				.get(2)
				.getInterfaces()
				.add(new Interface(nivel + 2, new Pair(areas[1].getNodes().get(
						3), areas[3].getNodes().get(0)), 0.3));
		// --------------------------------------------------//
		interfaces = areas[2].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(nivel + 2, new Pair(areas[2].getNodes().get(
						0), areas[0].getNodes().get(2)), 0.5));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(nivel + 2, new Pair(areas[2].getNodes().get(
						1), areas[1].getNodes().get(2)), 0.1));
		interfaces
				.get(2)
				.getInterfaces()
				.add(new Interface(nivel + 2, new Pair(areas[2].getNodes().get(
						3), areas[3].getNodes().get(0)), 0.1));
		// --------------------------------------------------//
		interfaces = areas[3].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(nivel + 2, new Pair(areas[3].getNodes().get(
						0), areas[1].getNodes().get(3)), 0.3));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(nivel + 2, new Pair(areas[3].getNodes().get(
						0), areas[2].getNodes().get(3)), 0.1));

		return rede;
	}

	public Node createMultiLevelNetworkWith64NodesBy16() {
		Node rede = new Node(0, 0, null, null);

		Node[] areas = null;
		List<Interface> interfaces = null;

		areas = createNodesInParent(rede, 0, 1);
		createNodesInParent16(areas[0], 0, 2);
		createNodesInParent16(areas[1], 16, 2);
		createNodesInParent16(areas[2], 32, 2);
		createNodesInParent16(areas[3], 48, 2);

		// --------------------------------------------------//
		interfaces = areas[0].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[0].getNodes().get(5),
						areas[1].getNodes().get(0)), 0.1));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[0].getNodes().get(10),
						areas[2].getNodes().get(0)), 0.5));
		// --------------------------------------------------//
		interfaces = areas[1].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[1].getNodes().get(0),
						areas[0].getNodes().get(5)), 0.1));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[1].getNodes().get(10),
						areas[2].getNodes().get(5)), 0.1));
		interfaces
				.get(2)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[1].getNodes().get(15),
						areas[3].getNodes().get(0)), 0.3));
		// --------------------------------------------------//
		interfaces = areas[2].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[2].getNodes().get(0),
						areas[0].getNodes().get(10)), 0.5));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[2].getNodes().get(5),
						areas[1].getNodes().get(10)), 0.1));
		interfaces
				.get(2)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[2].getNodes().get(15),
						areas[3].getNodes().get(0)), 0.1));
		// --------------------------------------------------//
		interfaces = areas[3].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[3].getNodes().get(0),
						areas[1].getNodes().get(15)), 0.3));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[3].getNodes().get(0),
						areas[2].getNodes().get(15)), 0.1));

		return rede;
	}

	public Node createMultiLevelNetworkWith64NodesBy8() {
		Node rede = new Node(0, 0, null, null);

		Node[] areas = new Node[8];
		List<Interface> interfaces = null;

		areas = create8NodesInParent(rede, 0, 1);

		createNodesInParent8Type1(areas[0], 0, 2);
		createNodesInParent8Type2(areas[1], 8, 2);
		createNodesInParent8Type1(areas[2], 16, 2);
		createNodesInParent8Type2(areas[3], 24, 2);
		createNodesInParent8Type1(areas[4], 32, 2);
		createNodesInParent8Type2(areas[5], 40, 2);
		createNodesInParent8Type1(areas[6], 48, 2);
		createNodesInParent8Type2(areas[7], 56, 2);

		// --------------------------------------------------//
		interfaces = areas[0].getInterfaces();

		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[0].getNodes().get(6),
						areas[1].getNodes().get(1)), 0.1));

		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[0].getNodes().get(5),
						areas[2].getNodes().get(0)), 0.1));

		// --------------------------------------------------//
		interfaces = areas[1].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[1].getNodes().get(1),
						areas[0].getNodes().get(6)), 0.1));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[1].getNodes().get(2),
						areas[4].getNodes().get(0)), 0.5));

		// --------------------------------------------------//
		interfaces = areas[2].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[2].getNodes().get(0),
						areas[0].getNodes().get(5)), 0.1));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[2].getNodes().get(6),
						areas[3].getNodes().get(1)), 0.1));

		// --------------------------------------------------//
		interfaces = areas[3].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[3].getNodes().get(1),
						areas[2].getNodes().get(6)), 0.1));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[3].getNodes().get(2),
						areas[4].getNodes().get(5)), 0.1));
		interfaces
				.get(2)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[3].getNodes().get(7),
						areas[6].getNodes().get(0)), 0.3));

		// --------------------------------------------------//
		interfaces = areas[4].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[4].getNodes().get(0),
						areas[1].getNodes().get(2)), 0.5));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[4].getNodes().get(5),
						areas[3].getNodes().get(2)), 0.1));
		interfaces
				.get(2)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[4].getNodes().get(6),
						areas[5].getNodes().get(1)), 0.1));

		// --------------------------------------------------//
		interfaces = areas[5].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[5].getNodes().get(1),
						areas[4].getNodes().get(6)), 0.1));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[5].getNodes().get(7),
						areas[6].getNodes().get(0)), 0.1));

		// --------------------------------------------------//
		interfaces = areas[6].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[6].getNodes().get(0),
						areas[3].getNodes().get(7)), 0.3));
		interfaces
				.get(1)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[6].getNodes().get(0),
						areas[5].getNodes().get(7)), 0.1));
		interfaces
				.get(2)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[6].getNodes().get(6),
						areas[7].getNodes().get(1)), 0.1));
		// --------------------------------------------------//
		interfaces = areas[7].getInterfaces();
		interfaces
				.get(0)
				.getInterfaces()
				.add(new Interface(2, new Pair(areas[7].getNodes().get(1),
						areas[6].getNodes().get(6)), 0.1));

		return rede;
	}

	private Node[][] createNodesInParent16(Node parent, int idNumberInitial,
			int level) {

		Node[][] nodes = new Node[4][];

		nodes[0] = createNodesInParent(parent, idNumberInitial, level);
		nodes[1] = createNodesInParent(parent, idNumberInitial + 4, level);
		nodes[2] = createNodesInParent(parent, idNumberInitial + 8, level);
		nodes[3] = createNodesInParent(parent, idNumberInitial + 12, level);

		Interface inf = new Interface(level,
				new Pair(nodes[0][1], nodes[1][0]), 0.1);
		nodes[0][1].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[0][2], nodes[2][0]), 0.5);
		nodes[0][2].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[1][0], nodes[0][1]), 0.1);
		nodes[1][0].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[1][2], nodes[2][1]), 0.1);
		nodes[1][2].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[1][3], nodes[3][0]), 0.3);
		nodes[1][3].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[2][0], nodes[0][2]), 0.5);
		nodes[2][0].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[2][1], nodes[1][2]), 0.1);
		nodes[2][1].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[2][3], nodes[3][0]), 0.1);
		nodes[2][3].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[3][0], nodes[1][3]), 0.3);
		nodes[3][0].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[3][0], nodes[2][3]), 0.1);
		nodes[3][0].getInterfaces().add(inf);

		return nodes;

	}

	private Node[][] createNodesInParent8Type1(Node parent,
			int idNumberInitial, int level) {

		Node[][] nodes = new Node[2][];

		nodes[0] = createNodesInParent(parent, idNumberInitial, level);
		nodes[1] = createNodesInParent(parent, idNumberInitial + 4, level);

		Interface inf = new Interface(level,
				new Pair(nodes[0][1], nodes[1][0]), 0.1);
		nodes[0][1].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[1][0], nodes[0][1]), 0.1);
		nodes[1][0].getInterfaces().add(inf);

		return nodes;

	}

	private Node[][] createNodesInParent8Type2(Node parent,
			int idNumberInitial, int level) {

		Node[][] nodes = new Node[2][];

		nodes[0] = createNodesInParent(parent, idNumberInitial, level);
		nodes[1] = createNodesInParent(parent, idNumberInitial + 4, level);

		Interface inf = new Interface(level,
				new Pair(nodes[0][3], nodes[1][0]), 0.1);
		nodes[0][3].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[1][0], nodes[0][3]), 0.1);
		nodes[1][0].getInterfaces().add(inf);

		return nodes;

	}

	private Node[] createNodesInParent(Node parent, int idNumberInitial,
			int level) {
		Node[] nodes = new Node[4];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(i + idNumberInitial, level, parent);
			nodes[i].setParent(parent);
		}

		Interface inf = new Interface(level, new Pair(nodes[0], nodes[1]), 0.1);
		nodes[0].getInterfaces().add(inf);
		inf = new Interface(level, new Pair(nodes[0], nodes[2]), 0.5);
		nodes[0].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[1], nodes[0]), 0.1);
		nodes[1].getInterfaces().add(inf);
		inf = new Interface(level, new Pair(nodes[1], nodes[2]), 0.1);
		nodes[1].getInterfaces().add(inf);
		inf = new Interface(level, new Pair(nodes[1], nodes[3]), 0.3);
		nodes[1].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[2], nodes[0]), 0.5);
		nodes[2].getInterfaces().add(inf);
		inf = new Interface(level, new Pair(nodes[2], nodes[1]), 0.1);
		nodes[2].getInterfaces().add(inf);
		inf = new Interface(level, new Pair(nodes[2], nodes[3]), 0.1);
		nodes[2].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[3], nodes[1]), 0.3);
		nodes[3].getInterfaces().add(inf);
		inf = new Interface(level, new Pair(nodes[3], nodes[2]), 0.1);
		nodes[3].getInterfaces().add(inf);

		parent.getNodes().addAll(Arrays.asList(nodes));

		return nodes;
	}

	private Node[] create8NodesInParent(Node parent, int idNumberInitial,
			int level) {
		Node[] nodes = new Node[8];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(i + idNumberInitial, level, parent);
			nodes[i].setParent(parent);
		}

		Interface inf = new Interface(level, new Pair(nodes[0], nodes[1]), 0.1);
		nodes[0].getInterfaces().add(inf);
		inf = new Interface(level, new Pair(nodes[0], nodes[2]), 0.1);
		nodes[0].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[1], nodes[0]), 0.1);
		nodes[1].getInterfaces().add(inf);
		inf = new Interface(level, new Pair(nodes[1], nodes[4]), 0.5);
		nodes[1].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[2], nodes[0]), 0.1);
		nodes[2].getInterfaces().add(inf);
		inf = new Interface(level, new Pair(nodes[2], nodes[3]), 0.1);
		nodes[2].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[3], nodes[2]), 0.1);
		nodes[3].getInterfaces().add(inf);
		inf = new Interface(level, new Pair(nodes[3], nodes[4]), 0.1);
		nodes[3].getInterfaces().add(inf);
		inf = new Interface(level, new Pair(nodes[3], nodes[6]), 0.5);
		nodes[3].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[4], nodes[1]), 0.5);
		nodes[4].getInterfaces().add(inf);
		inf = new Interface(level, new Pair(nodes[4], nodes[3]), 0.1);
		nodes[4].getInterfaces().add(inf);
		inf = new Interface(level, new Pair(nodes[4], nodes[5]), 0.1);
		nodes[4].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[5], nodes[4]), 0.1);
		nodes[5].getInterfaces().add(inf);
		inf = new Interface(level, new Pair(nodes[5], nodes[6]), 0.1);
		nodes[5].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[6], nodes[3]), 0.5);
		nodes[6].getInterfaces().add(inf);
		inf = new Interface(level, new Pair(nodes[6], nodes[5]), 0.1);
		nodes[6].getInterfaces().add(inf);
		inf = new Interface(level, new Pair(nodes[6], nodes[7]), 0.1);
		nodes[6].getInterfaces().add(inf);

		inf = new Interface(level, new Pair(nodes[7], nodes[6]), 0.1);
		nodes[7].getInterfaces().add(inf);

		parent.getNodes().addAll(Arrays.asList(nodes));

		return nodes;
	}

}
