package hnn.models;

import java.util.ArrayList;
import java.util.List;

public class Interface {

	private int level;
	private Pair pair;
	private double cost;
	private boolean enable;
	private List<Interface> interfaces;

	public Interface(int level, Pair pair, double cost) {
		this.level = level;
		this.pair = pair;
		this.cost = cost;
		this.enable = true;
		this.interfaces = new ArrayList<Interface>();
	}

	public Interface(int level, Pair pair, double cost, boolean enable,
			List<Interface> interfaces) {
		this.level = level;
		this.pair = pair;
		this.cost = cost;
		this.enable = enable;
		this.interfaces = interfaces;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((pair.getFrom() == null) ? 0 : pair.getFrom().hashCode());
		result = prime * result
				+ ((pair.getTo() == null) ? 0 : pair.getTo().hashCode());
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
		Interface other = (Interface) obj;
		if (pair.getFrom() == null) {
			if (other.getPair().getFrom() != null)
				return false;
		} else if (!pair.getFrom().equals(other.getPair().getFrom()))
			return false;
		if (pair.getTo() == null) {
			if (other.pair.getTo() != null)
				return false;
		} else if (!pair.getTo().equals(other.pair.getTo()))
			return false;
		return true;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return "xi=" + this.pair.getFrom().getId() + "," + pair.getTo().getId();
	}

	public List<Interface> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<Interface> interfaces) {
		this.interfaces = interfaces;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Interface generatePhysicalInterface() {
		return new PhysicalInterface(level, pair, cost);
	}

	public Pair getPair() {
		return pair;
	}

	public void setPair(Pair pair) {
		this.pair = pair;
	}

}
