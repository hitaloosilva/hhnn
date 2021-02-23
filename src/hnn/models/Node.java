package hnn.models;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {

	private int id;

	private int level;

	private Node parent;

	private List<Interface> interfaces;

	private List<Node> nodes;

	public Node(int id, int level, Node parent) {
		this.id = id;
		this.level = level;
		this.parent = parent;
		this.interfaces = new ArrayList<Interface>();
		this.nodes = new ArrayList<Node>();
	}

	public Node(int id, int level, Node parent, List<Interface> interfaces,
			List<Node> nodes) {
		this.id = id;
		this.level = level;
		this.parent = parent;
		this.interfaces = interfaces;
		this.nodes = nodes;
	}

	public Node(int id, int level, Node parent, List<Interface> interfaces) {
		this.id = id;
		this.level = level;
		this.parent = parent;
		this.interfaces = interfaces;
		this.nodes = new ArrayList<Node>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public List<Interface> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<Interface> interfaces) {
		this.interfaces = interfaces;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	@Override
	public String toString() {
		int parentId = 0;
		if (this.parent != null) {
			parentId = this.parent.getId();
		}
		return String.format("%02d:%02d:%02d", this.level, parentId,
				this.id);
	}

	public Interface getInterfaceByDestination(Node to) {
		Interface result = null;
		for (Interface interface1 : interfaces) {
			if (interface1.getPair().getTo().equals(to)) {
				result = interface1;
				break;
			}
		}
		return result;
	}

	public Node getParentByLevel(int level) {
		Node node = this;
		while (level < node.level) {
			node = node.getParent();
		}
		return node;
	}

	public static Interface createInterfaceEmpty(Pair pair) {
		Interface inf = new Interface(pair.getFrom().getLevel(), pair, 0,
				false, null);
		return inf;
	}

	public void printNode() {
		System.out.println(makeTabs(this) + "L:" + this.level + "-Id:"
				+ this.id);
		System.out.print(makeTabs(this) + "Conected to[");
		if (this.interfaces != null) {
			for (Interface inf : this.interfaces) {
				System.out.print(inf.getPair().getTo() + ",");
			}
		}
		System.out.println("]");
		if (this.nodes != null) {
			for (Node node : this.nodes) {
				node.printNode();
			}
		}
	}

	private String makeTabs(Node node) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < node.level; i++) {
			buf.append("\t");
		}
		return buf.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + level;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (id != other.id)
			return false;
		if (level != other.level)
			return false;
		return true;
	}

	@Override
	public int compareTo(Node o) {
		int retorno = 0;
		if (level < o.level)
			retorno = -1;
		else if (level > o.level)
			retorno = 1;
		else {
			if (id < o.id)
				retorno = -1;
			else if (id > o.id)
				retorno = 1;
		}
		return retorno;
	}

}
