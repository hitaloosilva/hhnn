package hnn.models;

import java.util.List;

public class PhysicalInterface extends Interface {

	private int wavelengthNumber;

	public PhysicalInterface(int level, Pair pair, double cost) {
		super(level, pair, cost);
		this.setWavelengthNumber(100);
	}

	public PhysicalInterface(int level, Pair pair, double cost, boolean enable,
			List<Interface> interfaces, int wavelengthNumber) {
		super(level, pair, cost, enable, interfaces);
		this.setWavelengthNumber(wavelengthNumber);
	}

	public int getWavelengthNumber() {
		return wavelengthNumber;
	}

	public void setWavelengthNumber(int wavelengthNumber) {
		this.wavelengthNumber = wavelengthNumber;
	}

}
