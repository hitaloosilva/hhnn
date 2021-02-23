package ga.models;

public class Couple {
	
	private Chromossome father;
	private Chromossome mother;
	
	public Couple () {
		
	}
	
	public Couple (Chromossome _father, Chromossome _mother) {
		this.father = _father;
		this.mother = _mother;
	}
	
	public Chromossome getFather() {
		return father;
	}
	public void setFather(Chromossome father) {
		this.father = father;
	}
	public Chromossome getMother() {
		return mother;
	}
	public void setMother(Chromossome mother) {
		this.mother = mother;
	}
	

}
