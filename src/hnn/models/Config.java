package hnn.models;

public class Config {

	private int u[];
	private double A;
	private double B;
	private double C;
	private double slope;
	private int biasFunctionType;
	private int energyFunctionType;
	private int synapseFunctionType;

	public Config(int[] u, double a, double b, double c, double slope, int biasFunctionType,
			int energyFunctionType, int synapseFunctionType) {
		this.u = u;
		A = a;
		B = b;
		C = c;
		this.setSlope(slope);
		this.biasFunctionType = biasFunctionType;
		this.energyFunctionType = energyFunctionType;
		this.synapseFunctionType = synapseFunctionType;
	}

	public int[] getU() {
		return u;
	}

	public void setU(int[] u) {
		this.u = u;
	}

	public double getA() {
		return A;
	}

	public void setA(double a) {
		A = a;
	}

	public double getB() {
		return B;
	}

	public void setB(double b) {
		B = b;
	}

	public double getC() {
		return C;
	}

	public void setC(double c) {
		C = c;
	}

	public int getBiasFunctionType() {
		return biasFunctionType;
	}

	public void setBiasFunctionType(int biasFunctionType) {
		this.biasFunctionType = biasFunctionType;
	}

	public int getEnergyFunctionType() {
		return energyFunctionType;
	}

	public void setEnergyFunctionType(int energyFunctionType) {
		this.energyFunctionType = energyFunctionType;
	}

	public int getSynapseFunctionType() {
		return synapseFunctionType;
	}

	public void setSynapseFunctionType(int synapseFunctionType) {
		this.synapseFunctionType = synapseFunctionType;
	}

	public double getSlope() {
		return slope;
	}

	public void setSlope(double slope) {
		this.slope = slope;
	}

}
