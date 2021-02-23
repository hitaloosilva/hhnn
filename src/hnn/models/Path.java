package hnn.models;

import java.util.ArrayList;
import java.util.List;

public class Path {
	
	private Pair pair;
	private List<Stretch> stretchs;
	private double totalCost;
	
	public Path(Pair pair) {
		this.pair = pair;
		this.stretchs = new ArrayList<Stretch>();
		this.setTotalCost(0.0);
	}
	
	public Path(Pair pair, List<Stretch> stretchs) {
		this.pair = pair;
		this.stretchs = stretchs;
	}
	
	public void addStretch(Stretch stretch){
		if(stretch != null){
			stretchs.add(stretch);
			setTotalCost(getTotalCost() + stretch.getCost());
		}
	}
	
	public Pair getPair() {
		return pair;
	}
	public void setPair(Pair pair) {
		this.pair = pair;
	}
	public List<Stretch> getStretchs() {
		return stretchs;
	}
	public void setStretchs(List<Stretch> stretchs) {
		this.stretchs = stretchs;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

}
